package org.example.easy.sql;

import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * @Author: zjh
 * @Date: 2022/7/25 3:56 PM
 */
public enum SqlTokenKind {
    SQL_TK_STR(false),
    SQL_TK_EOF(false),

    // SQL reserved
    SQL_TK_ADD(true),
    SQL_TK_ALL(true),
    SQL_TK_ALTER(true),
    SQL_TK_AND(true),
    SQL_TK_ANY(true),
    SQL_TK_AS(true),
    SQL_TK_ASC(true),
    SQL_TK_AVG(true),
    SQL_TK_BACKUP(true),
    SQL_TK_BETWEEN(true),
    SQL_TK_BY(true),
    SQL_TK_CASE(true),
    SQL_TK_CHECK(true),
    SQL_TK_COLUMN(true),
    SQL_TK_CONSTRAINT(true),
    SQL_TK_COUNT(true),
    SQL_TK_CREATE(true),
    SQL_TK_DATABASE(true),
    SQL_TK_DEFAULT(true),
    SQL_TK_DELETE(true),
    SQL_TK_DESC(true),
    SQL_TK_DISTINCT(true),
    SQL_TK_DROP(true),
    SQL_TK_EXEC(true),
    SQL_TK_EXISTS(true),
    SQL_TK_FOREIGN(true),
    SQL_TK_FROM(true),
    SQL_TK_FULL(true),
    SQL_TK_GROUP(true),
    SQL_TK_HAVING(true),
    SQL_TK_IN(true),
    SQL_TK_INDEX(true),
    SQL_TK_INNER(true),
    SQL_TK_INSERT(true),
    SQL_TK_INTO(true),
    SQL_TK_IS(true),
    SQL_TK_JOIN(true),
    SQL_TK_KEY(true),
    SQL_TK_LEFT(true),
    SQL_TK_LIKE(true),
    SQL_TK_LIMIT(true),
    SQL_TK_MAX(true),
    SQL_TK_MIN(true),
    SQL_TK_NAME(true),
    SQL_TK_NOT(true),
    SQL_TK_NULL(true),
    SQL_TK_OR(true),
    SQL_TK_ORDER(true),
    SQL_TK_OUTER(true),
    SQL_TK_PRIMARY(true),
    SQL_TK_PROCEDURE(true),
    SQL_TK_REPLACE(true),
    SQL_TK_RIGHT(true),
    SQL_TK_ROWNUM(true),
    SQL_TK_SELECT(true),
    SQL_TK_SET(true),
    SQL_TK_SUM(true),
    SQL_TK_TABLE(true),
    SQL_TK_TOP(true),
    SQL_TK_TRUNCATE(true),
    SQL_TK_UNION(true),
    SQL_TK_UNIQUE(true),
    SQL_TK_UPDATE(true),
    SQL_TK_VALUES(true),
    SQL_TK_VIEW(true),
    SQL_TK_WHERE(true),

    // C delimiter
    SQL_TK_L_BRACE(false),     // {
    SQL_TK_R_BRACE(false),     // }

    // C punctuation
    SQL_TK_L_BRACKET(false),   // [
    SQL_TK_R_BRACKET(false),   // ]
    SQL_TK_L_PAREN(false),     // (
    SQL_TK_R_PAREN(false),     // )
    SQL_TK_DOT(false),
    SQL_TK_MEMBER(false),      // ->

    SQL_TK_BIT_NOT(false),
    SQL_TK_INC(false),
    SQL_TK_DEC(false),
    SQL_TK_LOG_NOT(false),
    SQL_TK_DIV(false),
    SQL_TK_MUL_DEREF(false),
    SQL_TK_MOD(false),
    SQL_TK_PLUS(false),
    SQL_TK_SUB(false),
//    SQL_TK_SHL(false),
//    SQL_TK_SHR(false), // >>
    SQL_TK_GT(false), // >
    SQL_TK_GE(false),
    SQL_TK_LT(false), // <
    SQL_TK_LE(false),
    SQL_TK_NEQ(false),
    SQL_TK_BIT_AND(false),
    SQL_TK_BIT_XOR(false),
    SQL_TK_BIT_OR(false),
    SQL_TK_LOG_AND(false),
    SQL_TK_LOG_OR(false),
    SQL_TK_QUESTION(false),
    SQL_TK_AT(false),   // @
    SQL_TK_COLON(false), // :
    SQL_TK_EQ(false),
    SQL_TK_COMMA(false), // ,
    SQL_TK_SEMICOLON(false), // ;

    SQL_TK_IDENTIFIER(false),
    // 输入 $
    SQL_TK_INPUT(false),
    SQL_TK_NUM(false),
    SQL_TK_ELLIPSIS(false),

    SQL_TK_ARGU(false),
    ;

    final Boolean ifKeyword;

    SqlTokenKind(boolean ifKeyword) {
        this.ifKeyword = ifKeyword;
    }

    public static final LinkedHashMap<String, SqlTokenKind> keywordMap = new LinkedHashMap<>();
    static {
        for (SqlTokenKind kind : values()) {
            if (kind.ifKeyword) {
                keywordMap.put(kind.name().substring(7).toLowerCase(Locale.ROOT), kind);
            }
        }
    }
}
