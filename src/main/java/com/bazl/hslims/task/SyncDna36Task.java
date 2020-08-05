package com.bazl.hslims.task;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.helpers.CodisGeneInfoJsonConverter;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.services.center.SceneInvestigationService;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.bazl.hslims.webservice.client.Dna36DataWebService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2017/3/12.
 */
public class SyncDna36Task  implements Runnable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LimsCaseInfoService limsCaseInfoService;
    @Autowired
    private LimsConsignmentService limsConsignmentService;
    @Autowired
    private LimsSampleInfoService limsSampleInfoService;
    @Autowired
    private LimsPersonInfoService limsPersonInfoService;
    @Autowired
    private LimsSampleGeneService limsSampleGeneService;
/*
    @Autowired
    SyncDna36DataService syncDna36DataService;

    @Autowired
    Dna36DataWebService dna36DataWebService;*/


    @Autowired
    SceneInvestigationService sceneInvestigationService;

    int size = 90;

    @Override
    public void run() {

        if(Constants.SYNC_DNA36_TASK_STARTED) {
            return;
        }
        Constants.SYNC_DNA36_TASK_STARTED = true;

        log.info("开始检索并同步数据....");

        //doSync();

        doSceneFeedback();

        log.info("完成同步数据....");

        Constants.SYNC_DNA36_TASK_STARTED = false;
    }


    public void doSceneFeedback(){
        sceneInvestigationService.selectAllNotFeedbak();

        sceneInvestigationService.updateStatus();
    }
/*
    private boolean doSync()
    {
        List<Integer> caseIdList = null;
        int idx = 0;
        int totalCnt = 0;
        try {
            caseIdList = syncDna36DataService.selectPendingCaseIdList();
        } catch(Exception e) {
            log.error("获取待同步数据列表错误！", e);
            return false;
        }

        if(ListUtils.isNullOrEmptyList(caseIdList)){
            try {
                caseIdList = syncDna36DataService.selectFailedCaseIdList();
            } catch(Exception e) {
                log.error("获取失败的待同步数据列表重试时错误！", e);
                return false;
            }
        }

        if(ListUtils.isNullOrEmptyList(caseIdList)){
            return false;
        }

        for(Integer caseId : caseIdList){
            syncByCaseId(caseId);

            *//*try {
                syncByCaseId(caseId);

                syncDna36DataService.addSyncDna36Record(queue);
            }catch(Exception ex){
                queue.setSyncFailedFlag(Constants.FLAG_TRUE);
                String failedMsg = ex.getMessage();
                if(StringUtils.isNotBlank(failedMsg)){
                    if(failedMsg.length() > 2048){
                        queue.setSyncFailedMsg(failedMsg.substring(0, 2048));
                    }else{
                        queue.setSyncFailedMsg(failedMsg);
                    }
                }
                syncDna36DataService.updateQueueFailed(queue);
            }*//*
        }

        log.info("数据同步完成！");

        return true;
    }*/
/*
    private boolean syncByCaseId(Integer caseId) {
        SyncDna36Queue syncDna36Queue = new SyncDna36Queue();
        syncDna36Queue.setCaseId(caseId);
        List<SyncDna36Queue> queueList = syncDna36DataService.selectList(syncDna36Queue);

        if(ListUtils.isNullOrEmptyList(queueList)) {
            return false;
        }

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseId);
        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByCaseId(caseId);
        List<LimsSampleInfo> sampleInfoList = new ArrayList<>();
        List<LimsSampleGene> sampleGeneList = new ArrayList<>();

        LimsSampleInfo sampleInfo = null;
        LimsSampleGene sampleGene = null;
        for(SyncDna36Queue queue : queueList) {
            sampleInfo = limsSampleInfoService.selectById(queue.getSampleId());
            sampleGene = limsSampleGeneService.selectAuditedBySampleId(queue.getSampleId());

            if(sampleInfo != null) {
                sampleInfoList.add(sampleInfo);

                if(sampleGene != null){
                    sampleGeneList.add(sampleGene);
                }
            }
        }

        String newCaseId = UUID.randomUUID().toString();
        newCaseId = newCaseId.replaceAll("-", "");

        String consignmentId = UUID.randomUUID().toString();
        consignmentId = consignmentId.replaceAll("-", "");
        try {
            Map<String, Object> caseInfoJson = getCaseInfoJsonString(newCaseId, consignmentId, caseInfo, consignmentList);
            Map<String, Object> consignmentJson = getConsignmentInfoJsonString(consignmentId, consignmentList);
            List<Map<String, Object>> sceneEvidenceJson = getSceneEvidenceJsonString(newCaseId, consignmentList, sampleInfoList);
            List<Map<String, Object>> casePersonJson = getCasePersonnelSampleJsonString(newCaseId, consignmentList, personInfoList, sampleInfoList);
            List<Map<String, Object>> sampleGeneJson = this.getSampleGeneJsonString(sampleGeneList, sceneEvidenceJson, casePersonJson);

            Map<String, Object> map = new LinkedHashMap<String,Object>();
            map.put("operateType", "2");
            map.put("lawCase", caseInfoJson);
            map.put("consignment", consignmentJson);
            map.put("sceneEvidenceList", sceneEvidenceJson);
            map.put("casePersonnelSampleList", casePersonJson);
            map.put("sampleGeneList", sampleGeneJson);

            executeUpload(map);
            syncDna36DataService.updateQueueSucceedByCaseId(caseId);
        } catch(Exception ex) {
            String failedMsg = ex.getMessage();
            if(StringUtils.isNotBlank(failedMsg)
                    && failedMsg.length() >= 4000){
                failedMsg = failedMsg.substring(0, 4000);
            }

            syncDna36DataService.updateQueueFailedByCaseId(caseId, failedMsg);
            return false;
        }

        return false;
    }*/


    /*private void executeUpload(Map<String, Object> dataMap) throws Exception {
        String str = "[]";
        ObjectMapper jsonObjectMapper = new ObjectMapper();

        *//*String params = "{"
                        + "\"operateType\":\"" + operateType + "\""
                        + ", \"lawCase\":" + caseInfoJson
                        + ", \"consignment\":" + consignmentJson
                        + ", \"sceneEvidenceList\":" + sceneEvidenceJson
                        + ", \"casePersonnelSampleList\":" + casePersonJson
                        + "}";*//*

        try {
            str = jsonObjectMapper.writeValueAsString(dataMap);
        } catch (JsonProcessingException e) {
            log.error("数据转换JSON失败！错误如下："+e);
        }

        String result = this.dna36DataWebService.submitLawCaseRegInfos(str);
        JSONObject resultObj = JSONObject.fromObject(result);
        String success = "true";
        if(resultObj.containsKey("success")){
            success = (String) resultObj.get("success");
        }

        if(!success.equals("true")){
            if(resultObj.containsKey("errorMsg")) {
                throw new Exception((String) resultObj.get("errorMsg"));
            }else{
                throw new Exception("数据同步失败，未知原因！");
            }
        }
    }*/

    static final String LAB_SERVER_NO = "522400";
    static final String LAB_SERVER_NAME = "贵州省毕节地区公安局";
    static final String LAB_ID="A35BECD4786744958AC2C8809E89D2AA";
    static final String SAMPLE_CATEGORY_WUZHENG = "1";           //现场物证
    static final String SAMPLE_CATEGORY_SHOUHAIREN = "2";           //受害人
    static final String SAMPLE_CATEGORY_XIANYIREN = "3";           //嫌疑人
    static final String SAMPLE_CATEGORY_OFFENDER = "4";           //违法犯罪人员
    static final String SAMPLE_CATEGORY_WEIZHIMINGSHITI = "5";           //未知名尸体
    static final String SAMPLE_CATEGORY_SHIZONGRENYUAN = "6";           //失踪人员
    static final String SAMPLE_CATEGORY_SHIZONGRENYUANQINSHU = "7";           //失踪人员亲属
    static final String SAMPLE_CATEGORY_ZAINANRENYUAN = "8";           //灾难人员
    static final String SAMPLE_CATEGORY_ZAINANRENYUANQINSHU = "9";           //灾难人员亲属
    static final String SAMPLE_CATEGORY_TESHUQUNTI = "10";           //特殊群体
    static final String SAMPLE_CATEGORY_JICHUKU = "11";           //基础库
    static final String SAMPLE_CATEGORY_ZHIKONG = "12";           //质控库
    static final String SAMPLE_CATEGORY_GUOJIJIAOHUAN = "13";           //国际交换库
    static final String SAMPLE_CATEGORY_HUNHEWUZHENG = "14";           //混合物证库
    static final String SAMPLE_CATEGORY_ANJIANQITA = "15";           //案件其他人员
    static final String SAMPLE_CATEGORY_XIANYIRENQINSHU = "16";           //嫌疑人亲属
    static final String SAMPLE_CATEGORY_SHOUHAIRENQINSHU = "17";           //受害人亲属
    static final String SAMPLE_CATEGORY_YSTR = "18";           //Y库

    private Map<String, Object> getCaseInfoJsonString(String caseId, String consignmentId, LimsCaseInfo caseInfo, List<LimsConsignment> consignmentList){
        LimsConsignment consignment = consignmentList.get(0);

        Map<String, Object> map = new LinkedHashMap<String,Object>();

        map.put("id", caseId);
        map.put("consignmentId", consignmentId);
        String caseName = "";
        if(StringUtils.isNotBlank(caseInfo.getCaseName())){
            caseName = caseInfo.getCaseName().trim();
            if(caseName.indexOf("\"") > -1){
                caseName.replaceAll("\"", "“");
            }
        }
        map.put("caseName", caseName);
        map.put("externalCaseNo", (StringUtils.isNotBlank(caseInfo.getCaseXkNo()) ? caseInfo.getCaseXkNo().trim() : ""));
        map.put("sceneRegionalism", LAB_SERVER_NO);
        map.put("scenePlace", (StringUtils.isNotBlank(caseInfo.getCaseLocation()) ? caseInfo.getCaseLocation().trim() : ""));
        map.put("occurrenceDate", (caseInfo.getCaseDatetime() == null ? "" : DateUtils.dateToString(caseInfo.getCaseDatetime(),"yyyy-MM-dd HH:mm:ss")));
        map.put("caseType", (StringUtils.isNotBlank(caseInfo.getCaseType()) ? caseInfo.getCaseType() : ""));

        map.put("caseProperty",  (StringUtils.isNotBlank(caseInfo.getCaseProperty()) ? convertToDna36CaseProperty(caseInfo.getCaseProperty()) : ""));
        map.put("identifyRequest", (StringUtils.isNotBlank(consignment.getIdentifyRequirement()) ? consignment.getIdentifyRequirement().trim() : ""));
        String caseSummary = "";
        if(StringUtils.isNotBlank(caseInfo.getCaseBrief())){
            caseSummary = caseInfo.getCaseBrief().trim();
            if(caseSummary.indexOf("\"") > -1){
                caseSummary.replaceAll("\"", "“");
            }
        }
        map.put("caseSummary", caseSummary);
        map.put("caseStatus", "1");
        map.put("matchedStatus", "2");
        map.put("caseLabNo", (StringUtils.isBlank( caseInfo.getCaseNo()) ? "" :  caseInfo.getCaseNo()));
        map.put("receptionManId", "");
        map.put("receptionMan", consignment.getAcceptor());
        map.put("receptionRegionalism", LAB_SERVER_NO);
        map.put("receptionOrgName", LAB_SERVER_NAME);
        map.put("receptionTel", "xxx-xxxxxxxx");
        map.put("storeFlag", "0");
        map.put("labId", LAB_ID);
        map.put("initServerNo", LAB_SERVER_NO);
        map.put("deleteFlag", "0");
        map.put("remark", (StringUtils.isNotBlank(caseInfo.getRemark()) ? caseInfo.getRemark().trim() : ""));
        map.put("createUser", (StringUtils.isBlank(caseInfo.getCreatePerson()) ? "" : caseInfo.getCreatePerson()));
        map.put("createDatetime", (caseInfo.getCreateDatetime() == null ? "" : DateUtils.dateToString(caseInfo.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss")));
        map.put("updateUser", (StringUtils.isBlank(caseInfo.getUpdatePerson()) ? "" : caseInfo.getUpdatePerson()));
        if (StringUtils.isNotBlank(caseInfo.getUpdatePerson())) {
            map.put("updateUser", caseInfo.getUpdatePerson());
        }else {
            map.put("updateUser", "");
        }
        map.put("updateDatetime", (caseInfo.getUpdateDatetime() == null ? "" : DateUtils.dateToString(caseInfo.getUpdateDatetime(),"yyyy-MM-dd HH:mm:ss")));
        /*String caseJson = "{";

        caseJson += "\"id\":\"" + caseId + "\",";
        caseJson += "\"consignmentId\":\"" + consignmentId + "\",";

        String caseName = "";
        if(StringUtils.isNotBlank(caseInfo.getCaseName())){
            caseName = caseInfo.getCaseName().trim();
            if(caseName.indexOf("\"") > -1){
                caseName.replaceAll("\"", "“");
            }
        }
        caseJson += "\"caseName\":\"" + caseName + "\",";
        caseJson += "\"externalCaseNo\":\"" + (StringUtils.isNotBlank(caseInfo.getCaseXkNo()) ? caseInfo.getCaseXkNo().trim() : "") + "\",";
        caseJson += "\"sceneRegionalism\":\"" + LAB_SERVER_NO + "\",";
        caseJson += "\"scenePlace\":\"" + (StringUtils.isNotBlank(caseInfo.getCaseLocation()) ? caseInfo.getCaseLocation().trim() : "") + "\",";
        caseJson += "\"occurrenceDate\":\"" + (caseInfo.getCaseDatetime() == null ? "" : DateUtils.dateToString(caseInfo.getCaseDatetime(),"yyyy-MM-dd HH:mm:ss")) + "\",";
        caseJson += "\"caseType\":\"" + (StringUtils.isNotBlank(caseInfo.getCaseType()) ? caseInfo.getCaseType() : "") + "\",";
        caseJson += "\"caseProperty\":\"" + (StringUtils.isNotBlank(caseInfo.getCaseProperty()) ? convertToDna36CaseProperty(caseInfo.getCaseProperty()) : "") + "\",";
        caseJson += "\"identifyRequest\":\"" + (StringUtils.isNotBlank(consignment.getIdentifyRequirement()) ? consignment.getIdentifyRequirement().trim() : "") + "\",";

        String caseSummary = "";
        if(StringUtils.isNotBlank(caseInfo.getCaseBrief())){
            caseSummary = caseInfo.getCaseBrief().trim();
            if(caseSummary.indexOf("\"") > -1){
                caseSummary.replaceAll("\"", "“");
            }
        }
        caseJson += "\"caseSummary\":\"" + caseSummary + "\",";
        caseJson += "\"caseStatus\":\"1\",";
        caseJson += "\"matchedStatus\":\"2\",";
        caseJson += "\"caseLabNo\":\"" + caseInfo.getCaseNo() + "\",";
        caseJson += "\"receptionManId\":\"\",";
        caseJson += "\"receptionMan\":\"" + consignment.getAcceptor() + "\",";
        caseJson += "\"receptionRegionalism\":\"" + LAB_SERVER_NO + "\",";
        caseJson += "\"receptionOrgName\":\"" + LAB_SERVER_NAME + "\",";
        caseJson += "\"receptionTel\":\"xxx-xxxxxxxx\",";
        caseJson += "\"storeFlag\":\"0\",";
        caseJson += "\"labId\":\"" + LAB_ID + "\",";
        caseJson += "\"initServerNo\":\"" + LAB_SERVER_NO + "\",";
        caseJson += "\"deleteFlag\":\"0\",";
        caseJson += "\"remark\":\"" + (StringUtils.isNotBlank(caseInfo.getRemark()) ? caseInfo.getRemark().trim() : "") + "\",";
        caseJson += "\"createUser\":\"" + (StringUtils.isBlank(caseInfo.getCreatePerson()) ? "" : caseInfo.getCreatePerson()) + "\",";
        caseJson += "\"createDatetime\":\"" + (caseInfo.getCreateDatetime() == null ? "" : DateUtils.dateToString(caseInfo.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss")) + "\",";
        caseJson += "\"updateUser\":\"" + (StringUtils.isBlank(caseInfo.getUpdatePerson()) ? "" : caseInfo.getUpdatePerson()) + "\",";
        caseJson += "\"updateDatetime\":\"" + (caseInfo.getUpdateDatetime() == null ? "" : DateUtils.dateToString(caseInfo.getUpdateDatetime(),"yyyy-MM-dd HH:mm:ss")) + "\"";

        caseJson += "}";*/

        return map;
    }

    private Map getConsignmentInfoJsonString(String consignmentId, List<LimsConsignment> consignmentList){
        LimsConsignment mainConsign = null;
        LimsConsignment supplyConsign = null;
        for(LimsConsignment consign : consignmentList){
            if(consign.getAdditionalFlag().equals(Constants.FLAG_FALSE)){
                mainConsign = consign;
            }else{
                supplyConsign = consign;
            }
        }

        Map map = new LinkedHashMap<String,Object>();

        map.put("id", consignmentId);
        map.put("agnecyRegionalism", mainConsign.getDelegateOrg().substring(0, 6));
        map.put("agencyName", (StringUtils.isNotBlank(mainConsign.getDelegateOrgDesc()) ? mainConsign.getDelegateOrgDesc().trim() : ""));
        map.put("agencyPhone", (mainConsign.getDelegator1Phone() + "、" + mainConsign.getDelegator2Phone()).trim());
        if(mainConsign.getAcceptDatetime() != null)
            map.put("consignDate", DateUtils.dateToString(mainConsign.getAcceptDatetime(),"yyyy-MM-dd HH:mm:ss"));
        else if(mainConsign.getDelegateDatetime() != null)
            map.put("consignDate", DateUtils.dateToString(mainConsign.getDelegateDatetime(),"yyyy-MM-dd HH:mm:ss"));
        else if(mainConsign.getCreateDatetime() != null)
            map.put("consignDate", DateUtils.dateToString(mainConsign.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss"));
        map.put("consignerName", (mainConsign.getDelegator1() + "、" + mainConsign.getDelegator2()));
        map.put("consignerCertificateNo", (mainConsign.getDelegator1Cno() + "、" + mainConsign.getDelegator2Cno()).trim());
        if(supplyConsign != null){
            map.put("supplyAgencyRegionalism", supplyConsign.getDelegateOrg().substring(0, 6));
            map.put("supplyAgencyName", (StringUtils.isNotBlank(supplyConsign.getDelegateOrgDesc()) ? supplyConsign.getDelegateOrgDesc().trim() : ""));
            map.put("supplyMan", (supplyConsign.getDelegator1() + "、" + supplyConsign.getDelegator2()).trim());
            map.put("supplyManPhone", (supplyConsign.getDelegator1Phone() + "、" + supplyConsign.getDelegator2Phone()).trim());
            map.put("supplyDate", (supplyConsign.getDelegateDatetime() == null ? "" : DateUtils.dateToString(supplyConsign.getDelegateDatetime(),"yyyy-MM-dd HH:mm:ss")));
        }
        map.put("labId", LAB_ID);
        map.put("initServerNo", LAB_SERVER_NO);
        map.put("deleteFlag", "0");
        map.put("remark", (StringUtils.isNotBlank(mainConsign.getRemark()) ? mainConsign.getRemark().trim() : ""));
        map.put("createUser", (StringUtils.isBlank(mainConsign.getCreatePerson()) ? "" : mainConsign.getCreatePerson()));
        map.put("createDatetime", (mainConsign.getCreateDatetime() == null ? "" : DateUtils.dateToString(mainConsign.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss")));
        if (StringUtils.isNotBlank(mainConsign.getUpdatePerson())) {
            map.put("updateUser", mainConsign.getUpdatePerson());
        }else {
            map.put("updateUser", "");
        }
        map.put("updateDatetime", (mainConsign.getUpdateDatetime() == null ? "" : DateUtils.dateToString(mainConsign.getUpdateDatetime(),"yyyy-MM-dd HH:mm:ss")));

        /*String consignmentJson = "{";

        consignmentJson += "\"id\":\"" + consignmentId + "\",";
        consignmentJson += "\"agnecyRegionalism\":\"" + mainConsign.getDelegateOrg().substring(0, 6) + "\",";
        consignmentJson += "\"agencyName\":\"" + (StringUtils.isNotBlank(mainConsign.getDelegateOrgDesc()) ? mainConsign.getDelegateOrgDesc().trim() : "") + "\",";
        consignmentJson += "\"agencyPhone\":\"" + (mainConsign.getDelegator1Phone() + "、" + mainConsign.getDelegator2Phone()).trim() + "\",";
        if(mainConsign.getAcceptDatetime() != null)
            consignmentJson += "\"consignDate\":\"" + DateUtils.dateToString(mainConsign.getAcceptDatetime(),"yyyy-MM-dd HH:mm:ss") + "\",";
        else if(mainConsign.getDelegateDatetime() != null)
            consignmentJson += "\"consignDate\":\"" + DateUtils.dateToString(mainConsign.getDelegateDatetime(),"yyyy-MM-dd HH:mm:ss") + "\",";
        else if(mainConsign.getCreateDatetime() != null)
            consignmentJson += "\"consignDate\":\"" + DateUtils.dateToString(mainConsign.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss") + "\",";


        consignmentJson += "\"consignerName\":\"" + (mainConsign.getDelegator1() + "、" + mainConsign.getDelegator2()) + "\",";
        consignmentJson += "\"consignerCertificateNo\":\"" + (mainConsign.getDelegator1Cno() + "、" + mainConsign.getDelegator2Cno()).trim() + "\",";

        if(supplyConsign != null){
            consignmentJson += "\"supplyAgencyRegionalism\":\"" + supplyConsign.getDelegateOrg().substring(0, 6) + "\",";
            consignmentJson += "\"supplyAgencyName\":\"" + (StringUtils.isNotBlank(supplyConsign.getDelegateOrgDesc()) ? supplyConsign.getDelegateOrgDesc().trim() : "") + "\",";
            consignmentJson += "\"supplyMan\":\"" + (supplyConsign.getDelegator1() + "、" + supplyConsign.getDelegator2()).trim() + "\",";
            consignmentJson += "\"supplyManPhone\":\"" + (supplyConsign.getDelegator1Phone() + "、" + supplyConsign.getDelegator2Phone()).trim() + "\",";
            consignmentJson += "\"supplyDate\":\"" + (supplyConsign.getDelegateDatetime() == null ? "" : DateUtils.dateToString(supplyConsign.getDelegateDatetime(),"yyyy-MM-dd HH:mm:ss")) + "\",";
        }

        consignmentJson += "\"labId\":\"" + LAB_ID + "\",";
        consignmentJson += "\"initServerNo\":\"" + LAB_SERVER_NO + "\",";
        consignmentJson += "\"deleteFlag\":\"0\",";
        consignmentJson += "\"remark\":\"" + (StringUtils.isNotBlank(mainConsign.getRemark()) ? mainConsign.getRemark().trim() : "") + "\",";
        consignmentJson += "\"createUser\":\"" + (StringUtils.isBlank(mainConsign.getCreatePerson()) ? "" : mainConsign.getCreatePerson()) + "\",";
        consignmentJson += "\"createDatetime\":\"" + (mainConsign.getCreateDatetime() == null ? "" : DateUtils.dateToString(mainConsign.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss")) + "\",";
        consignmentJson += "\"updateUser\":\"" + (StringUtils.isBlank(mainConsign.getUpdatePerson()) ? "" : mainConsign.getUpdatePerson()) + "\",";
        consignmentJson += "\"updateDatetime\":\"" + (mainConsign.getUpdateDatetime() == null ? "" : DateUtils.dateToString(mainConsign.getUpdateDatetime(),"yyyy-MM-dd HH:mm:ss")) + "\"";

        consignmentJson += "}";*/

        return map;
    }


    static final String DEFAULT_EXAMINE_USER1_ID = "F7B035ECED5759CA82570E9FEFB05C75";       //管理员ID
    static final String DEFAULT_EXAMINE_USER1 = "管理员";  //管理员名称
    private List<Map<String, Object>> getSceneEvidenceJsonString(String caseId, List<LimsConsignment> consignmentList, List<LimsSampleInfo> sampleInfoList){

        List<Map<String,Object>> sceneEvidenceList = new ArrayList<>();
        String evidenceId = "";
        for (LimsSampleInfo sampleInfo : sampleInfoList) {
            Map<String,Object> paramMap = new LinkedHashMap<String,Object>();
            if(sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_PERSON)){
                continue;
            }

            evidenceId = UUID.randomUUID().toString();
            evidenceId = evidenceId.replaceAll("-", "");

            paramMap.put("id", evidenceId);
            paramMap.put("caseId", caseId);
            String evidenceName = "";
            if(StringUtils.isNotBlank(sampleInfo.getSampleName())){
                evidenceName = sampleInfo.getSampleName().trim();
                if(evidenceName.indexOf("\"") > -1){
                    evidenceName.replaceAll("\"", "“");
                }
            }
            paramMap.put("evidenceName", evidenceName);
            paramMap.put("sampleType", sampleInfo.getSampleType());
            paramMap.put("samplePackaging", (StringUtils.isNotBlank(sampleInfo.getSamplePacking()) ? sampleInfo.getSamplePacking().trim() : ""));
            paramMap.put("sampleLabNo", sampleInfo.getSampleNo());
            String description = "";
            if(StringUtils.isNotBlank(sampleInfo.getSampleDesc())){
                description = sampleInfo.getSampleDesc().trim();
                if(description.indexOf("\"") > -1){
                    description.replaceAll("\"", "“");
                }
            }
            paramMap.put("description", description);
            paramMap.put("examineUser1", DEFAULT_EXAMINE_USER1);
            paramMap.put("examineUser1Id", DEFAULT_EXAMINE_USER1_ID);
            paramMap.put("labId", LAB_ID);
            paramMap.put("storeFlag", Constants.FLAG_TRUE);
            paramMap.put("initServerNo", LAB_SERVER_NO);
            paramMap.put("deleteFlag", "0");
            paramMap.put("remark", (StringUtils.isNotBlank(sampleInfo.getRemark()) ? sampleInfo.getRemark().trim() : ""));
            paramMap.put("createUser", StringUtils.isBlank(sampleInfo.getCreatePerson()) ? "" : sampleInfo.getCreatePerson());
            paramMap.put("createDatetime", (sampleInfo.getCreateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getCreateDatetime(), "yyyy-MM-dd HH:mm:ss")));
            if (StringUtils.isNotBlank(sampleInfo.getUpdatePerson())) {
                paramMap.put("updateUser", sampleInfo.getUpdatePerson());
            }else {
                paramMap.put("updateUser", "");
            }
            paramMap.put("updateDatetime", (sampleInfo.getUpdateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getUpdateDatetime(), "yyyy-MM-dd HH:mm:ss")));
            for(LimsConsignment consign : consignmentList){
                if(sampleInfo.getConsignmentId().equals(consign.getId())
                        &&consign.getAdditionalFlag().equals(Constants.FLAG_TRUE)){
                    paramMap.put("isSupplied", "1");
                    break;
                }
            }

            sceneEvidenceList.add(paramMap);
        }

       /* String evidenceJson = "[";
        int idx = 0;
        String evidenceId = "";
        for(LimsSampleInfo sampleInfo : sampleInfoList) {
            if(sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_PERSON)){
                continue;
            }
            if(idx == 0) {
                evidenceJson += "{";
            }else{
                evidenceJson += ",{";
            }
            evidenceId = UUID.randomUUID().toString();
            if(evidenceId.length() > 32){
                evidenceId = evidenceId.replaceAll("-", "");
            }
            evidenceJson += "\"id\":\"" + evidenceId + "\",";
            evidenceJson += "\"caseId\":\"" + caseId + "\",";

            String evidenceName = "";
            if(StringUtils.isNotBlank(sampleInfo.getSampleName())){
                evidenceName = sampleInfo.getSampleName().trim();
                if(evidenceName.indexOf("\"") > -1){
                    evidenceName.replaceAll("\"", "“");
                }
            }

            String description = "";
            if(StringUtils.isNotBlank(sampleInfo.getSampleDesc())){
                description = sampleInfo.getSampleDesc().trim();
                if(description.indexOf("\"") > -1){
                    description.replaceAll("\"", "“");
                }
            }

            evidenceJson += "\"evidenceName\":\"" + evidenceName + "\",";
            evidenceJson += "\"sampleType\":\"" + sampleInfo.getSampleType() + "\",";
            evidenceJson += "\"samplePackaging\":\"" + (StringUtils.isNotBlank(sampleInfo.getSamplePacking()) ? sampleInfo.getSamplePacking().trim() : "") + "\",";
            evidenceJson += "\"sampleLabNo\":\"" + sampleInfo.getSampleNo() + "\",";
            evidenceJson += "\"description\":\"" + description + "\",";
            evidenceJson += "\"examineUser1\":\"" + DEFAULT_EXAMINE_USER1 + "\",";
            evidenceJson += "\"examineUser1Id\":\"" + DEFAULT_EXAMINE_USER1_ID + "\",";
            evidenceJson += "\"labId\":\"" + LAB_ID + "\",";
            evidenceJson += "\"storeFlag\":\"" + Constants.FLAG_FALSE + "\",";
            evidenceJson += "\"initServerNo\":\"" + LAB_SERVER_NO + "\",";
            evidenceJson += "\"deleteFlag\":\"0\",";
            evidenceJson += "\"remark\":\"" + (StringUtils.isNotBlank(sampleInfo.getRemark()) ? sampleInfo.getRemark().trim() : "") + "\",";
            evidenceJson += "\"createUser\":\"" + sampleInfo.getCreatePerson() + "\",";
            evidenceJson += "\"createDatetime\":\"" + (sampleInfo.getCreateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getCreateDatetime(), "yyyy-MM-dd HH:mm:ss")) + "\",";
            evidenceJson += "\"updateUser\":\"" + sampleInfo.getUpdatePerson() + "\",";
            evidenceJson += "\"updateDatetime\":\"" + (sampleInfo.getUpdateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getUpdateDatetime(), "yyyy-MM-dd HH:mm:ss")) + "\"";

            for(LimsConsignment consign : consignmentList){
                if(sampleInfo.getConsignmentId().equals(consign.getId())
                        &&consign.getAdditionalFlag().equals(Constants.FLAG_TRUE)){
                    evidenceJson += ",\"isSupplied\":\"1\",";
                    break;
                }
            }

            evidenceJson += "}";
            idx++;
        }
        evidenceJson += "]";*/

        return sceneEvidenceList;
    }


    private List<Map<String,Object>> getCasePersonnelSampleJsonString(String caseId, List<LimsConsignment> consignmentList, List<LimsPersonInfo> personInfoList, List<LimsSampleInfo> sampleInfoList){

        List<Map<String,Object>> casePersonnelSampleList = new ArrayList<>();
        LimsPersonInfo personInfo = null;
        String personId = "";
        for (LimsSampleInfo sampleInfo : sampleInfoList) {
            Map<String,Object> paramMap = new LinkedHashMap<String,Object>();
            if(sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)){
                continue;
            }

            for(LimsPersonInfo per : personInfoList){
                if(sampleInfo.getRefPersonId().equals(per.getId())){
                    personInfo = per;
                    break;
                }
            }

            personId = UUID.randomUUID().toString();
            personId = personId.replaceAll("-", "");

            paramMap.put("id", personId);
            paramMap.put("caseId", caseId);
            paramMap.put("relationWithCase", convertPersonTypeToRelationWithCase(personInfo.getPersonType()));
            String personnelName = "";
            if(StringUtils.isNotBlank(personInfo.getPersonName())){
                personnelName = personInfo.getPersonName().trim();
                if(personnelName.indexOf("\"") > -1){
                    personnelName.replaceAll("\"", "“");
                }
            }
            paramMap.put("personnelName", personnelName);
            paramMap.put("gender", convertPersonGender(personInfo.getPersonType()));
            paramMap.put("idCardNo", (StringUtils.isNotBlank(personInfo.getPersonIdcardNo()) ? personInfo.getPersonIdcardNo().trim() : "无"));
            paramMap.put("sampleType", sampleInfo.getSampleType());
            paramMap.put("samplePackaging", (StringUtils.isNotBlank(sampleInfo.getSamplePacking()) ? sampleInfo.getSamplePacking().trim() : ""));
            paramMap.put("sampleLabNo", sampleInfo.getSampleNo());
            paramMap.put("description", (StringUtils.isNotBlank(sampleInfo.getSampleDesc()) ? sampleInfo.getSampleDesc().trim() : ""));
            paramMap.put("examineUser1", DEFAULT_EXAMINE_USER1);
            paramMap.put("examineUser1Id", DEFAULT_EXAMINE_USER1_ID);
            paramMap.put("labId", LAB_ID);
            paramMap.put("storeFlag", Constants.FLAG_TRUE);
            paramMap.put("initServerNo", LAB_SERVER_NO);
            paramMap.put("deleteFlag", "0");
            paramMap.put("remark", (StringUtils.isNotBlank(sampleInfo.getRemark()) ? sampleInfo.getRemark().trim() : ""));
            paramMap.put("createUser", StringUtils.isBlank(sampleInfo.getCreatePerson()) ? "" : sampleInfo.getCreatePerson());
            paramMap.put("createDatetime", (sampleInfo.getCreateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getCreateDatetime(), "yyyy-MM-dd HH:mm:ss")));
            if (StringUtils.isNotBlank(sampleInfo.getUpdatePerson())) {
                paramMap.put("updateUser", sampleInfo.getUpdatePerson());
            }else {
                paramMap.put("updateUser", "");
            }
            paramMap.put("updateDatetime", (sampleInfo.getUpdateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getUpdateDatetime(), "yyyy-MM-dd HH:mm:ss")));
            for(LimsConsignment consign : consignmentList){
                if(sampleInfo.getConsignmentId().equals(consign.getId())
                        &&consign.getAdditionalFlag().equals(Constants.FLAG_TRUE)){
                    paramMap.put("isSupplied", "1");
                    break;
                }
            }

            casePersonnelSampleList.add(paramMap);
        }


        /*String casePersonJson = "[";

        int idx = 0;
        LimsPersonInfo personInfo = null;
        String personId = "";
        for(LimsSampleInfo sampleInfo : sampleInfoList) {
            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                continue;
            }

            for(LimsPersonInfo per : personInfoList){
                if(sampleInfo.getRefPersonId().equals(per.getId())){
                    personInfo = per;
                    break;
                }
            }

            //if(personInfo.getPersonType().equals(Constants.PERSON_TYPE_BEIHAIREN_RELATIVE)){
                //TODO
                //亲属类型人员需要同步到国家库的RELATIVE表中
                //
            //    continue;
            //}

            if (idx == 0) {
                casePersonJson += "{";
            } else {
                casePersonJson += ",{";
            }
            personId = UUID.randomUUID().toString();
            if(personId.length() > 32){
                personId = personId.replaceAll("-", "");
            }
            casePersonJson += "\"id\":\"" + personId + "\",";
            casePersonJson += "\"caseId\":\"" + caseId + "\",";
            casePersonJson += "\"relationWithCase\":\"" + convertPersonTypeToRelationWithCase(personInfo.getPersonType()) + "\",";

            String personnelName = "";
            if(StringUtils.isNotBlank(personInfo.getPersonName())){
                personnelName = personInfo.getPersonName().trim();
                if(personnelName.indexOf("\"") > -1){
                    personnelName.replaceAll("\"", "“");
                }
            }

            casePersonJson += "\"personnelName\":\"" + personnelName + "\",";
            casePersonJson += "\"gender\":\"" + convertPersonGender(personInfo.getPersonType()) + "\",";
            casePersonJson += "\"idCardNo\":\"" + (StringUtils.isNotBlank(personInfo.getPersonIdcardNo()) ? personInfo.getPersonIdcardNo().trim() : "无") + "\",";
            casePersonJson += "\"sampleType\":\"" + sampleInfo.getSampleType() + "\",";
            casePersonJson += "\"samplePackaging\":\"" + (StringUtils.isNotBlank(sampleInfo.getSamplePacking()) ? sampleInfo.getSamplePacking().trim() : "") + "\",";
            casePersonJson += "\"sampleLabNo\":\"" + sampleInfo.getSampleNo() + "\",";
            casePersonJson += "\"description\":\"" + (StringUtils.isNotBlank(sampleInfo.getSampleDesc()) ? sampleInfo.getSampleDesc().trim() : "") + "\",";
            casePersonJson += "\"examineUser1\":\"" + DEFAULT_EXAMINE_USER1 + "\",";
            casePersonJson += "\"examineUser1Id\":\"" + DEFAULT_EXAMINE_USER1_ID + "\",";
            casePersonJson += "\"labId\":\"" + LAB_ID + "\",";
            casePersonJson += "\"storeFlag\":\"" + Constants.FLAG_FALSE + "\",";
            casePersonJson += "\"initServerNo\":\"" + LAB_SERVER_NO + "\",";
            casePersonJson += "\"deleteFlag\":\"0\",";
            casePersonJson += "\"remark\":\"" + (StringUtils.isNotBlank(sampleInfo.getRemark()) ? sampleInfo.getRemark().trim() : "") + "\",";
            casePersonJson += "\"createUser\":\"" + sampleInfo.getCreatePerson() + "\",";
            casePersonJson += "\"createDatetime\":\"" + (sampleInfo.getCreateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getCreateDatetime(), "yyyy-MM-dd HH:mm:ss")) + "\",";
            casePersonJson += "\"updateUser\":\"" + sampleInfo.getUpdatePerson() + "\",";
            casePersonJson += "\"updateDatetime\":\"" + (sampleInfo.getUpdateDatetime() == null ? "" : DateUtils.dateToString(sampleInfo.getUpdateDatetime(), "yyyy-MM-dd HH:mm:ss")) + "\"";

            for(LimsConsignment consign : consignmentList){
                if(sampleInfo.getConsignmentId().equals(consign.getId())
                        &&consign.getAdditionalFlag().equals(Constants.FLAG_TRUE)){
                    casePersonJson += ",\"isSupplied\":\"1\",";
                    break;
                }
            }

            casePersonJson += "}";
            idx++;
        }
        casePersonJson += "]";*/

        return casePersonnelSampleList;
    }

    /**
     * 生成上报基因分型的列表
     * @param sampleGeneList
     * @return
     */
    public List<Map<String, Object>> getSampleGeneJsonString(List<LimsSampleGene> sampleGeneList,
                                 List<Map<String,Object>> sceneEvidenceList, List<Map<String,Object>> casePersonSampleList)
        throws Exception {
        List<Map<String, Object>> geneMapList = new ArrayList<>();

        Map<String, Object> geneMap = null;
        for(LimsSampleGene sampleGene : sampleGeneList){
            geneMap = new HashMap<>();

            String id = UUID.randomUUID().toString();
            id = id.replaceAll("-", "");
            geneMap.put("id", id);
            geneMap.put("sampleFlag", sampleGene.getSampleFlag());      //样本类型，人员样本或物证样本
            geneMap.put("labId", LAB_ID);
            geneMap.put("serverNo", LAB_SERVER_NO);

            if(Constants.SAMPLE_FLAG_EVIDENCE.equals(sampleGene.getSampleFlag())){
                geneMap.put("category", "1");           //1-物证样本， 2-案件人员样本， 3-亲属样本
                geneMap.put("sampleId", getUploadSampleIdBySampleNo(sampleGene.getSampleNo(), sceneEvidenceList));
                geneMap.put("sampleCategory", "1");
            }else{
                geneMap.put("category", "2");           //1-物证样本， 2-案件人员样本， 3-亲属样本
                geneMap.put("sampleId", getUploadSampleIdBySampleNo(sampleGene.getSampleNo(), casePersonSampleList));
                geneMap.put("sampleCategory", getUploadSampleCategoryBySampleNo(sampleGene.getSampleNo(), casePersonSampleList));
            }

            geneMap.put("sampleLabNo", sampleGene.getSampleNo());
            geneMap.put("storeDate", (sampleGene.getCreateDatetime() == null ? "" : DateUtils.dateToString(sampleGene.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss")));
            geneMap.put("storeBy", sampleGene.getCreatePerson());
            geneMap.put("storeFlag", "1");
            if (StringUtils.isBlank(sampleGene.getAuditPerson())) {
                geneMap.put("reviewBy", sampleGene.getCreatePerson());
            }else {
                geneMap.put("reviewBy", sampleGene.getAuditPerson());
            }
            if (sampleGene.getAuditDatetime() == null) {
                geneMap.put("reviewDate", DateUtils.dateToString(sampleGene.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss"));
            }else {
                geneMap.put("reviewDate", DateUtils.dateToString(sampleGene.getAuditDatetime(),"yyyy-MM-dd HH:mm:ss"));
            }
            geneMap.put("reviewStatus", "1");
            geneMap.put("reagentKit", "");          //TODO 试剂盒

            String geneInfoStr = sampleGene.getGeneInfo();
            List<CodisGeneInfo> geneInfoList = CodisGeneInfoJsonConverter.convertJsonStringToCodisGeneInfoList(geneInfoStr);
            int alleleCount = 0;
            HashMap<String, String> dna36LocusMap = Dna36LocusNameMap.getDna36LocusNameMap();
            for(CodisGeneInfo codisGene : geneInfoList) {
                if(dna36LocusMap.containsKey(codisGene.getGeneName().toUpperCase())){
                    geneMap.put(dna36LocusMap.get(codisGene.getGeneName().toUpperCase())+"_1", codisGene.getGeneVal1());
                    geneMap.put(dna36LocusMap.get(codisGene.getGeneName().toUpperCase())+"_2", codisGene.getGeneVal2());
                }else{
                    geneMap.put(codisGene.getGeneName().toUpperCase() + "_1", codisGene.getGeneVal1());
                    geneMap.put(codisGene.getGeneName().toUpperCase() + "_2", codisGene.getGeneVal2());
                }

                if(StringUtils.isNotBlank(codisGene.getGeneVal1())
                        && StringUtils.isNotBlank(codisGene.getGeneVal2())){
                    alleleCount++;
                }
            }
            geneMap.put("alleleCount", alleleCount);
            geneMap.put("matchedStatus", "2");
            geneMap.put("createUser", sampleGene.getCreatePerson());
            geneMap.put("createDatetime", (sampleGene.getCreateDatetime() == null ? "" : DateUtils.dateToString(sampleGene.getCreateDatetime(),"yyyy-MM-dd HH:mm:ss")));
            if (StringUtils.isNotBlank(sampleGene.getUpdatePerson())) {
                geneMap.put("updateUser", sampleGene.getUpdatePerson());
            }else {
                geneMap.put("updateUser", "");
            }
            geneMap.put("updateDatetime", (sampleGene.getUpdateDatetime() == null ? "" : DateUtils.dateToString(sampleGene.getUpdateDatetime(),"yyyy-MM-dd HH:mm:ss")));
            geneMapList.add(geneMap);
        }

        return geneMapList;
    }

    private String getUploadSampleCategoryBySampleNo(String sampleNo, List<Map<String,Object>> sampleList){
        for(Map<String, Object> map : sampleList){
            if(map.containsKey("sampleLabNo")
                    && map.get("sampleLabNo").equals(sampleNo)){

                String relationWithCase = map.get("relationWithCase").toString();
                if("1".equals(relationWithCase)){
                    return SAMPLE_CATEGORY_XIANYIREN;     //嫌疑人
                }else if("2".equals(relationWithCase)){
                    return SAMPLE_CATEGORY_SHOUHAIREN;     //被害人
                }else {
                    return SAMPLE_CATEGORY_ANJIANQITA;
                }
            }
        }

        return null;
    }


    private String getUploadSampleIdBySampleNo(String sampleNo, List<Map<String,Object>> sampleList){
        for(Map<String, Object> map : sampleList){
            if(map.containsKey("sampleLabNo")
                    && map.get("sampleLabNo").equals(sampleNo)){
                return map.get("id").toString();
            }
        }

        return null;
    }

    private String convertPersonTypeToRelationWithCase(String personType){
        if(Constants.PERSON_TYPE_XIANYIREN.equals(personType)){
            return "1";     //嫌疑人
        }else if(Constants.PERSON_TYPE_BEIHAIREN.equals(personType)){
            return "2";     //被害人
        }

        return "3";
    }


    private String convertPersonGender(String personGender){
        if(Constants.GENDER_MALE_NAME.equals(personGender)){
            return "1";     //男
        }else if(Constants.GENDER_FEMALE_NAME.equals(personGender)){
            return "2";     //女
        }

        return "9";
    }


    //案件性质转换
    private String convertToDna36CaseProperty(String limsCaseProperty){
        String dna36CaseProperty = limsCaseProperty;

        if("01".equals(limsCaseProperty)){              //杀人
            dna36CaseProperty = "01";
        }else if("02".equals(limsCaseProperty) || "23".equals(limsCaseProperty)){              //强奸、轮奸
            dna36CaseProperty = "02";
        }else if("03".equals(limsCaseProperty)){              //碎尸
            dna36CaseProperty = "03";
        }else if("04".equals(limsCaseProperty)){              //爆炸
            dna36CaseProperty = "04";
        }else if("05".equals(limsCaseProperty)){              //抢夺抢劫
            dna36CaseProperty = "05";
        }else if("06".equals(limsCaseProperty)){              //民事
            dna36CaseProperty = "06";
        }else if("07".equals(limsCaseProperty)){              //交通
            dna36CaseProperty = "07";
        }else if("08".equals(limsCaseProperty)                  //盗窃
                || "09".equals(limsCaseProperty)
                || "10".equals(limsCaseProperty)
                || "11".equals(limsCaseProperty)
                || "12".equals(limsCaseProperty)){
            dna36CaseProperty = "08";
        }else if("13".equals(limsCaseProperty)){              //伤害
            dna36CaseProperty = "09";
        }else if("14".equals(limsCaseProperty)){              //纵火
            dna36CaseProperty = "10";
        }else if("15".equals(limsCaseProperty)){              //投毒
            dna36CaseProperty = "11";
        }else if("16".equals(limsCaseProperty)){              //毒品
            dna36CaseProperty = "14";
        }else if("17".equals(limsCaseProperty)){              //意外
            dna36CaseProperty = "15";
        }else if("18".equals(limsCaseProperty)){              //劫持
            dna36CaseProperty = "17";
        }else if("19".equals(limsCaseProperty)){              //绑架
            dna36CaseProperty = "16";
        }else if("20".equals(limsCaseProperty) || "21".equals(limsCaseProperty)){              //诈骗、敲诈勒索
            dna36CaseProperty = "18";
        }else if("22".equals(limsCaseProperty)){              //焚尸
            dna36CaseProperty = "19";
        }else if("24".equals(limsCaseProperty)){              //其他
            dna36CaseProperty = "99";
        }

        return dna36CaseProperty;
    }

}
