package org.example.easy;

import lombok.extern.slf4j.Slf4j;


/**
 * @Author: zjh
 * @Date: 2022/6/9 12:47 PM
 */
@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {ApplicationLoader.class})
public class CheckTest
//        extends TestCase
{
//    @Test
//    public void testTop(){
//        putTrace("zjh");
//        test02();
//    }
//
//    @Test
//    public void test05() {
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8");
//        CraneUtils.runByNoArgWrapper("quotaCal mark 计算",
//                () -> {
//                    String ids;
//                    ids = "6152571,6156106,6158366,6158370,6158296,6158357,6152107,6151746,6157423,6157441,6150658,6150602,6150277,6141962,6141907,6150066,6156120,6155978,6155931,6149597,6141570,6148605,6155748,6155557,6155419,6148492,6148438,6155161,6147868,6154383,6131871,6131818,6129149,6140098,6141833,6167534,6172610,6167537,6167261,6167219,6172322,6167205,6172090,6171011,6170499,6165227,6152857,6169939,6169528,6161500,6169856,6161476,6163846,6163894,6169354,6163005,6162863,6161139,6169226,6168770,6168660,6168667,6168636,6164657,6167689,6153176,6167691,6192836,6203124,6203048,6202851,6192089,6192071,6182595,6200018,6199691,6186050,6190353,6182936,6190356,6190352,6186032,6185832,6185658,6185711,6199175,6188291,6188288,6183691,6197924,6197657,6180078,6197160,6188159,6196958,6196257,6179590,6194158,6180707,6180619";
//                    ids = "6263063";
//                    List<Long> idList = Arrays.stream(ids.split(",")).map(String::trim).map(Long::valueOf).collect(Collectors.toList());
//                    return idList;
//                },
//                originId -> {
//                    OriginData originData = queryOriginOn(originId);
//                    originDataService.processAfterOcr(originData);
//                    return true;
//                },
//                x -> x,
//                true,
//                true,
//                "",
//                1
//        );
//    }
//
//    @Test
//    public void test03() {
//        CommonReq req = new CommonReq();
//        req.setAction("quotaCal");
//        req.setTable("origin");
////        req.setSql("origin_data_id = 5142073");
//        req.setSql("created_time >= '2022-10-27 00:00:00' and created_time < '2022-10-28 00:00:00'");
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 3 and grocery_city_name in ('深圳', '成都')");
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 4 and grocery_city_name in ('岳阳', '长沙')");
//
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 1 and grocery_city_name in ('上海')");
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 4 and grocery_city_name in ('岳阳')");
//
//
////        req.setSql("id in ('6202653')");
////        req.setSql("id in ('6179623')");
////        req.setSql("id in ('6261826')");
//
////        req.setSql("mark_id in ('522146')");
//
//        req.setLimit(1000);
//        req.setOffset(0);
//        req.setMis("zjh");
//        req.setQuota(0);
//        req.setMulti(true);
//        req.setThread("4");
//        markRefresh(req);
//    }
//
//    @Test
//    public void test02() {
//        CommonReq req = new CommonReq();
//        req.setAction("quotaCal");
//        req.setTable("mark");
////        req.setSql("origin_data_id = 5142073");
//        req.setSql("created_time >= '2022-10-27 00:00:00' and created_time < '2022-10-28 00:00:00'");
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 3 and grocery_city_name in ('深圳', '成都')");
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 4 and grocery_city_name in ('岳阳', '长沙')");
//        req.setLimit(1000);
//        req.setOffset(0);
//        req.setMis("zjh");
//        req.setQuota(0);
//        req.setMulti(true);
//        req.setThread("4");
//
//        QuotaRes res = quotaCal(req);
//        System.out.println("success");
//    }
//
//    @Test
//    public void test04() {
//        CommonReq req = new CommonReq();
//        req.setSql("created_time > '2022-10-27 00:00:00' and created_time < '2022-10-28 00:00:00' and data_type = 4");
////        req.setSql("mark_id = '13481726'");
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 3 and grocery_city_name in ('深圳', '成都')");
////        req.setSql("created_time >= '2022-08-01 00:00:00' and created_time < '2022-08-06 00:00:00' and data_type = 4 and grocery_city_name in ('岳阳', '长沙')");
//        req.setLimit(300);
//        req.setOffset(0);
//        req.setMulti(true);
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "6");
//
//        CopyOnWriteArraySet<String> systemMarks = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> realTimeSystemMarks = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> manualMarks = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> allMarks = new CopyOnWriteArraySet<>();
//
//        AtomicInteger originLack = new AtomicInteger();
//
//        CraneUtils.runByNoArgWrapper("quotaCal mark 计算",
//                () -> select(markInfoMapper, new MarkInfoDOExample(), req.getSql(), req.getLimit(), req.getOffset()),
//                markInfoDO -> {
//                    ManualMarkInfo markInfo = markInfoRepository.queryByOriginDataIdWithBlobs(markInfoDO.getOriginDataId());
//                    OriginData originData = originDataRepository.queryByIdWithBlob(markInfoDO.getOriginDataId());
//                    if (originData == null) {
//                        originLack.getAndIncrement();
//                        return false;
//                    }
//                    DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(markInfo.getDataType());
//
//                    // 线下实时解析数据
//                    DataSyncModel model = buildModel(originData);
//                    String originDataContent = originData.getOriginData();
//                    DataSyncModel.SynCommonData data = JsonKits.parse(originDataContent, DataSyncModel.SynCommonData.class);
//
//                    DeliveryPickupData matched = deliveryProcessor.buildDeliveryPickupData(model, data, dataTypeEnum);
//                    if (TaskTypeEnum.SYSTEM.getValue().equals(markInfo.getMarkType())) {
//                        systemMarks.add(markInfo.getMarkId());
//                    } else {
//                        manualMarks.add(markInfo.getMarkId());
//                    }
//                    TaskTypeEnum realTime = taskTypeService.initTaskType(matched, dataTypeEnum);
//                    if (realTime == TaskTypeEnum.SYSTEM) {
//                        realTimeSystemMarks.add(markInfo.getMarkId());
//                    }
//                    allMarks.add(markInfo.getMarkId());
//                    return true;
//                },
//                MarkInfoDO::getMarkId,
//                req.getMulti(),
//                true,
//                "",
//                1);
//        double percent = 0;
//        int systemSize = systemMarks.size();
//        int allSize = allMarks.size();
//        if (allSize != 0) {
//            percent = systemSize / (double)allSize;
//        }
//        String join = StringUtils.join(manualMarks, ",");
//        ArrayList<String> newAddList = new ArrayList<>(realTimeSystemMarks);
//        newAddList.removeAll(systemMarks);
//        System.out.println("success");
//    }
//
//    public static String FORMAT_OFFLINE_CONFIG =
//            "_lxsdk_cuid=18026229f44c8-0e5817713e4472-35736a03-1fa400-18026229f44c8; _lxsdk=18026229f44c8-0e5817713e4472-35736a03-1fa400-18026229f44c8; s_u_745896=ci5LRcz25TwIGi23/WAjuA==; moa_deviceId=69288A01DEBC5EE097105FE32CAA69BE; uu=24dd1360-0190-11ed-b57a-73d370c5dbc4; cid=1; misid=zjh; ssoid=eAGFjq9LQ1EYQHlb8GGSgWB8cVsY3_3uj_ddk2_ziVFYECyy--73mhqsBmexmBYULSoTFGEaLDZRUPwPVDAYBptmi1hUxGw75RxOGIy9fPYL0c7xe-cGMDRgUZC2kxEBxZadl5k2ymFuGbmaZUZJDw68rj8Gpco8u2bGy7xmLBIlIKbTekOnKdhYgJ5JJTaSxNh6Gp18bD7cQrmA_4bpZ2iqOHv31N2-hrmL57f7HmwEtdGR5tJqY8VzaXyw3-tfHb6ebg3P24OD9vDs6BsmitH65W6l_Ct0gvBvbi-oOcnaoxA-9saAU8KwY6kMxsRITIvCGKGsjbW2ihYiJpToPRNXjcqFJqnISrKALcjzlvkCK_lpZw**eAEFwQkBACAIA8BKyoZIHPn6R_DuNPxulCozNQa-EWScjgooRihpi_eBuRiTLS21pJ4V3T414BGp**ttmXbdpomBnfZsuMM5q-anZig2qYMU7yMs1k9uTarTXvZnHGJgdMRZY1BzDTPqTcN-zFpeGpgODNDduVIyrKbA**NTg0ODU0NCx6aHVqdW5odWEwMyzmnLHkv4rljY4semh1anVuaHVhMDNAbWVpdHVhbi5jb20sMSwwMzIyODYwMSwxNjY3MDIzODM2MDU1; yun_portal_ssoid=eAGFjq9LQ1EYQHlb8GGSgWB8cVsY3_3uj_ddk2_ziVFYECyy--73mhqsBmexmBYULSoTFGEaLDZRUPwPVDAYBptmi1hUxGw75RxOGIy9fPYL0c7xe-cGMDRgUZC2kxEBxZadl5k2ymFuGbmaZUZJDw68rj8Gpco8u2bGy7xmLBIlIKbTekOnKdhYgJ5JJTaSxNh6Gp18bD7cQrmA_4bpZ2iqOHv31N2-hrmL57f7HmwEtdGR5tJqY8VzaXyw3-tfHb6ebg3P24OD9vDs6BsmitH65W6l_Ct0gvBvbi-oOcnaoxA-9saAU8KwY6kMxsRITIvCGKGsjbW2ihYiJpToPRNXjcqFJqnISrKALcjzlvkCK_lpZw**eAEFwQkBACAIA8BKyoZIHPn6R_DuNPxulCozNQa-EWScjgooRihpi_eBuRiTLS21pJ4V3T414BGp**ttmXbdpomBnfZsuMM5q-anZig2qYMU7yMs1k9uTarTXvZnHGJgdMRZY1BzDTPqTcN-zFpeGpgODNDduVIyrKbA**NTg0ODU0NCx6aHVqdW5odWEwMyzmnLHkv4rljY4semh1anVuaHVhMDNAbWVpdHVhbi5jb20sMSwwMzIyODYwMSwxNjY3MDIzODM2MDU1; s_m_id_3299326472=AwMAAAA5AgAAAAIAAAE9AAAALED52xMa0lnVbc2q/+YVoSWUrKXWACCKMGbsHaMP0O7vGIMClXzbq9fiKr4PAAAAI5DkHFID1p4uBvOponqlsjqJZNNRfYRQIL9kCNBXBHtNOAzq; logan_session_token=ssesv5ph5z6vge4o2zqs; _lxsdk_s=1841914a8d9-dcb-7b1-3aa||381"
//            ;
//
//
//
//    @Data
//    public static class CommonReq {
//        String action;
//        String table;
//        String sql;
//        Integer limit;
//        Integer offset;
//        String mis;
//        Integer quota;
//        Boolean multi;
//        String thread;
//    }
//
//    @AllArgsConstructor
//    @Data
//    public static class AnalyzeRes {
//        String markId;
//        String url;
//        String urlMd5;
//        String picDirection;
//
//        List<AnalyzeItem> items;
//    }
//
//    public boolean quotaCalInner(String markId, Long originId, QuotaRes res) {
//        // markId 有线上线下区别
//        OriginData originData;
//        ManualMarkInfo markInfo;
//
//        // 1. 查询
//        markInfo = markInfoRepository.queryByOriginDataIdWithBlobs(originId);
////        markInfo = queryFromOn(originId);
//
////        originData = queryOriginOn(originId);
//        originData = originDataRepository.queryByIdWithBlob(originId);
//
//        // 2. 无原始数据不进行计算
//        if (originData == null) {
//            res.originLack.getAndIncrement();
//            return true;
//        }
//        @Nullable String picDirection = getMarkProperty(markId, PropertyEnum.PIC_DIRECTION);
//        res.totalValid.getAndIncrement();
//
//        // 3. 进行解析
////        CUR_MARK_ID.set(markId);
//        DataTypeEnum dataTypeEnum = DataTypeEnum.valueOf(markInfo.getDataType());
//        DataSyncModel.SynCommonData commonData = JsonKits.parse(originData.getOriginData(), DataSyncModel.SynCommonData.class);
//        DeliveryPickupData formatData = FormatUtils.parseOcrData(commonData.getData(), dataTypeEnum);
//        DataSyncModel dataSyncModel = OriginDataService.buildModel(originData);
//        autoMarkService.autoFillPoiInfo(dataSyncModel, formatData);
//
////        if (taskTypeEnum == TaskTypeEnum.SYSTEM || queryFromOn.getMarkType().equals(TaskTypeEnum.SYSTEM.getValue())) {
////            if (taskTypeEnum == TaskTypeEnum.SYSTEM && queryFromOn.getMarkType().equals(TaskTypeEnum.SYSTEM.getValue())) {
////                if (queryFromOn.getRejectStatus().equals(RejectStatusEnum.REJECT.getValue())) {
////                    res.taskTypeCommonReject.add(markId);
////                } else {
////                    res.taskTypeCommon.add(markId);
////                }
////            } else if (taskTypeEnum == TaskTypeEnum.SYSTEM) {
////                res.taskTypeOffline.add(markId);
////            } else if (queryFromOn.getMarkType().equals(TaskTypeEnum.SYSTEM.getValue())) {
////                if (queryFromOn.getRejectStatus().equals(RejectStatusEnum.REJECT.getValue())) {
////                    res.taskTypeOnlineReject.add(markId);
////                } else {
////                    res.taskTypeOffline.add(markId);
////                }
////            }
////        }
//
//        // 分拣类型
//        TaskTypeEnum taskTypeEnum = taskTypeService.initTaskType(formatData, dataTypeEnum);
//        if (taskTypeEnum == TaskTypeEnum.SYSTEM) {
//            res.systemMarkRealTime.getAndIncrement();
//        }
//        if (TaskTypeEnum.SYSTEM.getValue().equals(markInfo.getMarkType())) {
//            res.systemMarkAll.getAndIncrement();
//        }
//
//
//        // 4.1 图片质量指标计算
//        Assert.isTrue(markInfo.getPicQuality() != null);
//        Assert.isTrue(formatData.getPicQuality() != null);
//        boolean skip = false;
//        if (!formatData.getPicQuality() || markInfo.getPicQuality().equals(PicQualityEnum.UNQUALIFIED.getValue())) {
//            if (!formatData.getPicQuality() && markInfo.getPicQuality().equals(PicQualityEnum.UNQUALIFIED.getValue())) {
//                res.unqualified.add(markId);
//            } else if (!formatData.getPicQuality()) {
//                res.unqualifiedFormat.add(markId);
//            } else if (markInfo.getPicQuality().equals(PicQualityEnum.UNQUALIFIED.getValue())) {
//                res.unqualifiedMark.add(markId);
//            } else {
//                Assert.isTrue(false);
//            }
//            skip = true;
//        }
//        // 4.2 图片版式
//        Assert.isTrue(markInfo.getTemplateLayout() != null);
//        Assert.isTrue(formatData.getFit() != null);
//        if (!formatData.getFit() || markInfo.getTemplateLayout().equals(TemplateLayoutStatus.UN_FIT.getValue())) {
//            if (!formatData.getFit() && markInfo.getTemplateLayout().equals(TemplateLayoutStatus.UN_FIT.getValue())) {
//                res.unfit.add(markId);
//            } else if (!formatData.getFit()) {
//                res.unfitFormat.add(markId);
//            } else if (markInfo.getTemplateLayout().equals(TemplateLayoutStatus.UN_FIT.getValue())) {
//                res.unfitMark.add(markId);
//            } else {
//                Assert.isTrue(false);
//            }
//            skip = true;
//        }
//        // 不合格图片跳过
//        if (skip) {
//            return true;
//        }
//
//        ServiceResponse ocrDataRes = JsonKits.parse(commonData.getData(), ServiceResponse.class);
//        int table = (int) ocrDataRes.getResult_info().getDoc_tree().getNodes().values().stream().filter(x -> x.getRegion_type().equals("table") && x.getTable_attributes().getN_rows() > 6).count();
//        int size = (int) formatData.getContent().stream().filter(x -> BillTypeEnum.getByAliasElseException(x.getType().getValue()).getMajor()).count();
//        if (table >= 2 && size == 1) {
//            res.getMergedError().add(markId);
//        }
//
//        // Content
//        List<DeliveryPickupData.Content> markContents = JsonKits.parseList(markInfo.getMarkData(), DeliveryPickupData.Content.class);
//
////        ManualMarkInfo formatMark = markInfoRepository.queryByOriginDataIdWithBlobs(originId);
////        List<DeliveryPickupData.Content> formatContents = JsonKits.parseList(formatMark.getMarkData(), DeliveryPickupData.Content.class);
//
//        List<DeliveryPickupData.Content> formatContents = formatData.getContent();
//
////        List<DeliveryPickupData.Content> formatContents = JsonKits.parseList(queryBindOn(originId).getDataBindResult(), DeliveryPickupData.Content.class);
//
////        formatContents = formatContents.stream().filter(x -> !BillTypeEnum.isMargin(x.getType().getValue())).collect(Collectors.toList());
//
//        checkTypeEquals(markContents, formatContents);
//        if (markContents.size() < formatContents.size()) {
//            res.deleteForms.add(markId);
//            return true;
//        }
//        if (markContents.size() > formatContents.size()) {
//            res.addForms.add(markId);
//            return true;
//        }
//        if (containsEmptyForm(markContents) || containsEmptyForm(formatContents)) {
//            res.emptyForms.add(markId);
//            return true;
//        }
//        Assert.isTrue(!containsEmptyIdForm(formatContents));
//        if (containsEmptyIdForm(markContents)) {
//            res.emptyIdForms.add(markId);
//            return true;
//        }
//        if (!checkContentPos(formatContents)) {
//            res.typeDisorderForms.add(markId);
//            return true;
//        }
//
//        QuotaRes.TableQuotaRes tableRes = new QuotaRes.TableQuotaRes();
//        tableRes.markId = markId;
//        Ref<Boolean> tableBad = new Ref<>(false);
//
//        DeliveryPickupData.Content markDeliveryContent = null;
//        DeliveryPickupData.Content markPickupContent = null;
//        DeliveryPickupData.Content markMarginContent = null;
//        DeliveryPickupData.Content formatDeliveryContent = null;
//        DeliveryPickupData.Content formatPickupContent = null;
//        DeliveryPickupData.Content formatMarginContent = null;
//
//
//        Triple<DeliveryPickupData.Content, DeliveryPickupData.Content, DeliveryPickupData.Content> markDispatch =
//                dispatchContent(markContents);
//        markDeliveryContent = markDispatch.getLeft();
//        markPickupContent = markDispatch.getMiddle();
//        markMarginContent = markDispatch.getRight();
//        Triple<DeliveryPickupData.Content, DeliveryPickupData.Content, DeliveryPickupData.Content> formatDispatch =
//                dispatchContent(formatContents);
//        formatDeliveryContent = formatDispatch.getLeft();
//        formatPickupContent = formatDispatch.getMiddle();
//        formatMarginContent = formatDispatch.getRight();
//
//        AnalyzeItem tpl = new AnalyzeItem(markId, markInfo.getShowUrl(), originData.getUrlMd5(), picDirection, null, null);
//
//        compareContent(markDeliveryContent, formatDeliveryContent, res, tableRes, tableBad, markId, dataTypeEnum, tpl, markInfo, originData);
//        compareContent(markPickupContent, formatPickupContent, res, tableRes, tableBad, markId, dataTypeEnum, tpl, markInfo, originData);
////        compareContent(markMarginContent, formatMarginContent, res, tableRes, tableBad, markId, dataTypeEnum, tpl);
//        if (tableBad.getValue()) {
//            res.tableColChange.add(tableRes);
//        }
//        return true;
//    }
//
//    public static List<Integer> getIds(DeliveryPickupData.Content content) {
//        // 前面已经把为空的排除了，这里不会有问题
//        List<DeliveryPickupData.DeliveryPickTableData> tableData = content.getInfos().getTable().getTableData();
//        HashSet<Integer> ids = new HashSet<>();
//        for (DeliveryPickupData.DeliveryPickTableData tableDatum : tableData) {
//            List<Integer> idList = ObjectUtils.object2Map(tableDatum).values().stream().filter(Objects::nonNull)
//                    .filter(x -> x.getClass() == BillTexts.class)
//                    .map(x -> ((BillTexts) x).getId()).filter(Objects::nonNull).collect(Collectors.toList());
//            ids.addAll(idList);
//        }
//        return new ArrayList<>(ids);
//    }
//
//    public static boolean judgeModifyTable(DeliveryPickupData.Content markContent,
//                                           DeliveryPickupData.Content formatContent) {
//        List<Integer> markIds = getIds(markContent);
//        List<Integer> formatIds = getIds(formatContent);
//        markIds.retainAll(formatIds);
//        return markIds.size() == 0;
//    }
//
//
//
//    public static AnalyzeItem buildItem(AnalyzeItem tpl, String key, List<String> values) {
//        AnalyzeItem res = DeepCloneUtils.copy(tpl);
//        res.setKey(key);
//        res.setValues(values);
//        return res;
//    }
//
//    @SneakyThrows
//    public static List<String> callEs(CompanyEnum companyEnum, String skuName) {
//        String matchPhraseReqFormat =
//                "{\"clusterName\":\"eaglenode_es-smartinquiry\",\"indexName\":\"latest_sku\",\"resultType\":\"json\",\"source\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"match_phrase\\\":{\\\"skuName\\\":\\\"%s\\\"}},{\\\"term\\\":{\\\"companyId\\\":\\\"%s\\\"}}],\\\"must_not\\\":[],\\\"should\\\":[],\\\"filter\\\":[]}},\\\"from\\\":0,\\\"size\\\":\\\"5\\\",\\\"sort\\\":[],\\\"profile\\\":false}\"}";
//        String matchPhraseReq = String.format(matchPhraseReqFormat, skuName, companyEnum.getValue());
//        String simpleUrl = "https://es.sankuai.com/webapi/search/simple";
//
//        Object obj = reqEs(simpleUrl, matchPhraseReq);
//        List<String> res = new React(obj).get("data").get("hits")
//                .get("hits").listGet("_source").listGet("skuName").expose();
//        if (CollectionUtils.isNotEmpty(res)) {
//            return res;
//        }
//        return null;
//
////        String expertUrl = "https://es.sankuai.com/webapi/search/expert";
////        String matchReq = String.format(
////                "{\"source\":\"{\\\"query\\\":{\\\"bool\\\":{\\\"must\\\":[{\\\"match\\\":{\\\"skuName\\\":{\\\"query\\\":\\\"%s\\\",\\\"operator\\\":\\\"or\\\"}}},{\\\"term\\\":{\\\"companyId\\\":\\\"%s\\\"}}],\\\"must_not\\\":[],\\\"should\\\":[],\\\"filter\\\":[]}},\\\"from\\\":0,\\\"size\\\":\\\"10\\\",\\\"sort\\\":[],\\\"profile\\\":false}\",\"clusterName\":\"eaglenode_es-smartinquiry\",\"searchBy\":\"alias\",\"indexName\":\"latest_sku\",\"pathType\":\"Search\",\"path\":\"/_search\",\"method\":\"POST\",\"id\":\"\",\"pipelineId\":\"\"}"
////                ,
////                skuName, companyEnum.getValue()
////        );
////        Object obj2 = reqEs(expertUrl, matchReq);
////        return new React(obj2).get("data").get("result").get("hits")
////                .get("hits").listGet("_source").listGet("skuName").expose();
//    }
//
//    public static final OkHttpClient client = new OkHttpClient().newBuilder().build();
//
//    @SneakyThrows
//    public static Object reqEs(String url, String req) {
//        MediaType mediaType = MediaType.parse("application/json");
//        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, req);
//        Request request = new Request.Builder()
//                .url(url)
//                .method("POST", body)
//                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"")
//                .addHeader("Accept", "application/json, text/plain, */*")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("sec-ch-ua-mobile", "?1")
//                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.64 Mobile Safari/537.36")
//                .addHeader("sec-ch-ua-platform", "\"Android\"")
//                .addHeader("Cookie",
//                        "_lxsdk_cuid=18026229f44c8-0e5817713e4472-35736a03-1fa400-18026229f44c8; _lxsdk=18026229f44c8-0e5817713e4472-35736a03-1fa400-18026229f44c8; s_u_745896=ci5LRcz25TwIGi23/WAjuA==; moaDeviceId=69288A01DEBC5EE097105FE32CAA69BE; moa_deviceId=69288A01DEBC5EE097105FE32CAA69BE; _lx_utm=utm_source%3Dxm; s_m_id_3299326472=AwMAAAA5AgAAAAIAAAE9AAAALFnrbDa1kcRaOZg8HhHnZvUtiGoiC7BUr0a2p02eT8Iqorpz3Jwl34IQVwtQAAAAI3NeGo+yzwEixie2iP77jDOyW0q+bq8uCLBkzxZPUjyStagU; 7de570fff2_ssoid=eAGFjjtLw1AYhkkQKZ0kk2PGNoN837kfJ5MacfQyCC5ycnLOqH_AoekguHgDBYWCQsFBUJwU94KDoz_AwUvWzoJYEWe3h5eHl6cRTL18voZx92H0dAikKUvHJXjvyWzMDeWWWjRQIDPGKMNEwoz1Qjlttc6eg6i95opV6zbdttBEqRRwPs86PM9BSwS-kFPSSVOhszyuhr2dY2iF5N9j9ZM0Fy7ujfrDA1i6-zp53IdekDQnl1c6W6WLorfBZX178b579XFe1deD-qaanoi796ft1q98FDT-ws6CGVlQdIKg0gL5mA0wKXA8CI-cW9xAwSljnBLkUq7H3jCCsgQrE2DElUo4Ig0xBdO0NBa_AWH0Zf4**eAEFwYEBwCAIA7CXoIDOcyja_09YklTudxGrsKrdR9pp3aO8MCyx8TbPzadhHYxxFG6MEr4fUegSXg; webNewUuid=7b31e62189615b31a047616216f155c1_1653445321577; _lxsdk_s=1810a2945f4-fdc-217-355%7C%7C23"
//                )
//                .build();
//        Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            return JsonKits.parse(response.body().string(), Object.class);
//        } finally {
//            if (response != null) {
//                response.close();
//            }
//        }
//    }
//
//    public void compareContent(DeliveryPickupData.Content markContent,
//                               DeliveryPickupData.Content formatContent,
//                               QuotaRes res,
//                               QuotaRes.TableQuotaRes tableRes,
//                               Ref<Boolean> tableBad,
//                               String markId,
//                               DataTypeEnum dataTypeEnum, AnalyzeItem tpl, ManualMarkInfo markInfo, OriginData originData) {
//
//        if (markContent == null && formatContent == null) {
//            return;
//        }
//        if (markContent == null || formatContent == null || judgeModifyTable(markContent, formatContent)) {
//            res.modifiedForms.add(markId);
//            return;
//        }
//
//        // 测试系统标注增加减少
//        List<DeliveryPickupData.Content> newContent = JsonKits.parseList(markInfo.getMarkData(), DeliveryPickupData.Content.class);
//        DeliveryPickupData pickupData = new DeliveryPickupData();
//        pickupData.setContent(newContent);
//        pickupData.setFit(TemplateLayoutStatus.FIT.getValue().equals(markInfo.getTemplateLayout()));
//        pickupData.setPicQuality(PicQualityEnum.QUALIFIED.getValue().equals(markInfo.getPicQuality()));
//
//        DataSyncModel dataSyncModel = OriginDataService.buildModel(originData);
//        if (TaskTypeEnum.SYSTEM.getValue().equals(markInfo.getMarkType())) {
//            res.systemMarkQualified.add(markId);
//            if (RejectStatusEnum.REJECT.getValue().equals(markInfo.getRejectStatus())) {
//                res.systemReject.add(markId);
//            }
//        }
//
//        for (DeliveryPickupData.DeliveryPickTableData.Column value : DeliveryPickupData.DeliveryPickTableData.Column.values()) {
//            String prop = value.getValue();
//            List<Integer> markIds = getTableSpecificColumn(Arrays.asList(markContent), prop).stream()
//                    .map(x -> x.getId()).filter(Objects::nonNull).collect(Collectors.toList());
//            List<Integer> formatIds = getTableSpecificColumn(Arrays.asList(formatContent), prop).stream()
//                    .map(x -> x.getId()).filter(Objects::nonNull).collect(Collectors.toList());
//            if (CollectionUtils.isEmpty(markIds)) {
//                continue;
//            }
//            res.markColTotal.getAndIncrement();
//            int size = formatIds.size();
//            markIds.removeAll(formatIds);
//            if ((double) markIds.size() / size <= 0.2) {
//                res.markColSuccess.getAndIncrement();
//            } else {
//                res.markColFailedCount.getAndIncrement();
//                TryCatchUtils.exec(markId, true, x -> res.markColFailed.get(prop).add(x));
//            }
//        }
////        if (true) {
////            return;
////        }
//
//        BillTypeEnum billTypeEnum = BillTypeEnum.getByAliasElseException(markContent.getType().getValue());
//        res.getPaperSetGroup().getItems().add(buildItem(tpl, billTypeEnum.getKey(), Arrays.asList()));
//        if (billTypeEnum == BillTypeEnum.PICKUP) {
//            res.pickupForm.add(markId);
//            if (CollectionUtils.isNotEmpty(formatContent.getInfos().getTexts())) {
//                res.pickupNotEmpty.add(markId);
//            }
//        } else if (billTypeEnum == BillTypeEnum.DELIVERY) {
//            res.deliveryForm.add(markId);
//        }
//
//        // 表单
//        List<BillTexts> markTexts = markContent.getInfos().getTexts();
//        List<BillTexts> formatTexts = formatContent.getInfos().getTexts();
//
//        Map<String, BillTexts> markTextsMap = markTexts.stream().collect(Collectors.toMap(BillTexts::getKey, x -> x));
//        Map<String, BillTexts> formatTextsMap = formatTexts.stream().collect(Collectors.toMap(BillTexts::getKey, x -> x));
//        List<String> textKeys = getKeys(markTextsMap, formatTextsMap);
//        // 表单 badcase
//        for (String key : textKeys) {
//            Assert.isTrue(key != null);
//            BillTexts mark = markTextsMap.get(key);
//
//            BillTexts format = formatTextsMap.get(key);
//            String markValue = getTextValue(mark);
//            String formatValue = getTextValue(formatTextsMap.get(key));
//            if (StringUtils.isNotBlank(markValue)) {
//                res.textCount.incrementAndGet();
//            }
//            if (mark == null) {
//                Assert.isTrue(format != null);
//                res.textDeleteGroup.items.add(buildItem(tpl, key, Arrays.asList("", getTextValue(format))));
//            } else if (format == null) {
//                Assert.isTrue(mark != null);
//                res.textAddGroup.items.add(buildItem(tpl, key, Arrays.asList(getTextValue(mark), "")));
//            } else {
//                Integer count = QuotaCountUtils.getUnmodifiedCount(Arrays.asList(markValue),
//                        Arrays.asList(formatValue));
//                if (count == 0) {
//                    res.textModifiedGroup.items.add(buildItem(tpl, key, Arrays.asList(markValue, formatValue)));
//                    if (TaskTypeEnum.SYSTEM.getValue().equals(markInfo.getMarkType())) {
//                        res.textSysModifiedGroup.items.add(buildItem(tpl, key, Arrays.asList(markValue, formatValue)));
//                    } else {
//                        res.textUnSysModifiedGroup.items.add(buildItem(tpl, key, Arrays.asList(markValue, formatValue)));
//                    }
//                }
//            }
//
//            res.testAllGroup.items.add(buildItem(tpl, key, Arrays.asList(markValue, formatValue)));
//            if (TaskTypeEnum.SYSTEM.getValue().equals(markInfo.getMarkType())) {
//                if (Boolean.FALSE.equals(mark.getRectify())) {
//                    res.rejectGroup.items.add(buildItem(tpl, key, Arrays.asList(markValue, formatValue)));
//                }
//                Integer count = QuotaCountUtils.getUnmodifiedCount(Arrays.asList(markValue),
//                        Arrays.asList(formatValue));
//                if (count == 1) {
//                    res.rejectLessGroup.items.add(buildItem(tpl, key, Arrays.asList(markValue, formatValue)));
//                }
//            }
//            if ("门店地址".equals(key) && format != null && mark != null && Boolean.FALSE.equals(mark.getRectify())) {
//                res.textRectifyGroup.items.add(buildItem(tpl, key, Arrays.asList(markValue, formatValue)));
//            }
//        }
//
//        // 表格绑定
//        Assert.isTrue(markContent.getInfos().getTable().getTableData() != null);
//        Triple<List<String>, List<String>, List<String>> judgeRes = judgeBindResult(markContent.getInfos().getTable().getTableData(), formatContent.getInfos().getTable().getTableData());
//
//        boolean notGoing = false;
//        if (billTypeEnum == BillTypeEnum.DELIVERY) {
//            if (CollectionUtils.isNotEmpty(judgeRes.getLeft())) {
//                tableRes.deliveryAddColumn = judgeRes.getLeft();
//                tableBad.setValue(true);
//                notGoing = true;
//            }
//            if (CollectionUtils.isNotEmpty(judgeRes.getMiddle())) {
//                tableRes.deliveryModifiedColumn = judgeRes.getMiddle();
//                tableBad.setValue(true);
//                notGoing = true;
//            }
//            if (CollectionUtils.isNotEmpty(judgeRes.getRight())) {
//                tableRes.deliveryDeleteColumn = judgeRes.getRight();
//                tableBad.setValue(true);
//                notGoing = true;
//            }
//        } else if (billTypeEnum == BillTypeEnum.PICKUP) {
//            if (CollectionUtils.isNotEmpty(judgeRes.getLeft())) {
//                tableRes.pickupAddColumn = judgeRes.getLeft();
//                tableBad.setValue(true);
//                notGoing = true;
//            }
//            if (CollectionUtils.isNotEmpty(judgeRes.getMiddle())) {
//                tableRes.pickupModifiedColumn = judgeRes.getMiddle();
//                tableBad.setValue(true);
//                notGoing = true;
//            }
//            if (CollectionUtils.isNotEmpty(judgeRes.getRight())) {
//                tableRes.pickupDeleteColumn = judgeRes.getRight();
//                tableBad.setValue(true);
//                notGoing = true;
//            }
//        } else {
//            Assert.isTrue(false);
//        }
//
//        // 如果表格绑定错误那就不对表格内容做解析了
//        if (notGoing) {
//            return;
//        }
//
//        List<String> props = DeliveryPickupFieldUtil.listTableProps(dataTypeEnum, billTypeEnum);
//        props = props.stream().filter(x -> !x.equals("nothing")).collect(Collectors.toList());
//        ArrayList<String> lackProps = new ArrayList<>();
//        for (String prop : props) {
//            List<BillTexts> column = getSpecificColumn(formatContent.getInfos().getTable().getTableData(), prop);
//            List<BillTexts> ids = column.stream().filter(x -> x != null && x.getId() != null).collect(Collectors.toList());
//            if (CollectionUtils.isEmpty(ids)) {
//                lackProps.add(prop);
//            }
//        }
//        if (CollectionUtils.isNotEmpty(lackProps)) {
//            if (BillTypeEnum.DELIVERY == billTypeEnum) {
//                tableRes.deliveryLackColumn = lackProps;
//                tableBad.setValue(true);
//                notGoing = true;
//            } else {
//                tableRes.pickupLackColumn = lackProps;
//                tableBad.setValue(true);
//                notGoing = true;
//            }
//        }
//
//        // 如果表格绑定错误那就不对表格内容做解析了
//        if (notGoing) {
//            return;
//        }
//
//        for (String prop : props) {
//            List<BillTexts> formatCells = getSpecificColumn(formatContent.getInfos().getTable().getTableData(), prop)
//                    .stream().filter(x -> x.getId() != null).collect(Collectors.toList());
//            Map<Integer, BillTexts> formatMap = formatCells.stream().collect(Collectors.toMap(BillTexts::getId, x -> x, (x1, x2) -> x1));
//            List<BillTexts> markCells = getSpecificColumn(markContent.getInfos().getTable().getTableData(), prop).stream().filter(Objects::nonNull).collect(Collectors.toList());
//
//            ArrayList<Integer> markIds = new ArrayList<>();
//            for (BillTexts markCell : markCells) {
//
////                if (prop.equals("productName")){
////                    res.productNameCount.getAndIncrement();
//////                    Assert.isTrue(markCell);
////                    if (StringUtils.isBlank(markCell.getValue())) {
////                        res.productNameEmpty.add(markId);
////                    } else {
////                        List<String> skuNames = matchEs(dataTypeEnum.getCompanyEnum(), markCell.getValue());
////                        if (CollectionUtils.isNotEmpty(skuNames)){
////                            res.matchPhraseCount.getAndIncrement();
////                            String matchName = skuNames.get(0);
////                            Integer count = QuotaCountUtils.getUnmodifiedCount(Arrays.asList(markCell.getValue()), Arrays.asList(matchName));
////                            if (count == 0) {
////                                res.matchDiff.add(buildItem(tpl, prop, Arrays.asList(markCell.getValue(), matchName)));
////                            }
////                        }
////                    }
////                }
//
//                if (prop.equals("attribute")) {
//                    if (StringUtils.isNotBlank(markCell.getValue())
//                            && !markCell.getValue().matches(".*\\d.*|.*投线.*|.*投团.*")) {
//                        res.getAttributeGroup().getItems().add(buildItem(tpl, markCell.getValue(), Arrays.asList(markCell.getValue(), "")));
//                    }
//                }
//
//                res.cellCount.getAndIncrement();
//                if (markCell.getId() == null) {
//                    res.getCellAddGroup().getItems().add(buildItem(tpl, prop, Arrays.asList(markCell.getValue(), "")));
//                    continue;
//                }
//                markIds.add(markCell.getId());
//                BillTexts formatColumn = formatMap.get(markCell.getId());
//                if (formatColumn == null) {
//                    // 解析逻辑不一致，会出现这种情况
//                    continue;
//                }
//                Integer count = QuotaCountUtils.getUnmodifiedCount(Arrays.asList(markCell.getValue()),
//                        Arrays.asList(formatColumn.getValue()));
//                if (count == 0) {
//                    res.getCellModifiedGroup().getItems().add(buildItem(tpl, prop, Arrays.asList(markCell.getValue(), formatColumn.getValue())));
//                }
//
//                if (TaskTypeEnum.SYSTEM.getValue().equals(markInfo.getMarkType())) {
//                    if (Boolean.FALSE.equals(markCell.getRectify())) {
//                        res.rejectGroup.items.add(buildItem(tpl, prop, Arrays.asList(markCell.getValue(), "")));
//                    }
//                }
//            }
//            formatMap.keySet().stream().filter(x -> !markIds.contains(x)).forEach(x -> {
//                BillTexts formatValue = formatMap.get(x);
//                res.getCellDeleteGroup().getItems().add(buildItem(tpl, prop, Arrays.asList("", formatValue.getValue())));
//            });
//        }
//    }
//
//    public static <T> List<String> getKeys(Map<String, T> left, Map<String, T> right) {
//        HashSet<String> set = new HashSet<>(left.keySet());
//        set.addAll(right.keySet());
//        return new ArrayList<>(set);
//    }
//
//    public static String getTextValue(BillTexts texts) {
//        if (texts == null || texts.getValue() == null) {
//            return "";
//        }
//        return texts.getValue();
//    }
//
//    public Triple<List<String>, List<String>, List<String>> judgeBindResult(List<DeliveryPickupData.DeliveryPickTableData> markTableLines,
//                                                                            List<DeliveryPickupData.DeliveryPickTableData> formatTableLines) {
//        List<String> addColumn = new ArrayList<>();
//        List<String> modifiedColomn = new ArrayList<>();
//        List<String> deleteColumn = new ArrayList<>();
//
//        Map<String, List<BillTexts>> markProps = new LinkedHashMap<>();
//        Map<String, List<BillTexts>> formatProps = new LinkedHashMap<>();
//
//        for (DeliveryPickupData.DeliveryPickTableData markLine : markTableLines) {
//            Map<String, BillTexts> map = parseTableLineToMap(markLine);
//            map.forEach((k, v) -> {
//                List<BillTexts> texts = markProps.computeIfAbsent(k, x -> new ArrayList<>());
//                texts.add(v);
//            });
//        }
//        for (DeliveryPickupData.DeliveryPickTableData formatLine : formatTableLines) {
//            Map<String, BillTexts> map = parseTableLineToMap(formatLine);
//            map.forEach((k, v) -> {
//                List<BillTexts> texts = formatProps.computeIfAbsent(k, x -> new ArrayList<>());
//                texts.add(v);
//            });
//        }
//
//        List<String> keys = getKeys(markProps, formatProps);
//        for (String key : keys) {
//            List<BillTexts> markList = markProps.getOrDefault(key, new ArrayList<>());
//            List<BillTexts> formatList = formatProps.getOrDefault(key, new ArrayList<>());
//
//            List<String> markContents = markList.stream().map(BillTexts::getValue).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//            List<String> formatContents = markList.stream().map(BillTexts::getValue).filter(StringUtils::isNotBlank).collect(Collectors.toList());
//            if (markContents.isEmpty() && formatContents.isEmpty()) {
//                continue;
//            }
//
//            List<Integer> markIds = markList.stream().map(BillTexts::getId).filter(Objects::nonNull).collect(Collectors.toList());
//            List<Integer> formatIds = formatList.stream().map(BillTexts::getId).filter(Objects::nonNull).collect(Collectors.toList());
//            if (CollectionUtils.isEmpty(formatIds) && CollectionUtils.isNotEmpty(markIds)) {
//                addColumn.add(key);
//                continue;
//            }
//            if (CollectionUtils.isEmpty(markIds) && CollectionUtils.isNotEmpty(formatIds)) {
//                deleteColumn.add(key);
//                continue;
//            }
//            if (!formatIds.containsAll(markIds)) {
//                modifiedColomn.add(key);
////                continue;
//            }
//        }
//        return MutableTriple.of(addColumn, modifiedColomn, deleteColumn);
//    }
//
//    public Map<String, BillTexts> parseTableLineToMap(DeliveryPickupData.DeliveryPickTableData markLine) {
//        Map<String, Object> map = ObjectUtils.object2Map(markLine);
//        Map<String, BillTexts> res = new LinkedHashMap<>();
//        //noinspection ConstantConditions
//        map.forEach((k, v) -> {
//            if (k.toLowerCase(Locale.ROOT).contains("others")) {
//                return;
//            }
//            if (v == null || v.getClass() != BillTexts.class) {
//                return;
//            }
//            res.put(k, (BillTexts) v);
//        });
//        return res;
//    }
//
//
//    public static boolean containsEmptyIdForm(List<DeliveryPickupData.Content> contents) {
//        for (DeliveryPickupData.Content content : contents) {
//            if (BillTypeEnum.isMargin(content.getType().getValue())) {
//                continue;
//            }
//            List<Integer> ids = getIds(content);
//            if (CollectionUtils.isEmpty(ids)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static boolean containsEmptyForm(List<DeliveryPickupData.Content> contents) {
//        for (DeliveryPickupData.Content content : contents) {
//            if (BillTypeEnum.isMargin(content.getType().getValue())) {
//                continue;
//            }
//            if (content.getInfos().getTable() == null) {
//                return true;
//            }
//            List<DeliveryPickupData.DeliveryPickTableData> tableData = content.getInfos().getTable().getTableData();
//            if (CollectionUtils.isEmpty(tableData)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public static Triple<DeliveryPickupData.Content, DeliveryPickupData.Content, DeliveryPickupData.Content> dispatchContent(List<DeliveryPickupData.Content> contents) {
//        DeliveryPickupData.Content deliveryContent = null;
//        DeliveryPickupData.Content pickupContent = null;
//        DeliveryPickupData.Content marginContent = null;
//        for (DeliveryPickupData.Content content : contents) {
//            BillTypeEnum billTypeEnum = BillTypeEnum.getByAliasElseException(content.getType().getValue());
//            if (billTypeEnum == BillTypeEnum.DELIVERY) {
//                deliveryContent = content;
//            } else if (billTypeEnum == BillTypeEnum.PICKUP) {
//                pickupContent = content;
//            } else if (billTypeEnum == BillTypeEnum.MARGIN) {
//                marginContent = content;
//            } else {
//                Assert.isTrue(false);
//            }
//        }
//        return ImmutableTriple.of(deliveryContent, pickupContent, marginContent);
//    }
//
//    public static Map<String, String> convert(Map<String, List<AnalyzeRes>> map) {
//        LinkedHashMap<String, String> res = new LinkedHashMap<>();
//        map.forEach((k, v) -> {
//            res.put(k, JsonKits.toJson(v));
//        });
//        return res;
//    }
//
//    @Data
//    public static class QuotaRes {
//        AtomicInteger totalValid = new AtomicInteger(0);
//        AtomicInteger originLack = new AtomicInteger(0);
//
//        CountGroup attributeGroup = new CountGroup();
//
//        CopyOnWriteArrayList<String> mergedError = new CopyOnWriteArrayList<>();
//
//        AtomicInteger systemMarkRealTime = new AtomicInteger(0);
//        AtomicInteger systemMarkAll = new AtomicInteger(0);
//        CopyOnWriteArraySet<String> systemMarkQualified = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> systemReject = new CopyOnWriteArraySet<>();
////        AtomicInteger systemMarkAddBySimple = new AtomicInteger(0);
//
//        // expect 0
//        CopyOnWriteArrayList<String> unqualifiedMark = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> unqualifiedFormat = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> unqualified = new CopyOnWriteArrayList<>();
//
//        CopyOnWriteArrayList<String> unfitMark = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> unfitFormat = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> unfit = new CopyOnWriteArrayList<>();
//
//        CopyOnWriteArrayList<String> taskTypeOnline = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> taskTypeOnlineReject = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> taskTypeOffline = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> taskTypeCommon = new CopyOnWriteArrayList<>();
//        CopyOnWriteArrayList<String> taskTypeCommonReject = new CopyOnWriteArrayList<>();
//
//        // 是否有删除整个表单表格
//        CopyOnWriteArraySet<String> addForms = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> deleteForms = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> emptyForms = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> emptyIdForms = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> typeDisorderForms = new CopyOnWriteArraySet<>();
//        CopyOnWriteArraySet<String> modifiedForms = new CopyOnWriteArraySet<>();
//
//        AtomicInteger markColTotal = new AtomicInteger(0);
//        AtomicInteger markColSuccess = new AtomicInteger(0);
//        //        CopyOnWriteArraySet<String> markColFailed = new CopyOnWriteArraySet<>();
//        LoadingCache<String, CopyOnWriteArraySet<String>> markColFailed = LoadingCacheUtils.build(x -> new CopyOnWriteArraySet<>());
//        AtomicInteger markColFailedCount = new AtomicInteger(0);
//
//
//        // 类型统计
//        CountGroup paperSetGroup = new CountGroup();
//
//        CopyOnWriteArraySet<String> pickupNotEmpty = new CopyOnWriteArraySet<>();
//
//        CopyOnWriteArraySet<String> pickupForm = new CopyOnWriteArraySet<>();
//
//        CopyOnWriteArraySet<String> deliveryForm = new CopyOnWriteArraySet<>();
//
//
//        // 表单 标注后表单个数
//        AtomicInteger textCount = new AtomicInteger(0);
//        CountGroup testAllGroup = new CountGroup();
//
//        CountGroup rejectGroup = new CountGroup();
//        CountGroup rejectLessGroup = new CountGroup();
//
//
//        CountGroup textAddGroup = new CountGroup();
//        CountGroup textModifiedGroup = new CountGroup();
//        CountGroup textSysModifiedGroup = new CountGroup();
//        CountGroup textUnSysModifiedGroup = new CountGroup();
//        CountGroup textDeleteGroup = new CountGroup();
//
//        CountGroup textRectifyGroup = new CountGroup();
//
//        // 表格
//        AtomicInteger cellCount = new AtomicInteger(0);
//
//        AtomicInteger productNameCount = new AtomicInteger(0);
//        CopyOnWriteArraySet<String> productNameEmpty = new CopyOnWriteArraySet<>();
//
//        AtomicInteger matchPhraseCount = new AtomicInteger(0);
//        CopyOnWriteArrayList<AnalyzeItem> matchDiff = new CopyOnWriteArrayList<>();
//
//
//        CountGroup cellAddGroup = new CountGroup();
//        CountGroup cellModifiedGroup = new CountGroup();
//        CountGroup cellDeleteGroup = new CountGroup();
//
//        CopyOnWriteArrayList<TableQuotaRes> tableColChange = new CopyOnWriteArrayList<>();
//
//        @Data
//        public static class CountGroup {
//            CopyOnWriteArrayList<AnalyzeItem> items = new CopyOnWriteArrayList<>();
//            Map<String, ItemRes> group = new LinkedHashMap<>();
//            ItemRes totalGroup;
//        }
//
//        @AllArgsConstructor
//        public static class ItemRes {
//            List<AnalyzeRes> items;
//            String value;
//            Integer size;
//        }
//
//        public static class TableQuotaRes {
//            String markId;
//
//            List<String> deliveryAddColumn;
//            List<String> deliveryModifiedColumn;
//            List<String> deliveryDeleteColumn;
//
//            List<String> deliveryLackColumn;
//
//            List<String> pickupAddColumn;
//            List<String> pickupModifiedColumn;
//            List<String> pickupDeleteColumn;
//
//            List<String> pickupLackColumn;
//        }
//    }
//
//    public static List<AnalyzeRes> mergeItem(List<AnalyzeItem> items) {
//        Map<String, Optional<AnalyzeRes>> res = items.stream().map(
//                x -> new AnalyzeRes(x.getMarkId(), x.getUrl(), x.getUrlMd5(), x.getPicDirection(), new ArrayList<>(Arrays.asList(x)))
//        ).collect(Collectors.groupingBy(AnalyzeRes::getMarkId, Collectors.reducing((x1, x2) -> {
//            x1.getItems().addAll(x2.getItems());
//            return x1;
//        })));
//        return res.values().stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
//    }
//
//    public static BindRecord queryBindOn(Long originId) {
//        String queryBindSQL = "{\"sql\":\"SELECT * from bill_data_bind_record WHERE origin_data_id = %s;\",\"databaseId\":274982,\"targetInstance\":null}";
//        Object o = callRds(String.format(queryBindSQL, originId), onlineUrl, FORMAT_OFFLINE_CONFIG);
//        React index = new React(o).get("data").index(0).get("rows").index(0);
//        index.set("createdTime", null).set("updatedTime", null);
//        Object expose = index.expose();
//        if (expose == null){
//            return null;
//        }
//        BindRecord parse = JsonKits.parse(JsonKits.toJson(expose), BindRecord.class);
//        return parse;
//    }
//
//    public static OriginData queryOriginOn(Long originId) {
//        String originSQLFormat = "{\"sql\":\"SELECT * from origin_data_info WHERE id = '%s';\",\"databaseId\":274982,\"targetInstance\":null}";
//        Object o = callRds(String.format(originSQLFormat, originId), onlineUrl, FORMAT_OFFLINE_CONFIG);
//        React index = new React(o).get("data").index(0).get("rows").index(0);
//        index.set("createdTime", null).set("updatedTime", null);
//        Object expose = index.expose();
//        if (expose == null){
//            return null;
//        }
//        OriginData parse = JsonKits.parse(JsonKits.toJson(expose), OriginData.class);
//        return parse;
//    }
//
//    public static final String onlineUrl = "https://rds.mws.sankuai.com/api/v2/autosql/execute-dql";
//
//    @SneakyThrows
//    public static Object callRds(String content, String url, String cookie) {
//        MediaType mediaType = MediaType.parse("application/json");
//        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, content);
//        Request request = new Request.Builder()
//                .url(url)
//                .method("POST", body)
//                .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"")
//                .addHeader("M-TRACEID", "-7486781951923190220")
//                .addHeader("sec-ch-ua-mobile", "?1")
//                .addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.0.0 Mobile Safari/537.36")
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json, text/plain, */*")
//                .addHeader("M-APPKEY", "fe_DBA")
//                .addHeader("sec-ch-ua-platform", "\"Android\"")
//                .addHeader("Sec-Fetch-Site", "same-origin")
//                .addHeader("Sec-Fetch-Mode", "cors")
//                .addHeader("Sec-Fetch-Dest", "empty")
//                .addHeader("Cookie", cookie)
//                .build();
//        Response response = client.newCall(request).execute();
//        Assert.isTrue(response.code() == 200);
//        Object res = JsonKits.parse(response.body().string(), Object.class);
//        response.body().close();
//        return res;
//    }
//
//    @NotNull
//    public static <T> List<T> select(Object mapper, Object example, String sql, Integer limit, Integer offset) {
//        Assert.isTrue(StringUtils.isNotBlank(sql));
//        Object criteria = invokeNoArgs(example, "createCriteria");
//        addCriterion(criteria, sql);
//
////        ReflectionUtils
//        invoke(example, "setOrderByClause", new Object[]{"id desc"});
//        if (limit != null) {
//            invoke(example, "setRows", new Integer[]{limit});
//        }
//        if (offset != null) {
//            invoke(example, "setOffset", new Integer[]{offset});
//        }
//        //noinspection unchecked
//        return (List<T>) invoke(mapper, "selectByExample", new Object[]{example});
//    }
//
//    public static Object invokeNoArgs(Object object, String methodName) {
//        return invoke(object, methodName, new Object[]{});
//    }
//
//    @SneakyThrows
//    public static void addCriterion(Object criteria, String sql) {
//        Method method = criteria.getClass().getSuperclass().getDeclaredMethod("addCriterion", String.class);
//        method.setAccessible(true);
//        method.invoke(criteria, sql);
//    }
//
//    public static Map<String, QuotaRes.ItemRes> groupRes(List<AnalyzeRes> input) {
//        Map<String, List<AnalyzeRes>> tmp = input.stream().flatMap(x -> {
//            Map<String, AnalyzeRes> map = new LinkedHashMap<>();
//            AnalyzeRes tpl = DeepCloneUtils.copy(x);
//            tpl.setItems(new ArrayList<>());
//
//            for (AnalyzeItem item : x.getItems()) {
//                AnalyzeRes analyzeRes = map.computeIfAbsent(item.getKey(), y -> DeepCloneUtils.copy(tpl));
//                analyzeRes.getItems().add(item);
//            }
//            return map.values().stream();
//        }).collect(Collectors.groupingBy(x -> x.getItems().get(0).getKey(), Collectors.toList()));
//        LinkedHashMap<String, QuotaRes.ItemRes> res = new LinkedHashMap<>();
//        tmp.forEach((k, v) -> {
//            res.put(k, buildItemRes(v));
//        });
//        return res;
//    }
//
//    public static QuotaRes.ItemRes buildItemRes(List<AnalyzeRes> v) {
//        Optional<Integer> total = v.stream().map(x -> x.getItems().size()).reduce((x1, x2) -> x1 + x2);
//        return new QuotaRes.ItemRes(v, JsonKits.toJson(v), total.isPresent() ? total.get() : 0);
//    }
//
//    @AllArgsConstructor
//    @Data
//    public static class AnalyzeItem {
//        // group 信息
//        String markId;
//        String url;
//        String urlMd5;
//        String picDirection;
//
//        // item 信息
//        String key;
//        List<String> values;
//    }
//
//    public String getMarkProperty(String markId, PropertyEnum propertyEnum) {
//        MarkPropertyDOExample propertyDOExample = new MarkPropertyDOExample();
//        propertyDOExample.createCriteria().andDataIdEqualTo(markId).andCodeEqualTo(propertyEnum.getCode());
//        List<MarkPropertyDO> propertyDOS = markPropertyMapper.selectByExample(propertyDOExample);
//        if (CollectionUtils.isEmpty(propertyDOS)) {
//            return null;
//        }
//        Assert.isTrue(propertyDOS.size() == 1);
//        return propertyDOS.get(0).getValue();
//    }
//
//    public void checkTypeEquals(List<DeliveryPickupData.Content> markContents,
//                                List<DeliveryPickupData.Content> formatContents) {
//        Assert.isTrue(markContents != null && markContents.size() <= 3);
//        Assert.isTrue(formatContents != null && formatContents.size() <= 3);
//        if (markContents.size() >= 2) {
//            List<String> markType = markContents.stream().map(x -> x.getType().getValue()).distinct().collect(Collectors.toList());
//            Assert.isTrue(markType.size() == markContents.size());
//            // 经标注后，位置确实会发生变动
////            checkContentPos(markContents);
//        }
//        if (formatContents.size() >= 2) {
//            List<String> formatType = formatContents.stream().map(x -> x.getType().getValue()).distinct().collect(Collectors.toList());
//            Assert.isTrue(formatType.size() == formatContents.size());
//        }
//    }
//
//
//    public boolean checkPos(int prev, int after) {
//        if (prev == -1 || after == -1) {
//            return true;
//        }
//        return prev < after;
//    }

}
