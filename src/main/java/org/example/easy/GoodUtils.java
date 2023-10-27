package org.example.easy;

import com.jayway.jsonpath.JsonPath;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zjh
 * @Date: 2023/3/26 11:19
 */
public class GoodUtils {
//    递归设置Map
    public static void setRecursive(Map map, List<String> nests, String key, Object value) {
        if (nests.size() == 0) {
            // 如果没有nest，直接将value设置到map中
            map.put(key, value);
        } else {
            // 如果有多个nests，则需要递归设置子Map的值
            String nest = nests.get(0);
            Map childMap = (Map) map.get(nest);
            if (childMap == null) {
                // 如果子Map不存在，创建一个新的Map，并将其设置到父Map中
                childMap = new LinkedHashMap();
                map.put(nest, childMap);
            }
            // 递归调用setRecursive函数设置子Map的值
            setRecursive(childMap, nests.subList(1, nests.size()), key, value);
        }
    }

//    根据nests列表递归获取嵌套map的值，如果没有值返回null，不抛异常，public static void getRecursiveMap(Map map, List<String> nests)
    public static Map getRecursiveMap(Map map, List<String> nests) {
        // 如果nests为空，则返回当前map
        if (nests.isEmpty()) {
            return map;
        }

        // 获取第一个嵌套的key
        String key = nests.get(0);

        // 如果当前map中不包含该key，则返回null
        if (map.get(key) == null) {
            return null;
        }

        // 获取该key对应的value
        Object value = map.get(key);
//        递归调用getRecursiveMap方法
        return getRecursiveMap((Map) value, nests.subList(1, nests.size()));
    }

    public static void defaultRecursiveMap(Map map, List<String> nests) {
        // 如果nests为空，则返回当前map
        if (nests.isEmpty()) {
            return;
        }

        // 获取第一个嵌套的key
        String key = nests.get(0);

        // 如果当前map中不包含该key，则返回null
        if (map.get(key) == null) {
            map.put(key, new LinkedHashMap<>());
        }

        // 获取该key对应的value
        Object value = map.get(key);
//        递归调用getRecursiveMap方法
        defaultRecursiveMap((Map) value, nests.subList(1, nests.size()));
    }


    public static <T> List<T> addInNewList(List<T> list, T... objs) {
        List<T> res = new ArrayList<>(list);
        for (T obj : objs) {
            res.add(obj);
        }
        return res;
    }

    public static void main(String[] args) {
    }
}
