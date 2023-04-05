package org.example.easy.gene;

import lombok.AllArgsConstructor;
import org.example.easy.Gather;

import java.util.List;

/**
 * @Author: zjh
 * @Date: 2022/7/31 1:13 PM
 */
@AllArgsConstructor
public class InterParam {
    public String type;
    public String name;
    public String desc;
    public String url;
    public List<Gather.Col> cols;
    public List<List<Object>> items;
    public Class outputClazz;
}
