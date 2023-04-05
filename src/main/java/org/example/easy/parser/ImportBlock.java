package org.example.easy.parser;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static org.example.easy.parser.Unit.trimChar;

/**
 * @Author: zjh
 */
public class ImportBlock {
    Integer start;
    Integer end;
    String input;

    List<String> importNodes1;
    List<String> importNodes2;

    Unit unit;

    public ImportBlock(String input, int startPos, int endPos, Unit unit) {
        this.importNodes1 = new ArrayList<>();
        this.importNodes2 = new ArrayList<>();
        this.start = startPos;
        this.end = endPos;
        this.unit = unit;
        this.input = input;
        parse(input.substring(start, end + 1));
    }

    public void parse(String content) {
        String[] split = content.split("\n");
        boolean thirdParty = true;
        for (String s : split) {
            if (s.trim().isEmpty()) {
                continue;
            }
            thirdParty = s.matches("import\\s+java.*;");
            String importName = trimChar(s.trim().substring("import".length()).trim(), ';').trim();
            if (thirdParty) {
                importNodes2.add(importName);
            } else {
                importNodes1.add(importName);
            }
        }
    }

    public void importUnit(Unit unit) {
        String importStr = String.format("%s.%s", unit.packName, unit.clazzName);
        importName(importStr);
    }

    public void importUnits(List<Unit> units) {
        if (CollectionUtils.isEmpty(units)) {
            return;
        }
        for (Unit unit : units) {
            // 被换掉了
            this.unit.importBlock.importUnit(unit);
        }
    }

    public void importBlock(ImportBlock importBlock) {
        if (!CollectionUtils.isEmpty(importBlock.importNodes1)) {
            for (String s : importBlock.importNodes1) {
                this.unit.importBlock.importName(s);
            }
        }
        if (!CollectionUtils.isEmpty(importBlock.importNodes2)) {
            for (String s : importBlock.importNodes2) {
                this.unit.importBlock.importName(s);
            }
        }
    }

    public void importName(String name) {
        if (name.startsWith("java")) {
            if (importNodes2.contains(name)){
                return;
            }
            importNodes2.add(name);
        } else {
            if (importNodes1.contains(name)) {
                return;
            }
            importNodes1.add(name);
        }
        order();
        this.unit.replaceImport();
    }

    public void order() {
        importNodes1 = importNodes1.stream().sorted().collect(Collectors.toList());
        importNodes2 = importNodes2.stream().sorted().collect(Collectors.toList());
    }

    public static void main(String[] args) {
        String s = "import com.sankuai.grocerysmart.inf.apollo.config.MccConfig;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.dao.entity.TaskNoticeDOExample;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.dao.entity.TaskNoticeExtendConfigDOExample;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.dao.entity.TaskNoticeSetDOExample;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.dao.repository.TaskBaseInfoRepository;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.dao.repository.TaskNoticeExtendConfigRepository;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.dao.repository.TaskNoticeRepository;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.dao.repository.TaskNoticeSetRepository;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.gateway.DxPushGateway;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.service.es.EsMarkQueryService;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.service.es.EsQualityQueryService;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.service.es.EsUniversalMarkService;\n" +
                "import com.sankuai.grocerysmart.inf.apollo.service.es.EsUniversalQualityService;";
        ImportBlock block = new ImportBlock(s, 0, s.length() - 1, null);
        block.importName("java.hello");
        block.importName("com.sankuai.grocerysmart.inf.apollo.cx");
        System.out.println(block.print());
    }

    public String print() {
        StringBuilder res = new StringBuilder();
        if (CollectionUtils.isNotEmpty(importNodes1)) {
            for (String s : importNodes1) {
                res.append(String.format("import %s;\n", s));
            }
        }
        res.append("\n");
        if (CollectionUtils.isNotEmpty(importNodes2)) {
            for (String s : importNodes2) {
                res.append(String.format("import %s;\n", s));
            }
        }
        return res.toString().trim();
    }
}
