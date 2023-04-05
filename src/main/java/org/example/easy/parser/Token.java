package org.example.easy.parser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

/**
 * @Author: zjh
 * @Date: 2022/7/25 3:55 PM
 */
@ToString
public class Token {
    public TokenKind kind;
    public String input;
    public int startPos;
    public int endPos;
    public String content;
    public int lineNum;

    public long val;
    public String type;
    @JsonIgnore
    public Token next;
    public int index; // macro

    Token(TokenKind kind, String input, Integer startPos, Integer endPos){
        this.kind = kind;
        this.startPos = startPos;
        this.endPos = endPos;
        this.content = input.substring(startPos, endPos);
        this.lineNum = getLineNum(input, startPos);
    }

    public static int getLineNum(String input, Integer pos) {
        int num = 1;
        for (int i = 0; i < input.length() && i <= pos; i++) {
            if(input.charAt(i) == '\n') {
                num++;
            }
        }
        return num;
    }

}
