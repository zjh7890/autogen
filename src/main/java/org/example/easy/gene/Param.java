package org.example.easy.gene;

import lombok.AllArgsConstructor;
import org.example.easy.shepherd.CriteriaType;

import java.util.function.Function;

/**
 * @Author: zjh
 * @Date: 2022/7/19 12:07 PM
 */
@AllArgsConstructor
public class Param<PC, EC> {
    String name;
    Class<PC> clazz;

    String entityName;
    Class<EC> entityClazz;

    Function<PC, EC> converter;

    // for query
    /**
     * defaults to equal
     */
    CriteriaType criteriaType;

    String desc;

    public static <SPC> Param<SPC, SPC> build(String name, Class<SPC> clazz) {
        return new Param<>(name, clazz, name, clazz, x -> x, null, "");
    }

    public static <SPC> Param<SPC, SPC> buildSimpleEqual(String name, Class<SPC> clazz) {
        return new Param<>(name, clazz, name, clazz, x -> x, CriteriaType.EQUAL, "");
    }
}
