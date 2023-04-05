package org.example.easy.sql;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.example.easy.sql.SqlTokenKind.*;


/**
 * @Author: zjh
 * @Date: 2022/7/25 8:24 PM
 */
public class SqlTokenizer {
    public static int[] puncTable = new int[128];
    private final String input;
    private Integer pos;

    static {
        initPuncTable();
    }

    public SqlTokenizer(String input, Integer pos) {
        this.input = input;
        this.pos = pos;
    }

    public static void main1(String[] args) {
        System.out.println("\\n");
        System.out.println("hello" +  StringEscapeUtils.unescapeJava("\\") + "gg");
    }

    public List<SqlToken> tokenize() {
        int posBeforeEat;  // for general use: position before eat
        List<SqlToken> tokens = new ArrayList<>();
        while (pos < input.length()) {
            pos = eatSpace(input, pos);
            if (input.startsWith("\"", pos)) {
                posBeforeEat = pos;
                ++pos;
                while (!input.startsWith("\"", pos)) {
                    ++pos;
                }
                ++pos;
                tokens.add(new SqlToken(SQL_TK_STR, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (input.startsWith("'", pos)) {
                posBeforeEat = pos;
                ++pos;
                while (!input.startsWith("'", pos)) {
                    pos++;
                }
                ++pos;
                tokens.add(new SqlToken(SQL_TK_STR, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (input.startsWith("`", pos)) {
                ++pos;
                posBeforeEat = pos;
                pos = expectIdentifier(input, pos);
                String id = input.substring(posBeforeEat, pos);
                tokens.add(new SqlToken(SQL_TK_IDENTIFIER, input, posBeforeEat, pos));
                pos = expectChar(input, pos, '`');
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
                if (startPos.equals(pos)) {
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

                tokens.add(new SqlToken(SQL_TK_NUM, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            }
            else if (isIdStartChar(input.charAt(pos))) {
                posBeforeEat = pos;
                pos = expectIdentifier(input, pos);
                String id = input.substring(posBeforeEat, pos);
                SqlTokenKind kind = input.charAt(posBeforeEat) == '$' ? SQL_TK_INPUT : SQL_TK_IDENTIFIER;
                kind = Optional.ofNullable(keywordMap.get(id)).orElse(kind);
                tokens.add(new SqlToken(kind, input, posBeforeEat, pos));
                Assert.isTrue(pos >= 0);
            } else if (isCPunct(input.charAt(pos))) {
                SqlToken newTK = null;
                switch (input.charAt(pos)) {
                    case '[':
                        newTK = new SqlToken(SQL_TK_L_BRACKET, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ']':
                        newTK = new SqlToken(SQL_TK_R_BRACKET, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '(':
                        newTK = new SqlToken(SQL_TK_L_PAREN, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ')':
                        newTK = new SqlToken(SQL_TK_R_PAREN, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '.':
                        if (input.startsWith("...", pos)) {
                            newTK = new SqlToken(SQL_TK_ELLIPSIS, input, pos, pos + 3);
                            pos += 3;
                        } else {
                            newTK = new SqlToken(SQL_TK_DOT, input, pos, pos + 1);
                            ++pos;
                        }
                        break;
                    case '-':
                        // -> --  -= -
                        if (input.startsWith("->", pos)) {
                            newTK = new SqlToken(SQL_TK_MEMBER, input, pos, pos + 2);
                            pos += 2;
                        } else if (input.startsWith("--", pos)) {
                            newTK = new SqlToken(SQL_TK_DEC, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new SqlToken(SQL_TK_SUB, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '~':
                        newTK = new SqlToken(SQL_TK_BIT_NOT, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '+':
                        // ++ += +
                        if (input.startsWith("++", pos)) {
                            newTK = new SqlToken(SQL_TK_INC, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new SqlToken(SQL_TK_ADD, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '*':
                        // *= *
                        if (input.startsWith("*", pos)) {
                            newTK = new SqlToken(SQL_TK_MUL_DEREF, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '|':
                        // || |= |
                        if (input.startsWith("||", pos)) {
                            newTK = new SqlToken(SQL_TK_LOG_OR, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new SqlToken(SQL_TK_BIT_OR, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '&':
                        // &= && &
                        if (input.startsWith("&&", pos)) {
                            newTK = new SqlToken(SQL_TK_LOG_AND, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new SqlToken(SQL_TK_BIT_AND, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '/':
                        if (input.startsWith("/", pos)) {
                            newTK = new SqlToken(SQL_TK_DIV, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '%':
                        if (input.startsWith("%", pos)) {
                            newTK = new SqlToken(SQL_TK_MOD, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '<':
                        // <<= << <= <
                        if (input.startsWith("<=", pos)) {
                            newTK = new SqlToken(SQL_TK_LE, input, pos, pos + 2);
                            pos += 2;
                        }
//                        else if (input.startsWith("<<", pos)) {
//                            newTK = new SqlToken(SQL_TK_SHL, pos, pos + 2, input.substring(pos, pos + 2));
//                            pos += 2;
//                        }
                        else {
                            newTK = new SqlToken(SQL_TK_LT, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '>':
                        if (input.startsWith(">=", pos)) {
                            newTK = new SqlToken(SQL_TK_GE, input, pos, pos + 2);
                            pos += 2;
                        }
//                        else if (input.startsWith(">>", pos)) {
//                            newTK = new SqlToken(SQL_TK_SHR, pos, pos + 2, input.substring(pos, pos + 2));
//                            pos += 2;
//                        }
                        else {
                            newTK = new SqlToken(SQL_TK_GT, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '=':
                        // == =
                        if (input.startsWith("==", pos)) {
                            newTK = new SqlToken(SQL_TK_EQ, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new SqlToken(SQL_TK_EQ, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '!':
                        // != !
                        if (input.startsWith("!=", pos)) {
                            newTK = new SqlToken(SQL_TK_NEQ, input, pos, pos + 2);
                            pos += 2;
                        } else {
                            newTK = new SqlToken(SQL_TK_LOG_NOT, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '^':
                        // ^= ^
                        if (input.startsWith("^", pos)) {
                            newTK = new SqlToken(SQL_TK_BIT_XOR, input, pos, pos + 1);
                            pos += 1;
                        }
                        break;
                    case '?':
                        newTK = new SqlToken(SQL_TK_QUESTION, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '@':
                        newTK = new SqlToken(SQL_TK_AT, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ':':
                        newTK = new SqlToken(SQL_TK_COLON, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ',':
                        newTK = new SqlToken(SQL_TK_COMMA, input, pos, pos + 1);
                        ++pos;
                        break;
                    case ';':
                        newTK = new SqlToken(SQL_TK_SEMICOLON, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '{':
                        newTK = new SqlToken(SQL_TK_L_BRACE, input, pos, pos + 1);
                        ++pos;
                        break;
                    case '}':
                        newTK = new SqlToken(SQL_TK_R_BRACE, input, pos, pos + 1);
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
        while(pos < content.length() && isIdChar(content.charAt(pos))){
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
