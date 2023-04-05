package org.example.easy.parser;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author: zjh
 */
@Data
public class Var {
    public Token docToken;
    public Token startToken;
    public Parser parser;
    public Type type;
    public Integer startPos;
    public Integer endPos;
    public Boolean ifStatic;
    public Boolean ifFinal;
    public Boolean ifAbstract;
    public Boolean ifDefault;
    public BlockType varType;
    public List<Var> vars = new ArrayList<>();
    public List<Var> funcs = new ArrayList<>();
    public List<Var> clazzes = new ArrayList<>();
    public boolean ifTransient;
    public List<Arg> args;

    int len;
    int scopeDepth;    // local: scopeDepth != 0  global: == 0

    AccessType accessType = AccessType.DEFAULT;

    Initializer init;
    Node initExpr;

    String name;
    int offset;     // for struct member
    int enumVal;    // for enumerator
    // for function
    int stackSize;
    List<Var> locals;
    Node exprs;    // expressions

    public Var findByTypeAndName(BlockType type, String name, List<String> args) {
        if (type == BlockType.VAR) {
            return vars.stream().filter(x -> x.name.equals(name)).findAny().orElse(null);
        } else if (type == BlockType.FUNC) {
            return funcs.stream().filter(x -> {
                Objects.requireNonNull(x.name);
                if (!x.name.equals(name)) {
                    return false;
                }
                if (args.size() != x.args.size()) {
                    return false;
                }
                for (int i = 0; i < args.size(); i++) {
                    if (!args.get(i).equals(x.args.get(i).type.name)) {
                        return false;
                    }
                }
                return true;
            }).findAny().orElse(null);
        } else if (type == BlockType.CLAZZ) {
            return clazzes.stream().filter(x -> x.name.equals(name)).findAny().orElse(null);
        }
        return null;
    }

    public String exposeInterface() {
        if (this.varType != BlockType.FUNC) {
            throw new RuntimeException();
        }
        int typeStart = this.type.startPos;
        int endPos = 0;
        Token find = this.type.startToken;
        while (find.kind != TokenKind.TK_L_BRACE) {
            endPos = find.endPos;
            find = find.next;
        }
        return this.parser.input.substring(typeStart, endPos).trim() + ";";
    }
}
