package com.bazl.hslims.web.controller.center.generalQuery;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.helper.CosignmentInfoHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/17.
 */
@Controller
@RequestMapping("/center/query")
public class GeneralQueryController extends BaseController {

    @Autowired
    DictService dictService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;

    /**
     * 案件查询
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/caseInfoList.html")
    public ModelAndView caseInfoList(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) {
        ModelAndView modelAndView = init();
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        if(query.getAdditionalFlag()==null){
            query.setAdditionalFlag(Constants.FLAG_FALSE);
        }

        query = resetCaseInfoQuery(query);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOGeneralQueryList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOGeneralQueryCount(query);

        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/query/caseInfoList");
        return modelAndView;
    }

    /**
     * 样本查询
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/sampleInfoList.html")
    public ModelAndView sampleInfoList(HttpServletRequest request, LimsSampleInfoVO query, PageInfo pageInfo) {
        ModelAndView modelAndView = init();
        /**
         * 实验室人员
         */
        List<LabUser> labUserList = labUserService.selectAll();

        query = resetSampleInfoQuery(query);
        query.getEntity().setSampleFlag(Constants.SAMPLE_FLAG_EVIDENCE);
        List<LimsSampleInfoVO> limsSampleInfoVOList = limsSampleInfoService.selectVOSampleInfoList(query, pageInfo);
        int totalCnt = limsSampleInfoService.selectVOSampleCount(query);

        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("query", query);
        modelAndView.addObject("sampleInfoList", limsSampleInfoVOList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/query/sampleInfoList");
        return modelAndView;
    }

    /**
     * 人员查询
     * @param request
     * @param query
     * @param pageInfo
     * @return
     */
    @RequestMapping("/personSampleInfoList.html")
    public ModelAndView personSampleInfoList(HttpServletRequest request, LimsSampleInfoVO query, PageInfo pageInfo) {
        ModelAndView modelAndView = init();
        /**
         * 实验室人员
         */
        List<LabUser> labUserList = labUserService.selectAll();

        query = resetSampleInfoQuery(query);
        query.getEntity().setSampleFlag(Constants.SAMPLE_FLAG_PERSON);
        List<LimsSampleInfoVO> limsSampleInfoVOList = limsSampleInfoService.selectVOPersonSampleList(query, pageInfo);
        int totalCnt = limsSampleInfoService.selectVOPersonSampleCount(query);

        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("query", query);
        modelAndView.addObject("sampleInfoList", limsSampleInfoVOList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/query/personSampleInfoList");
        return modelAndView;
    }


    /**
     * 查看样本详情
     * @param request
     * @param consignmentId
     * @return
     */
    @RequestMapping("/detailsSample.html")
    public ModelAndView editCase(HttpServletRequest request, Integer consignmentId,String sampleFlag) {
        ModelAndView modelAndView =init();

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);

        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);

        List<LimsConsignmentInfo> consignmentInfoList = null;
        DelegateOrg delegateOrg = null;
        if (StringUtils.isNotBlank(consignment.getDelegateOrg())) {
            delegateOrg = delegateOrgService.selectByDelegateOrgCode(consignment.getDelegateOrg());
            consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(delegateOrg.getId());
        } else {
            delegateOrg = delegateOrgService.selectByOrgName(caseInfo.getCreatePerson());
            consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(delegateOrg.getId());
        }

        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);
        //只显示已受理的检材
        for (Iterator<LimsSampleInfo> it = sampleInfoList.iterator(); it.hasNext(); ) {
            LimsSampleInfo si = it.next();
            if (!Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED.equals(si.getAcceptStatus())) {
                it.remove();
            }
        }

        //如果补送时，加载该案件其他委托中已受理的人员和检材列表
        if (Constants.FLAG_TRUE.equals(consignment.getAdditionalFlag())) {
            List<LimsPersonInfo> acceptedPersonInfoList = limsPersonInfoService.selectListByCaseId(caseInfo.getId());
            List<LimsSampleInfo> acceptedSampleInfoList = limsSampleInfoService.selectAcceptedListByCaseId(caseInfo.getId());

            //去掉本次委托的人员、检材
            LimsPersonInfo tmpPerson = null;
            for (Iterator<LimsPersonInfo> personInfoIterator = acceptedPersonInfoList.iterator(); personInfoIterator.hasNext(); ) {
                tmpPerson = personInfoIterator.next();
                if (tmpPerson.getConsignmentId().equals(consignmentId)) {
                    personInfoIterator.remove();
                }
            }

            LimsSampleInfo tmpSample = null;
            for (Iterator<LimsSampleInfo> sampleInfoIterator = acceptedSampleInfoList.iterator(); sampleInfoIterator.hasNext(); ) {
                tmpSample = sampleInfoIterator.next();
                if (tmpSample.getConsignmentId().equals(consignmentId)) {
                    sampleInfoIterator.remove();
                }
            }

            modelAndView.addObject("otherPersonInfoList", acceptedPersonInfoList);
            modelAndView.addObject("otherSampleInfoList", acceptedSampleInfoList);
        }

        modelAndView.addObject("sampleFlag",sampleFlag);
        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("delegateOrg", delegateOrg);
        modelAndView.addObject("delegatorList", CosignmentInfoHelper.getDelegatorList(consignmentInfoList));
        modelAndView.addObject("delegatorCertificateNoList", CosignmentInfoHelper.getDelegatorCertificateNoList(consignmentInfoList));
        modelAndView.addObject("delegatorPhoneList", CosignmentInfoHelper.getDelegatorPhoneList(consignmentInfoList));
        modelAndView.setViewName("center/query/detailsSample");
        return modelAndView;
    }

    /**
     * 查询样本是否有审核通过的基因型
     * @param request
     * @param personSampleId
     * @return
     */
    @RequestMapping(value="/queryGene.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> queryGene(HttpServletRequest request,@RequestParam Integer personSampleId){
        Map<String,Object> result = new HashMap<>();

        try {
            LimsSampleGene limsSampleGene = limsSampleGeneService.selectAuditStatusBySampleId(personSampleId);
            if (null != limsSampleGene){
                result.put("geneId",limsSampleGene.getId());
                result.put("success",true);
            }else{
                result.put("success",false);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("根据样本id查询基因型错误！"+e);
        }
        return result;
    }


    public ModelAndView init(){
        ModelAndView mv = new ModelAndView();
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DictItem> samplelTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();

        mv.addObject("personTypeList",personTypeList);
        mv.addObject("samplelTypeList",samplelTypeList);
        mv.addObject("caseStatusList", caseStatusList);
        mv.addObject("caseTypeList", caseTypeList);
        mv.addObject("casePropertyList", casePropertyList);
        mv.addObject("delegateOrgList", delegateOrgList);
        return mv;
    }

    public LimsSampleInfoVO resetSampleInfoQuery(LimsSampleInfoVO query){
        if (StringUtils.isBlank(query.getEntity().getSampleNo())){
            query.getEntity().setSampleNo(null);
        }else {
            query.getEntity().setSampleNo(query.getEntity().getSampleNo().trim());
        }

        if (StringUtils.isBlank(query.getEntity().getSampleName())){
            query.getEntity().setSampleName(null);
        }else {
            query.getEntity().setSampleName(query.getEntity().getSampleName().trim());
        }

        if (StringUtils.isBlank(query.getEntity().getSampleType())){
            query.getEntity().setSampleType(null);
        }else {
            query.getEntity().setSampleType(query.getEntity().getSampleType().trim());
        }

        if (null == query.getAcceptDatetimeStart()){
            query.setAcceptDatetimeStart(null);
        }else {
            query.setAcceptDatetimeStart(query.getAcceptDatetimeStart());
        }

        if (null == query.getAcceptDatetimeEnd()){
            query.setAcceptDatetimeEnd(null);
        }else {
            query.setAcceptDatetimeEnd(query.getAcceptDatetimeEnd());
        }

        if (StringUtils.isBlank(query.getEntity().getAcceptPerson())){
            query.getEntity().setAcceptPerson(null);
        }else {
            query.getEntity().setAcceptPerson(query.getEntity().getAcceptPerson().trim());
        }

        if (StringUtils.isBlank(query.getPersonName())){
            query.setPersonName(null);
        }else {
            query.setPersonName(query.getPersonName().trim());
        }

        if (StringUtils.isBlank(query.getPersonType())){
            query.setPersonType(null);
        }else {
            query.setPersonType(query.getPersonType().trim());
        }

        if (StringUtils.isBlank(query.getPersonIdcardNo())){
            query.setPersonIdcardNo(null);
        }else {
            query.setPersonIdcardNo(query.getPersonIdcardNo().trim());
        }

        return query;
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO){
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseNo())){
            caseInfoVO.getEntity().setCaseNo(null);
        }else {
            caseInfoVO.getEntity().setCaseNo(caseInfoVO.getEntity().getCaseNo());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())){
            caseInfoVO.getEntity().setCaseXkNo(null);
        }else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())){
            caseInfoVO.getEntity().setCaseName(null);
        }else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegatorName())){
            caseInfoVO.setDelegatorName(null);
        }else {
            caseInfoVO.setDelegatorName(caseInfoVO.getDelegatorName());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateAcceptor())){
            caseInfoVO.setDelegateAcceptor(null);
        }else {
            caseInfoVO.setDelegateAcceptor(caseInfoVO.getDelegateAcceptor());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseStatus())){
            caseInfoVO.getEntity().setCaseStatus(null);
        }else {
            caseInfoVO.getEntity().setCaseStatus(caseInfoVO.getEntity().getCaseStatus());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseProperty())){
            caseInfoVO.getEntity().setCaseProperty(null);
        }else {
            caseInfoVO.getEntity().setCaseProperty(caseInfoVO.getEntity().getCaseProperty());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseType())){
            caseInfoVO.getEntity().setCaseType(null);
        }else {
            caseInfoVO.getEntity().setCaseType(caseInfoVO.getEntity().getCaseType());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateOrg())){
            caseInfoVO.setDelegateOrg(null);
        }else {
            caseInfoVO.setDelegateOrg(caseInfoVO.getDelegateOrg());
        }

        if (caseInfoVO.getDelegateDatetimeStart() == null){
            caseInfoVO.setDelegateDatetimeStart(null);
        }else {
            caseInfoVO.setDelegateDatetimeStart(caseInfoVO.getDelegateDatetimeStart());
        }

        if (caseInfoVO.getDelegateDatetimeEnd() == null){
            caseInfoVO.setDelegateDatetimeEnd(null);
        }else {
            caseInfoVO.setDelegateDatetimeEnd(caseInfoVO.getDelegateDatetimeEnd());
        }

        if (caseInfoVO.getAcceptDateStart() == null){
            caseInfoVO.setAcceptDateStart(null);
        }else {
            caseInfoVO.setAcceptDateStart(caseInfoVO.getAcceptDateStart());
        }

        if (caseInfoVO.getAcceptDateEnd() == null){
            caseInfoVO.setAcceptDateEnd(null);
        }else {
            caseInfoVO.setAcceptDateEnd(caseInfoVO.getAcceptDateEnd());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getDeleteFlag())){
            caseInfoVO.getEntity().setDeleteFlag(Constants.FLAG_FALSE);
        }else {
            caseInfoVO.getEntity().setDeleteFlag(caseInfoVO.getEntity().getDeleteFlag());
        }

        if (StringUtils.isBlank(caseInfoVO.getAdditionalFlag())){
            caseInfoVO.setAdditionalFlag(null);
        }else {
            caseInfoVO.setAdditionalFlag(caseInfoVO.getAdditionalFlag());
        }

        if (caseInfoVO.getCaseDateStart() == null){
            caseInfoVO.setCaseDateStart(null);
        }else {
            caseInfoVO.setCaseDateStart(caseInfoVO.getCaseDateStart());
        }

        if (caseInfoVO.getCaseDateEnd() == null){
            caseInfoVO.setCaseDateEnd(null);
        }else {
            caseInfoVO.setCaseDateEnd(caseInfoVO.getCaseDateEnd());
        }

        return caseInfoVO;
    }

}
