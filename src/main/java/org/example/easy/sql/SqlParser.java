package org.example.easy.sql;

import com.google.common.base.CaseFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static org.example.easy.sql.SqlNodeKind.*;
import static org.example.easy.sql.SqlTokenKind.*;


/**
 * @Author: zjh
 * @Date: 2022/8/16 7:23 PM
 */
public class SqlParser {

    List<SqlToken> sqlTokens;
    Integer sqlTokenPos;

    public SqlParser(List<SqlToken> SqlTokens, Integer SqlTokenPos) {
        this.sqlTokens = SqlTokens;
        this.sqlTokenPos = SqlTokenPos;
    }

    public static void main(String[] args) {
//        SqlParseRes sqlParseRes = parseSql("cityId = $cityId and bizDt = $bizDt and mis is  null and bizDt is   not null");
        SqlParseRes sqlParseRes = parseSql("`name` = $name and rule_name = $ruleName and kk is null");
//        sqlParseRes.to
        System.out.println(sqlParseRes);
    }

    public static SqlParseRes parseSql(String input) {
        try {
            List<SqlToken> tokens = new SqlTokenizer(input, 0).tokenize();
            SqlParser parser = new SqlParser(tokens, 0);
            SqlNode node = parser.parse();
            checkAndOr(node, true);
            List<SqlNode> orNodes = genOrList(node);
            List<List<SqlNode>> nodes = convertNode(orNodes);

            SqlParseRes res = new SqlParseRes();
            res.setNodes(nodes);

            List<String> vars = new ArrayList<>();
            List<String> inputs = new ArrayList<>();
            List<String> notNullVars = new ArrayList<>();
            Map<String, String> input2VarMap = new LinkedHashMap<>();

            for (List<SqlNode> andList : nodes) {
                for (SqlNode n : andList) {
                    // 单节点下的，用来做变量联结
                    List<String> tmpVars = new ArrayList<>();
                    List<String> tmpInputs = new ArrayList<>();
                    List<String> tmpNotNullVar = new ArrayList<>();
                    fetchVars(n, tmpVars, tmpInputs, tmpNotNullVar);
                    if (!CollectionUtils.isEmpty(tmpVars)
                    && !CollectionUtils.isEmpty(tmpInputs)) {
                        input2VarMap.put(
                                toLowerCamel(tmpInputs.get(0)),
                                toLowerCamel(tmpVars.get(0))
                                );
                    }
                    vars.addAll(tmpVars);
                    inputs.addAll(tmpInputs);
                    notNullVars.addAll(tmpNotNullVar);
                }
            }
            res.setVars(vars.stream().distinct()
                    .map(SqlParser::toLowerCamel).collect(Collectors.toList()));
            res.setInputs(inputs.stream().distinct()
                    .map(SqlParser::toLowerCamel).collect(Collectors.toList()));
            res.setNoJudgeNullVars(notNullVars.stream().distinct()
                    .map(SqlParser::toLowerCamel).collect(Collectors.toList()));
            res.setJudgeNullVars(res.getVars().stream()
                    .map(SqlParser::toLowerCamel).filter(x -> !res.getNoJudgeNullVars().contains(x)).collect(Collectors.toList()));
            res.setInput2VarMap(input2VarMap);
            return res;
        } catch (Exception e) {
            throw new RuntimeException("sql解析有误，是否是因为name等关键字导致，也可能是因为格式错误", e);
        }

    }

    public static String toLowerCamel(String s) {
        if (s.contains("_")) {
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
        }
        return s;
    }

    public static void fetchVars(SqlNode node, List<String> vars, List<String> inputs, List<String> notNullVars) {
        if (node == null) {
            return;
        }
        fetchVars(node.lhs, vars, inputs, notNullVars);
        fetchVars(node.rhs, vars, inputs, notNullVars);
        if (node.nodeKind == SQL_ND_VAR) {
            vars.add(node.content);
        } else if (node.nodeKind == SQL_ND_INPUT) {
            inputs.add(node.content.trim().substring(1));
        } else if (node.nodeKind == SQL_ND_IS_NULL || node.nodeKind == SQL_ND_IS_NOT_NULL) {
            notNullVars.add(node.lhs.content);
        }
    }

    @NoArgsConstructor
    @Data
    public static class SqlParseRes {
        public List<List<SqlNode>> nodes;
        public List<String> vars;
        public List<String> inputs;
        public List<String> judgeNullVars;
        public List<String> noJudgeNullVars;
        public Map<String, String> input2VarMap;

    }

    public static List<List<SqlNode>> convertNode(List<SqlNode> orNodes) {
        List<List<SqlNode>> res = new ArrayList<>();
        for (SqlNode orNode : orNodes) {
            res.add(genAndList(orNode));
        }
        return res;
    }

    public static List<SqlNode> genAndList(SqlNode node) {
        List<SqlNode> res = new ArrayList<>();
        if (node.nodeKind == SQL_ND_AND) {
            res.addAll(genAndList(node.lhs));
            res.addAll(genAndList(node.rhs));
            return res;
        }
        else {
            return Arrays.asList(node);
        }
    }

    public static List<SqlNode> genOrList(SqlNode node) {
        if (node.nodeKind != SQL_ND_AND
                && node.nodeKind != SQL_ND_OR) {
            return Arrays.asList(node);
        }
        List<SqlNode> res = new ArrayList<>();
        if (node.nodeKind == SQL_ND_OR) {
            List<SqlNode> llist = genOrList(node.lhs);
            List<SqlNode> rlist = genOrList(node.rhs);
            res.addAll(llist);
            res.addAll(rlist);
            return res;
        }
        else {
            List<SqlNode> llist = genOrList(node.lhs);
            List<SqlNode> rlist = genOrList(node.rhs);
            for (SqlNode lNode : llist) {
                for (SqlNode rNode : rlist) {
                    SqlNode sqlNode = SqlNode.buildToken(SQL_ND_AND, lNode, rNode);
                    res.add(sqlNode);
                }
            }
            return res;
        }
    }

    public static void checkAndOr(SqlNode node, boolean andOr) {
        if (node == null) {
            return;
        }
        Assert.isTrue(node.nodeKind.getValid());
        nodeCheck(node);
        if (node.nodeKind == SQL_ND_AND
        || node.nodeKind == SQL_ND_OR) {
            if (andOr) {
                checkAndOr(node.lhs, true);
                checkAndOr(node.rhs, true);
            } else {
                throw new RuntimeException();
            }
        } else {
            checkAndOr(node.lhs, false);
            checkAndOr(node.rhs, false);
        }
    }

    public static void nodeCheck(SqlNode node) {
        if (node.nodeKind.getBinary()) {
            Assert.isTrue(node.lhs != null && node.rhs != null);
        }
        else if (node.nodeKind.getUnary()) {
            Assert.isTrue(node.lhs != null && node.rhs == null);
        }
        else if (node.nodeKind.getLeaf()) {
            Assert.isTrue(node.lhs == null && node.rhs == null);
        }
        else {
            throw new RuntimeException();
        }

        if (node.nodeKind == SQL_ND_AND
        || node.nodeKind == SQL_ND_OR) {
            Assert.isTrue(!node.lhs.nodeKind.getLeaf());
            Assert.isTrue(!node.rhs.nodeKind.getLeaf());
        }
        else if (node.nodeKind.getSelect()) {
            Assert.isTrue(!node.lhs.nodeKind.getSelect());
            Assert.isTrue(!node.rhs.nodeKind.getSelect());
            Assert.isTrue(node.lhs.nodeKind == SQL_ND_VAR || node.rhs.nodeKind == SQL_ND_VAR);
        }
        else if (node.nodeKind.getBinary() && !node.nodeKind.getSelect()) {
            Assert.isTrue(!node.lhs.nodeKind.getSelect());
            Assert.isTrue(!node.rhs.nodeKind.getSelect());
        }
    }

    public SqlNode unary() {
        if (consumeSqlTokenSuccess(SQL_TK_L_PAREN)) {
            return parse();
        }

        SqlNode res;
        SqlToken tok;
        SqlNodeKind kind;
        if ((tok = consumeSqlToken(SQL_TK_INPUT)) != null) {
            kind = SQL_ND_INPUT;
            res = new SqlNode(kind, tok);
        }
        else if ((tok = consumeSqlToken(SQL_TK_IDENTIFIER)) != null) {
            if (consumeSqlTokenSuccess(SQL_TK_IS)) {
                if (consumeSqlTokenSuccess(SQL_TK_NOT)) {
                    kind = SQL_ND_IS_NOT_NULL;
                } else {
                    kind = SQL_ND_IS_NULL;
                }
                expectSqlToken(SQL_TK_NULL);
                res = new SqlNode(kind, sqlTokens.get(sqlTokenPos - 2));
                res.lhs = new SqlNode(SQL_ND_VAR, tok);
            } else {
                res = new SqlNode(SQL_ND_VAR, tok);
            }
        } else if ((tok = consumeSqlToken(SQL_TK_NUM)) != null) {
            kind = SQL_ND_NUM;
            res = new SqlNode(kind, tok);
        } else if ((tok = consumeSqlToken(SQL_TK_STR)) != null) {
            kind = SQL_ND_STR;
            res = new SqlNode(kind, tok);
        } else {
            throw new RuntimeException();
        }
        return res;
    }

    public SqlNode binary(int priority) {
        SqlNode lhs = unary();
        SqlNode rhs;
        while (!touchEnd() && !consumeSqlTokenSuccess(SQL_TK_R_PAREN)) {
            if(priority >= getOpPriority(curSqlToken().kind)){
                return lhs;
            }
            SqlToken tok = curSqlToken();
            sqlTokenPos++;
            rhs = binary(getOpPriority(tok.kind));
            lhs = new SqlNode(binTokenToNode(tok.kind), lhs, rhs, tok);
        }
        return lhs;
    }

    SqlNodeKind binTokenToNode(SqlTokenKind kind){
        switch (kind){
            case SQL_TK_MUL_DEREF:
                return SQL_ND_MUL;
            case SQL_TK_DIV:
                return SQL_ND_DIV;
            case SQL_TK_MOD:
                return SQL_ND_MOD;
            case SQL_TK_SUB:
                return SQL_ND_SUB;
            case SQL_TK_ADD:
                return SQL_ND_ADD;
            case SQL_TK_GE:
                return SQL_ND_GE;
            case SQL_TK_GT:
                return SQL_ND_GT;
            case SQL_TK_LE:
                return SQL_ND_LE;
            case SQL_TK_LT:
                return SQL_ND_LT;
            case SQL_TK_NEQ:
                return SQL_ND_NEQ;
            case SQL_TK_BIT_AND:
                return SQL_ND_BITAND;
            case SQL_TK_BIT_XOR:
                return SQL_ND_BITXOR;
            case SQL_TK_BIT_OR:
                return SQL_ND_BITOR;
            case SQL_TK_LOG_AND:
                return SQL_ND_LOGAND;
            case SQL_TK_LOG_OR:
                return SQL_ND_LOGOR;
            case SQL_TK_EQ:
                return SQL_ND_EQ;
            case SQL_TK_COMMA:
                return SQL_ND_COMMA;
            case SQL_TK_AND:
                return SQL_ND_AND;
            case SQL_TK_OR:
                return SQL_ND_OR;
        }
        throw new RuntimeException();
    }

    int getOpPriority(SqlTokenKind kind){
        switch (kind){
            case SQL_TK_DIV:
            case SQL_TK_MUL_DEREF:
            case SQL_TK_MOD:
                return 140;
            case SQL_TK_ADD:
            case SQL_TK_SUB:
                return 130;
            case SQL_TK_EQ:
            case SQL_TK_GE:
            case SQL_TK_GT:
            case SQL_TK_LE:
            case SQL_TK_LT:
                return 110;
            case SQL_TK_NEQ:
                return 100;
            case SQL_TK_BIT_AND:
                return 80;
            case SQL_TK_BIT_XOR:
                return 70;
            case SQL_TK_BIT_OR:
                return 60;
            case SQL_TK_LOG_AND:
                return 50;
            case SQL_TK_LOG_OR:
                return 40;
            case SQL_TK_QUESTION:
                return 30;
            case SQL_TK_COMMA:
            case SQL_TK_AND:
            case SQL_TK_OR:
                return 10;
            default:
                throw new RuntimeException();
        }
    }

    public SqlNode parse() {
        SqlNode node = binary(-1);
        Assert.isTrue(touchEnd());
        return node;
    }

    public boolean touchEnd() {
        return sqlTokenPos >= sqlTokens.size();
    }

    public void handleInitExpr() {
        while (true) {
            if (consumeSqlTokenSuccess(SQL_TK_SEMICOLON)) {
                return;
            } else if (consumeSqlTokenSuccess(SQL_TK_L_BRACE)) {
                skipBlock();
            }
            skipSqlToken();
        }
    }

    public void skipBlock() {
        while(true) {
            SqlToken curSqlToken = curSqlToken();
            if (consumeSqlTokenSuccess(SQL_TK_L_BRACE)) {
                skipBlock();
            }
            if (consumeSqlTokenSuccess(SQL_TK_R_BRACE)) {
                return;
            }
            skipSqlToken();
        }

    }

    public void skipParen() {
        while(true) {
            SqlToken curSqlToken = curSqlToken();
            if (consumeSqlTokenSuccess(SQL_TK_L_PAREN)) {
                skipParen();
            }
            if (consumeSqlTokenSuccess(SQL_TK_R_PAREN)) {
                return;
            }
            skipSqlToken();
        }

    }

    public SqlToken curSqlToken() {
        if (sqlTokenPos >= sqlTokens.size()) {
            System.out.println("hit");
        }
        return sqlTokens.get(sqlTokenPos);
    }

    public boolean peekEnd(){
        if(curSqlToken().kind == SQL_TK_EOF){
            return false;
        }

        if(curSqlToken().kind == SQL_TK_R_BRACE){
            return true;
        }

        return curSqlToken().kind == SQL_TK_COMMA &&
                sqlTokens.get(sqlTokenPos + 1).kind == SQL_TK_R_BRACE;
    }


    public SqlToken consumeEnd(){
        if(peekEnd()){
            SqlToken tok;
            if(curSqlToken().kind == SQL_TK_R_BRACE){
                tok = curSqlToken();
                sqlTokenPos++;
                return tok;
            }

            if(curSqlToken().kind == SQL_TK_COMMA){
                tok = curSqlToken();
                sqlTokenPos++;
                return tok;
            }
            Assert.isTrue(false, String.format("ErrorCompileLog.java:%s, content: %s", curSqlToken().lineNum, curSqlToken().content));
        }

        return null;
    }

    public SqlToken expectSqlToken(SqlTokenKind kind){
        SqlToken curSqlToken = curSqlToken();
        if (curSqlToken.kind != kind) {
            System.out.println("hit");
        }
        Assert.isTrue(curSqlToken.kind == kind, String.format("ErrorCompileLog.java:%s, content: %s", curSqlToken().lineNum, curSqlToken().content));
        SqlToken tok = curSqlToken;
        sqlTokenPos++;
        return tok;
    }

    public boolean peekSqlToken(SqlTokenKind kind){
        return curSqlToken().kind == kind;
    }

    public boolean peekNextSqlToken(SqlTokenKind kind){
        return sqlTokens.get(sqlTokenPos + 1).kind == kind;
    }

    public SqlToken consumeSqlToken(SqlTokenKind kind){
        if(curSqlToken().kind == kind){
            SqlToken tok = curSqlToken();
            sqlTokenPos++;
            return tok;
        }
        return null;
    }

    public void skipSqlToken(){
        sqlTokenPos++;
    }

    public boolean consumeSqlTokenSuccess(SqlTokenKind kind){
        return consumeSqlToken(kind) != null;
    }
}
