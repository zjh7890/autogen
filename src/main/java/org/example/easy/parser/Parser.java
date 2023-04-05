package org.example.easy.parser;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

import static org.example.easy.parser.TokenKind.*;

/**
 * @Author: zjh
 */
public class Parser {
//    @JsonIgnore
    public String input;
//    @JsonIgnore
    List<Token> tokens;
//    @JsonIgnore
    Integer tokenPos;

    @Nullable
    Token lastDoc;

    public Parser(List<Token> tokens, Integer tokenPos, String input) {
        this.tokens = tokens;
        this.tokenPos = tokenPos;
        this.input = input;
    }

    public Var parse() {
        return parseInner(null);
    }

    public Var parseInner(String clazzName) {
        if (peekToken(TK_R_BRACE)) {
            return null;
        }
        while (consumeToken(TK_SEMICOLON) != null) {
            ;
        }
        Var var = new Var();
        var.parser = this;
        consumeAndSetDoc(var);
        var.startPos = curToken().startPos;
        var.startToken = curToken();
        consumeAnnotation();
        setStaticAndFinal(var);
        Token tok;
        if ((tok = consumeToken(TK_CLASS)) != null
                || (tok = consumeToken(TK_INTERFACE)) != null
                || (tok = consumeToken(TK_ENUM)) != null) {
            // 类
            Token token = expectToken(TK_IDENTIFIER);
            var.name = token.content;
            var.varType = BlockType.CLAZZ;
            consumeGeneric();
            consumeClazzExtend();
            consumeImplements();
            consumeClazzExtend();

            expectToken(TK_L_BRACE);

            if (tok.kind == TK_ENUM) {
                // 解析枚举成员 RED, YELLOW
                while(true) {
                    consumeAndSetDoc(var);
                    tok = consumeToken(TK_IDENTIFIER);
                    skipParen();
                    if (consumeTokenSuccess(TK_SEMICOLON)) {
                        break;
                    }
                    expectToken(TK_COMMA);
                }
            }

            Var innerVar;
            while ((innerVar = parseInner(var.name)) != null) {
                if (innerVar.varType == BlockType.VAR) {
                    var.vars.add(innerVar);
                } else if (innerVar.varType == BlockType.FUNC) {
                    var.funcs.add(innerVar);
                } else if (innerVar.varType == BlockType.CLAZZ) {
                    var.clazzes.add(innerVar);
                }
            }
            expectToken(TK_R_BRACE);
        } else {
            boolean ifConstructor = false;
            if (peekToken(TK_IDENTIFIER) && peekNextToken(TK_L_PAREN)) {
                // constructor or enum items
                Token token = consumeToken(TK_IDENTIFIER);
                var.name = token.content;
                ifConstructor = true;
            } else {
                // 变量 or 函数
                Type type = parseBaseType();
                if ((tok = consumeToken(TK_IDENTIFIER)) == null) {
                    String msg = String.format("ErrorCompileLog.java:%s, content: %s", curToken().lineNum, curToken().content);
                    throw new RuntimeException(msg);
                }
                var.name = tok.content;
                var.type = type;
            }

            if (consumeTokenSuccess(TK_L_PAREN)) {
                List<Arg> args = parseArgs();
                var.args = args;
                if (consumeTokenSuccess(TK_THROWS)) {
                    expectToken(TK_IDENTIFIER);
                    while(consumeTokenSuccess(TK_COMMA)) {
                        expectToken(TK_IDENTIFIER);
                    }
                }

                if (consumeTokenSuccess(TK_L_BRACE)) {
                    skipBlock();
                } else if (consumeTokenSuccess(TK_SEMICOLON)) {
                    // ();
                } else {
                    // (),
                    expectToken(TK_COMMA);
                }
                var.varType = BlockType.FUNC;
            } else {
                var.varType = BlockType.VAR;
                while(true) {
                    // 变量
                    if (consumeTokenSuccess(TK_ASSIGN)) {
                        handleInitExpr();
                        var.varType = BlockType.VAR;
                    }
                    if (consumeTokenSuccess(TK_SEMICOLON)) {
                        break;
                    }
                    expectToken(TK_COMMA);
                    expectToken(TK_IDENTIFIER);
                }

            }
        }
        var.endPos = tokens.get(tokenPos - 1).endPos;
        return var;
    }

    public void consumeClazzExtend() {
        if (consumeTokenSuccess(TK_EXTENDS)) {
            expectBaseType();
            consumeGeneric();
        }
    }

    public static class A<T extends List> {

    }

    public void consumeImplements() {
        if (consumeTokenSuccess(TK_IMPLEMENTS)) {
            expectBaseType();
            consumeGeneric();
        }
    }

    public Token expectBaseType() {
        Token curToken = curToken();
        tokenPos++;
        Assert.isTrue(isTypeToken(curToken), String.format("ErrorCompileLog.java:%s, content: %s", curToken().lineNum, curToken().content));

        while (consumeTokenSuccess(TK_DOT)) {
            expectToken(TK_IDENTIFIER);
        }
        return curToken;
    }

    public void handleInitExpr() {
        while (true) {
            if (peekToken(TK_SEMICOLON)) {
                return;
            } else if (peekToken(TK_COMMA)) {
                return;
            }  else if (consumeTokenSuccess(TK_L_BRACE)) {
                skipBlock();
            }
            skipToken();
        }
    }

    public void skipBlock() {
        while(true) {
            Token curToken = curToken();
            if (consumeTokenSuccess(TK_L_BRACE)) {
                skipBlock();
            }
            if (consumeTokenSuccess(TK_R_BRACE)) {
                return;
            }
            skipToken();
        }

    }

    public List<Arg> parseArgs() {
        List<Arg> args = new ArrayList<>();
        if (consumeTokenSuccess(TK_R_PAREN)) {
            return args;
        }
        while(true) {
            consumeAnnotation();
            Type type = parseBaseType();
            Token token = expectToken(TK_IDENTIFIER);
            Arg arg = new Arg();
            arg.type = type;
            arg.name = token.content;
            args.add(arg);

            if (consumeTokenSuccess(TK_R_PAREN)) {
                break;
            }
            expectToken(TK_COMMA);
        }
        return args;
    }

    public void skipParen() {
        while(true) {
            Token curToken = curToken();
            if (consumeTokenSuccess(TK_L_PAREN)) {
                skipParen();
            }
            if (consumeTokenSuccess(TK_R_PAREN)) {
                return;
            }
            skipToken();
        }

    }

    public boolean isTypeToken(Token tok){
        Assert.isTrue(tok.kind != TK_EOF, String.format("ErrorCompileLog.java:%s, content: %s", curToken().lineNum, curToken().content));
        switch (tok.kind){
            case TK_VOID:
            case TK_BOOLEAN:
            case TK_CHAR:
            case TK_SHORT:
            case TK_INT:
            case TK_LONG:
            case TK_SIGNED:
            case TK_STRUCT:
            case TK_UNION:
            case TK_ENUM:
            case TK_QUESTION:
                return true;
            case TK_IDENTIFIER:
                return true;
            default:
                return false;
        }
    }

    public Type parseBaseType(){
        Type type = new Type();
        Token token = expectBaseType();
        type.name = token.content;
        type.startPos = token.startPos;
        type.startToken = token;
        if (consumeTokenSuccess(TK_EXTENDS) || consumeTokenSuccess(TK_SUPER)) {
            Type baseType = parseBaseType();
        }
        consumeGeneric();
        return type;
    }


    public Token curToken() {
        if (tokenPos >= tokens.size()) {
            throw new RuntimeException();
        }
        return tokens.get(tokenPos);
    }

    public void setStaticAndFinal(Var var) {
        var.ifStatic = false;
        var.ifFinal = false;
        var.ifAbstract = false;
        var.ifDefault = false;

        consumePrefix(var);
        consumePrefix(var);
        consumePrefix(var);
        consumePrefix(var);
        consumePrefix(var);
    }

    public void consumePrefix(Var var) {
        if (consumeTokenSuccess(TK_STATIC)) {
            var.ifStatic = true;
        } else if (consumeTokenSuccess(TK_FINAL)) {
            var.ifFinal = true;
        } else if (consumeTokenSuccess(TK_ABSTRACT)) {
            var.ifAbstract = true;
        } else if (consumeTokenSuccess(TK_PRIVATE)) {
            var.accessType = AccessType.PRIVATE;
        } else if (consumeTokenSuccess(TK_PROTECTED)) {
            var.accessType = AccessType.PROTECTED;
        } else if (consumeTokenSuccess(TK_PUBLIC)) {
            var.accessType = AccessType.PUBLIC;
        } else if (consumeTokenSuccess(TK_DEFAULT)) {
            var.ifDefault = true;
        } else if (consumeTokenSuccess(TK_TRANSIENT)) {
            var.ifTransient = true;
        }
    }

    public void consumeAndSetDoc(Var var) {
        while (consumeToken(TK_SEMICOLON) != null) {
            ;
        }
        while (true) {
            Token tmp = consumeToken(TK_DOC);
            if (tmp != null) {
//                lastDoc = tmp;
                var.docToken = tmp;
            } else {
                break;
            }
            while (consumeToken(TK_SEMICOLON) != null) {
                ;
            }
        }
        while (consumeToken(TK_SEMICOLON) != null) {
            ;
        }
    }

    public boolean peekEnd(){
        if(curToken().kind == TK_EOF){
            return false;
        }

        if(curToken().kind == TK_R_BRACE){
            return true;
        }

        return curToken().kind == TK_COMMA &&
                tokens.get(tokenPos + 1).kind == TK_R_BRACE;
    }

    public Token consumeAnnotation() {
        while (consumeTokenSuccess(TK_AT)) {
            consumeToken(TK_IDENTIFIER);
            if (consumeTokenSuccess(TK_L_PAREN)) {
                while(!consumeTokenSuccess(TK_R_PAREN)) {
                    skipToken();
                }
            }
        }
        return null;
    }

    public Token consumeGeneric() {
        if (consumeTokenSuccess(TK_LT)) {
            while (true) {
                Type typeArg = parseBaseType();
                typeArg.typeArgs.add(typeArg);
                if (consumeTokenSuccess(TK_GT)) {
                    break;
                } else {
                    expectToken(TK_COMMA);
                }
            }
        }
        return null;
    }

    public Token consumeEnd(){
        if(peekEnd()){
            Token tok;
            if(curToken().kind == TK_R_BRACE){
                tok = curToken();
                tokenPos++;
                return tok;
            }

            if(curToken().kind == TK_COMMA){
                tok = curToken();
                tokenPos++;
                return tok;
            }
            Assert.isTrue(false, String.format("ErrorCompileLog.java:%s, content: %s", curToken().lineNum, curToken().content));
        }

        return null;
    }

    public Token expectToken(TokenKind kind){
        Token curToken = curToken();
        if (curToken.kind != kind) {
            System.out.println("hit");
        }
        if (curToken.kind != kind) {
            System.out.println("fsad");
        }
        Assert.isTrue(curToken.kind == kind, String.format("ErrorCompileLog.java:%s, content: %s", curToken().lineNum, curToken().content));
        Token tok = curToken;
        tokenPos++;
        return tok;
    }

    public boolean peekToken(TokenKind kind){
        return curToken().kind == kind;
    }

    public boolean peekNextToken(TokenKind kind){
        return tokens.get(tokenPos + 1).kind == kind;
    }

    public Token consumeToken(TokenKind kind){
        if(curToken().kind == kind){
            Token tok = curToken();
            tokenPos++;
            return tok;
        }
        return null;
    }

    public void skipToken(){
        tokenPos++;
    }

    public boolean consumeTokenSuccess(TokenKind kind){
        return consumeToken(kind) != null;
    }
}
