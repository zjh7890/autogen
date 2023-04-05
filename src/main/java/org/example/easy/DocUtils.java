package org.example.easy;

import com.google.common.base.CaseFormat;
import org.example.easy.shepherd.*;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用处：
 * 1.生成接口文档，方便拷到 yapi
 * 2.生成牧羊人配置信息，方便 cv
 * 用法参见 main 函数, 依赖 @ShepherdApi 注解
 *
 * @Author: zjh
 * @Date: 2022/1/7 2:11 PM
 */
public class DocUtils {
    public static void main(String[] args) throws Exception {
//        List<DocumentParam> document = document(TtMarkQueryThriftService.class);
//        List<DocumentParam> document = document(TaskNoticeThriftService.class);
//        List<DocumentParam> document1 = document(TtMarkThriftService.class);
//        document.addAll(document1);
//        System.out.println(JsonKits.toJson(document));

//        List<ShepherdInfo> document = shepherd(AttendanceThriftService.class);
//        List<ShepherdInfo> document1 = shepherd(TtMarkQueryThriftService.class);
//        document.addAll(document1);
//        System.out.println(JsonKits.toJson(document1));
//        shepherd(TtMarkQueryThriftService.class);
    }

    public static List<DocumentParam> document(Class<?> clazz) throws Exception {
        Method[] methods = clazz.getDeclaredMethods();
        List<DocumentParam> methodInfos = new ArrayList<>();
        for (Method method : methods) {
            if (method.isSynthetic()) {
                continue;
            }
            ShepherdApi shepherdApi = AnnotationUtils.findAnnotation(method, ShepherdApi.class);
            if (shepherdApi == null || shepherdApi.finish()) {
                continue;
            }

            StringBuilder methodUrl = new StringBuilder(shepherdApi.url().equals(ShepherdApi.DEFAULT_URL) ?
                    "/" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, method.getName()) :
                    shepherdApi.url());

            Parameter[] parameters = method.getParameters();
            Map<String, Object> map = new HashMap<>();
            Object request = null;
            Object response = null;
            int hasArgs = 0;
            if (shepherdApi.method() == ShepherdApi.Method.GET) {
                boolean first = true;
                for (Parameter p : parameters) {
                    QueryParam queryParam = p.getDeclaredAnnotation(QueryParam.class);
                    if (queryParam == null) {
                        continue;
                    }
                    hasArgs = 1;
                    Object make = BeanMaker.make(p.getParameterizedType(), null, p.getType().getName(), null);
                    if (first) {
                        methodUrl.append("?");
                        first = false;
                    }
                    methodUrl.append(String.format("%s=%s", p.getName(), JsonKits.toJson(make)));
                }
            } else {
                // POST
                boolean notfound = true;
                for (Parameter p : parameters) {
                    BodyParam bodyParam = p.getDeclaredAnnotation(BodyParam.class);
                    if (bodyParam == null) {
                        continue;
                    }
                    hasArgs = 2;
                    notfound = false;
                    request = BeanMaker.make(p.getParameterizedType(), null, p.getType().getName(), null);
                }
                // 默认第一个
                if (notfound && parameters.length != 0) {
                    hasArgs = 2;
                    request = BeanMaker.make(parameters[0].getParameterizedType(), null, parameters[0].getType().getName(), null);
                }
            }

            String comment = "无需参数";
            if (hasArgs == 1) {
                comment = "参数放在 url";
            } else if (hasArgs == 2) {
                comment = "参数放在 body";
            }

            response = BeanMaker.make(method.getGenericReturnType(), null, null, null);

            String path = "/api/apollo" + methodUrl;
            methodInfos.add(new DocumentParam(shepherdApi.description(), path, shepherdApi.method().name(), request, response, comment));
        }
        return methodInfos;
    }

    public static List<ShepherdInfo> shepherd(Class<?> clazz) {
//        ShepherdApiSet clazzApiAnno = clazz.getDeclaredAnnotation(ShepherdApiSet.class);
//        if (clazzApiAnno == null) {
//            System.out.println("clazz Anno is null");
//            return Collections.emptyList();
//        }
        List<ShepherdInfo> list = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            ShepherdApi methodApiAnno = method.getDeclaredAnnotation(ShepherdApi.class);
            if (methodApiAnno == null || methodApiAnno.finish()) {
                continue;
            }
            ShepherdInfo info = new ShepherdInfo();
            if (methodApiAnno.name().isEmpty()) {
                info.setApiName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, method.getName()));
            } else {
                info.setApiName(String.valueOf(methodApiAnno.name()));
            }
            info.setDescription(methodApiAnno.description());

            String methodUrl = methodApiAnno.url().equals(ShepherdApi.DEFAULT_URL) ?
                    "/" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, method.getName()) :
                    methodApiAnno.url();
            info.setPath("/api/apollo" + methodUrl);
            info.setMethodTypes(methodApiAnno.method() == ShepherdApi.Method.POST ? "post" : "get");
            info.setTimeout_front(methodApiAnno.timeoutFront());
            info.setAlias("alias");
            info.setRemoteAppkey("com.sankuai.grocerysmart.inf.apollo");
            info.setServiceName(clazz.getTypeName());
            info.setMethodName(method.getName());
            info.setTimeout_back(methodApiAnno.timeoutBack());

            List<ShepherdParamInfo> paramInfos = new ArrayList<>();
            for (Parameter parameter : method.getParameters()) {
                ShepherdParamInfo paramInfo = new ShepherdParamInfo();
                paramInfo.setParamType(parameter.getType().getCanonicalName());

                BodyParam bodyParam = parameter.getDeclaredAnnotation(BodyParam.class);
                ContextParam contextParam = parameter.getDeclaredAnnotation(ContextParam.class);
                QueryParam queryParam = parameter.getDeclaredAnnotation(QueryParam.class);

                if (bodyParam != null) {
                    paramInfo.setDsl(BodyParam.DSL);
                    paramInfo.setExpressionType("JsonPath");
                } else if (contextParam != null) {
                    paramInfo.setDsl(ContextParam.MIS);
                    paramInfo.setExpressionType("JsonPath");
                } else if (queryParam != null) {
                    paramInfo.setDsl(String.format(queryParam.DSL_FORMAT, parameter.getName()));
                    paramInfo.setExpressionType("FreeMarker");
                } else {
                    paramInfo.setDsl(BodyParam.DSL);
                    paramInfo.setExpressionType("JsonPath");
                }
                paramInfos.add(paramInfo);
            }
            info.setParamInfos(paramInfos);
            list.add(info);
        }
        return list;
    }
}
