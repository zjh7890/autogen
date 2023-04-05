package org.example.easy.parser;

/**
 * @Author: zjh
 */
public class Node {
    Node next;
    Node nested;
    NodeKind kind;
    Node lhs;
    Node rhs;
    String type;
    long val;

    Integer pos;  // for goto expr, pos stores the dest label
    int len;
    Node args;

    Var member;

    Var var;

    // conditional
    Node cond;
    Node then;
    Node els;
    Node init;
    Node incre;
    // switch, expressions at then
    int labelSeq;
}
