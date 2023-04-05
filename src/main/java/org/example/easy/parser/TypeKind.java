package org.example.easy.parser;

/**
 * @Author: zjh
 */
public enum TypeKind {
    // builtin type
    TY_VOID,
    TY_BOOL,
    TY_CHAR,
    TY_SHORT,
    TY_INT,
    TY_LONG,
    // function
    TY_FUNCTION,
    // pointer
    TY_POINTER,
    TY_ARRAY,
    // user-defined type, struct enum union
    TY_STRUCT,
    TY_UNION,
    TY_ENUM
}
