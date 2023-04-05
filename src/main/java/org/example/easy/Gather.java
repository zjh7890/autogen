package org.example.easy;

import lombok.AllArgsConstructor;
import org.example.easy.gene.ColumnParam;
import org.example.easy.gene.GenParam;
import org.example.easy.gene.InterParam;
import org.example.easy.gene.TableParam;
import org.example.easy.shepherd.ConditionItemDTO;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: zjh
 * @Date: 2022/7/30 4:49 PM
 */
public class Gather {
    @AllArgsConstructor
    public static class Col {
        String name;
        Class clazz;
        Boolean nullable;

        public Col(String name, Class clazz) {
            this.name = name;
            this.clazz = clazz;
            this.nullable = false;
        }
    }

    public static GenParam ttSkuInters = new GenParam(
            "TtMarkService", "TtMarkQueryThriftServiceImpl", "/tt",
            Arrays.asList(new GenParam.Schema("GgSku", null, "id", null)),
            Arrays.asList(
                    GenParam.F.buildQuery(null, null, "mysql", "TtMarkInfo",
                            GenParam.Selector.buildPlain(false, Arrays.asList("createdTime")),
                            "ttCreatedTime desc",
                            null,
                            Arrays.asList(),
                            "cityId = $cityId and bizDt = $bizDt"),
                    GenParam.F.buildQuery("queryEsMarkInfo", null, "es", "ArtsMarkInfo",
                            GenParam.Selector.buildAggr(Arrays.asList(new GenParam.AggrCol("count", false, "*"))),
                            "createdTime desc",
                            15,
                            Arrays.asList("cityId", "bizDt"),
                            "cityId = $cityId and bizDt = $bizDt"),
                    GenParam.F.buildDelete(null, null, "mysql", "TtSku")
            ),
            Arrays.asList(
                    new InterParam("enum", "ZjhTypeEnum", "通知类型枚举", "/zjh_types",
                            Arrays.asList(new Col("value", Integer.class),
                                    new Col("desc", String.class),
                                    new Col("name", String.class, true)),
                            Arrays.asList(
                                    Arrays.asList("DD", 1, "dd纸单", "name"),
                                    Arrays.asList("TCC", 3, "tcc纸单", "name"),
                                    Arrays.asList("XS", 4, "xs纸单", null)
                            ), ConditionItemDTO.class),
                    new InterParam("enum","ZjhTypeEnum1", "通知类型枚举", "/zjh_types",
                            Arrays.asList(new Col("value", Integer.class),
                                    new Col("desc", String.class)),
                            Arrays.asList(
                                    Arrays.asList("DD", 1, "dd纸单"),
                                    Arrays.asList("TCC", 3, "tcc纸单"),
                                    Arrays.asList("XS", 4, "xs纸单")
                            ), ConditionItemDTO.class),
                    new InterParam("lion","ZJH_MINUTES_INTERVAL", "通知类型枚举", "/zjh_types",
                            Arrays.asList(new Col("value", Integer.class),
                                    new Col("desc", String.class)),
                            Arrays.asList(
                                    Arrays.asList("DD", 1, "dd纸单"),
                                    Arrays.asList("TCC", 3, "tcc纸单"),
                                    Arrays.asList("XS", 4, "xs纸单")
                            ), null)
            ),
            Arrays.asList("MccConfig")
    );
}
