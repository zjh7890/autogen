package org.example.easy.parser;

import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * @Author: zjh
 * @Date: 2022/7/25 3:56 PM
 */
public enum TokenKind {
    TK_STR(false),
    TK_EOF(false),

    TK_DOC(false),

    // C reserved
    TK_ABSTRACT(true),
    TK_BOOLEAN(true),
    TK_BREAK(true),
    TK_CASE(true),
    TK_CHAR(true),
    TK_CLASS(true),
    TK_CONST(true),
    TK_CONTINUE(true),
    TK_DEFAULT(true),
    TK_TRANSIENT(true),
    TK_DO(true),
    TK_ELSE(true),
    TK_ENUM(true),
    TK_EXTENDS(true),
    TK_SUPER(true),
    TK_FOR(true),
    TK_GOTO(true),
    TK_IF(true),
    TK_IMPLEMENTS(true),
    TK_INT(true),
    TK_INTERFACE(true),
    TK_LONG(true),
    TK_PACKAGE(true),
    TK_PRIVATE(true),
    TK_PROTECTED(true),
    TK_PUBLIC(true),
    TK_RETURN(true),
    TK_SHORT(true),
    TK_SIGNED(true),
    TK_SIZEOF(true),
    TK_STATIC(true),
    TK_STRUCT(true),
    TK_UNION(true),
    TK_SWITCH(true),
    TK_THROWS(true),
    TK_TYPEDEF(true),
    TK_VOID(true),
    TK_WHILE(true),
    TK_FINAL(true),

    // C delimiter
    TK_L_BRACE(false),     // {
    TK_R_BRACE(false),     // }

    // C punctuation
    TK_L_BRACKET(false),   // [
    TK_R_BRACKET(false),   // ]
    TK_L_PAREN(false),     // (
    TK_R_PAREN(false),     // )
    TK_DOT(false),
    TK_MEMBER(false),      // ->

    TK_BIT_NOT(false),
    TK_INC(false),
    TK_DEC(false),
    TK_LOG_NOT(false),
    TK_DIV(false),
    TK_MUL_DEREF(false),
    TK_MOD(false),
    TK_ADD(false),
    TK_SUB(false),
//    TK_SHL(false),
//    TK_SHR(false), // >>
    TK_GT(false), // >
    TK_GE(false),
    TK_LT(false), // <
    TK_LE(false),
    TK_EQ(false),
    TK_NEQ(false),
    TK_BIT_AND(false),
    TK_BIT_XOR(false),
    TK_BIT_OR(false),
    TK_LOG_AND(false),
    TK_LOG_OR(false),
    TK_QUESTION(false),
    TK_AT(false),   // @
    TK_COLON(false), // :
    TK_ASSIGN(false),
    TK_DIV_ASSIGN(false),
    TK_MUL_ASSIGN(false),
    TK_MOD_ASSIGN(false),
    TK_ADD_ASSIGN(false),
    TK_SUB_ASSIGN(false),
    TK_SHL_ASSIGN(false),
    TK_SHR_ASSIGN(false),
    TK_BITAND_ASSIGN(false),
    TK_BITOR_ASSIGN(false),
    TK_BITXOR_ASSIGN(false),
    TK_COMMA(false), // ,
    TK_SEMICOLON(false), // ;

    TK_IDENTIFIER(false),
    TK_NUM(false),
    TK_ELLIPSIS(false),

    TK_ARGU(false),
    ;

    final Boolean ifKeyword;

    TokenKind(boolean ifKeyword) {
        this.ifKeyword = ifKeyword;
    }

    public static final LinkedHashMap<String, TokenKind> keywordMap = new LinkedHashMap<>();
    static {
        for (TokenKind kind : values()) {
            if (kind.ifKeyword) {
                keywordMap.put(kind.name().substring(3).toLowerCase(Locale.ROOT), kind);
            }
        }
    }
}
