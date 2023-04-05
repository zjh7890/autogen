package org.example.easy.parser;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.easy.parser.TokenKind.*;

/**
 * @Author: zjh
 * @Date: 2022/7/25 8:24 PM
 */
public class Tokenizer {
    public static int[] puncTable = new int[128];
    private final String input;
    private final String clazz;
    private Integer pos;

    static {
        initPuncTable();
    }

    public Tokenizer(String input, String clazz, Integer pos) {
        this.input = input;
        this.clazz = clazz;
        this.pos = pos;
    }

    public static void main1(String[] args) {
        System.out.println("\\n");
        System.out.println("hello" +  StringEscapeUtils.unescapeJava("\\") + "gg");
    }

    public List<Token> tokenize() {
        int posBeforeEat;  // for general use: position before eat
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            pos = eatSpace(input, pos);
            if (input.startsWith("//", pos)) {
                posBeforeEat = pos;
                pos = eatLine(input, pos);
                tokens.add(new Token(TK_DOC, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (input.startsWith("/*", pos)) {
                posBeforeEat = pos;
                pos = input.indexOf("*/", pos + 2);
                Assert.isTrue(pos != -1, "注释块未闭合");
                pos += 2;
                tokens.add(new Token(TK_DOC, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (input.startsWith("\"", pos)) {
                posBeforeEat = pos;
                ++pos;
                while (!input.startsWith("\"", pos)) {
                    if (input.startsWith("\\", pos)) {
                        pos++;
                    }
                    pos++;
                }
                ++pos;
                tokens.add(new Token(TK_STR, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (input.startsWith("'", pos)) {
                posBeforeEat = pos;
                ++pos;
                if (input.startsWith("\\", pos)) {
                    ++pos;
                }
                pos++;
                pos = expectChar(input, pos, '\'');
                tokens.add(new Token(TK_NUM, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (Character.isDigit(input.charAt(pos))) {
                posBeforeEat = pos;
                int base;
                Class type = Integer.class;
                if (input.startsWith("0x", pos)) {
                    pos += 2;
                    base = 16;
                    Assert.isTrue(Character.isDigit(input.charAt(pos)));
                }
                else if (input.startsWith("0b", pos)) {
                    pos += 2;
                    base = 2;
                    Assert.isTrue(Character.isDigit(input.charAt(pos)));
                }
                else if (input.startsWith("0", pos)) {
                    ++pos;
                    base = 8;
                }
                else {
                    base = 10;
                }
                Integer startPos = pos;
                // 16进制
                while (input.substring(pos, pos + 1).matches("[0-9a-fA-F]")) {
                    pos++;
                }

                long num;
                if (startPos == pos) {
                    num = 0;
                } else {
                    num = Long.parseLong(input.substring(startPos, pos));
                }
                if (input.startsWith("L", pos) || input.startsWith("l", pos)) {
                    type = Long.class;
                    pos++;
                }

                if (num > 2147483647 || num < -2147483647) {
                    type = Long.class;
                }

                tokens.add(new Token(TK_NUM, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (isIdStartChar(input.charAt(pos))) {
                posBeforeEat = pos;
                pos = expectIdentifier(input, pos);
                String id = input.substring(posBeforeEat, pos);
                TokenKind kind = TK_IDENTIFIER;
                kind = Optional.ofNullable(keywordMap.get(id)).orElse(kind);
                tokens.add(new Token(kind, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            } else if (isCPunct(input.charAt(pos))) {
                Token newTK = null;
                switch (input.charAt(pos)) {
                    case '[':
                        newTK = new Token(TK_L_BRACKET, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ']':
                        newTK = new Token(TK_R_BRACKET, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '(':
                        newTK = new Token(TK_L_PAREN, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ')':
                        newTK = new Token(TK_R_PAREN, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '.':
                        if (input.startsWith("...", pos)) {
                            newTK = new Token(TK_ELLIPSIS, input, pos, pos + 3);
                            pos += 3;
                        } else {
                            newTK = new Token(TK_DOT, input, pos, pos + 1);
                            ++pos;
                        }
                        break;
                    case '-':
                        // -> --  -= -
                        if (input.startsWith("->", pos)) {
                            newTK = new Token(TK_MEMBER, input, pos, pos + 2);
                            pos += 2;
                        } else if (input.startsWith("--", pos)) {
                            newTK = new Token(TK_DEC, input, pos, pos + 2);
                            pos += 2;
                        } else if (input.startsWith("-=", pos)) {
                            newTK = new Token(TK_SUB_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_SUB, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '~':
                        newTK = new Token(TK_BIT_NOT, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '+':
                        // ++ += +
                        if (input.startsWith("++", pos)) {
                            newTK = new Token(TK_INC, input, pos, pos + 2);
                            pos += 2;
                        } else if (input.startsWith("+=", pos)) {
                            newTK = new Token(TK_ADD_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_ADD, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '*':
                        // *= *
                        if (input.startsWith("*=", pos)) {
                            newTK = new Token(TK_MUL_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_MUL_DEREF, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '|':
                        // || |= |
                        if (input.startsWith("||", pos)) {
                            newTK = new Token(TK_LOG_OR, input, pos, pos + 2);
                            pos += 2;
                        } else if (input.startsWith("|=", pos)) {
                            newTK = new Token(TK_BITOR_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_BIT_OR, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '&':
                        // &= && &
                        if (input.startsWith("&&", pos)) {
                            newTK = new Token(TK_LOG_AND, input, pos, pos + 2);
                            pos += 2;
                        } else if (input.startsWith("&=", pos)) {
                            newTK = new Token(TK_BITAND_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_BIT_AND, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '/':
                        if (input.startsWith("/=", pos)) {
                            newTK = new Token(TK_DIV_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_DIV, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '%':
                        if (input.startsWith("%=", pos)) {
                            newTK = new Token(TK_MOD_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_MOD, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '<':
                        // <<= << <= <
                        if (input.startsWith("<<=", 3)) {
                            newTK = new Token(TK_SHL_ASSIGN, input, pos, pos + 3);
                            pos += 3;
                        }
                        else if (input.startsWith("<=", pos)) {
                            newTK = new Token(TK_LE, input, pos, pos + 2);
                            pos += 2;
                        }
//                        else if (input.startsWith("<<", pos)) {
//                            newTK = new Token(TK_SHL, pos, pos + 2, input.substring(pos, pos + 2));
//                            pos += 2;
//                        }
                        else {
                            newTK = new Token(TK_LT, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '>':
                        if (input.startsWith(">>=", 3)) {
                            newTK = new Token(TK_SHR_ASSIGN, input, pos, pos + 3);
                            pos += 3;
                        }
                        else if (input.startsWith(">=", pos)) {
                            newTK = new Token(TK_GE, input, pos, pos + 2);
                            pos += 2;
                        }
//                        else if (input.startsWith(">>", pos)) {
//                            newTK = new Token(TK_SHR, pos, pos + 2, input.substring(pos, pos + 2));
//                            pos += 2;
//                        }
                        else {
                            newTK = new Token(TK_GT, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '=':
                        // == =
                        if (input.startsWith("==", pos)) {
                            newTK = new Token(TK_EQ, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_ASSIGN, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '!':
                        // != !
                        if (input.startsWith("!=", pos)) {
                            newTK = new Token(TK_NEQ, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_LOG_NOT, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '^':
                        // ^= ^
                        if (input.startsWith("^=", pos)) {
                            newTK = new Token(TK_BITXOR_ASSIGN, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new Token(TK_BIT_XOR, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '?':
                        newTK = new Token(TK_QUESTION, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '@':
                        newTK = new Token(TK_AT, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ':':
                        newTK = new Token(TK_COLON, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ',':
                        newTK = new Token(TK_COMMA, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ';':
                        newTK = new Token(TK_SEMICOLON, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '{':
                        newTK = new Token(TK_L_BRACE, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '}':
                        newTK = new Token(TK_R_BRACE, input, pos, pos + 1);
                        ++pos;
                        break;
                    default:
                        Assert.isTrue(false, "unknown punctuation");
                }
                Assert.isTrue(newTK != null);
                tokens.add(newTK);
                Assert.isTrue(pos >= 0);
            } else {
                Assert.isTrue(pos >= 0);
                Assert.isTrue(false, "unknown character: " + input.substring(pos, pos + 10));
            }

            pos = eatSpace(input, pos);
            Assert.isTrue(pos <= input.length());
        }
        return tokens;
    }

    public boolean isCPunct(int c){
        if(c < 0 || c > 127){
            return false;
        }
        return puncTable[c] == 1;
    }

    public Integer expectIdentifier(String content, Integer pos){
        assert(isIdStartChar(content.charAt(pos)));
        return eatIdChar(content, pos);
    }

    public Integer eatIdChar(String content, Integer pos){
        while(isIdChar(content.charAt(pos))){
            ++pos;
        }
        return pos;
    }

    public boolean isIdChar(char c){
        return Character.isDigit(c) || Character.isAlphabetic(c) || c == '_' || c == '$';
    }

    public boolean isIdStartChar(int c){
        return Character.isAlphabetic(c) || c == '_' || c == '$';
    }

    public Integer expectChar(String content, Integer pos, int c) {
        assert (content.charAt(pos) == c);
        ++pos;
        return pos;
    }

    /**
     * \f \n \r \t \v ' '
     */
    public Integer eatSpace(String content, Integer pos) {
        while (pos < content.length() && Character.isWhitespace(content.charAt(pos))) {
            ++pos;
        }
        return pos;
    }

    public Integer eatLine(String content, Integer pos) {
        while (!isCRLF(content.charAt(pos))) {
            ++pos;
        }
        return pos;
    }

    public boolean isCRLF(char c) {
        return c == '\r' || c == '\n';
    }

    public static void initPuncTable() {
        puncTable['!'] = 1;
        puncTable['%'] = 1;
        puncTable['&'] = 1;
        puncTable['('] = 1;
        puncTable[')'] = 1;
        puncTable['*'] = 1;
        puncTable['+'] = 1;
        puncTable[','] = 1;
        puncTable['-'] = 1;
        puncTable['.'] = 1;
        puncTable['/'] = 1;
        puncTable[':'] = 1;
        puncTable[';'] = 1;
        puncTable['<'] = 1;
        puncTable['='] = 1;
        puncTable['>'] = 1;
        puncTable['?'] = 1;
        puncTable['@'] = 1;
        puncTable['['] = 1;
        puncTable[']'] = 1;
        puncTable['{'] = 1;
        puncTable['|'] = 1;
        puncTable['}'] = 1;
        puncTable['~'] = 1;
    }
}
