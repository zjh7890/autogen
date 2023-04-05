package org.example.easy.sql;

import lombok.Getter;

/**
 * @Author: zjh
 */
public enum SqlNodeKind {
    SQL_ND_OR(true, true, false, false, false),
    SQL_ND_AND(true, true, false, false, false),

    SQL_ND_LIKE(true, true, false, false, true),
    SQL_ND_GT(true, true, false, false, true),
    SQL_ND_GE(true, true, false, false, true),
    SQL_ND_LT(true, true, false, false, true),
    SQL_ND_LE(true, true, false, false, true),
    SQL_ND_NEQ(true, true, false, false, true),
    SQL_ND_EQ(true, true, false, false, true),

    SQL_ND_DIV(true, true, false, false, false),
    SQL_ND_MUL(true, true, false, false, false),
    SQL_ND_MOD(true, true, false, false, false),
    SQL_ND_ADD(true, true, false, false, false),
    SQL_ND_SUB(true, true, false, false, false),


    SQL_ND_IS_NULL(true, false, true, false, false),
    SQL_ND_IS_NOT_NULL(true, false, true, false, false),
    SQL_ND_INPUT(true, false, false, true, false),
    SQL_ND_VAR(true, false, false, true, false),
    SQL_ND_NUM(true, false, false, true, false),
    SQL_ND_STR(true, false, false, true, false),

    SQL_ND_BITNOT(false, false, false, false, false),
    SQL_ND_LOGNOT(false, false, false, false, false),
    SQL_ND_BITAND(false, false, false, false, false),
    SQL_ND_BITXOR(false, false, false, false, false),
    SQL_ND_BITOR(false, false, false, false, false),
    SQL_ND_LOGAND(false, false, false, false, false),
    SQL_ND_LOGOR(false, false, false, false, false),

    SQL_ND_COMMA(false, false, false, false, false),

    SQL_ND_IF(false, false, false, false, false),
    SQL_ND_WHILE(false, false, false, false, false),
    SQL_ND_DO(false, false, false, false, false),
    SQL_ND_FOR(false, false, false, false, false),
    SQL_ND_GOTO(false, false, false, false, false),
    SQL_ND_RETURN(false, false, false, false, false),
    SQL_ND_SWITCH(false, false, false, false, false),
    SQL_ND_BREAK(false, false, false, false, false),
    SQL_ND_LABEL(false, false, false, false, false),
    SQL_ND_CASE(false, false, false, false, false),
    SQL_ND_CONTINUE(false, false, false, false, false),
    SQL_ND_DEFAULT(false, false, false, false, false),
    ;
    @Getter
    private final Boolean valid;

    @Getter
    private final Boolean binary;

    @Getter
    private final Boolean unary;

    @Getter
    private final Boolean leaf;

    @Getter
    private final Boolean select;

    SqlNodeKind(Boolean valid, Boolean binary, Boolean unary, Boolean leaf, Boolean select) {
        this.valid = valid;
        this.binary = binary;
        this.unary = unary;
        this.leaf = leaf;
        this.select = select;
    }
}
