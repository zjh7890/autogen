package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhujunhua
 * @Date: 2023/12/11 17:16
 */
public class Generator {
    static List<Integer> generate(int a) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            list.add(i);
        }
        return list;
    }
}
