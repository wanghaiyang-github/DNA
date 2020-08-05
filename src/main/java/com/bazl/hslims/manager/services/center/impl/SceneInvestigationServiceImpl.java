package com.bazl.hslims.manager.services.center.impl;

import com.alibaba.fastjson.JSON;
import com.bazl.hslims.manager.dao.LimsCaseInfoDao;
import com.bazl.hslims.manager.model.XkFeedbackCaseInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.services.center.SceneInvestigationService;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.webservice.client.SceneManagerPortType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SceneInvestigationServiceImpl
        implements SceneInvestigationService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    DictService dictService;
    @Autowired
    SceneManagerPortType sceneManagerPortType;

    /*@Override
    public Map<String, Object> findSceneInvestigationByNo(String xkNo)
            throws Exception
    {
        Map<String, Object> CaseSampleMapInfo = new HashMap();
        String address = SystemInfoUtil.getLocalIpAddress();

        String param = getWSParam(new String[] { "10.8.41.240", "lims", xkNo });
        try
        {
            String result = this.sceneDnaManagerPortType.getSceneInvestigation(param);
            CaseSampleMapInfo = getSceneInvestigationFromStr(result);
        }catch (MalformedURLException e){
            this.logger.error(e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException("现勘系统WebService地址无效！");
        }catch (ConnectException e) {
            this.logger.error(e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException("与现勘系统连接不畅！");
        }catch (Exception e){
            this.logger.error(e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException("目标系统没有对应的勘验信息！");
        }
        return CaseSampleMapInfo;
    }*/


    @Autowired
    LimsCaseInfoDao limsCaseInfoDao;

    @Override
    public void selectAllNotFeedbak() {
        List<XkFeedbackCaseInfo> list = limsCaseInfoDao.selectAllNotFeedbak();

        for(XkFeedbackCaseInfo feedbackCaseInfo : list) {
            try {
                String params = convertJsonString(feedbackCaseInfo);
                String result = sceneManagerPortType.feedbackConsignment(params);
                logger.info(feedbackCaseInfo.getCaseXkNo() + " ------- feedbackConsignment result : " + result);
            }catch(Exception ex){
                logger.error(feedbackCaseInfo.getCaseXkNo() + " ------- feedbackConsignment error! " + ex.getMessage(), ex);

                continue;
            }
        }

    }

    @Override
    public void updateStatus(){
        List<XkFeedbackCaseInfo> list = limsCaseInfoDao.selectAllNotFeedbak();

        for(XkFeedbackCaseInfo feedbackCaseInfo : list) {
            try {
                String params = convertJsonString1(feedbackCaseInfo);
                String result = sceneManagerPortType.feedbackConsignmentStatus(params);
                logger.info(feedbackCaseInfo.getCaseXkNo() + " ------- feedbackConsignmentStatus result : " + result);
            }catch(Exception ex){
                logger.error(feedbackCaseInfo.getCaseXkNo() + " ------- feedbackConsignmentStatus error! " + ex.getMessage(), ex);

                continue;
            }
        }
    }

    private String convertJsonString(XkFeedbackCaseInfo feedbackCaseInfo){
        JSONObject obj = new JSONObject();
        obj.put("submId", feedbackCaseInfo.getCaseId());
        obj.put("submCode", feedbackCaseInfo.getConsignmentNo());
        obj.put("departmentCode", feedbackCaseInfo.getDelegateOrg());
        obj.put("submitDate", DateUtils.dateToString(feedbackCaseInfo.getDelegateDatetime(),"yyyy-MM-dd HH:mm:ss"));
        obj.put("serverName", "毕节市公安司法鉴定中心");
        obj.put("serverCode", "522400");
        obj.put("investigationNo", feedbackCaseInfo.getCaseXkNo());

        return obj.toString();
    }

    private String convertJsonString1(XkFeedbackCaseInfo feedbackCaseInfo){
        JSONObject obj = new JSONObject();
        obj.put("submCode", feedbackCaseInfo.getConsignmentNo());
        if(StringUtils.isNotBlank(feedbackCaseInfo.getCaseStatus())
                && feedbackCaseInfo.getCaseStatus().equals("03")){
            obj.put("submStatusCode", "2");
            obj.put("submStatusDesc","已鉴定");
        }else{
            obj.put("submStatusCode", "1");
            obj.put("submStatusDesc","已受理");
        }
        obj.put("serverCode", "522400");

        return obj.toString();
    }

    @Override
    public Map<String, Object> findSceneInvestigationByNo(String xkNo) {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> xkMap = new HashMap<String, Object>();
        String returnXk = null;
        try {
            map.put("eviType", 8);
            map.put("investigationNo", xkNo);

            String jsonString = JSON.toJSONString(map);
             returnXk = sceneManagerPortType.extractSceneInfo(jsonString);
            //returnXk = "{\"result\":{\"investigationNo\":\"K5203290500002019030011\",\"caseNo\":\"A5203294100002019030026\",\"caseName\":\"2019-03-08 13:00贵州省遵义市余庆县子营街道兴隆修车场其他\",\"exposureProcess\":\"2019年3月8日15时许，李文书因其号牌为贵CAM589的小型货车刹车的气管漏气问题到陆红刚处修车场修理，陆洪刚修好后，又在该车下的修车坑内修理该车底盘的十字结，后陆红刚让李文书启动车查看车况时，因李文书忘记其停车时把车档位排到一档的，启动车后，车突然向前面冲一下，刚好这时陆红刚准备从修车坑内上来，导致陆红刚被车撞到，后120到场把陆红刚送到余庆县人民医院抢救，经医院抢救无效死亡。\",\"caseProperty\":\"1\",\"caseType\":\"119000\",\"caseCategory\":\"0\",\"sceneDetail\":\"贵州省遵义市余庆县子营街道兴隆修车场\",\"occurrenceDate\":\"2019-03-08 13:00:00\",\"investigationOrgan\":\"余庆县公安局刑侦大队\",\"investigationOrganCode\":\"520329050000\",\"sceneRegionalism\":\"520329050000\",\"iterationNo\":\"0\",\"evidenceList\":[{\"serialNo\":\"1\",\"evidenceType\":\"8\",\"evidenceName\":\"1号物证疑似血泊\",\"evidenceNo\":\"W52032905000020190300112002001\",\"amount\":\"1\",\"collectedBy\":\"刘刚、王余、兰林\",\"evidenceSampleCode\":\"2002\",\"evidenceSampleName\":\"血迹\",\"collectedDate\":\"2019-03-08 00:00:00\",\"leftPosition\":\"木方西端上\",\"collectionMode\":\"照相固定，生物棉签擦拭提取\",\"evidenceDesc\":\"无\",\"width\":2400,\"height\":1600,\"fileName\":\"12.jpg\",\"type\":\"jpg\"},{\"serialNo\":\"2\",\"evidenceType\":\"8\",\"evidenceName\":\"2号物证疑似血泊\",\"evidenceNo\":\"W52032905000020190300112002002\",\"amount\":\"1\",\"collectedBy\":\"刘刚、王余、兰林\",\"evidenceSampleCode\":\"2002\",\"evidenceSampleName\":\"血迹\",\"collectedDate\":\"2019-03-08 00:00:00\",\"leftPosition\":\"木方东端上\",\"collectionMode\":\"照相固定，生物棉签擦拭提取\",\"evidenceDesc\":\"无\",\"width\":2400,\"height\":1600,\"fileName\":\"13.jpg\",\"type\":\"jpg\"},{\"serialNo\":\"3\",\"evidenceType\":\"8\",\"evidenceName\":\"3号物证疑似滴落状血迹\",\"evidenceNo\":\"W52032905000020190300112002003\",\"amount\":\"1\",\"collectedBy\":\"刘刚、王余、兰林\",\"evidenceSampleCode\":\"2002\",\"evidenceSampleName\":\"血迹\",\"collectedDate\":\"2019-03-08 00:00:00\",\"leftPosition\":\"木凳上\",\"collectionMode\":\"照相固定，生物棉签擦拭提取\",\"evidenceDesc\":\"无\",\"width\":2400,\"height\":1600,\"fileName\":\"14.jpg\",\"type\":\"jpg\"},{\"serialNo\":\"4\",\"evidenceType\":\"8\",\"evidenceName\":\"4号物证擦拭拭子\",\"evidenceNo\":\"W52032905000020190300112001004\",\"amount\":\"1\",\"collectedBy\":\"刘刚、王余、兰林\",\"evidenceSampleCode\":\"2001\",\"evidenceSampleName\":\"DNA\",\"collectedDate\":\"2019-03-08 00:00:00\",\"leftPosition\":\"车牌凹陷变形处\",\"collectionMode\":\"照相固定，生物棉签擦拭提取\",\"evidenceDesc\":\"无\",\"width\":2400,\"height\":1600,\"fileName\":\"17.jpg\",\"type\":\"jpg\"},{\"serialNo\":\"5\",\"evidenceType\":\"8\",\"evidenceName\":\"5号物证疑似抛溅状血迹\",\"evidenceNo\":\"W52032905000020190300112002005\",\"amount\":\"1\",\"collectedBy\":\"刘刚、王余、兰林\",\"evidenceSampleCode\":\"2002\",\"evidenceSampleName\":\"血迹\",\"collectedDate\":\"2019-03-08 00:00:00\",\"leftPosition\":\"车牌东侧保险杠上\",\"collectionMode\":\"照相固定，生物棉签擦拭提取\",\"evidenceDesc\":\"无\",\"width\":2400,\"height\":1600,\"fileName\":\"18.jpg\",\"type\":\"jpg\"},{\"serialNo\":\"6\",\"evidenceType\":\"8\",\"evidenceName\":\"6号物证方向盘擦拭拭子\",\"evidenceNo\":\"W52032905000020190300112001006\",\"amount\":\"1\",\"collectedBy\":\"刘刚、王余、兰林\",\"evidenceSampleCode\":\"2001\",\"evidenceSampleName\":\"DNA\",\"collectedDate\":\"2019-03-08 00:00:00\",\"leftPosition\":\"方向盘上\",\"collectionMode\":\"照相固定，生物棉签擦拭提取\",\"evidenceDesc\":\"无\",\"width\":2500,\"height\":1667,\"fileName\":\"21.jpg\",\"type\":\"jpg\"},{\"serialNo\":\"7\",\"evidenceType\":\"8\",\"evidenceName\":\"7号物证排档杆擦拭拭子\",\"evidenceNo\":\"W52032905000020190300112002007\",\"amount\":\"1\",\"collectedBy\":\"刘刚、王余、兰林\",\"evidenceSampleCode\":\"2001\",\"evidenceSampleName\":\"DNA\",\"collectedDate\":\"2019-03-08 00:00:00\",\"leftPosition\":\"排挡杆上\",\"collectionMode\":\"照相固定，生物棉签擦拭提取\",\"evidenceDesc\":\"无\",\"width\":2400,\"height\":1600,\"fileName\":\"22.jpg\",\"type\":\"jpg\"}]}}";

            xkMap = analysisXk(returnXk);
            this.logger.error("现堪返回数据", returnXk);
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException("目标系统没有对应的勘验信息！");
        }
        return xkMap;
    }

    public String getWSParam(String... par) {
        StringBuffer param = new StringBuffer("");
        param.append("<?xml  version=\"1.0\" encoding=\"UTF-8\"?>");
        param.append("<ROOT>");
        param.append("<AUTHORADDRESS>").append(par[0]).append("</AUTHORADDRESS>");
        param.append("<AUTHORCODE>").append(par[1]).append("</AUTHORCODE>");
        param.append("<K_NO>").append(par[2]).append("</K_NO>");
        param.append("</ROOT>");
        return param.toString();
    }

    private Map<String, Object> getSceneInvestigationFromStr(String result) throws Exception {

        this.logger.error("解析后：" + result);

        if ((result == null) || (result.equals(""))) {
            return null;
        }

        Map<String, Object> warpInfo = new HashMap();
        LimsCaseInfo limsCaseInfo = new LimsCaseInfo();
        LimsConsignment consignment = new LimsConsignment();
        List<LimsPersonInfo> limsPersonInfoList = new ArrayList();
        List<LimsSampleInfo> limsSampleInfoList = new ArrayList();
        try {
            Document document = DocumentHelper.parseText(result);
            Element root = document.getRootElement();
            Element caseElement = root.element("CASE");

            /*Element caseNoAttr = caseElement.element("CASE_NO");
            String caseNo = caseNoAttr.getTextTrim();
            if ((caseNo != null) && (caseNo.equals("-1"))) {
                return warpInfo;
            }*/

            //现堪编号
            Element kNoAttr = caseElement.element("K_NO");
            if (kNoAttr != null) {
                limsCaseInfo.setCaseXkNo(kNoAttr.getTextTrim());
            }
            //案件类型
            Element caseTypeAttr = caseElement.element("CASE_TYPE");
            if (caseTypeAttr != null) {
                limsCaseInfo.setCaseType(caseTypeAttr.getTextTrim());
            }
            //案件名称
            Element caseNameAttr = caseElement.element("CASE_NAME");
            if (caseNameAttr != null) {
                limsCaseInfo.setCaseName(caseNameAttr.getTextTrim());
            }
            //案发地点行政编号
            Element sceneRegionalismAttr = caseElement.element("SCENE_REGIONALISM");
            if (sceneRegionalismAttr != null) {
                limsCaseInfo.setCaseLocation(sceneRegionalismAttr.getTextTrim());
            }
            //案发地点
            Element scenePlaceAttr = caseElement.element("SCENE_PLACE");
            if (scenePlaceAttr != null) {
                limsCaseInfo.setCaseLocationDesc(scenePlaceAttr.getTextTrim());
            }
            //案发时间
            Element occurrenceDateAttr = caseElement.element("OCCURRENCE_DATE");
            if (occurrenceDateAttr != null) {
                String occurrenceDateStr = occurrenceDateAttr.getTextTrim();
                Date occurrenceDate = DateUtils.stringToDate(occurrenceDateStr, "yyyy-MM-dd");
                limsCaseInfo.setCaseDatetime(occurrenceDate);
            }
            //简要案情
            Element caseSummaryAttr = caseElement.element("CASE_SUMMARY");
            if (caseSummaryAttr != null) {
                limsCaseInfo.setCaseBrief(caseSummaryAttr.getTextTrim());
            }

            Element reserve1Attr = caseElement.element("RESERVE1");
            Element externalCaseNoAttr = caseElement.element("EXTERNAL_CASE_NO");
            Element casePropertyAttr = caseElement.element("CASE_PROPERTY");
            if (casePropertyAttr != null) {
                limsCaseInfo.setCaseProperty(casePropertyAttr.getTextTrim());
            }
            Element wtbhAttr = caseElement.element("WTBH");
            if (wtbhAttr != null) {
                consignment.setConsignmentNo(wtbhAttr.getTextTrim());
            }
            //人员信息
            Element memberElement = root.element("PERSON_LIST");
            List<DictItem> personTypeList;
            if (memberElement != null) {
                List memberElementList = memberElement.elements("PERSON");
                for (int i = 0; i < memberElementList.size(); i++) {
                    Element element = (Element) memberElementList.get(i);
                    LimsPersonInfo limsPersonInfo = new LimsPersonInfo();

                    Element idAttr = element.element("ID");

                    Element nameAttr = element.element("NAME");
                    if (nameAttr != null) {
                        limsPersonInfo.setPersonName(nameAttr.getTextTrim());
                    }
                    Element sexAttr = element.element("SEX");
                    if (sexAttr != null) {
                        if (sexAttr.getTextTrim().equals("男")) {
                            limsPersonInfo.setPersonGender("01");
                        } else if (sexAttr.getTextTrim().equals("女")) {
                            limsPersonInfo.setPersonGender("02");
                        } else {
                            limsPersonInfo.setPersonGender("03");
                        }
                    }
                    Element ageAttr = element.element("AGE");

                    Element idCardAttr = element.element("IDCARD");
                    if (idCardAttr != null) {
                        limsPersonInfo.setPersonIdcardNo(idCardAttr.getTextTrim());
                    }
                    Element addressAttr = element.element("ADDRESS");
                    if (addressAttr != null) {
                        limsPersonInfo.setPresentAddress(addressAttr.getTextTrim());
                    }
                    Element personTypeAttr = element.element("PERSON_TYPE");
                    String type;
                    if (personTypeAttr != null) {
                        type = personTypeAttr.getTextTrim();
                        limsPersonInfo.setPersonType(type);
                        personTypeList = this.dictService.selectDictItemListByType("PERSON_TYPE");
                        for (DictItem dictItem : personTypeList) {
                            if (type.equals(dictItem.getDictCode())) {
                                limsPersonInfo.setPersonTypeName(dictItem.getDictName());
                            }
                        }
                    }
                    limsPersonInfoList.add(limsPersonInfo);
                }
            }
            //检材
            Element bioEvidenceListElement = root.element("BIO_EVIDENCE_LIST");
            List bioEvidenceElementList = bioEvidenceListElement.elements("BIO_EVIDENCE");
            for (int i = 0; i < bioEvidenceElementList.size(); i++) {
                LimsSampleInfo limsSampleInfo = new LimsSampleInfo();
                Element bioEvidenceElement = (Element) bioEvidenceElementList.get(i);

                /*Element wNoAttr = bioEvidenceElement.element("W_NO");
                if (wNoAttr != null) {
                    limsSampleInfo.setSampleNo(wNoAttr.getTextTrim());
                }*/
                Element descriptionAttr = bioEvidenceElement.element("DESCRIPTION");
                if (descriptionAttr != null) {
                    limsSampleInfo.setSampleDesc(descriptionAttr.getTextTrim());
                }
                Element collectByAttr = bioEvidenceElement.element("COLLECT_BY");
                if (collectByAttr != null) {
                    limsSampleInfo.setExtractPerson(collectByAttr.getTextTrim());
                }
                Element collectDateAttr = bioEvidenceElement.element("COLLECT_DATE");
                if (collectDateAttr != null) {
                    String collectDateStr = collectDateAttr.getTextTrim();
                    Date collectDate = DateUtils.stringToDate(collectDateStr, "yyyy-MM-dd");
                    limsSampleInfo.setExtractDatetime(collectDate);
                }
                Element evidenceNameAttr = bioEvidenceElement.element("EVIDENCE_NAME");
                if (evidenceNameAttr != null) {
                    limsSampleInfo.setSampleName(evidenceNameAttr.getTextTrim());
                }
                Element sampleTypeAttr = bioEvidenceElement.element("SAMPLE_TYPE");
                if ((sampleTypeAttr != null) && (!sampleTypeAttr.equals(""))) {
                    limsSampleInfo.setSampleType(xkTypeToLimsType(sampleTypeAttr.getTextTrim()));
                    List<DictItem> sampleTypeList = this.dictService.selectDictItemListByType("SAMPLE_TYPE");
                    for (DictItem dictItem : sampleTypeList) {
                        if (xkTypeToLimsType(sampleTypeAttr.getTextTrim()).equals(dictItem.getDictCode())) {
                            limsSampleInfo.setSampleTypeName(dictItem.getDictName());
                        }
                    }
                }
                Element collectPosAttr = bioEvidenceElement.element("COLLECT_POS");
                Element testDescAttr = bioEvidenceElement.element("TEST_DESC");
                Element warnAttr = bioEvidenceElement.element("WARN_MSG");
                Element flagAttr = bioEvidenceElement.element("TYPE");
                if (flagAttr != null) {
                    String flag = flagAttr.getTextTrim();
                    if (flag.equals("1")) {
                        limsSampleInfo.setSampleFlag("1");
                    } else {
                        limsSampleInfo.setSampleFlag("0");
                    }
                }
                Element personIdAttr = bioEvidenceElement.element("PERSON_ID");
                LimsPersonInfo limsPersonInfo;
                Element relationAttr;
                if (personIdAttr != null) {
                    for (Iterator localIterator2 = limsPersonInfoList.iterator(); localIterator2.hasNext(); ) {
                        limsPersonInfo = (LimsPersonInfo) localIterator2.next();
                        if (personIdAttr.getTextTrim().equals(limsPersonInfo.getId())) {
                            limsSampleInfo.setRefPersonName(limsPersonInfo.getPersonName());
                        }
                    }
                    relationAttr = bioEvidenceElement.element("SAMPLE_RELATION");
                    if (relationAttr != null) {
                       /* for ( limsPersonInfo = (LimsPersonInfo) limsPersonInfoList.iterator(); limsPersonInfo.hasNext();) {*/
                        for (int a = 0; a < limsPersonInfoList.size(); a++) {
                            /*limsPersonInfo = (LimsPersonInfo)limsPersonInfo.next();*/
                            limsPersonInfo = limsPersonInfoList.get(a);
                            if (personIdAttr.getTextTrim().equals(limsPersonInfo.getId())) {
                                limsPersonInfo.setRelativeIdentify(relationAttr.getTextTrim());
                                List<DictItem> personRelationList = this.dictService.selectDictItemListByType("PERSON_RELATION");
                                for (DictItem dictItem : personRelationList) {
                                    if (relationAttr.getTextTrim().equals(dictItem.getDictCode())) {
                                        limsPersonInfo.setRelativeIdentifyName(dictItem.getDictName());
                                    }
                                }
                            }
                        }
                    }
                }

                Element sceneAddressAttr = root.element("AUTHORADDRESS");
                Element acceptStatus = bioEvidenceElement.element("IF_SJ");
                if (acceptStatus != null) {
                    limsSampleInfo.setAcceptStatus(acceptStatus.getTextTrim());
                }
                limsSampleInfoList.add(limsSampleInfo);
            }
            warpInfo.put("limsCaseInfo", limsCaseInfo);
            warpInfo.put("consignment", consignment);
            warpInfo.put("limsSampleInfoList", limsSampleInfoList);
            warpInfo.put("limsPersonInfoList", limsPersonInfoList);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException("现勘系统返回的勘验信息不能正确解析！");
        }
        this.logger.error("返回：" + warpInfo);
        return warpInfo;
    }


    private Map<String, Object> analysisXk(String result) throws Exception {
        Map<String, Object> warpInfo = new HashMap();
        LimsCaseInfo caseInfo = new LimsCaseInfo();
        List<LimsSampleInfo> limsSampleInfoList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //解析现勘返回数据
            JSONObject jsonObject = JSONObject.fromObject(result);
            JSONObject resultData = jsonObject.getJSONObject("result");
            //现勘号
            String investigationNo = resultData.get("investigationNo").toString();
            //简要案情
            String exposureProcess = resultData.get("exposureProcess").toString();
            //案件性质
            String caseProperty = resultData.get("caseProperty").toString();
            //案件类别
            String caseType = resultData.get("caseType").toString();
            //是否命案
            //JSONObject caseCategory = resultData.getJSONObject("caseCategory");
            //发案地点
            String sceneDetail = resultData.get("sceneDetail").toString();
            //发案时间
            String occurrenceDate = resultData.get("occurrenceDate").toString();
            //勘验单位
            String investigationOrgan = resultData.get("investigationOrgan").toString();
            //生物物证
            JSONArray evidenceList = resultData.getJSONArray("result");

            caseInfo.setCaseXkNo(investigationNo.toString());
            caseInfo.setCaseBrief(exposureProcess.toString());
            if (caseProperty.toString().equals("0")) {
                caseInfo.setCaseType("2");
            } else if (caseProperty.toString().equals("1")) {
                caseInfo.setCaseType("1");
            }
            caseInfo.setCaseProperty(xkCaseTypeToLimsType(caseType.toString()));
            caseInfo.setCaseLocation(sceneDetail.toString());

            String caseDatetime = occurrenceDate.toString();
            caseInfo.setCaseDatetime(sdf.parse(caseDatetime));

            for (int i = 0; i < evidenceList.size(); i++) {
                JSONObject jsonObj = evidenceList.getJSONObject(i);
                for (int j = 0; j < jsonObj.size(); j++) {
                    LimsSampleInfo sampleInfo = new LimsSampleInfo();
                    //物证名称
                    String evidenceName = jsonObj.get("evidenceName").toString();
                    //样本类型
                    String evidenceSampleCode = jsonObj.get("evidenceSampleCode").toString();
                    String evidenceSampleName = jsonObj.get("evidenceSampleName").toString();
                    //物证编号
                    String evidenceNo = jsonObj.get("evidenceNo").toString();
                    //性状
                    //String character = jsonObj.get("character").toString();
                    //提取人
                    String collectedBy = jsonObj.get("collectedBy").toString();
                    //提取时间
                    String collectedDate = jsonObj.get("collectedDate").toString();
                    //提取部位
                    String leftPosition = jsonObj.get("leftPosition").toString();
                    //提取方法
                    String collectionMode = jsonObj.get("collectionMode").toString();
                    //特征描述
                    String evidenceDesc = jsonObj.get("evidenceDesc").toString();
                    //数量
                    String amount = jsonObj.get("amount").toString();

                    sampleInfo.setSampleType(xkTypeToLimsType(evidenceSampleCode));
                    if (sampleInfo.getSampleType().equals("11") ) {
                        sampleInfo.setSampleTypeName("脱落细胞");
                    } else {
                        sampleInfo.setSampleTypeName(evidenceSampleName);
                    }

                    /*sampleInfo.setSampleType("01");
                    sampleInfo.setSampleTypeName("血样");*/

                    sampleInfo.setSampleName(evidenceName);
                    sampleInfo.setSampleProperties("01");
                    sampleInfo.setOtherPropertiesDesc("棉签");

                    sampleInfo.setEvidenceNo(evidenceNo);
                    sampleInfo.setExtractDatetime(sdf.parse(collectedDate));
                    sampleInfo.setExtractPerson(collectedBy);
                    sampleInfo.setSampleDesc(evidenceDesc);
                    sampleInfo.setSamplePacking("物证袋");
                    sampleInfo.setSampleFlag("0");
                    limsSampleInfoList.add(sampleInfo);
                    break;
                }
            }
        } catch (Exception ex) {
            this.logger.error("转换现勘数据失败" + ex.getMessage());
        }
        warpInfo.put("caseInfo", caseInfo);
        warpInfo.put("limsSampleInfoList", limsSampleInfoList);
        return warpInfo;
    }

    public String xkCaseTypeToLimsType(String caseType) {
        if (caseType == "") {
            return "09";
        }

        switch (caseType) {
            case "040101":
                return "01"; //杀人
            case "050201":
                return "08";//入室盗窃
            case "040103":
                return "13";//伤害
            case "040105":
                return "02";//强奸
            case "040110":
                return "19";//绑架
            case "050101":
                return "05";//抢劫
            case "020101":
                return "14";//放火
            case "020103":
                return "04";//爆炸
            case "020104":
                return "15";//投毒
        }
        return "09";
    }

    public String xkTypeToLimsType(String bioEvidenceType) {
        if (bioEvidenceType == "") {
            return "99";
        }
        int bioEvidenctTypeInt = 0;
        try {
            bioEvidenctTypeInt = Integer.parseInt(bioEvidenceType);
        } catch (Exception e) {
            return "99";
        }
        switch (bioEvidenctTypeInt) {
            case 2002:
                return "01";
            case 2003:
                return "09";
            case 2004:
                return "02";
            case 2005:
                return "04";
            case 2011:
                return "07";
            case 2013:
                return "03";
            case 2014:
                return "03";
            case 2001:
                return "11";
            case 2006:
            case 2007:
            case 2008:
            case 2009:
            case 2010:
            case 2012:
        }
        return "99";
    }

}
