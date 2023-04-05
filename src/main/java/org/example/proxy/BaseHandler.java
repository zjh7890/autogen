package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zjh
 * @Date: 2023/4/3 10:19
 */
public class BaseHandler implements InvocationHandler {
    protected Object obj;

    public Set<String> set = new LinkedHashSet<>();

    public BaseHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        throw new RuntimeException();
    }

    public static Object build(BaseHandler handler) {
        return Proxy.newProxyInstance(
                handler.obj.getClass().getClassLoader(),
                handler.obj.getClass().getInterfaces(),
                handler);
    }
}
