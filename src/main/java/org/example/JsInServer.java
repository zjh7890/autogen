package org.example;

import lombok.SneakyThrows;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

// http://www.cnblogs.com/charlexu/p/3424963.html
public class JsInServer {
    @SneakyThrows
    public static String run(String res, String s) {
        // 得到一个ScriptEngine对象
        ScriptEngineManager maneger = new ScriptEngineManager();
        ScriptEngine engine = maneger.getEngineByName("JavaScript");

        InputStream inputStream = JsInServer.class.getClassLoader().getResourceAsStream(res);
        assert inputStream != null;
        Reader scriptReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        try {
            engine.eval(scriptReader);
            if (engine instanceof Invocable) {
                // 调用JS方法
                Invocable invocable = (Invocable) engine;
                return (String) invocable.invokeFunction(s);
            } else {
                System.out.println("false");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
