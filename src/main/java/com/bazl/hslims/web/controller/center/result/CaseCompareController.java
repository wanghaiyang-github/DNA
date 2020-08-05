package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.config.MatchConfig;
import com.bazl.hslims.manager.model.domain.TotalRelativeMatchResult;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.relativeMatch.RelationCaseMatch;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DataFormat;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.CaseCompareResultGroup;
import com.bazl.hslims.web.datamodel.CaseCompareResultInfoModel;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/4.
 */
@Controller
@RequestMapping("/center/3")
public class CaseCompareController extends BaseController {

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

    @RequestMapping("/caseCompare.html")
    public ModelAndView caseCompare(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo,Integer caseId) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);


        if (StringUtils.isBlank(query.getEntity().getCaseName())){
            query.getEntity().setCaseName(null);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseStatus())){
            query.getEntity().setCaseStatus(null);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseType())){
            query.getEntity().setCaseType(null);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseProperty())){
            query.getEntity().setCaseProperty(null);
        }

        if (query.getAcceptDateStart() == null){
            query.setAcceptDateStart(null);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (query.getAcceptDateEnd() == null){
            query.setAcceptDateEnd(null);
        }else{
            oldTime = sdf.format(query.getAcceptDateEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            query.setAcceptDateEnd(newTime);
        }

        if (StringUtils.isBlank(query.getEntity().getCaseNo())) {
            query.getEntity().setCaseNo(null);
        }else{
            query.getEntity().setCaseNo(query.getEntity().getCaseNo());
        }
        if (StringUtils.isBlank(query.getEntity().getCaseXkNo())) {
            query.getEntity().setCaseXkNo(null);
        }else{
            query.getEntity().setCaseXkNo(query.getEntity().getCaseXkNo().trim());
        }
        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(caseId);

        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED,Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        if(limsCaseInfo!=null){
            query.getEntity().setCaseNo(limsCaseInfo.getCaseNo());
        }
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("query",query);
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("limsCaseInfo",limsCaseInfo);
        modelAndView.setViewName("center/result/caseCompare");
        return modelAndView;
    }


    @RequestMapping("/compare.html")
    public ModelAndView compare(HttpServletRequest request, Integer caseId, int matchLimit ) {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseId);

        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectAcceptStatusListByCaseId(caseId);
        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectAuditedListByCaseId(caseId);
        List<LimsSampleGene> mixSampleGeneList = limsSampleGeneService.selectMixListByCaseId(caseId);
        List<CaseInnerMatched> caseInnerMatchedList = caseInnerMatchedService.selectListByCaseId(caseId);

        LimsSampleGene lsg = new LimsSampleGene();
        List<LimsSampleGene> newSampleGeneList = new ArrayList<>();
        List<LimsSampleGene> newMixSampleGeneList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(consignmentList)) {
            LimsConsignment consignment = consignmentList.get(0);

            if (ListUtils.isNotNullAndEmptyList(sampleGeneList))
                newSampleGeneList.addAll(sampleGeneList);

            if (ListUtils.isNotNullAndEmptyList(mixSampleGeneList))
                newMixSampleGeneList.addAll(mixSampleGeneList);

            if (StringUtils.isNotBlank(consignment.getMatchCaseNo())) {
                lsg.setSampleNo(consignment.getMatchCaseNo().trim());
                lsg.setAuditStatus(Constants.FLAG_TRUE);

                List<LimsSampleGene> matchSampleGeneList = limsSampleGeneService.selectGeneInfoList(lsg);

                if (ListUtils.isNotNullAndEmptyList(matchSampleGeneList)) {
                    for (LimsSampleGene matchSampleGene : matchSampleGeneList) {
                        if (matchSampleGene.getGeneType().equals(Constants.GENE_TYPE_STR)) {
                            newSampleGeneList.add(matchSampleGene);
                        }else if (matchSampleGene.getGeneType().equals(Constants.GENE_TYPE_MIX)) {
                            newMixSampleGeneList.add(matchSampleGene);
                        }
                    }
                }
            }

        }

        for(LimsSampleGene gene : sampleGeneList){
            for(LimsSampleInfo sampleInfo : sampleInfoList){
                if(sampleInfo.getId().equals(gene.getSampleId())){
                    gene.setSampleName(sampleInfo.getSampleName());
                    break;
                }
            }
        }

        if(matchLimit == 0){
            matchLimit = Constants.SAME_MATCH_LIMIT_DEFAULT;
        }
        List<CaseCompareResultGroup> matchedGroupList = doCompare(newSampleGeneList, matchLimit);
        List<CaseCompareResultGroup> newMatchedGroupList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(matchedGroupList)) {
            for (CaseCompareResultGroup ccrg : matchedGroupList) {
                for (LimsSampleGene sampleGene : ccrg.getMatchedSamples()) {
                    if (sampleGene.getSampleNo().contains(caseInfo.getCaseNo())) {
                        newMatchedGroupList.add(ccrg);
                        break;
                    }
                }
            }
        }
        List<CaseCompareResultGroup> mixMatchedGroupList = mixDoCompare(newMixSampleGeneList, newSampleGeneList, matchLimit);
        List<CaseCompareResultGroup> newMixMatchedGroupList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(mixMatchedGroupList)) {
            for (CaseCompareResultGroup mixCcrg : mixMatchedGroupList) {
                for (LimsSampleGene mixSampleGene : mixCcrg.getMixMatchedSamples()) {
                    if (mixSampleGene.getSampleNo().contains(caseInfo.getCaseNo())) {
                        newMixMatchedGroupList.add(mixCcrg);
                        break;
                    }
                }
            }
        }
        Set<LimsSampleGene> notMatchedGeneList = new LinkedHashSet<>();
        Set<LimsSampleInfo> noResultSampleList = new LinkedHashSet<>();

        boolean hasMatched;
        for(LimsSampleGene sampleGene : sampleGeneList){
            hasMatched = false;
            for(CaseCompareResultGroup group : newMatchedGroupList){
                for(LimsSampleGene matched : group.getMatchedSamples()){
                    if(matched.getId().equals(sampleGene.getId())){
                        hasMatched = true;
                        break;
                    }
                }
                if(hasMatched) { break; }
            }

            if(!hasMatched) {
                notMatchedGeneList.add(sampleGene);
            }
        }

        boolean mixHasMatched;
        for(LimsSampleGene sampleGene : mixSampleGeneList) {
            mixHasMatched = false;

            if (ListUtils.isNotNullAndEmptyList(newMixMatchedGroupList)) {
                for(CaseCompareResultGroup group : newMixMatchedGroupList){
                    for(LimsSampleGene matched : group.getMixMatchedSamples()){
                        if(matched.getId().equals(sampleGene.getId())){
                            mixHasMatched = true;
                            break;
                        }
                    }

                    if(mixHasMatched) break;
                }
            }

            if(!mixHasMatched)
                notMatchedGeneList.add(sampleGene);
        }

        boolean hasResult;
        for(LimsSampleInfo sample : sampleInfoList){
            hasResult = false;
            for(LimsSampleGene gene : sampleGeneList){
                if(gene.getSampleId().equals(sample.getId())){
                    hasResult = true;
                    break;
                }
            }

            for(LimsSampleGene gene : mixSampleGeneList){
                if(gene.getSampleId().equals(sample.getId())){
                    hasResult = true;
                    break;
                }
            }

            if(!hasResult)
                noResultSampleList.add(sample);

        }

        RaceInfo raceInfo = new RaceInfo();
        List<RaceInfo> raceInfoList = raceInfoService.selectRaceInfoList(raceInfo);

        if (ListUtils.isNotNullAndEmptyList(caseInnerMatchedList) && ListUtils.isNotNullAndEmptyList(newMatchedGroupList)){
            for (CaseCompareResultGroup ccr:newMatchedGroupList){
                for(LimsSampleGene matched : ccr.getMatchedSamples()){
                    String sampleId = matched.getSampleId().toString();
                    for (int i = 0;i < caseInnerMatchedList.size(); i++){
                        if (ListUtils.isNotNullAndEmptyList(caseInnerMatchedList)){
                            String samId = caseInnerMatchedList.get(i).getSample2Id() == null?"":caseInnerMatchedList.get(i).getSample2Id().toString();
                            String totalProbability = caseInnerMatchedList.get(i).getTotalProbability() == null?"":caseInnerMatchedList.get(i).getTotalProbability().toString();
                            if (sampleId.equals(samId))
                                matched.setTotalProbability(totalProbability);
                        }
                    }
                }
            }
        }

        List<CaseCompareResultInfo> compareResultInfoList = caseCompareResultInfoService.selectListByCaseId(caseId);
        List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectAuditedListByCaseId(caseId);

        for (CaseCompareResultInfo compareResultInfo : compareResultInfoList) {
            for (LimsSampleInfo limsSampleInfo : sampleInfoList) {
                if (StringUtils.isNotBlank(compareResultInfo.getFatherSampleNo())) {
                    if (compareResultInfo.getFatherSampleNo().equals(limsSampleInfo.getSampleNo()))
                        compareResultInfo.setFatherSampleName(limsSampleInfo.getSampleName());
                }

                if (StringUtils.isNotBlank(compareResultInfo.getMotherSampleNo())) {
                    if (compareResultInfo.getMotherSampleNo().equals(limsSampleInfo.getSampleNo()))
                        compareResultInfo.setMotherSampleName(limsSampleInfo.getSampleName());
                }

                if (StringUtils.isNotBlank(compareResultInfo.getChildSampleNo())) {
                    if (compareResultInfo.getChildSampleNo().equals(limsSampleInfo.getSampleNo()))
                        compareResultInfo.setChildSampleName(limsSampleInfo.getSampleName());
                }
            }

            for (LimsSampleGene limsSampleGene : limsSampleGeneList) {
                if (StringUtils.isNotBlank(compareResultInfo.getFatherSampleNo())) {
                    if (compareResultInfo.getFatherSampleNo().equals(limsSampleGene.getSampleNo()))
                        compareResultInfo.setReagentNameF(limsSampleGene.getReagentName());
                }

                if (StringUtils.isNotBlank(compareResultInfo.getMotherSampleNo())) {
                    if (compareResultInfo.getMotherSampleNo().equals(limsSampleGene.getSampleNo()))
                        compareResultInfo.setReagentNameM(limsSampleGene.getReagentName());
                }

                if (StringUtils.isNotBlank(compareResultInfo.getChildSampleNo())) {
                    if (compareResultInfo.getChildSampleNo().equals(limsSampleGene.getSampleNo()))
                        compareResultInfo.setReagentNameC(limsSampleGene.getReagentName());
                }
            }

            Integer matchedMode = compareResultInfo.getMatchedMode();

            if (matchedMode == 1) {
                compareResultInfo.setBacgroundF("green");
                compareResultInfo.setBacgroundM("green");
            }else if (matchedMode == 2) {
                compareResultInfo.setBacgroundF("green");
                compareResultInfo.setBacgroundM("red");
            }else if (matchedMode == 3) {
                compareResultInfo.setBacgroundF("red");
                compareResultInfo.setBacgroundM("green");
            }else if (matchedMode == 4) {
                compareResultInfo.setBacgroundF("red");
                compareResultInfo.setBacgroundM("red");
            }else {
                compareResultInfo.setBacgroundF("red");
                compareResultInfo.setBacgroundM("red");
            }
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("matchLimit", matchLimit);
        modelAndView.addObject("matchRelationLimit", Constants.SAME_RELATION_MATCH_LIMIT_DEFAULT);
        modelAndView.addObject("halfDiffCount", Constants.SAME_RELATION_HALF_COUNT);
        modelAndView.addObject("matchedGroupList", newMatchedGroupList);
        modelAndView.addObject("mixMatchedGroupList", newMixMatchedGroupList);
        modelAndView.addObject("noResultSampleList", noResultSampleList);
        modelAndView.addObject("notMatchedGeneList", notMatchedGeneList);
        modelAndView.addObject("compareResultInfoList", compareResultInfoList);
        modelAndView.addObject("sampleGeneList", sampleGeneList);
        modelAndView.addObject("mixSampleGeneList", mixSampleGeneList);
        modelAndView.addObject("raceInfoList", raceInfoList);
        modelAndView.setViewName("center/result/caseCompareDetail");
        return modelAndView;
    }

    @RequestMapping(value = "/realtionCompareTo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public List<Map<String, Object>> realtionCompareTo(HttpServletRequest request,@RequestBody RelationCompare relationCompare) {
        LimsSampleGene sampleGene;
        List<Map<String, Object>> result = new ArrayList<>();

        LimsSampleGene FsampleGene = new LimsSampleGene();
        if (StringUtils.isNotBlank(relationCompare.getFatherSampleNo())) {
            sampleGene = new LimsSampleGene();
            sampleGene.setSampleNo(relationCompare.getFatherSampleNo());
            FsampleGene = limsSampleGeneService.selectGeneInfoList(sampleGene).get(0);
        }

        LimsSampleGene MsampleGene = new LimsSampleGene();
        if (StringUtils.isNotBlank(relationCompare.getMotherSampleNo())) {
            sampleGene = new LimsSampleGene();
            sampleGene.setSampleNo(relationCompare.getMotherSampleNo());
            MsampleGene = limsSampleGeneService.selectGeneInfoList(sampleGene).get(0);
        }

        LimsSampleGene ZsampleGene = new LimsSampleGene();
        String[] zBarcodeArr = relationCompare.getChildSampleNoStr().substring(0, relationCompare.getChildSampleNoStr().length() - 1).split(",");
        for (int i = 0; i < zBarcodeArr.length; i ++) {
            sampleGene = new LimsSampleGene();
            sampleGene.setSampleNo(zBarcodeArr[i]);
            ZsampleGene = limsSampleGeneService.selectGeneInfoList(sampleGene).get(0);

            result.add(relativeMatch(FsampleGene, MsampleGene, ZsampleGene, relationCompare));
        }

        return result;
    }

    private Map<String, Object> relativeMatch(LimsSampleGene FsampleGene, LimsSampleGene MsampleGene, LimsSampleGene ZsampleGene,
                                              RelationCompare relationCompare) {
        Map<String, Object> paramMap = new HashMap<>();

        if (FsampleGene != null) {
            paramMap.put("panelNameF", StringUtils.isBlank(FsampleGene.getReagentName()) ? "" : FsampleGene.getReagentName().toString());
            paramMap.put("sampleNameF", StringUtils.isBlank(FsampleGene.getSampleName()) ? "" : FsampleGene.getSampleName().toString());
            paramMap.put("sampleNoF", StringUtils.isBlank(FsampleGene.getSampleNo()) ? "" : FsampleGene.getSampleNo().toString());
            paramMap.put("sampleIdF", FsampleGene.getSampleId());
        }

        if (MsampleGene != null) {
            paramMap.put("panelNameM", StringUtils.isBlank(MsampleGene.getReagentName()) ? "" : MsampleGene.getReagentName().toString());
            paramMap.put("sampleNameM", StringUtils.isBlank(MsampleGene.getSampleName()) ? "" : MsampleGene.getSampleName().toString());
            paramMap.put("sampleNoM", StringUtils.isBlank(MsampleGene.getSampleNo()) ? "" : MsampleGene.getSampleNo().toString());
            paramMap.put("sampleIfdM", MsampleGene.getSampleId());
        }

        if (ZsampleGene != null) {
            paramMap.put("panelNameC", StringUtils.isBlank(ZsampleGene.getReagentName()) ? "" : ZsampleGene.getReagentName().toString());
            paramMap.put("sampleNameC", StringUtils.isBlank(ZsampleGene.getSampleName()) ? "" : ZsampleGene.getSampleName().toString());
            paramMap.put("sampleNoC", StringUtils.isBlank(ZsampleGene.getSampleNo()) ? "" : ZsampleGene.getSampleNo().toString());
            paramMap.put("sampleIdC", ZsampleGene.getSampleId());
        }

        MatchConfig matchConfig = new MatchConfig();
        RelationCaseMatch relationCaseMatch = new RelationCaseMatch();
        TotalRelativeMatchResult matchResult = relationCaseMatch.relationCaseMatchSampleList(FsampleGene, MsampleGene, ZsampleGene,
                relationCompare, matchConfig.getTotalPiLength());


        paramMap.put("matchMode", matchResult.getMatchMode());
//        int TotalPossibility = matchResult.getResult().getTotalPossibility();
//        double d=(double) i;
//        DataFormat.formatDecimal(matchResult.getResult().getTotalPossibility() == null?0.0:matchResult.getResult().getTotalPossibility(), 3, 1, true)
        if(matchResult.getMatchMode() == TotalRelativeMatchResult.MATCHMODE_UNMATCHED_FM) {
            paramMap.put("totalPi", matchResult.getResult().getTotalPossibility());
            paramMap.put("matchCount", matchResult.getResult().getMatchCount());
            paramMap.put("matchModeName", "无");
        }else if (matchResult.getMatchMode() ==  TotalRelativeMatchResult.MATCHMODE_MATCHED_FM){
            paramMap.put("totalPi", matchResult.getResult().getTotalPossibility());
            paramMap.put("matchCount", matchResult.getResult().getMatchCount());
            paramMap.put("matchModeName", "父/母亲");
        } else if (matchResult.getMatchMode() == TotalRelativeMatchResult.MATCHMODE_MATCHED_F) {
            paramMap.put("totalPi", matchResult.getFResult().getTotalPossibility());;
            paramMap.put("matchCount", matchResult.getFResult().getMatchCount());
            paramMap.put("matchModeName", "父");
        } else if (matchResult.getMatchMode() == TotalRelativeMatchResult.MATCHMODE_MATCHED_M) {
            paramMap.put("totalPi", matchResult.getMResult().getTotalPossibility());;
            paramMap.put("matchCount", matchResult.getMResult().getMatchCount());
            paramMap.put("matchModeName", "母亲");
        }else if (matchResult.getMatchMode() == TotalRelativeMatchResult.MATCHMODE_MATCHED_FM_UNMATCHEDALL ){
            paramMap.put("totalPi", matchResult.getResult().getTotalPossibility());;
            paramMap.put("matchCount", matchResult.getResult().getMatchCount());
            paramMap.put("matchModeName", "无");
        }else if(matchResult.getMatchMode() == TotalRelativeMatchResult.MATCHMODE_CHILD_MIX) {
            paramMap.put("totalPi", "--");
            paramMap.put("matchCount", "0");
            paramMap.put("matchModeName", "(孩子)混合样本");
        }else if(matchResult.getMatchMode() == TotalRelativeMatchResult.MATCHMODE_FATHER_MIX) {
            paramMap.put("totalPi", "--");
            paramMap.put("matchCount", "0");
            paramMap.put("matchModeName",  "(父亲)混合样本");
        }else if(matchResult.getMatchMode() == TotalRelativeMatchResult.MATCHMODE_MOTHER_MIX) {
            paramMap.put("totalPi", "--");
            paramMap.put("matchCount", "0");
            paramMap.put("matchModeName", "(母亲)混合样本");
        }else {
            paramMap.put("totalPi", " -- ");
            paramMap.put("matchCount", "0");
            paramMap.put("matchModeName", "无");
        }

        return paramMap;
    }

    @RequestMapping(value="/saveComparisonResult.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> saveComparisonResult(HttpServletRequest request, @RequestBody CaseCompareResultInfoModel caseCompareResultInfoModel){
        Map<String, Object> result = new HashMap<String, Object>();

        LimsCaseInfo caseInfo = caseCompareResultInfoModel.getCaseInfo();
        boolean sameFlag = saveSameCompareResult(caseInfo);

        List<CaseCompareResultInfo> caseCompareResultInfoList = caseCompareResultInfoModel.getCaseCompareResultInfoList();
        boolean relationFlag = saveRelationCompareResult(caseInfo, caseCompareResultInfoList);

        if (sameFlag || relationFlag) {
            result.put("success", true);
        }else {
            result.put("success", false);
        }

        return result;

    }

    public boolean saveSameCompareResult(LimsCaseInfo caseInfo) {

        boolean flag = true;

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(caseInfo.getId());
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseInfo.getId());
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByCaseId(caseInfo.getId());
        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectAuditedListByCaseId(caseInfo.getId());

        LimsSampleGene lsg = new LimsSampleGene();
        List<LimsSampleGene> matchSampleGeneList = null;
        if (ListUtils.isNotNullAndEmptyList(consignmentList)) {
            LimsConsignment consignment = consignmentList.get(0);

            if (StringUtils.isNotBlank(consignment.getMatchCaseNo())) {
                lsg.setSampleNo(consignment.getMatchCaseNo().trim());
                lsg.setAuditStatus(Constants.FLAG_TRUE);

                matchSampleGeneList = limsSampleGeneService.selectGeneInfoList(lsg);

                if (ListUtils.isNotNullAndEmptyList(matchSampleGeneList)) {
                    for (LimsSampleGene matchSampleGene : matchSampleGeneList) {
                        sampleGeneList.add(matchSampleGene);
                    }
                }
            }

        }

        for(LimsSampleGene gene : sampleGeneList){
            for(LimsSampleInfo sampleInfo : sampleInfoList){
                if(sampleInfo.getId().equals(gene.getSampleId())){
                    gene.setSampleName(sampleInfo.getSampleName());
                    gene.setRefPersonId(sampleInfo.getRefPersonId());
                    break;
                }
            }
        }

        int matchLimit = caseInfo.getMatchLimit();
        if(matchLimit == 0){
            matchLimit = Constants.SAME_MATCH_LIMIT_DEFAULT;
        }
        List<CaseCompareResultGroup> matchedGroupList = doCompare(sampleGeneList, matchLimit);
        List<CaseCompareResultGroup> newMatchedGroupList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(matchedGroupList)) {
            for (CaseCompareResultGroup ccrg : matchedGroupList) {
                for (LimsSampleGene sampleGene : ccrg.getMatchedSamples()) {
                    if (sampleGene.getSampleNo().trim().contains(limsCaseInfo.getCaseNo().trim())) {
                        newMatchedGroupList.add(ccrg);
                        break;
                    }
                }
            }
        }
        try {
            if (ListUtils.isNotNullAndEmptyList(newMatchedGroupList)){
                for (int m = 0;m < newMatchedGroupList.size();m++){
                    CaseInnerMatched caseInnerMatched = new CaseInnerMatched();

                    caseInnerMatched.setCaseId(caseInfo.getId());

                    CaseCompareResultGroup caseCompareResultGroup = newMatchedGroupList.get(m);

                    sampleGeneList = caseCompareResultGroup.getMatchedSamples();

                    int count = sampleGeneList.size();
                    LimsSampleGene lsg1 = null;
                    LimsSampleGene lsg2 = null;
                    Map map = null;

                    for (int i = 0;i < count;i++){

                        lsg1 = sampleGeneList.get(i);
                        if (lsg1.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE))
                            continue;

                        for (int j = 0;j < count;j++){
                            map = new HashMap<>();
                            lsg2 = sampleGeneList.get(j);

                            if (lsg2.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)){
                                map = compareCount(lsg1, lsg2);
                                int sameCount = 0;
                                sameCount = (Integer)map.get("matchCount");
                                if (sameCount > 0){
                                    caseInnerMatched.setCreatePerson(lsg1.getCreatePerson());
                                    caseInnerMatched.setSameCount(sameCount);
                                    caseInnerMatched.setDiffCount((Integer)map.get("diffCount"));

                                    Double fProb = calculateSingleMarkerLR(lsg1, lsg2,caseInfo.getRaceName());
                                    String str = DataFormat.formatDecimal(fProb == null?0.0:fProb, 3, 1, true);
                                    caseInnerMatched.setTotalProbability(str);

                                    caseInnerMatched.setGroupId(lsg1.getSampleId());
                                    caseInnerMatched.setSample1Id(lsg1.getSampleId());
                                    caseInnerMatched.setSample2Id(lsg2.getSampleId());
                                    caseInnerMatched.setSample3Id(0);
                                    caseInnerMatched.setMatchedMode(1);
                                    caseInnerMatchedService.delete(caseInnerMatched);
                                    caseInnerMatchedService.insert(caseInnerMatched);
                                }
                            }
                        }
                    }
                }

            }else {
                flag = false;
            }

        }catch (Exception e){
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    public boolean saveRelationCompareResult(LimsCaseInfo caseInfo, List<CaseCompareResultInfo> caseCompareResultInfoList) {
        boolean flag = true;

        try {
            Integer caseId = caseInfo.getId();
            caseCompareResultInfoService.deleteByCaseId(caseId);

            for (CaseCompareResultInfo compareResultInfo : caseCompareResultInfoList) {
                if (compareResultInfo.getCaseId() != null && compareResultInfo.getCaseId() != 0) {

                    if (StringUtils.isBlank(compareResultInfo.getFatherSampleNo()))
                        compareResultInfo.setFatherSampleNo(null);

                    if (StringUtils.isBlank(compareResultInfo.getMotherSampleNo()))
                        compareResultInfo.setMotherSampleNo(null);

                    if (StringUtils.isBlank(compareResultInfo.getChildSampleNo()))
                        compareResultInfo.setChildSampleNo(null);

                    compareResultInfo.setCreatePerson(LimsSecurityUtils.getLoginName());

                    caseCompareResultInfoService.insert(compareResultInfo);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    public Map compareCount(LimsSampleGene lsg1,LimsSampleGene lsg2){
        Map mapCount = new HashMap();

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneStr1 = lsg1.getGeneInfo();
        String geneStr2 = lsg2.getGeneInfo();

        List<CodisGeneInfo> geneDetailList1 = null;
        List<CodisGeneInfo> geneDetailList2 = null;

        try {
            geneDetailList1 = jsonObjectMapper.readValue(geneStr1, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
            geneDetailList2 = jsonObjectMapper.readValue(geneStr2, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch(Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        if(ListUtils.isNullOrEmptyList(geneDetailList1) || ListUtils.isNullOrEmptyList(geneDetailList2)) {
            return null;
        }

        int matchCount = 0;
        int diffCount = 0;
        String gene1Val1 = "";
        String gene1Val2 = "";

        String gene2Val1 = "";
        String gene2Val2 = "";
        for(CodisGeneInfo gene1 : geneDetailList1){
            gene1Val1 = gene1.getGeneVal1();
            gene1Val2 = gene1.getGeneVal2();

            for(CodisGeneInfo gene2 : geneDetailList2){
                if(gene2.getGeneName().toUpperCase().equals(gene1.getGeneName().toUpperCase())) {
                    gene2Val1 = gene2.getGeneVal1();
                    gene2Val2 = gene2.getGeneVal2();

                    if (StringUtils.isNotBlank(gene2Val1) || StringUtils.isNotBlank(gene2Val2)) {
                        if((gene1Val1.toUpperCase().equals(gene2Val1.toUpperCase()) && gene1Val2.toUpperCase().equals(gene2Val2.toUpperCase())
                                || (gene1Val1.toUpperCase().equals(gene2Val2.toUpperCase())) && gene1Val2.toUpperCase().equals(gene2Val1.toUpperCase()))){
                            matchCount++;
                            break;
                        }else {
                            diffCount ++;
                            break;
                        }
                    }else {
                        diffCount ++;
                        break;
                    }
                }
            }
        }

        mapCount.put("matchCount",matchCount);
        mapCount.put("diffCount",diffCount);

        return mapCount;
    }

    public Double calculateSingleMarkerLR (LimsSampleGene lsg1, LimsSampleGene lsg2, String raceName){
        Double lr = 1.0d;
        Double theta = 1.0d;
        double probability = 1;

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneStr1 = lsg1.getGeneInfo();
        String geneStr2 = lsg2.getGeneInfo();

        List<CodisGeneInfo> geneDetailList1 = null;
        List<CodisGeneInfo> geneDetailList2 = null;

        try {
            geneDetailList1 = jsonObjectMapper.readValue(geneStr1, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
            geneDetailList2 = jsonObjectMapper.readValue(geneStr2, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch(Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        if(ListUtils.isNullOrEmptyList(geneDetailList1) || ListUtils.isNullOrEmptyList(geneDetailList2)) {
            return null;
        }

        String gene1Val1 = "";
        String gene1Val2 = "";

        String gene2Val1 = "";
        String gene2Val2 = "";
        List<AlleleFrequency> alleleFrequencyList;
        AlleleFrequency alleleFrequency;
        for(CodisGeneInfo gene1 : geneDetailList1){
            gene1Val1 = gene1.getGeneVal1().trim();
            gene1Val2 = gene1.getGeneVal2().trim();

            String geneName1 = gene1.getGeneName().toUpperCase();
            if (geneName1.equals("THO1") || geneName1.equals("TH01"))
                geneName1 = "TH01";
            for(CodisGeneInfo gene2 : geneDetailList2){
                String geneName2 = gene2.getGeneName().toUpperCase();
                if (geneName2.equals("THO1") || geneName2.equals("TH01"))
                    geneName2 = "TH01";
                if(geneName2.equals(geneName1)) {
                    alleleFrequency = new AlleleFrequency();
                    alleleFrequencyList = new ArrayList<>();
                    gene2Val1 = gene2.getGeneVal1().trim();
                    gene2Val2 = gene2.getGeneVal2().trim();

                    if((gene1Val1.toUpperCase().equals(gene2Val1.toUpperCase()) && gene1Val2.toUpperCase().equals(gene2Val2.toUpperCase())
                            || (gene1Val1.toUpperCase().equals(gene2Val2.toUpperCase())) && gene1Val2.toUpperCase().equals(gene2Val1.toUpperCase()))){
                        alleleFrequency.setRaceName(raceName);
                        alleleFrequency.setMarkerName(geneName1);
                        alleleFrequency.setAlleleName(gene1Val1);
                        alleleFrequencyList = alleleFrequencyService.selectAlleleFrequencyRepeatList(alleleFrequency);
                        if (ListUtils.isNotNullAndEmptyList(alleleFrequencyList)){
                            alleleFrequency = alleleFrequencyList.get(0);
                            Double p = Double.parseDouble(String.valueOf(alleleFrequency.getAlleleFrequency()));

                            if (p.isNaN())
                                lr = Double.NaN;
                            else if (p == 1.0d)
                                lr = 1.0d;
                            else if (gene1Val1.toUpperCase().compareTo(gene1Val2.toUpperCase()) == 0) {
                                lr = 1 / (p * p);
                            } else {
                                alleleFrequency.setAlleleName(gene1Val2);
                                alleleFrequencyList = alleleFrequencyService.selectAlleleFrequencyRepeatList(alleleFrequency);
                                if (ListUtils.isNotNullAndEmptyList(alleleFrequencyList)){
                                    alleleFrequency = alleleFrequencyList.get(0);
                                    Double q = Double.parseDouble(String.valueOf(alleleFrequency.getAlleleFrequency()));
                                    if (q.isNaN())
                                        lr = Double.NaN;
                                    else if(q == 1.0d)
                                        lr = 1.0d;
                                    else
                                        lr = 1 / (2 * p * q);
                                }
                            }

                            if(!lr.isNaN())
                                probability *= lr;
                        }
                    }else {
                        continue;
                    }
                }
            }
        }

        return probability;
    }

    private List<CaseCompareResultGroup> doCompare(List<LimsSampleGene> sampleGeneList, int matchLimit){
        List<CaseCompareResultGroup> groupList = new ArrayList<>();

        int cnt = sampleGeneList.size();
        LimsSampleGene s1 = null;
        LimsSampleGene s2 = null;
        HashSet<Integer> matchedIdxList = new LinkedHashSet<>();
        List<LimsSampleGene> matchedList = null;
        for(int i = 0; i < cnt; i++){
            if(matchedIdxList.contains(i))
                continue;

            matchedList = new ArrayList<>();
            s1 = sampleGeneList.get(i);
            for(int j = i+1; j < cnt; j++) {
                s2 = sampleGeneList.get(j);
                if(compare(s1, s2, matchLimit)){
                    matchedList.add(s2);
                    matchedIdxList.add(j);
                }
            }

            if(matchedList.size() > 0){
                CaseCompareResultGroup group = new CaseCompareResultGroup();
                matchedList.add(0, s1);
                matchedIdxList.add(i);

                group.setGroupId(i+1);
                group.setMatchedSamples(matchedList);
                groupList.add(group);
            }
        }

        return groupList;
    }

    private List<CaseCompareResultGroup> mixDoCompare(List<LimsSampleGene> mixSampleGeneList, List<LimsSampleGene> sampleGeneList, int matchLimit) {
        List<CaseCompareResultGroup> groupList = new ArrayList<>();

        LimsSampleGene sg1 = null;
        LimsSampleGene sg2 = null;

        HashSet<Integer> matchedIdxList = new LinkedHashSet<>();
        List<LimsSampleGene> matchedList = null;
        CaseCompareResultGroup group;
        Map<String, Object> map;
        if (ListUtils.isNotNullAndEmptyList(mixSampleGeneList) && ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            for(int i = 0; i < mixSampleGeneList.size(); i++){

                matchedList = new ArrayList<>();
                sg1 = mixSampleGeneList.get(i);

                for(int j = 0; j < sampleGeneList.size(); j++) {
                    sg2 = sampleGeneList.get(j);

                    if(sg2.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                        continue;
                    }

                    map = mixCompare(sg1, sg2, matchLimit);
                    if(map.get("match").equals(true)){
                        matchedList.add(sg2);
                        matchedIdxList.add(j);
                    }
                }

                if(matchedList.size() > 0){
                    matchedList.add(0, sg1);
                    matchedIdxList.add(i);

                    group = new CaseCompareResultGroup();
                    group.setGroupId(i+1);
                    group.setMixMatchedSamples(matchedList);
                    groupList.add(group);
                }
            }
        }

        return groupList;
    }

    private boolean compare(LimsSampleGene s1, LimsSampleGene s2, int matchLimit) {
        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneStr1 = s1.getGeneInfo();
        String geneStr2 = s2.getGeneInfo();

        List<CodisGeneInfo> geneDetailList1 = null;
        List<CodisGeneInfo> geneDetailList2 = null;

        try {
            geneDetailList1 = jsonObjectMapper.readValue(geneStr1, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
            geneDetailList2 = jsonObjectMapper.readValue(geneStr2, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch(Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        if(ListUtils.isNullOrEmptyList(geneDetailList1)
                || ListUtils.isNullOrEmptyList(geneDetailList2)) {
            return false;
        }

        int sameCount = 0;
        String gene1Val1 = "";
        String gene1Val2 = "";

        String gene2Val1 = "";
        String gene2Val2 = "";
        for(CodisGeneInfo gene1 : geneDetailList1){
            gene1Val1 = gene1.getGeneVal1();
            gene1Val2 = gene1.getGeneVal2();

            if (StringUtils.isNotBlank(gene1Val1) || StringUtils.isNotBlank(gene1Val2)) {
                for(CodisGeneInfo gene2 : geneDetailList2){
                    if(gene2.getGeneName().toUpperCase().equals(gene1.getGeneName().toUpperCase())) {
                        gene2Val1 = gene2.getGeneVal1();
                        gene2Val2 = gene2.getGeneVal2();

                        if (StringUtils.isNotBlank(gene2Val1) || StringUtils.isNotBlank(gene2Val2)) {
                            if((gene1Val1.toUpperCase().equals(gene2Val1.toUpperCase()) && gene1Val2.toUpperCase().equals(gene2Val2.toUpperCase())
                                    || (gene1Val1.toUpperCase().equals(gene2Val2.toUpperCase())) && gene1Val2.toUpperCase().equals(gene2Val1.toUpperCase()))){
                                sameCount++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return sameCount >= matchLimit;
    }

    private Map<String, Object> mixCompare(LimsSampleGene mixSg, LimsSampleGene sg, int matchLimit) {

        Map<String, Object> result = new HashMap<>();
        int sameCount = 0;
        int diffCount = 0;

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneStr = sg.getGeneInfo();
        String mixGeneStr = mixSg.getGeneInfo();

        List<CodisGeneInfo> geneDetailList = null;
        List<CodisGeneInfo> mixGeneDetailList = null;

        try {
            geneDetailList = jsonObjectMapper.readValue(geneStr, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
            mixGeneDetailList = jsonObjectMapper.readValue(mixGeneStr, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch(Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        if(ListUtils.isNullOrEmptyList(geneDetailList)
                || ListUtils.isNullOrEmptyList(mixGeneDetailList)) {

            result.put("match", Boolean.FALSE);
            result.put("sameCount", sameCount);
            result.put("diffCount", diffCount);

            return result;
        }

        CodisGeneInfo geneInfo;
        CodisGeneInfo mixGeneInfo;

        String geneVal1 = "";
        String geneVal2 = "";

        for(int i = 0; i < geneDetailList.size(); i++){
            boolean hasSame = false;
            geneInfo = geneDetailList.get(i);

            geneVal1 = geneInfo.getGeneVal1().toUpperCase();
            geneVal2 = geneInfo.getGeneVal2().toUpperCase();

            for(int j = 0; j < mixGeneDetailList.size(); j++){
                mixGeneInfo = mixGeneDetailList.get(j);

                String mixGeneVal1 = mixGeneInfo.getGeneVal1().toUpperCase();
                String mixGeneVal2 = mixGeneInfo.getGeneVal2().toUpperCase();
                String mixGeneVal3 = mixGeneInfo.getGeneVal3().toUpperCase();
                String mixGeneVal4 = mixGeneInfo.getGeneVal4().toUpperCase();
                if (geneInfo.getGeneName().toUpperCase().equals(mixGeneInfo.getGeneName().toUpperCase())) {
                    if (StringUtils.isNotBlank(mixGeneVal1) || StringUtils.isNotBlank(mixGeneVal2) || StringUtils.isNotBlank(mixGeneVal3) || StringUtils.isNotBlank(mixGeneVal4)) {
                        if ((mixGeneVal1.equals(geneVal1) || mixGeneVal2.equals(geneVal1) || mixGeneVal3.equals(geneVal1) || mixGeneVal4.equals(geneVal1)) &&
                                (mixGeneVal1.equals(geneVal2) || mixGeneVal2.equals(geneVal2) || mixGeneVal3.equals(geneVal2) || mixGeneVal4.equals(geneVal2))) {
                            hasSame = true;
                            sameCount++;
                            break;
                        }
                    }
                }
            }

            if(!hasSame){
                diffCount++;
            }
        }


        if(sameCount >= matchLimit){
            result.put("match", Boolean.TRUE);
        }else {
            result.put("match", Boolean.FALSE);
        }
        result.put("sameCount", sameCount);
        result.put("diffCount", diffCount);
        return result;
    }

}
