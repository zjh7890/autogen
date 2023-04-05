package org.example.easy;

import lombok.AllArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 提供 map 一些动态特性
 * @Author: zjh
 */
@AllArgsConstructor
public class React {
    @Nullable
    public Object value;

    public React get(String key) {
        if (value == null){
            return this;
        }
        if (!Map.class.isAssignableFrom(value.getClass())) {
            String errMsg = String.format("React get 失败, value 不是 Map 类型或 null, key: %s, class: %s", key, value.getClass().getName());
            throw new RuntimeException(errMsg);
        }
        Map map = (Map) value;
        return new React(map.get(key));
    }

    public React set(String key, Object obj) {
        if (value == null){
            return this;
        }
        if (!Map.class.isAssignableFrom(value.getClass())) {
            String errMsg = String.format("React get 失败, value 不是 Map 类型或 null, key: %s, class: %s", key, value.getClass().getName());
            throw new RuntimeException(errMsg);
        }
        Map map = (Map) value;
        map.put(key, obj);
        return this;
    }

    public React index(int idx) {
        if (value == null){
            return this;
        }
        if (!List.class.isAssignableFrom(value.getClass())) {
            String errMsg = String.format("React index 失败, value 不是 list 类型或 null, key: %s, class: %s", idx, value.getClass().getName());
            throw new RuntimeException(errMsg);
        }
        List list = (List) value;
        if (idx >= list.size()) {
            return new React(null);
        }
        return new React(list.get(idx));
    }

    public React listGet(String key) {
        if (value == null){
            return this;
        }
        if (!List.class.isAssignableFrom(value.getClass())) {
            String errMsg = String.format("React listGet 失败, value 不是 list 类型或 null, key: %s, class: %s", key, value.getClass().getName());
            throw new RuntimeException(errMsg);
        }
        List list = (List) value;
        List newList = new ArrayList(list.size());
        for (Object item : list) {
            newList.add(new React(item).getExpose(key));
        }
        return new React(newList);
    }

    @Nullable
    public <T> T getExpose(String key){
        return get(key).expose();
    }

    @Nullable
    public <T> T expose(){
        return (T) value;
    }

    @Nullable
    public Map exposeMap(){
        return (Map) value;
    }
}
