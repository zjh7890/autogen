package org.example.easy.parser;

/**
 * @Author: zjh
 */
public enum NodeKind {
    ND_NUM,
    ND_VAR,

    ND_CALL,
    ND_MEMBER_DOT,
    ND_MEMBER_PTR,

    ND_POS,
    ND_NEG,
    ND_BITNOT,
    ND_PRE_INC,
    ND_POST_INC,
    ND_PRE_DEC,
    ND_POST_DEC,
    ND_DEREF,
    ND_ADDR,
    ND_LOGNOT,
    ND_CAST,

    ND_DIV,
    ND_MUL,
    ND_MOD,

    ND_ADD,
    ND_SUB,

    ND_SHL,
    ND_SHR,

    ND_GT,
    ND_GE,
    ND_LT,
    ND_LE,

    ND_EQ,
    ND_NEQ,

    ND_BITAND,
    ND_BITXOR,
    ND_BITOR,
    ND_LOGAND,
    ND_LOGOR,

    ND_COND,    // ? :

    ND_ASSIGN,
    ND_DIV_ASSIGN,
    ND_MUL_ASSIGN,
    ND_MOD_ASSIGN,
    ND_ADD_ASSIGN,
    ND_SUB_ASSIGN,
    ND_SHL_ASSIGN,
    ND_SHR_ASSIGN,
    ND_BITAND_ASSIGN,
    ND_BITXOR_ASSIGN,
    ND_BITOR_ASSIGN,

    ND_COMMA,

    ND_IF,
    ND_WHILE,
    ND_DO,
    ND_FOR,
    ND_GOTO,
    ND_RETURN,
    ND_SWITCH,
    ND_BREAK,
    ND_LABEL,
    ND_CASE,
    ND_CONTINUE,
    ND_DEFAULT,
}
