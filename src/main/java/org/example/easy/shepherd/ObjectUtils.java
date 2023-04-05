package org.example.easy.shepherd;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: zjh
 * @Date: 2022/12/10 20:35
 */
public class ObjectUtils {
    @SneakyThrows
    public static Map object2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        LinkedHashMap<Object, Object> res = new LinkedHashMap<>();
        for (Field field : obj.getClass().getFields()) {
            if (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            res.put(field.getName(), field.get(obj));
        }
        return res;
    }
}
