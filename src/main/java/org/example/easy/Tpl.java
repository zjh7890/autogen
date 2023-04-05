package org.example.easy;

import java.util.Map;

/**
 * @Author: zjh
 * @Date: 2023/3/25 13:03
 */
public class Tpl {
    public static void assign(Map map) {
        assignInner(map, map);
    }

    private static void assignInner(Map map, Map root) {
        if (map == null) {
            return;
        }

    }
}
