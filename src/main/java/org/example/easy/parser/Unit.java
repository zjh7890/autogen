package org.example.easy.parser;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.example.easy.Gen;
import org.example.easy.GoodUtils;
import org.example.easy.JsonKits;
import org.example.easy.React;
import org.example.easy.gene.GenConfig;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.example.easy.Gen.*;

/**
 * @Author: zjh
 */
@NoArgsConstructor
@Slf4j
public class Unit {
    // input
    public String input;
    public String clazzName;
    public String filename;

    // output
    public String packName;
    public ImportBlock importBlock;
    public Var clazz;

    public static Unit buildByFileName(String filename) {
        return buildWhenFileNotExits(filename, Gen.fileReadAll(filename));
    }

    public void writeDown() {
        Gen.writeFile(this.filename, this.input);
    }

    public static void setThree(React render,
                                React scoped,
                                List<String> outNested,
                                String key,
                                Object value) {

    }

    public static void renderUnit(React layer,
                                  React methodLayer,
                                  React methodImplLayer,
                                  React method,
                                  React tree,
                                  React tpl,
                                  React render,
                                  List<String> outNested,
                                  List<Unit> units,
                                  List<Unit> curLayerUnits,
                                  List<Unit> toImports) {
        GoodUtils.defaultRecursiveMap(render.exposeMap(), outNested);
        // 填充layer
        fillMap(GoodUtils.getRecursiveMap(render.exposeMap(), outNested), layer.exposeMap(), true);
        fillMap(GoodUtils.getRecursiveMap(render.exposeMap(), outNested), methodLayer.exposeMap(), true);
        fillMap(GoodUtils.getRecursiveMap(render.exposeMap(), outNested), methodImplLayer.exposeMap(), true);
        fillMap(GoodUtils.getRecursiveMap(render.exposeMap(), outNested), method.exposeMap(), true);

        String tplName = tpl.getExpose("tpl");
        String interTplName = tpl.getExpose("interTpl");
        String filepath = null;
        if (tpl.getExpose("name") != null) {

            String path = tpl.getExpose("path");
            String dir = Objects.requireNonNull(tree.getExpose("PROJECT_PATH")).toString() +
                    Objects.requireNonNull(path).toString();
            Assert.isTrue(dir != null, String.format("路径没配吧, tpl: %s", (Object) tpl.expose()));
            String name = tpl.getExpose("name");
            Objects.requireNonNull(name);
            filepath = dir + "/" + name + ".java";
            String className = filepath.replaceAll(".+/(.*)\\.java", "$1");

            GoodUtils.setRecursive(
                    render.exposeMap(), outNested, "name", className);

            // 获取包名
            for (String s : GenConfig.PACK_NAME_START) {
                if (filepath.contains(s)) {
                    GoodUtils.setRecursive(
                            render.exposeMap(), outNested, "packName", filepath.replaceAll(String.format(".+(%s)(.+)/(.*)\\.java", s),
                                    "$1$2").replace("/", "."));
                }
            }
        }
        String interFilepath = null;
        if (tpl.getExpose("interName") != null) {
            String interPath = tpl.getExpose("interPath");
            String interDir = Objects.requireNonNull(tree.getExpose("PROJECT_PATH")).toString() +
                    Objects.requireNonNull(interPath).toString();
            Assert.isTrue(interDir != null, String.format("路径没配吧, tpl: %s", (Object) tpl.expose()));
            String interName = tpl.getExpose("interName");
            Objects.requireNonNull(interName);
            interFilepath = interDir + "/" + interName + ".java";
            String interClassName = interFilepath.replaceAll(".+/(.*)\\.java", "$1");

            GoodUtils.setRecursive(
                    render.exposeMap(), outNested, "interName", interClassName);

            // 获取包名
            for (String s : GenConfig.PACK_NAME_START) {
                if (interFilepath.contains(s)) {
                    GoodUtils.setRecursive(
                            render.exposeMap(), outNested, "interPackName", interFilepath.replaceAll(String.format(".+(%s)(.+)/(.*)\\.java", s),
                                    "$1$2").replace("/", "."));
                }
            }
        }

        // 去除exclude
        if (render.getExpose("__BASE") != null && render.getExpose("exclude") != null) {
            List<String> excludeList = render.getExpose("exclude");
            Map<String, Var> fields = render.getExpose("__BASE");
            for (String s : excludeList) {
                fields.remove(s);
            }
        }

        // scope 深拷贝一份

        React renderScoped = new React(JsonKits.parse(JsonKits.toJson(render.expose()), Object.class));
        Map map = GoodUtils.getRecursiveMap(renderScoped.exposeMap(), outNested);
        for (Object o : map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            renderScoped.set((String) entry.getKey(), entry.getValue());
        }
        Unit mainUnit = null;
        if (tpl.getExpose("name") != null) {
            Unit tplUnit = null;
            if (tplName != null) {
                String input;
                try {
                    input = formatTplFile(tplName, renderScoped.expose());
                } catch (Exception e) {
                    String errMsg = String.format("渲染模板出错，层级: %s, tplName: %s", outNested, tplName);
                    throw new RuntimeException(errMsg, e);
                }
                tplUnit = buildWhenFileNotExits(filepath, input);
                importByTplContent(tplName, tplUnit, renderScoped);
            }
            if (tplUnit == null && !new File(filepath).exists()) {
                throw new RuntimeException();
            }

            if (!new File(filepath).exists()) {
                mainUnit = tplUnit;
            } else {
                mainUnit = buildByFileName(filepath);
                mergeIntoUnit(mainUnit, tplUnit);
            }
            units.add(mainUnit);
//            mainUnit.importBlock.importUnits(toImports);

            LinkedHashMap<String, Var> fields = new LinkedHashMap<>();
            if (Boolean.TRUE.equals(tpl.getExpose("__BASE"))) {
                mainUnit.clazz.vars.stream().filter(x -> !x.ifStatic && !x.ifFinal).forEach(x -> {
                    fields.put(x.name, x);
                });
                render.set("__BASE", fields);
            }
        }

        Unit interUnit = null;
        if (tpl.getExpose("interName") != null) {
            Unit interTplUnit = null;
            if (interTplName != null) {
                String input;
                try {
                    input = formatTplFile(interTplName, renderScoped.expose());
                } catch (Exception e) {
                    String errMsg = String.format("渲染模板出错，层级: %s, interTplName: %s", outNested, tplName);
                    throw new RuntimeException(errMsg, e);
                }
                interTplUnit = buildWhenFileNotExits(interFilepath, input);
                // 自动import
                if (tplName != null) {
                    importByTplContent(tplName, interTplUnit, renderScoped);
                }
            }

            if (interTplUnit == null && !new File(interFilepath).exists()) {
                throw new RuntimeException();
            }
            if (!new File(interFilepath).exists()) {
                interUnit = interTplUnit;
            } else {
                interUnit = buildByFileName(interFilepath);
                mergeIntoUnit(interUnit, interTplUnit);
            }

            units.add(interUnit);
            if (mainUnit != null) {
                // 抽象出接口
                mainUnit.importBlock.importUnit(interUnit);
                for (Var func : mainUnit.clazz.funcs) {
                    if (func.ifStatic || func.accessType != AccessType.PUBLIC) {
                        continue;
                    }
                    List<String> argTypes = func.args.stream().map(x -> x.type.name).collect(Collectors.toList());
                    interUnit.replaceElementOrAdd(BlockType.FUNC, func.name, func.exposeInterface(), argTypes,
                            func.docToken != null ? func.docToken.content : null);
                }
            }
//            interUnit.importBlock.importUnits(toImports);
        }

        // 如果抽象了接口，用接口作为引入
        if (interUnit == null) {
            curLayerUnits.add(mainUnit);
        } else {
            curLayerUnits.add(interUnit);
        }

    }

    private static void mergeIntoUnit(Unit mainUnit, Unit tplUnit) {
        if (tplUnit != null) {
            mainUnit.importBlock.importBlock(tplUnit.importBlock);
            for (Var var : tplUnit.clazz.vars) {
                mainUnit.replaceElementOrAdd(
                        BlockType.VAR,
                        var.name,
                        tplUnit.input.substring(var.startPos, var.endPos),
                        new ArrayList<>(),
                        var.docToken != null ? var.docToken.content : null);
            }
            for (Var func : tplUnit.clazz.funcs) {
                List<String> argTypes = func.args.stream().map(x -> x.type.name).collect(Collectors.toList());
                mainUnit.replaceElementOrAdd(
                        BlockType.FUNC,
                        func.name,
                        tplUnit.input.substring(func.startPos, func.endPos),
                        argTypes,
                        func.docToken != null ? func.docToken.content : null);
                if (mainUnit.input.contains("}}")) {
                    System.out.println("hit");
                }
            }
            for (Var clazz : tplUnit.clazz.clazzes) {
                mainUnit.replaceElementOrAdd(
                        BlockType.FUNC,
                        clazz.name,
                        tplUnit.input.substring(clazz.startPos, clazz.endPos),
                        new ArrayList<>(),
                        clazz.docToken != null ? clazz.docToken.content : null);
            }
            System.out.println(tplUnit);
        }
    }

    public static void importByTplContent(String tpl, Unit unit, React renderScoped) {
        String content = resourceReadAll("tpl/" + tpl);
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String match = matcher.group(1);
            String[] subs = match.split("\\.");
            if (subs.length <= 1) {
                continue;
            }
            String[] nest = Arrays.copyOfRange(subs, 0, subs.length - 1);
            if (!subs[subs.length - 1].equals("name")
                    && !subs[subs.length - 1].equals("interName")) {
                continue;
            }
            Map map = GoodUtils.getRecursiveMap(renderScoped.exposeMap(), Arrays.asList(nest));
            if (map == null) {
                continue;
            }
            if (map.containsKey("packName") || map.containsKey("interPackName")) {
                if (subs[subs.length - 1].equals("name")) {
                    Objects.requireNonNull(map.get("packName"));
                    Objects.requireNonNull(map.get("name"));
                    unit.importBlock.importName(
                            String.format("%s.%s", map.get("packName"), map.get("name")));
                } else {
                    Objects.requireNonNull(map.get("interPackName"));
                    Objects.requireNonNull(map.get("interName"));
                    unit.importBlock.importName(
                            String.format("%s.%s", map.get("interPackName"), map.get("interName")));
                }
            }
        }
    }

    public static Unit buildWhenFileNotExits(String filename, String input) {
        Unit unit = new Unit();
        unit.input = input;
        unit.filename = filename;
        int i = filename.lastIndexOf("/");
        unit.clazzName = filename.substring(i + 1).split("\\.")[0];
        try {
            unit.compile();
        } catch (Exception e) {
            Gen.writeFile(
                    GenConfig.ERR_LOG_PATH
                    ,
                    unit.input);
            throw e;
        }
        return unit;
    }

    public void addString(BlockType type, String segment) {
        Assert.isTrue(type != null);
        Assert.isTrue(clazz != null);
        if (type == BlockType.VAR) {
            if (CollectionUtils.isNotEmpty(clazz.vars)) {
                appendToNode(clazz.vars.get(clazz.vars.size() - 1), segment);
            } else {
                addToHead(segment);
            }
        } else if (type == BlockType.FUNC) {
            if (CollectionUtils.isNotEmpty(clazz.funcs)) {
                appendToNode(clazz.funcs.get(clazz.funcs.size() - 1), segment);
            } else if (CollectionUtils.isNotEmpty(clazz.vars)) {
                appendToNode(clazz.vars.get(clazz.vars.size() - 1), segment);
            } else {
                addToHead(segment);
            }
        } else if (type == BlockType.CLAZZ) {
            if (CollectionUtils.isNotEmpty(clazz.clazzes)) {
                appendToNode(clazz.clazzes.get(clazz.clazzes.size() - 1), segment);
            } else if (CollectionUtils.isNotEmpty(clazz.funcs)) {
                appendToNode(clazz.funcs.get(clazz.funcs.size() - 1), segment);
            } else if (CollectionUtils.isNotEmpty(clazz.vars)) {
                appendToNode(clazz.vars.get(clazz.vars.size() - 1), segment);
            } else {
                addToHead(segment);
            }
        } else {
            Assert.isTrue(false);
        }
    }

    public void addToHead(String segment) {
        int i = input.indexOf("{");
        Assert.isTrue(i != -1);
        StringBuilder builder = new StringBuilder(input);
        builder.insert(i + 1, "\n    " + segment + "\n\n");
        input = builder.toString();
    }

    public void appendToNode(Var block, String segment) {
        StringBuilder builder = new StringBuilder(input);
        StringBuilder insert = builder.insert(block.endPos + 1, "\n    " + segment + "\n");
        input = insert.toString();
    }

    public void replaceElementOrAdd(BlockType type, String name, String segment, List<String> args, String docContent) {
        Var block = clazz.findByTypeAndName(type, name, args);
        if (block == null) {
            addString(type, segment);
        } else {
            if (block.docToken == null && docContent != null && !StringUtils.isEmpty(docContent.trim())) {
                String withDoc =
                        "%s\n" +
                        "    %s";
                String content = String.format(withDoc, docContent, segment);
                input = replace(input, block.startPos, block.endPos - 1, content);
            } else {
                input = replace(input, block.startPos, block.endPos - 1, segment);
            }
        }

        try {
            compile();
        } catch (Exception e) {
            Gen.writeFile(
                    GenConfig.ERR_LOG_PATH,
                    input);
            throw e;
        }

    }

    public void replaceImport() {
        input = replace(input, importBlock.start, importBlock.end - 1, importBlock.print());
        compile();
    }

    public static String trimChar(String str, char c) {
        char[] chars = str.toCharArray();
        int len = chars.length;
        int st = 0;
        while ( (st < len) && (chars[st] == c) ){
            st ++;
        }
        while ( (st < len) && (chars[len-1] == c) ){
            len --;
        }
        return (st >0) || (len<chars.length)? str.substring(st, len): str;
    }

    public static long count = 0;

    public void compile() {
        int packagePos = input.indexOf("package");
        int packageEndPos = input.indexOf(";", packagePos);
        String substring = input.substring(packagePos + "package".length(), packageEndPos + 1);
        packName = trimChar(substring.trim(), ';').trim();

        int importPos = input.indexOf("import");
        // 找到最后一个  import
        int i = input.lastIndexOf("\nimport ");
        int i1 = input.indexOf(";", i);
        importBlock = new ImportBlock(input, importPos, i1 + 1, this);
        List<Token> tokens = new Tokenizer(input, clazzName, i1 + 1).tokenize();
        for (int i2 = 0; i2 < tokens.size(); i2++) {
            if (i2 + 1 < tokens.size()) {
                tokens.get(i2).next = tokens.get(i2 + 1);
            }
            count++;
        }
//        System.out.println(count);
        clazz = new Parser(tokens, 0, input).parse();
    }

    public static String replace(String content, int left, int right, String newVal) {
        String a = content.substring(0, left);
        String b = content.substring(right + 1);
        return a + newVal + b;
    }

//    Program.input.substring(tokens.get(pos).startPos)
    public static void main(String[] args) {
        String input = resourceReadAll("test.java");
        Unit haha = Unit.buildWhenFileNotExits("/Users/zjh/IdeaProjects/autogen/src/main/resources/test.java", input);
        System.out.println(haha);

//        program.replaceElementOrAdd(BlockType.FUNC, "hello", "void hello(){// \"well done, my bro\"\n}");
    }
}
