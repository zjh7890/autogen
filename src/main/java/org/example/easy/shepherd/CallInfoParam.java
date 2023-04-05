package org.example.easy.shepherd;

import lombok.Data;

import java.util.LinkedHashMap;

/**
 * @Author: zjh
 * @Date: 2022/12/10 20:31
 */
@Data
public class CallInfoParam {
    public String clazzName;
    public String methodName;
    public LinkedHashMap<String, Object> args;
}
