package org.example.easy;


import de.hunsicker.jalopy.Jalopy;
import lombok.SneakyThrows;

/**
 * 调用eclipse jdt core对生成的java源码进行格式化
 *
 * @author pf-miles 2014-4-16 下午2:48:29
 */
public class FormatUtils {
    @SneakyThrows
    public static void main(String[] args) {
//        Formatter formatter = new Formatter();
//        String s = formatter.formatSource();
//        System.out.println(s);
        StringBuffer output = new StringBuffer();
        Jalopy j = new Jalopy();
        j.setEncoding("UTF-8");
        j.setInput(Gen.resourceReadAll("test.java"), "A.java"); // 第二个参数随便填个java类名
        j.setOutput(output);
        j.format();

        System.out.println(output);
    }
}