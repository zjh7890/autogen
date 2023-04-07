package org.example.easy;

import com.google.common.base.CaseFormat;
import org.example.easy.gene.ColumnParam;
import org.example.easy.gene.TableParam;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Date;

/**
 * @Author: zjh
 * @Date: 2023/4/6 12:17
 */
public class GenDDL {

    public static void main(String[] args) {
        String s = genSQL(new TableParam(
                "stu",
                "学生信息表",
                "mysql",
                Arrays.asList(
                        new ColumnParam(
                                "stuName", String.class, 64, "姓名"
                        ),
                        new ColumnParam(
                                "age", Integer.class, null, "年龄"
                        )
                )
        ));
        System.out.println(s);
    }


    public static String genSQL(TableParam tableParam) {
        StringBuilder builder = new StringBuilder();
        genSQLInner(tableParam, builder);
        return builder.toString();
    }


    public static final String CREATE_SQL_FORMAT = "CREATE TABLE `%s` (\n" +
            "  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增主键id',\n" +
            "%s" +
            "  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
            "  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='%s'";


    public static void genSQLInner(TableParam tableParam, StringBuilder builder) {
        StringBuilder part = new StringBuilder();
        for (ColumnParam column : tableParam.columns) {
            if (Arrays.asList("id", "createdTime", "updatedTime").contains(column.name)) {
                continue;
            }
            part.append(String.format("  `%s` %s %sCOMMENT '%s',\n",
                    CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, column.name),
                    getType(column.clazz, column.size),
                    null,
//                    CollectionUtils.isNotEmpty(column.bars) && column.bars.contains(NOTNULL) ? "NOT NULL " : "",
                    column.desc));
        }
        String format = String.format(CREATE_SQL_FORMAT, CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, tableParam.name), part, tableParam.desc);
        builder.append(format);
    }


    public static String getType(Class clazz, Integer size) {
        if (clazz == Long.class) {
            return "bigint(20)";
        }
        if (clazz == Integer.class) {
            return "int(11)";
        }
        if (clazz == Date.class) {
            return "datetime";
        }
        if (clazz == String.class) {
            Assert.isTrue(size != null);
            if (size >= LONG_TEXT) {
                return "longtext COLLATE utf8mb4_unicode_ci";
            } else if (size >= TEXT) {
                return "text COLLATE utf8mb4_unicode_ci";
            } else {
                return String.format("varchar(%s) COLLATE utf8mb4_unicode_ci", size);
            }
        }
        String errMsg = String.format("未知类, clazz: %s", clazz);
        throw new RuntimeException(errMsg);
    }

    /**
     * - varchar 小于255byte  1byte overhead
     * - varchar 大于255byte  2byte overhead
     * <p>
     * - tinytext 0-255 1 byte overhead
     * - text 0-65535 byte 2 byte overhead
     * - mediumtext 0-16M  3 byte overhead
     */
    public static final int TEXT = 5000;

    public static final int LONG_TEXT = 50000;


    public static boolean hasBlobs(TableParam tableParam) {
        for (ColumnParam column : tableParam.columns) {
            String type = getType(column.clazz, column.size);
            if (type.contains("text")) {
                return true;
            }
        }
        return false;
    }
}
