package org.example.easy.gene;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @Author: zjh
 */
@AllArgsConstructor
public class TableParam {
    public String name;
    public String desc;
    // mysql or es
    public String dbType;
    public List<ColumnParam> columns;
}
