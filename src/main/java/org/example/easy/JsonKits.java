package org.example.easy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

/**
 * @Author: zjh
 * @Date: 2022/12/9 16:50
 */
public class JsonKits {
    private static final ObjectMapper MAPPER = new ObjectMapper();

//    static {
//        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
//    }

    @SneakyThrows
    public static <T> T parse(String s, Class<T> clazz) {
        long l = System.currentTimeMillis();
        T t = MAPPER.readValue(s, clazz);
        return t;
    }

    @SneakyThrows
    public static String toJson(Object obj) {
        long l = System.currentTimeMillis();
        String s = MAPPER.writeValueAsString(obj);
        long l1 = System.currentTimeMillis();
        return s;
    }
}
