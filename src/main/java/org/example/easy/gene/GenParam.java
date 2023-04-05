package org.example.easy.gene;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: zjh
 */
@AllArgsConstructor
@Data
public class GenParam {
//    %sService
    public Object serviceClazz;

//    %sThriftServiceImpl
    public Object implClazz;

    public String urlPrefix;

    /**
     * {@link Schema} or Class or String
     */
    public List<Object> schemas;

    public List<F> params;

    public List<InterParam> interParams;

    public List<Object> clazzLink;

    @Data
    @AllArgsConstructor
    public static class Schema {
        Object table;
        String mainKey;
        String key;
        public List<Schema> subs;
    }

//         "cascader def class or unit",
//                 "query /url mysql main [selector plain_record exclude sub include] condition [bizDt, cityId] bizDt = $bizDt and cityId = $cityId order by export flat",
//                 "delete mysql cascade",
//                 "upsert mysql main exclude sub exclude",
//
//                 "tt_sku class or unit",
//                 "query mysql /url main exclude condition ... export flat order by",
//
//                 "mark_info_index mark or enum MarkEsService.class",
//                 "query /url queryName es index [aggr(group by) max() as misMarkExport /min()/sum()/count()] condition order by export"
    public static class Selector {
        // "plain" "aggr"
        public String type;

        // plain
        public Boolean include;
        public List<String> cols;

        // aggr
        public List<AggrCol> aggrCols;

        public static Selector buildPlain(Boolean include, List<String> cols) {
            Selector selector = new Selector();
            selector.type = "plain";
            selector.include = include;
            selector.cols = cols;
            return selector;
        }

        public static Selector buildAggr(List<AggrCol> aggrCols) {
            Selector selector = new Selector();
            selector.type = "aggr";
            selector.aggrCols = aggrCols;
            return selector;
        }

    }

    public static class Updater {
        // plain
        public Boolean include;
        public List<String> cols;

        public List<Updater> updaters;
    }

    public static class UpdateCol {
        public String init;
        public Boolean update;
        public Boolean insert;
    }

    @AllArgsConstructor
    public static class AggrCol {
        // min sum count
        public String type;
        public Boolean distinct;
        public String col;
    }

    @Data
    public static class F {
        // cascader clazz unit
        public String type;
        public String name;
        public String url;
        public String dbType;
        public Object schema;

        // query
//        Map<String, Selector> or Object
        public Object selector;
        public String order;
        public Integer exportType;

        // upsert
        public Map<String, Updater> updaterMap;

        public List<String> conditions;
        public String whereClause;

        public static F buildQuery(String name,
                                   String url,
                                   String dbType,
                                   Object schema,
                                   Object selectorMap,
                                   String order,
                                   Integer exportType,
                                   List<String> conditions,
                                   String whereClause) {
            F f = new F();
            f.type = "query";
            // name 为 null 就剩成默认的名字
            f.name = name;
            // 为 null 就剩成默认的 url
            f.url = url;
            f.dbType = dbType;
            f.schema = schema;
            f.selector = selectorMap;
            f.order = order;
            f.exportType = exportType;
            f.conditions = conditions;
            f.whereClause = whereClause;
            return f;
        }

        public static F buildDelete(String name,
                                    String url,
                                    String dbType,
                                    Object schema) {
            F f = new F();
            f.type = "delete";
            // name 为 null 就剩成默认的名字
            f.name = name;
            // 为 null 就剩成默认的 url
            f.url = url;
            f.dbType = dbType;
            f.schema = schema;
            return f;
        }
    }
}
