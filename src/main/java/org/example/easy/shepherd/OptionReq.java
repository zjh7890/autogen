package org.example.easy.shepherd;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @Author: zjh
 * @Date: 2022/12/10 20:32
 */
@Data
public class OptionReq<T> {
    private ArrayList options;

    public <E> void setOptions(ArrayList<E> options) {
        this.options = options;
    }

    public ArrayList getOptions() {
        return options;
    }

    public void setReqs(LinkedHashMap<String, T> linkedHashMap) {

    }
}
