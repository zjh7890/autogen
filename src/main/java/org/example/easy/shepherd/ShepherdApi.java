package org.example.easy.shepherd;

/**
 * @Author: zjh
 * @Date: 2022/12/10 20:53
 */
public @interface ShepherdApi {
    Object DEFAULT_URL = "";

    String  name();

    String  description();

    String  timeoutFront();

    String  timeoutBack();

    public enum Method {
        GET(),
        POST()
        ;
    } ;

    public boolean finish() default  false;

    String url();

    Method  method();
}
