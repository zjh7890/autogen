package org.example;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author: zhujunhua
 * @Date: 2023/12/11 17:18
 */
public class Consume {
    public static void main(String[] args) {
        List<Integer> generate = Generator.generate(10);
        if (CollectionUtils.isEmpty(generate)) {
            System.out.println("generate data success");
        }
    }
}
