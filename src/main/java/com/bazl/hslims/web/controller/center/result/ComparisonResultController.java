package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.MatchGroup;
import com.bazl.hslims.manager.model.po.MatchResult;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.RedisCacheUtil;
import com.bazl.hslims.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
@Controller
@RequestMapping("/center/3")
public class ComparisonResultController extends BaseController {

    @Autowired
    MatchResultService matchResultService;
    @Autowired
    MatchGroupService matchGroupService;
    @Autowired
    DictService dictService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    RedisCacheUtil redisCacheUtil;

    @RequestMapping("/comparisonResult.html")
    public ModelAndView comparisonResult(HttpServletRequest request, MatchResult matchResult, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();
        MatchGroup group = new MatchGroup();
        if (StringUtils.isNotBlank(matchResult.getSourceCaseName())) {
            group.setCaseName(matchResult.getSourceCaseName().trim());
        }
        if (StringUtils.isNotBlank(matchResult.getSample1Name())) {
            group.setSampleName(matchResult.getSample1Name().trim());
        }
        if (StringUtils.isNotBlank(matchResult.getSampleEntryType())) {
            group.setSampleEntryType(matchResult.getSampleEntryType().trim());
        }
        if (StringUtils.isNotBlank(matchResult.getSample1No())) {
            group.setSampleNo(matchResult.getSample1No().trim());
        }
        List<DictItem> dictSampleEntryTypeList = dictService.selectByParentDictTypeCode(Constants.SAMPLE_ENTRY_TYPE);
        List<MatchGroup> matchGroupList = matchGroupService.selectList(group, pageInfo);
        int totalCnt = matchGroupService.selectListCount(group);
        List<MatchResult> matchResultList = new ArrayList<>();
        Map<Integer, Object> result = new HashMap<>();

        for (MatchGroup matchGroup : matchGroupList) {
            MatchResult matchResult1 = new MatchResult();
            matchResult1.setGroupId(matchGroup.getId());
            matchResultList = matchResultService.selectListByGroupId(matchResult1);

            if(matchResultList.size() == 0){
                matchGroupService.deleteById(matchGroup.getId());
            }

            for (int i = 0; i < matchResultList.size(); i++) {
                for (DictItem dictSampleEntryType : dictSampleEntryTypeList) {
                    if (matchResultList.get(i).getSampleEntryType() != null) {
                        if (matchResultList.get(i).getSampleEntryType().equals(dictSampleEntryType.getDictCode())) {
                            matchResultList.get(i).setSampleEntryType(dictSampleEntryType.getDictName());
                        }
                    }
                }
            }
            result.put(matchGroup.getId(), matchResultList);
        }

        modelAndView.addObject("matchResultList", result);
        modelAndView.addObject("dictSampleEntryTypeList", dictSampleEntryTypeList);
        modelAndView.addObject("matchResult", matchResult);
        modelAndView.addObject("matchGroupList", matchGroupList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/result/comparisonResult");
        return modelAndView;
    }

    @RequestMapping("/resultDetails.html")
    public ModelAndView resultDetails(HttpServletRequest request, Integer groupId) {
        ModelAndView modelAndView = new ModelAndView();
        List<DictItem> dictSampleEntryTypeList = dictService.selectByParentDictTypeCode(Constants.SAMPLE_ENTRY_TYPE);

        MatchResult result = new MatchResult();
        result.setGroupId(groupId);
        List<MatchResult> matchResultList = matchResultService.selectByGroupId(result);

        modelAndView.addObject("matchResultList", matchResultList);
        modelAndView.setViewName("center/result/resultDetails");
        return modelAndView;
    }

    @RequestMapping(value = "/queryMatchedDatails.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> queryMatchedDatails(HttpServletRequest request, Integer matchResultId) {

        Map<String, Object> result = new HashMap<>();

        if (matchResultId != null) {
            MatchResult matchResult = matchResultService.selectByMatchId(matchResultId);
            if (matchResult.getSample1Id() != null) {
                String sampleGeneJson1 = redisCacheUtil.hget("sample_gene", matchResult.getSample1Id().toString());
                result.put("geneInfo1", sampleGeneJson1);
                String sample1Name = limsSampleInfoService.selectById(matchResult.getSample1Id()).getSampleName();
                result.put("sample1Name", sample1Name);
            }
            if (matchResult.getSample2Id() != null) {
                String sampleGeneJson2 = redisCacheUtil.hget("sample_gene", matchResult.getSample2Id().toString());
                result.put("geneInfo2", sampleGeneJson2);
                String sample2Name = limsSampleInfoService.selectById(matchResult.getSample2Id()).getSampleName();
                result.put("sample2Name", sample2Name);
            }
            if (matchResult.getSample3Id() != null) {
                String sampleGeneJson3 = redisCacheUtil.hget("sample_gene", matchResult.getSample3Id().toString());
                result.put("geneInfo3", sampleGeneJson3);
                String sample3Name = limsSampleInfoService.selectById(matchResult.getSample3Id()).getSampleName();
                result.put("sample3Name", sample3Name);
            }
            result.put("matchResultString", matchResult.getMatchResultString());
            result.put("totalProbability", matchResult.getTotalProbability());
            result.put("sameCount",matchResult.getSameCount());
        }
        return result;
    }

    @RequestMapping(value = "/deleteByMatchResult.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> deleteByMatchResult(HttpServletRequest request, Integer matchResultId) {
        Map<String, Object> result = new HashMap<>();
        try {
            matchResultService.deleteById(matchResultId);
            result.put("success", true);
        } catch (Exception ex) {
            result.put("success", false);
        }

        return result;
    }


}
