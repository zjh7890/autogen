package org.example.easy.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zjh
 */
public class Block {
    BlockType type;
    Integer start;
    Integer end;
    String name;

    List<Block> all = new ArrayList<>();
    List<Block> vars = new ArrayList<>();
    List<Block> funcs = new ArrayList<>();
    List<Block> clazzes = new ArrayList<>();


}
