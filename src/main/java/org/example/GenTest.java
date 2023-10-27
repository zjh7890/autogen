package org.example;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.example.easy.JsonKits;
import org.example.easy.sql.SqlParser;
import org.example.proxy.BaseHandler;
import org.example.proxy.MapHandler;

import java.io.File;
import java.lang.reflect.Proxy;
import java.util.*;

import static org.example.easy.Gen.formatTpl;
import static org.example.easy.Gen.resourceReadAll;
import static org.example.easy.sql.SqlParser.parseSql;

/**
 * @Author: zjh
 * @Date: 2023/3/28 09:20
 */
public class GenTest {

    public static void main12(String[] args) throws InterruptedException {
//        String filename = "/Users/zjh/code/grocery-smart-inf-apollo/grocery-smart-inf-apollo-server/src/main/java/com/sankuai/grocerysmart/inf/apollo/server/CheckController.java";
//        Unit unit = buildByFileName(filename);
//        program.replaceElementOrAdd(BlockType.FUNC, "hello", "void hello(){// \"well done, my bro\"\n}");
    }

    @Data
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
        public static class Record {
            private String name;
            private Integer age;
        }


    public static void main1(String[] args) {
        if (true) {
//            DateUtil.parseDate()
        }
//        if (true) {
//            System.out.println(new File("/Users/zjh/IdeaProjects/autogen/src/main/resources/tpl/eagle/FourLayers/service/service.txt").exists());
//            return;
//        }
//        List<Integer> integers = Arrays.asList(123, 456);
        String content = resourceReadAll("test.ftl");
//        SqlParser.SqlParseRes sqlParseRes = parseSql("`name` = $name and age = $age and kk is null");
//        System.out.println(sqlParseRes);

        MapHandler mapHandler = new MapHandler(new LinkedHashMap());
        Map map = (Map) BaseHandler.build(mapHandler);

        map.put("values", Arrays.asList("aaa", "bbb", "cccc"));

        System.out.println(formatTpl(content, map));
        System.out.println(map);
//        String parse = JsonKits.toJson(new Record());
//        System.out.println(parse);
//        Object parse1 = JsonKits.parse(parse, Object.class);
//        new ObjectMapper().readV
//        System.out.println(parse1);
    }

    public static void main(String[] args) {
        Map map = new HashMap();

        map.put("name", "zjh");

        String x = formatTpl("${name1} hahah", map);
        System.out.println(x);
    }


}
