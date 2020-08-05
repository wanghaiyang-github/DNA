package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DataFormat;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.RedisCacheUtil;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.SampleInfoDataModel;
import com.bazl.hslims.web.helper.GeneCompareHelper;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/4.
 */
@Controller
@RequestMapping("/center/3")
public class libraryCompareController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    DictService dictService;
    @Autowired
    CaseInnerMatchedService caseInnerMatchedService;
    @Autowired
    RaceInfoService raceInfoService;
    @Autowired
    AlleleFrequencyService alleleFrequencyService;
    @Autowired
    CaseCompareResultInfoService caseCompareResultInfoService;
    @Autowired
    MatchResultService matchResultService;
    @Autowired
    MatchGroupService matchGroupService;
    @Autowired
    RedisCacheUtil redisCacheUtil;

    @RequestMapping("/libraryCompare.html")
    public ModelAndView libraryCompare(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        if (StringUtils.isBlank(query.getEntity().getCaseName())) {
            query.getEntity().setCaseName(null);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseXkNo())) {
            query.getEntity().setCaseXkNo(null);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseStatus())) {
            query.getEntity().setCaseStatus(null);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseType())) {
            query.getEntity().setCaseType(null);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseProperty())) {
            query.getEntity().setCaseProperty(null);
        }

        if (query.getAcceptDateStart() == null) {
            query.setAcceptDateStart(null);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (query.getAcceptDateEnd() == null) {
            query.setAcceptDateEnd(null);
        }else{
            oldTime = sdf.format(query.getAcceptDateEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            query.setAcceptDateEnd(newTime);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseNo())) {
            query.getEntity().setCaseNo(null);
        }

        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCount(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("query", query);
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/result/libraryCompare");
        return modelAndView;
    }

    @RequestMapping("/enterDnaCompare.html")
    public ModelAndView libraryCompare(HttpServletRequest request, Integer caseId) {
        ModelAndView modelAndView = new ModelAndView();
        LimsSampleInfo sampleInfo = new LimsSampleInfo();
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectAuditStatusListByCaseId(caseId);
        List<DictItem> dictSampleEntryTypeList = dictService.selectByParentDictTypeCode(Constants.SAMPLE_ENTRY_TYPE);
        modelAndView.addObject("limsSampleInfoList", sampleInfoList);
        modelAndView.addObject("sampleInfo", sampleInfo);
        modelAndView.addObject("dictSampleEntryTypeList", dictSampleEntryTypeList);
        modelAndView.setViewName("center/result/enterDnaCompare");
        return modelAndView;
    }

    @RequestMapping(value = "/submitAndCompare.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitAndCompare(HttpServletRequest request, @RequestBody SampleInfoDataModel sampleInfoDataModel) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            List<MatchResult> matchResultList = new ArrayList<>();

            List<LimsSampleInfo> sampleInfoList = sampleInfoDataModel.getSampleInfoList();

            LimsSampleInfo sampleInfo;
            for (int i = 0; i < sampleInfoList.size(); i++) {
                sampleInfo = sampleInfoList.get(i);
                sampleInfo.setSubmitFlag("1");
                sampleInfo.setSubmitDatetime(new Date());
                sampleInfo.setSubmitPerson(LimsSecurityUtils.getLoginName());
                limsSampleInfoService.updateSubmitFlag(sampleInfo);

                String sampleEntryType = sampleInfo.getSampleEntryType();
                if(sampleEntryType.equals("08") || sampleEntryType.equals("09") || sampleEntryType.equals("10")){
                    //向redis中插入亲缘关系
                    //构造json:sameGroupSample(同组样本id)、sameGroupSampleRole(同组样本角色)、targetSampleRole(目标样本角色)
                    String jsonResult = constructJson("sameGroupSample", String.valueOf(sampleInfo.getSameGroupSample()), "sameGroupSampleRole", String.valueOf(sampleInfo.getSameGroupSampleRole()), "targetSampleRole", sampleInfo.getTargetSampleRole());
                    redisCacheUtil.hset("relative_dna_lib", String.valueOf(sampleInfo.getId()), jsonResult);
                    List<MatchResult> resultList = relativeCompare(sampleInfo);
                    if(ListUtils.isNotNullAndEmptyList(resultList)){
                        matchResultList.addAll(resultList);
                    }
                } else {
                    List<MatchResult> resultList = sameCompare(sampleInfo);
                    if(ListUtils.isNotNullAndEmptyList(resultList)){
                        matchResultList.addAll(resultList);
                    }
                }
            }
            saveMatchResult(matchResultList);
            result.put("success", true);
            result.put("matchResultList", matchResultList);
        } catch (Exception e) {
            result.put("success", false);
            logger.error(e.getMessage());
        }
        return result;
    }

    public void saveMatchResult(List<MatchResult> matchResultList){
        LabUser labUser = LimsSecurityUtils.getLoginLabUser();

        if(ListUtils.isNotNullAndEmptyList(matchResultList)) {
            for(MatchResult matchResult : matchResultList){
                matchResult.setCreatePerson(labUser.getUserName());
                matchResult.setCreateDatetime(new Date());
                Integer groupId = null;
                Integer sampleId = matchResult.getSample1Id();
                String matchMode = matchResult.getMatchedMode();
                List<MatchResult> matchResults = matchResultService.selectListBySampleIdAndMatchMode(sampleId, matchMode);
                if(ListUtils.isNullOrEmptyList(matchResults)){
                    sampleId = matchResult.getSample2Id();
                    matchResults = matchResultService.selectListBySampleId(sampleId);
                }

                Boolean flag = false;
                if(ListUtils.isNotNullAndEmptyList(matchResults)){
                    groupId = matchResults.get(0).getGroupId();
                    for(MatchResult result : matchResults){
                        if(sortSampleId(matchResult.getSample1Id(), matchResult.getSample2Id(), matchResult.getSample3Id()).
                                equals(sortSampleId(result.getSample1Id(), result.getSample2Id(), result.getSample3Id()))){
                            flag = true;
                            break;
                        }
                    }
                }

                if (flag){
                    continue;
                }
                if(groupId != null){
                    matchResult.setGroupId(groupId);
                    matchResultService.insert(matchResult);
                }else{
                    MatchGroup matchGroup = new MatchGroup();
                    matchGroup.setGroupNo("");
                    matchGroup.setMatchedMode(1);
                    matchGroupService.insert(matchGroup);
                    matchResult.setGroupId(matchGroup.getId());
                    matchResultService.insert(matchResult);
                }
            }
        }
    }

    public List<MatchResult> sameCompare(LimsSampleInfo sampleInfo) {
        String populationName = SystemUtil.getSystemConfig().getProperty("populationName");
        String minSameCount = SystemUtil.getSystemConfig().getProperty("minSameCount");
        String maxDiffCount = SystemUtil.getSystemConfig().getProperty("maxDiffCount");
        //从redis中取得gene信息
        Map<Object, Object> sampleGeneMap = redisCacheUtil.hgetAll("sample_gene");
        LimsSampleGene sourceSampleGene = limsSampleGeneService.selectAuditedBySampleId(sampleInfo.getId());
        List<MatchResult> matchResultList = new ArrayList<>();

        try {
            //进行同型比对
            if(sampleGeneMap != null && sampleGeneMap.size() > 0){
                for (Map.Entry entry : sampleGeneMap.entrySet()) {
                    MatchResult matchResult = new MatchResult();

                    if(sourceSampleGene.getSampleId() != Integer.parseInt(entry.getKey().toString())){
                        Map<String, Object> compareParamsMap = new HashMap<>();
                        compareParamsMap.put("minSameCount", minSameCount);
                        compareParamsMap.put("maxDiffCount", maxDiffCount);
                        Map<String, Object> matchResultMap = GeneCompareHelper.sameCompare(sourceSampleGene.getGeneInfo(),entry.getValue().toString(), compareParamsMap);
                        if((Boolean) matchResultMap.get("matched")){
                            Map<String, Object> lrMap = calculateSingleMarkerLR((List<CodisGeneInfo>) matchResultMap.get("matchGeneInfo"), populationName);
                            matchResult.setMatchedMode(Constants.MATCH_TYPE_SAME);
                            matchResult.setSample1Id(sourceSampleGene.getSampleId());
                            matchResult.setSample2Id(Integer.parseInt(entry.getKey().toString()));
                            matchResult.setSameCount(matchResultMap.get("sameCount") == null ? 0 : (Integer) matchResultMap.get("sameCount"));
                            matchResult.setDiffCount(matchResultMap.get("diffCount") == null ? 0 : (Integer) matchResultMap.get("diffCount"));
                            matchResult.setTotalProbability(lrMap.get("totalProbability") == null ? "" : (String) lrMap.get("totalProbability"));
                            matchResult.setMatchResultString(lrMap.get("matchResultString") == null ? "" : (String) lrMap.get("matchResultString"));

                            LimsSampleInfo limsSampleInfo1 = limsSampleInfoService.selectById(matchResult.getSample1Id());
                            matchResult.setSample1No(limsSampleInfo1.getSampleNo());
                            LimsSampleInfo limsSampleInfo2 = limsSampleInfoService.selectById(matchResult.getSample2Id());
                            matchResult.setSample2No(limsSampleInfo2.getSampleNo());

                            matchResultList.add(matchResult);
                        }
                    }
                }
            }
            //从redis中取得亲缘关系进行一找二比对
            Map<Object, Object> relativeMap = redisCacheUtil.hgetAll("relative_dna_lib");
            if(relativeMap != null && relativeMap.size() > 0){
                for (Map.Entry entry : relativeMap.entrySet()) {
                    MatchResult matchResult = new MatchResult();
                    String jsonStr = String.valueOf(entry.getValue());
                    Map<String, String> jsonMap = analysisJson(jsonStr);
                    String sameGroupSample = jsonMap.get("sameGroupSample");
                    String targetSampleRole = jsonMap.get("targetSampleRole");
                    String sameGroupSampleRole = jsonMap.get("sameGroupSampleRole");
                    String sameGroupSampleGene = redisCacheUtil.hget("sample_gene", sameGroupSample);
                    String srcGroupSampleGene = redisCacheUtil.hget("sample_gene", entry.getKey().toString());

                    if(sourceSampleGene.getSampleId() != Integer.parseInt(entry.getKey().toString()) && sourceSampleGene.getSampleId() != Integer.parseInt(sameGroupSample)){
                        Map<String, Object> matchResultMap = new HashMap<>();

                        Map<String, Object> compareParamsMap = new HashMap<>();
                        compareParamsMap.put("minSameCount", minSameCount);
                        compareParamsMap.put("maxDiffCount", maxDiffCount);

                        if(targetSampleRole.equals("配偶")){
                            if(sameGroupSampleRole.equals("父/母")){
                                matchResultMap = GeneCompareHelper.relativeCompare(sourceSampleGene.getGeneInfo(), sameGroupSampleGene, srcGroupSampleGene, compareParamsMap);
                            }
                            if(sameGroupSampleRole.equals("孩子")){
                                matchResultMap = GeneCompareHelper.relativeCompare(sourceSampleGene.getGeneInfo(), srcGroupSampleGene, sameGroupSampleGene, compareParamsMap);
                            }
                            matchResult.setMatchedMode(Constants.MATCH_TYPE_RELATIVE_POZN);
                        }else if(targetSampleRole.equals("子女")){
                            matchResultMap = GeneCompareHelper.relativeCompare(srcGroupSampleGene, sameGroupSampleGene, sourceSampleGene.getGeneInfo(), compareParamsMap);
                            matchResult.setMatchedMode(Constants.MATCH_TYPE_RELATIVE_FM);
                        }
                        if((Boolean) matchResultMap.get("matched")){
                            Map<String, Object> lrMap = calculateSingleMarkerLR((List<CodisGeneInfo>) matchResultMap.get("matchGeneInfo"), populationName);
                            matchResult.setSample1Id(sourceSampleGene.getSampleId());
                            matchResult.setSample2Id(Integer.parseInt(entry.getKey().toString()));
                            matchResult.setSample3Id(Integer.parseInt(sameGroupSample.toString()));
                            matchResult.setSameCount(matchResultMap.get("sameCount") == null ? 0 : (Integer) matchResultMap.get("sameCount"));
                            matchResult.setDiffCount(matchResultMap.get("diffCount") == null ? 0 : (Integer) matchResultMap.get("diffCount"));
                            matchResult.setTotalProbability(lrMap.get("totalProbability") == null ? "" : (String) lrMap.get("totalProbability"));
                            matchResult.setMatchResultString(lrMap.get("matchResultString") == null ? "" : (String) lrMap.get("matchResultString"));

                            LimsSampleInfo limsSampleInfo1 = limsSampleInfoService.selectById(matchResult.getSample1Id());
                            matchResult.setSample1No(limsSampleInfo1.getSampleNo());
                            LimsSampleInfo limsSampleInfo2 = limsSampleInfoService.selectById(matchResult.getSample2Id());
                            matchResult.setSample2No(limsSampleInfo2.getSampleNo());
                            LimsSampleInfo limsSampleInfo3 = limsSampleInfoService.selectById(matchResult.getSample3Id());
                            matchResult.setSample3No(limsSampleInfo3.getSampleNo());

                            matchResultList.add(matchResult);
                        }
                    }
                }
            }

        } catch (Exception e) {
            return null;
        }
        return ListUtils.isNullOrEmptyList(matchResultList) ? null : matchResultList;
    }

    public List<MatchResult> relativeCompare(LimsSampleInfo sampleInfo) {
        String populationName = SystemUtil.getSystemConfig().getProperty("populationName");
        String minSameCount = SystemUtil.getSystemConfig().getProperty("minSameCount");
        String maxDiffCount = SystemUtil.getSystemConfig().getProperty("maxDiffCount");

        Map<Object, Object> sampleGeneMap = redisCacheUtil.hgetAll("sample_gene");

        LimsSampleGene sourceASampleGene = limsSampleGeneService.selectAuditedBySampleId(sampleInfo.getId());
        LimsSampleGene sourceBSampleGene = limsSampleGeneService.selectAuditedBySampleId(sampleInfo.getSameGroupSample());
        List<MatchResult> matchResultList = new ArrayList<>();

        try {

            if(sampleGeneMap != null && sampleGeneMap.size() > 0){
                for (Map.Entry entry : sampleGeneMap.entrySet()) {
                    MatchResult matchResult = new MatchResult();

                    if(sourceASampleGene.getSampleId() != Integer.parseInt(entry.getKey().toString()) && sourceBSampleGene.getSampleId() != Integer.parseInt(entry.getKey().toString())){
                        LimsSampleGene targetSampleGene = new LimsSampleGene();
                        targetSampleGene.setSampleId(Integer.parseInt(entry.getKey().toString()));
                        targetSampleGene.setGeneInfo(entry.getValue().toString());

                        Map<String, Object> compareParamsMap = new HashMap<>();
                        compareParamsMap.put("minSameCount", minSameCount);
                        compareParamsMap.put("maxDiffCount", maxDiffCount);

                        String targetSampleRole = sampleInfo.getTargetSampleRole();
                        String sameGroupSampleRole = sampleInfo.getSameGroupSampleRole();

                        Map<String, Object> matchResultMap = null;
                        if(targetSampleRole.equals("配偶")){
                            if(sameGroupSampleRole.equals("父/母")){
                                matchResultMap = GeneCompareHelper.relativeCompare(sourceBSampleGene.getGeneInfo(), targetSampleGene.getGeneInfo(), sourceASampleGene.getGeneInfo(), compareParamsMap);
                            }
                            if(sameGroupSampleRole.equals("孩子")){
                                matchResultMap = GeneCompareHelper.relativeCompare(sourceASampleGene.getGeneInfo(), targetSampleGene.getGeneInfo(), sourceBSampleGene.getGeneInfo(), compareParamsMap);
                            }
                            matchResult.setMatchedMode(Constants.MATCH_TYPE_RELATIVE_PO);
                        }else if(targetSampleRole.equals("子女")){
                            matchResultMap = GeneCompareHelper.relativeCompare(sourceASampleGene.getGeneInfo(), sourceBSampleGene.getGeneInfo(), targetSampleGene.getGeneInfo(), compareParamsMap);
                            matchResult.setMatchedMode(Constants.MATCH_TYPE_RELATIVE_ZN);
                        }

                        if((Boolean) matchResultMap.get("matched")){
                            Map<String, Object> lrMap = calculateSingleMarkerLR((List<CodisGeneInfo>) matchResultMap.get("matchGeneInfo"), populationName);
                            matchResult.setSample1Id(sourceASampleGene.getSampleId());
                            matchResult.setSample2Id(targetSampleGene.getSampleId());
                            matchResult.setSample3Id(sourceBSampleGene.getSampleId());
                            matchResult.setSameCount(matchResultMap.get("sameCount") == null ? 0 : (Integer) matchResultMap.get("sameCount"));
                            matchResult.setDiffCount(matchResultMap.get("diffCount") == null ? 0 : (Integer) matchResultMap.get("diffCount"));
                            matchResult.setTotalProbability(lrMap.get("totalProbability") == null ? "" : (String) lrMap.get("totalProbability"));
                            matchResult.setMatchResultString(lrMap.get("matchResultString") == null ? "" : (String) lrMap.get("matchResultString"));

                            LimsSampleInfo limsSampleInfo1 = limsSampleInfoService.selectById(matchResult.getSample1Id());
                            matchResult.setSample1No(limsSampleInfo1.getSampleNo());
                            LimsSampleInfo limsSampleInfo2 = limsSampleInfoService.selectById(matchResult.getSample2Id());
                            matchResult.setSample2No(limsSampleInfo2.getSampleNo());
                            LimsSampleInfo limsSampleInfo3 = limsSampleInfoService.selectById(matchResult.getSample3Id());
                            matchResult.setSample3No(limsSampleInfo3.getSampleNo());

                            matchResultList.add(matchResult);
                        }
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        return ListUtils.isNullOrEmptyList(matchResultList) ? null : matchResultList;
    }

    public Map<String, Object> calculateSingleMarkerLR(List<CodisGeneInfo> matchGeneInfo, String raceName){
        Map<String, Object> result = new HashMap<>();

        Double lr = 1.0d;
        double probability = 1;
        List<Map<String, Object>> matchResultList = new ArrayList<>();

        if(ListUtils.isNullOrEmptyList(matchGeneInfo) ) {
            return null;
        }

        String geneVal1 = "";
        String geneVal2 = "";

        List<AlleleFrequency> alleleFrequencyList;
        AlleleFrequency alleleFrequency;
        for(CodisGeneInfo gene : matchGeneInfo){
            Map<String, Object> map = new HashMap<>();

            String geneName = gene.getGeneName().toUpperCase();
            if (geneName.equals("THO1") || geneName.equals("TH01")) {
                geneName = "TH01";
            }
            alleleFrequency = new AlleleFrequency();
            geneVal1 = gene.getGeneVal1().trim();
            geneVal2 = gene.getGeneVal2().trim();

            alleleFrequency.setRaceName(raceName);
            alleleFrequency.setMarkerName(geneName);
            alleleFrequency.setAlleleName(geneVal1);
            alleleFrequencyList = alleleFrequencyService.selectAlleleFrequencyRepeatList(alleleFrequency);
            if (ListUtils.isNotNullAndEmptyList(alleleFrequencyList)){
                alleleFrequency = alleleFrequencyList.get(0);
                Double p = Double.parseDouble(String.valueOf(alleleFrequency.getAlleleFrequency()));

                if (p.isNaN()) {
                    lr = Double.NaN;
                } else if (geneVal1.toUpperCase().compareTo(geneVal2.toUpperCase()) == 0) {
                    lr = 1 / (p * p);
                } else {
                    alleleFrequency.setAlleleName(geneVal2);
                    alleleFrequencyList = alleleFrequencyService.selectAlleleFrequencyRepeatList(alleleFrequency);
                    if (ListUtils.isNotNullAndEmptyList(alleleFrequencyList)){
                        alleleFrequency = alleleFrequencyList.get(0);
                        Double q = Double.parseDouble(String.valueOf(alleleFrequency.getAlleleFrequency()));
                        if (q.isNaN()) {
                            lr = Double.NaN;
                        }else {
                            lr = 1 / (2 * p * q);
                        }
                    }
                }
                if(!lr.isNaN()) {
                    map.put("geneName",geneName);
                    map.put("geneLr",lr);
                    matchResultList.add(map);
                    probability *= lr;
                }
            }

        }

        String totalProbability = DataFormat.formatDecimal(probability, 3, 1, true);
        result.put("totalProbability",totalProbability);

        ObjectMapper jsonObjectMapper = new ObjectMapper();
        try {
            String matchResultString = jsonObjectMapper.writeValueAsString(matchResultList);
            result.put("matchResultString",matchResultString);
        } catch (JsonProcessingException e) {
            logger.error("转换Json信息出错！", e);
        }
        return result;
    }

    public String constructJson(String name1, String val1, String name2, String val2, String name3, String val3){
        String result = "";
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = new HashMap<>();
        map.put(name1, val1);
        map.put(name2, val2);
        map.put(name3, val3);
        try {
            result = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error("构造Json出错！",e.getMessage());
        }
        return result;
    }

    public Map<String, String> analysisJson(String jsonStr){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = null;

        try {
            map = objectMapper.readValue(jsonStr.toString(), HashMap.class);
        } catch (IOException e) {
            logger.error("解析Json出错！",e.getMessage());
        }
        return map;
    }

    public String sortSampleId(Integer sample1Id, Integer sample2Id, Integer sample3Id){
        String result = "";
        List list = new ArrayList<>();
        if(sample1Id != null){
            list.add(sample1Id);
        }
        if(sample2Id != null){
            list.add(sample2Id);
        }
        if(sample3Id != null){
            list.add(sample3Id);
        }
        Collections.sort(list);//使用Collections的sort方法

        for(int i=0;i<list.size();i++){
            result += list.get(i);
        }
        return result;
    }
}
