package org.example.easy.parser;

import java.util.ArrayList;
import java.util.List;

public class Type {
    public Token startToken;
    Integer startPos;
    Integer endPos;
    Type next;
    TypeKind kind;
    int size;
    int align;
    boolean isComplete;
    // for pointer and array
    Type basetype;
    int arrayLen;
    // for struct enum and union
    Integer pos;
    int len;
    // struct or union
    List<Var> members;
    int scopeDepth;
    // function
    boolean hasVarArgs;
    List<Var> params;
    int argc;
    Type retType;

    public String name;

    List<Type> typeArgs = new ArrayList<>();
}
