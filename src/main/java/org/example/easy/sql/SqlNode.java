package org.example.easy.sql;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zjh
 * @Date: 2022/8/16 7:14 PM
 */
@NoArgsConstructor
@Data
public class SqlNode {
    public SqlNodeKind nodeKind;
    public SqlNode lhs;
    public SqlNode rhs;
    public SqlToken token;
    public String content;

    public SqlNode(SqlNodeKind kind, SqlToken tok){
        this.nodeKind = kind;
        this.token = tok;
        this.content = tok.content;
    }

    public SqlNode(SqlNodeKind kind, SqlNode lhs, SqlNode rhs, SqlToken tok){
        this.nodeKind = kind;
        this.lhs = lhs;
        this.rhs = rhs;
        this.token = tok;
        this.content = tok.content;
    }

    public static SqlNode buildToken(SqlNodeKind kind, SqlNode lhs, SqlNode rhs) {
        if (kind == SqlNodeKind.SQL_ND_AND) {
            SqlNode sqlNode = new SqlNode();
            sqlNode.setNodeKind(SqlNodeKind.SQL_ND_AND);
            sqlNode.setLhs(lhs);
            sqlNode.setRhs(rhs);
            sqlNode.setToken(null);
            sqlNode.setContent("and");
            return sqlNode;
        } else if (kind == SqlNodeKind.SQL_ND_OR) {
            SqlNode sqlNode = new SqlNode();
            sqlNode.setNodeKind(SqlNodeKind.SQL_ND_OR);
            sqlNode.setLhs(lhs);
            sqlNode.setRhs(rhs);
            sqlNode.setToken(null);
            sqlNode.setContent("or");
            return sqlNode;
        } else {
            throw  new RuntimeException();
        }
    }

}
