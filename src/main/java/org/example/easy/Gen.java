package org.example.easy;


import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableMap;
import com.squareup.okhttp.Response;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.JsInServer;
import org.example.easy.gene.*;
import org.example.easy.parser.BlockType;
import org.example.easy.parser.Unit;
import org.example.easy.parser.Var;
import org.example.easy.shepherd.*;
import org.example.easy.sql.SqlParser;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.example.easy.sql.SqlParser.parseSql;

// 首先描述三个概念，
// 1. layers：定义了层级结构及核心文件，
// 2. methods，定义了层级结构文件下的方法，穿透layers，
//    注意 methods 是于 layers 强绑定的存在，不存在 layers 变了，methods不用重新定义的情况
// 3. project，赋予 layers 的path一系列变量以真实值
// 4. service，此service与经典三层layers中的 service 不同，此 service 是一个实际实现，如 ServiceName: "Rule"
/**
 *
 * @Author: zjh
 */
@Slf4j
public class Gen {
    @SneakyThrows
    public static String resourceReadAll(String str) {
        InputStream inputStream = Gen.class.getClassLoader().getResourceAsStream(str);
        Objects.requireNonNull(inputStream);
        long filelength = inputStream.available();
        Charset encoding = StandardCharsets.UTF_8;
        byte[] filecontent = new byte[(int) filelength];
        try {
            inputStream.read(filecontent);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("fileReadAll");
        }
        return new String(filecontent, encoding);
    }

    public static String getPartStr(String s, String name) {
        Map map = (Map) ((Map) JsonKits.parse(s, Object.class)).get(name);
        return JsonKits.toJson(map);
    }

    public static void fillMap(Map toFill, Map data, boolean excludeObj) {
        if (data == null) {
            return;
        }
        for (Object ent : data.entrySet()) {
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>) ent;
            String key = entry.getKey();
            Object value = entry.getValue();
            if (excludeObj && (value instanceof List || value instanceof Map)) {
                continue;
            }
            toFill.put(key, value);
        }
    }

    @SneakyThrows
    public static void main(String[] args) throws IOException {
        // TODO: 2023/3/30 改下面这几个
////        ------------------
//        // eagle
//        String tplRoot = "classicTpl";
//        String tpl = "threeLayers";
//
//        String project = "eagle";
//        String method = "stu";

                // dubbo
        String tplRoot = "classicTpl";
        String tpl = "threeLayersDubbo";

        String project = "dubboMyServer";
        String method = "stu";
//        ------------------

        // 读取模板
        String layersStr = getPartStr(JsInServer.run(
                String.format("tpl/%s/%s/config.js", tplRoot, tpl),
                "getLayers"),
                "default");
        String projectsStr = getPartStr(JsInServer.run(
                        String.format("tpl/%s/project.js", tplRoot),
                        "getProjects"),
                project);
        String servicesStr = getPartStr(JsInServer.run(
                String.format("tpl/%s/%s/methods.js", tplRoot, tpl),
                "getServices"),
                method);

        Map layers = (Map) JsonKits.parse(layersStr, Object.class);
        Map projectMap = (Map) JsonKits.parse(projectsStr, Object.class);
        Map service = (Map) JsonKits.parse(servicesStr, Object.class);
        React tree = new React(new LinkedHashMap<>());

        tree.set("TPL", String.format("%s/%s", tplRoot, tpl));

        layers.forEach((k, v) -> {
            tree.set((String)k, v);
        });
        projectMap.forEach((k, v) -> {
            tree.set((String)k, v);
        });
        service.forEach((k, v) -> {
            tree.set((String)k, v);
        });

        Pattern pattern = Pattern.compile("\\$\\{([^}]+)\\}");
        Matcher matcher = pattern.matcher(layersStr);
        while (matcher.find()) {
            String match = matcher.group(1);
            if (tree.getExpose(match) != null) {
                layersStr = layersStr.replace(String.format("${%s}", match), (String) tree.getExpose(match));
            }
        }

        Map layersAssigned = (Map) JsonKits.parse(layersStr, Object.class);
        layersAssigned.forEach((k, v) -> {
            tree.set((String)k, v);
        });

        generate(tree);
    }


    @SneakyThrows
    public static void writeFile(String filepath, String content) {
        assureFileExists(filepath);
        BufferedWriter out = new BufferedWriter(new FileWriter(filepath));
        out.write(content);
        out.close();
    }

    public static void assureFileExists(String filepath) {
        Objects.requireNonNull(filepath);
        int i = filepath.lastIndexOf("/");
        String dirpath = filepath.substring(0, i);
        File dir = new File(dirpath);
        // 创建文件夹
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 创建文件
        File file = new File(filepath);
        if (!file.exists()) {
            boolean newFile = false;
            try {
                newFile = file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("文件不存在, 创建新文件, filepath: %s, 创建结果: %s%n", filepath, newFile);
        }
    }

    public static List selectLayer(List layers, List keys, Function<Object, Object> mapper) {
        Map collect = (Map) layers.stream().collect(Collectors.toMap(mapper, x -> x));
        List<Object> list = new ArrayList<>();
        for (Object key : keys) {
            if (collect.get(key) == null) {
                throw new RuntimeException();
            }
            list.add(collect.get(key));
        }
        return list;
    }

    public static Object selectMethod(List methods, String key, Function<Object, Object> mapper) {
        return selectLayer(methods, Collections.singletonList(key), mapper).get(0);
    }

    public static void generate(React tree) {
        List<Unit> units = new ArrayList<>();
        List methods = tree.getExpose("methods");
        for (Object m : methods) {
            React render = new React(new LinkedHashMap<>());
            fillMap(render.exposeMap(), tree.exposeMap(), true);
            React method = new React(m);
            fillMap(render.exposeMap(), method.exposeMap(), true);

            // 解析 SQL
            if (method.getExpose("sql") != null) {
                SqlParser.SqlParseRes sqlParseRes = parseSql(Objects.requireNonNull(method.getExpose("sql")));
                GoodUtils.setRecursive(render.exposeMap(), Collections.emptyList(), "sqlParsed", sqlParseRes);
            }

            // 选定方法模板
            Map methodTpl = (Map) selectMethod(tree.getExpose("methodTpls"), method.getExpose("type"),
                    x -> new React(x).getExpose("type"));
            fillMap(render.exposeMap(), methodTpl, true);
            List mode = new React(methodTpl).getExpose("mode");
            // 根据方法中的 mode选定层级
            List layers = selectLayer(tree.getExpose("layers"), mode, x -> new React(x).getExpose("layerName"));
            React methodLayers = new React(methodTpl.get("methodLayers"));
            React methodImplLayers = method.get("methodLayers");
            if (methodLayers.expose() == null) {
                methodLayers = new React(new LinkedHashMap<>());
            }
            if (methodImplLayers.expose() == null) {
                methodImplLayers = new React(new LinkedHashMap<>());
            }
            List<Unit> layerImports = new ArrayList<>();
            for (int i = layers.size() - 1; i >= 0; i--) {
                React layer = new React(layers.get(i));
                renderLayerWithDepends(
                        tree,
                        layer,
                        methodLayers.get((String) mode.get(i)),
                        methodImplLayers.get((String) mode.get(i)),
                        method,
                        units,
                        render,
                        layerImports);
            }

            for (Unit unit : units) {
                try {
                    unit.writeDown();
                } catch (Exception e) {
                    System.out.println("------");
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean shouldSkip(React dependTpl, React render) {
        if (dependTpl.getExpose("on") != null) {
            String script = dependTpl.getExpose("on");
            Binding binding = new Binding();
            render.exposeMap().forEach((k, v) -> {
                binding.setVariable((String) k, v);
            });
            GroovyShell shell = new GroovyShell(binding);
            Object result = shell.evaluate(script);
            if (!Boolean.TRUE.equals(result)) {
                return true;
            }
        }
        return false;
    }

    private static void renderLayerWithDepends(React tree,
                                               React layer,
                                               React methodLayer,
                                               React methodImplLayer,
                                               React method,
                                               List<Unit> units,
                                               React render,
                                               List<Unit> layerImports) {
        List<Unit> curLayerUnits = new ArrayList<>();
        String layerName = layer.getExpose("layerName");
        List<String> list = new ArrayList<>();
        list.add(layerName);

        // 设置类型转换信息，从层级上处理
        if (layer.getExpose("typeinfo") != null) {
            Map convertMap = new LinkedHashMap();
            List typeinfos = layer.getExpose("typeinfo");
            // 优先转换specific field
            for (Object t : typeinfos) {
                React typeinfo = new React(t);
                if (!"field".equals(typeinfo.getExpose("specifyType"))) {
                    continue;
                }
                Map<String, Var> fields = render.getExpose("__BASE");
                String specify = typeinfo.getExpose("specify");
                Var var = fields.get(specify);
                Objects.requireNonNull(var);
                if (specify.equals(var.getType().name)) {
                    continue;
                }
                convertMap.put(specify, Arrays.asList(var.getType().name, typeinfo.getExpose("type")));
                var.type.name = typeinfo.getExpose("type");
            }
            // 之后转换specific type
            for (Object t : typeinfos) {
                React typeinfo = new React(t);
                if (!"type".equals(typeinfo.getExpose("specifyType"))) {
                    continue;
                }
                Map<String, Var> fields = render.getExpose("__BASE");
                String specify = typeinfo.getExpose("specify");
                for (Map.Entry<String, Var> entry : fields.entrySet()) {
                    Var var = entry.getValue();
                    if (entry.getValue().getType().name.equals(specify)) {
                        convertMap.put(entry.getKey(), Arrays.asList(var.getType().name, typeinfo.getExpose("type")));
                        var.type.name = typeinfo.getExpose("type");
                    }
                }
            }
            render.set("convertMap", convertMap);
        }

        // 1.实例化layer的 depends
        List depends = layer.getExpose("depends");
        if (!CollectionUtils.isEmpty(depends)) {
            // 倒序实例化 layers 的模板
            for (int i = depends.size() - 1; i >= 0; i--) {
                React depend = new React(depends.get(i));
                Object dependName = depend.getExpose("dependName");
                Map couldDepends = layer.getExpose("couldDepends");
                React dependTpl = new React(couldDepends.get(dependName));
                Objects.requireNonNull(dependTpl.expose());
                if (shouldSkip(depend, render)) {
                    continue;
                }
                List<String> curNest = GoodUtils.addInNewList(list, (String) dependName);
                Unit.renderUnit(layer, methodLayer, methodImplLayer, method, tree, dependTpl, render, curNest, units, curLayerUnits,
                        null);
            }
        }

        // 2.再实例化 method 的 depends
        List depends2 = methodLayer.getExpose("depends");
        if (!CollectionUtils.isEmpty(depends2)) {
            // 倒序实例化 layers 的模板
            for (int i = depends2.size() - 1; i >= 0; i--) {
                React depend = new React(depends2.get(i));
                Object dependName = depend.getExpose("dependName");
                Map couldDepends = layer.getExpose("couldDepends");
                React dependTpl = new React(couldDepends.get(dependName));
                if (dependTpl.expose() == null) {
                    dependTpl = methodLayer.get("couldDepends").get((String) dependName);
                }
                Objects.requireNonNull(dependTpl.expose());
                if (shouldSkip(depend, render)) {
                    continue;
                }
                List<String> curNest = GoodUtils.addInNewList(list, (String) dependName);
                Unit.renderUnit(layer, methodLayer, methodImplLayer, method, tree, dependTpl, render, curNest, units, curLayerUnits,
                        null);
            }
        }

        // 导入当前层和下一层的类
        layerImports.addAll(curLayerUnits);
        if (layer.getExpose("name") == null && layer.getExpose("interName") == null) {
            log.info("该层没有定义核心文件name, layerName: {}", layerName);
        } else {
            Unit.renderUnit(layer, methodLayer, methodImplLayer, method, tree, layer, render, list, units, curLayerUnits, layerImports);
        }
        layerImports.clear();
        layerImports.addAll(curLayerUnits);
        render.exposeMap().remove("convertMap");
    }

    private static Map<String, Object> genEnumMap(InterParam interParam) {
        StringBuilder enumItems = new StringBuilder();
        StringBuilder enumFields = new StringBuilder();
        List<String> enumCons = new ArrayList<>();
        StringBuilder enumSet = new StringBuilder();

        for (List<Object> item : interParam.items) {
            String name = (String) item.get(0);
            List<String> args = new ArrayList<>();
            for (int i = 1; i < item.size(); i++) {
                Object o = item.get(i);
                if (o == null) {
                    args.add("null");
                } else if (o.getClass() == String.class) {
                    args.add(String.format("\"%s\"", o));
                } else {
                    args.add(o.toString());
                }
            }
            String format = String.format("    %s(%s),\n", name, StringUtils.join(args, ", "));
            enumItems.append(format);
        }

        for (Gather.Col col : interParam.cols) {
            String field = String.format("" +
                            "    @Getter\n" +
                            "%s" +
                            "    private final %s %s;\n",
                    col.nullable ? "    @Nullable\n" : "",
                    col.clazz.getSimpleName(), col.name);
            enumFields.append(field);
            enumCons.add(String.format("%s%s %s", col.nullable ? "@Nullable " : "", ((Class<?>) col.clazz).getSimpleName(), col.name));
            enumSet.append(String.format("        this.%s = %s;\n", col.name, col.name));
        }

        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("enumName", interParam.name);
        map.put("enumItems", enumItems.toString().trim());
        map.put("enumFields", enumFields.toString().trim());
        map.put("enumCons", StringUtils.join(enumCons, ", "));
        map.put("enumSet", enumSet.toString().trim());
        return map;
    }

    @SneakyThrows
    public static String formatTplFile(String filename, Map render) {
        return formatTplFileByMap(filename, render);
    }

    @SneakyThrows
    public static String formatTplFileByMap(String filename, Map data) {
        String tpl = resourceReadAll("tpl/" + filename);
        return formatTpl(tpl, data);
    }

    @SneakyThrows
    public static String formatTpl(String tpl, Map data) {
        /* 在整个应用的生命周期中，这个工作你应该只做一次。 */
        /* 创建和调整配置。 */
        Configuration cfg = new Configuration();
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("utils", resourceReadAll("tpl/utils.txt"));
        stringLoader.putTemplate("myTemplate", tpl);
        cfg.setTemplateLoader(stringLoader);

        Template temp = cfg.getTemplate("myTemplate", "utf-8");
        Writer out = new StringWriter(2000);
        temp.process(data, out);
        out.flush();
        return out.toString();
    }

    public static String extractDoContent(String content) {
        int i = content.indexOf("{");
        Assert.isTrue(i > 0);
        int end = content.indexOf("private static final long ");
        Assert.isTrue(end > 0 && end > i);
        List<String> lines = Arrays.stream(content.substring(i, end).trim().split("\n")).filter(x -> !x.contains("*") && !x.contains("{")).collect(Collectors.toList());
        return StringUtils.join(lines, "\n");
    }


    public static String fileReadAll(String fileName) {
        File file = new File(fileName);
        Charset encoding = StandardCharsets.UTF_8;
        long filelength = file.length();
        byte[] filecontent = new byte[(int) filelength];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (IOException e) {
            String errMsg = String.format("读取文件失败, fileName: %s", fileName);
            throw new RuntimeException(errMsg, e);
        }
        return new String(filecontent, encoding);
    }


    @SneakyThrows
    public static File createFile(String name) {
        File file = new File(name);
        if (file.createNewFile()) {
            System.out.println("文件创建成功！");
        } else {
            System.out.println("出错了，该文件已经存在。");
        }
        return file;
    }

    public static void deleteFile(String filename) {
        try {
            File file = new File(filename);
            if (file.delete()) {
                System.out.println(file.getName() + " 文件删除成功");
            } else {
                System.out.println("文件删除失败");
            }
        } catch (Exception e) {
//            log.info("", e);
        }
    }

    public static void getInterfaceInfo(Class clazz) {
        try {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            List<CallInfoParam> methodList = new ArrayList<>();
            OptionReq<CallInfoParam> optionReq = new OptionReq<>();
            for (Method method : declaredMethods) {
                Parameter[] parameters = method.getParameters();
                CallInfoParam infoParam = new CallInfoParam();
                LinkedHashMap<String, Object> args = new LinkedHashMap<>();
                for (Parameter parameter : parameters) {
                    Object o = BeanMaker.easySafeMake(parameter.getType());
                    args.put(parameter.getType().getTypeName(), o);
                }
                infoParam.clazzName = clazz.getCanonicalName();
                infoParam.methodName = method.getName();
                infoParam.args = args;
                methodList.add(infoParam);
            }

            CallInfoParam exportParam = new CallInfoParam();
            exportParam.clazzName = "export";
            LinkedHashMap<String, Object> exportArgs = new LinkedHashMap<>();
            exportArgs.put("req", new HashMap());
            exportArgs.put("exportType", 1);
            exportParam.setArgs(exportArgs);
            methodList.add(exportParam);

            optionReq.setOptions(new ArrayList<>());
            LinkedHashMap<String, CallInfoParam> linkedHashMap = new LinkedHashMap<>();
            for (int i = 0; i < methodList.size(); i++) {
                CallInfoParam p = methodList.get(i);
                String key = String.valueOf(i);
                if (p.clazzName.equals("export")) {
                    key = "z";
                }
                linkedHashMap.put(key, p);
            }
            optionReq.setOptions(new ArrayList<>());
            optionReq.setReqs(linkedHashMap);
            ImmutableMap<String, OptionReq<CallInfoParam>> map = ImmutableMap.of(clazz.getSimpleName(), optionReq);
            System.out.println(JsonKits.toJson(map));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Data
    @Builder
    public static class ClassInfo {
        String className;
        List<MethodInfo> methodInfos;
    }

    @Data
    public static class MethodInfo {
        private String methodName;
        private List<ParamInfo> paramInfoList;
    }

    @Data
    public static class ParamInfo {
        private String paramType;
        private Object paramInstance;
    }

    public static React getReact(String str) {
        return new React(JsonKits.parse(str, Object.class));
    }

    public static String reactJson(React react) {
        return JsonKits.toJson(react.expose());
    }

    @SneakyThrows
    public static React checkOk(Response response) {
        Assert.isTrue(response.code() == 200);
        String string = response.body().string();
        React react = new React(JsonKits.parse(string, Object.class));
        Assert.isTrue(react.get("code").expose().equals(0));
        return react;
    }
}
