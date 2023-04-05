package org.example.easy.gene;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: zjh
 */
@AllArgsConstructor
@Data
public class ColumnParam {
    public String name;
    public Class clazz;
    public Integer size;
    public String desc;
}
