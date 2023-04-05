package org.example.proxy;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: zjh
 * @Date: 2023/4/3 10:25
 */
public class MapHandler extends BaseHandler {
    public MapHandler(Object obj) {
        super(obj);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method get = Map.class.getDeclaredMethod("get", Object.class);
        if (method.equals(get)) {
            set.add((String) args[0]);
            return method.invoke(obj, args);
        } else {
            return method.invoke(obj, args);
        }
    }
}
