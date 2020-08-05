package com.bazl.hslims.web.controller.delegate.caseinfo;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.CNNMFilter;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import com.bazl.hslims.web.helper.CosignmentInfoHelper;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/1/2.
 */
@Controller
@RequestMapping("/wt/caseinfo")
public class DelegateCaseInfoController extends BaseController {

    @Autowired
    DictService dictService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    IdentifyKernelService identifyKernelService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;

    @RequestMapping("/listPending.html")
    public ModelAndView pending(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        ModelAndView modelAndView = initDict();

        query = resetQueryParams(query);
        if (query.getEntity().getEntrustmentType() == null) {
            query.setConsignmentStatus(Constants.CASE_INFO_STATUS_PENDING);
        }
        query.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
        /*List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);*/
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOAllList(query, pageInfo);
        /*int totalCnt = limsCaseInfoService.selectVOCnt(query);*/
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        modelAndView.addObject("query", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/listPending");
        return modelAndView;
    }

    private LimsCaseInfoVO resetQueryParams(LimsCaseInfoVO query) throws ParseException {
        String caseName = query.getEntity().getCaseName();
        if (StringUtils.isBlank(caseName)) {
            query.getEntity().setCaseName(null);
        } else {
            query.getEntity().setCaseName(caseName.trim());
        }

        if (StringUtils.isBlank(query.getEntity().getEntrustmentType())) {
            query.getEntity().setEntrustmentType(null);
        } else {
            query.getEntity().setEntrustmentType(query.getEntity().getEntrustmentType());
        }

        if (StringUtils.isBlank(query.getEntity().getCaseXkNo())) {
            query.getEntity().setCaseXkNo(null);
        } else {
            query.getEntity().setCaseXkNo(query.getEntity().getCaseXkNo());
        }

        if (StringUtils.isBlank(query.getEntity().getCaseXkNo())) {
            query.getEntity().setCaseXkNo(null);
        } else {
            query.getEntity().setCaseXkNo(query.getEntity().getCaseXkNo());
        }

        String caseProperty = query.getEntity().getCaseProperty();
        if (StringUtils.isBlank(caseProperty)) {
            query.getEntity().setCaseProperty(null);
        } else {
            query.getEntity().setCaseProperty(caseProperty.trim());
        }

        String delegatorName = query.getDelegatorName();
        if (StringUtils.isBlank(delegatorName)) {
            query.setDelegatorName(null);
        } else {
            query.setDelegatorName(delegatorName.trim());
        }

        if (query.getDelegateDatetimeStart() == null){
            query.setDelegateDatetimeStart(null);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (query.getDelegateDatetimeEnd() == null){
            query.setDelegateDatetimeEnd(null);
        }else{
            oldTime = sdf.format(query.getDelegateDatetimeEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            query.setDelegateDatetimeEnd(newTime);
        }

        return query;
    }

    @RequestMapping("/listAccepted.html")
    public ModelAndView accepted(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        ModelAndView modelAndView = initDict();

        query = resetQueryParams(query);
        query.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
        query.setConsignmentStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");

        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        modelAndView.addObject("query", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/listAccepted");
        return modelAndView;
    }

    @RequestMapping("/listRefused.html")
    public ModelAndView refused(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        ModelAndView modelAndView = initDict();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (query.getDelegateDatetimeEnd() == null){
            query.setDelegateDatetimeEnd(null);
        }else{
            oldTime = sdf.format(query.getDelegateDatetimeEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            query.setDelegateDatetimeEnd(newTime);
        }

        query.setConsignmentStatus(Constants.CASE_INFO_STATUS_REFUSED);
        query.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        modelAndView.addObject("query", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("delegate/caseinfo/listRefused");
        return modelAndView;
    }

    @RequestMapping("/edit.html")
    public ModelAndView edit(HttpServletRequest request, Integer consignmentId) {
        ModelAndView modelAndView = initDict();

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);

        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);

        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.addObject(PARAMS_OPERATE_TYPE, Constants.OPERATE_TYPE_EDIT);
        modelAndView.setViewName("delegate/caseinfo/identifyCaseReg");
        return modelAndView;
    }

    @RequestMapping(value = "/reInspection.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> refuseCase(HttpServletRequest request, @RequestBody ConsignmentDataModel consignmentDataModel) {

        Map<String, Object> result = new HashMap<>();
        try {
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();
            LimsConsignment consignment = consignmentDataModel.getConsignment();

            consignment.setStatus("01");
            consignment.setRefuseReason(null);
            limsConsignmentService.refuseCase(consignment);

            caseInfo.setCaseStatus("01");
            limsCaseInfoService.refuseCase(caseInfo);

            result.put("success", true);
        } catch (Exception e) {
            logger.error("重新检验失败!", e);
            result.put("error", false);
        }

        return result;

    }

    @RequestMapping(value = "/selectIsAccepted.html", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String, Object> selectIsAccepted(HttpServletRequest request, @RequestBody LimsConsignment consignment) {

        Map<String, Object> result = new HashMap<>();
        try {
            consignment = limsConsignmentService.selectById(consignment.getId());
            result.put("status", consignment.getStatus());
        } catch (Exception e) {
            logger.error("检验失败!", e);
            result.put("error", false);
        }

        return result;

    }

    @RequestMapping("/editSupply.html")
    public ModelAndView editSupply(HttpServletRequest request, Integer consignmentId) {
        ModelAndView modelAndView = initDict();

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);

        List<LimsPersonInfo> supplyPersonInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);
        List<LimsSampleInfo> supplySampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);

        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByCaseId(caseInfo.getId());
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByCaseId(caseInfo.getId());

        //找出非当前补送委托的人员和检材列表
        LimsPersonInfo tmpPer = null;
        for (Iterator<LimsPersonInfo> perIt = personInfoList.iterator(); perIt.hasNext(); ) {
            tmpPer = perIt.next();
            if (tmpPer.getConsignmentId().equals(consignment.getId())) {
                perIt.remove();
            }
        }
        LimsSampleInfo sampPer = null;
        for (Iterator<LimsSampleInfo> sampIt = sampleInfoList.iterator(); sampIt.hasNext(); ) {
            sampPer = sampIt.next();
            if (sampPer.getConsignmentId().equals(consignment.getId())) {
                sampIt.remove();
            }
        }

        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        //非当前补送委托的人员和检材列表
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        //当前补送委托的人员和检材列表
        modelAndView.addObject("supplyPersonInfoList", supplyPersonInfoList);
        modelAndView.addObject("supplySampleInfoList", supplySampleInfoList);

        modelAndView.addObject("additionalFlag", Constants.FLAG_TRUE);
        modelAndView.addObject(PARAMS_OPERATE_TYPE, Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/identifyEditRegSupply");
        return modelAndView;
    }

    @RequestMapping("/acceptedDetails.html")
    public ModelAndView acceptedDetails(HttpServletRequest request, Integer consignmentId) {
        ModelAndView modelAndView = initDict();

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);

        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/viewAcceptedCaseInfo");
        return modelAndView;
    }


    @RequestMapping("/del.html")
    public ModelAndView del(HttpServletRequest request, Integer consignmentId, String fromUrl) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            limsCaseInfoService.deleteByConsignmentId(consignmentId);
        } catch (Exception ex) {
            //TODO

        }
        modelAndView.setViewName("redirect:/wt/caseinfo/" + fromUrl + ".html");
        return modelAndView;
    }


    @RequestMapping("/delPersonById.html")
    @ResponseBody
    public String delPersonById(HttpServletRequest request, Integer personId) {
        boolean result = limsPersonInfoService.deleteById(personId);

        return "{\"success\":" + (result ? "\"true\"" : "\"false\"") + "}";
    }

    @RequestMapping("/delSampleById.html")
    @ResponseBody
    public String delSampleById(HttpServletRequest request, Integer sampleId) {
        boolean result = limsSampleInfoService.deleteById(sampleId);

        return "{\"success\":" + (result ? "\"true\"" : "\"false\"") + "}";
    }

    @RequestMapping("/updateSampleById.html")
    @ResponseBody
    public String updateSampleById(HttpServletRequest request, LimsSampleInfo limsSampleInfo) {

        boolean result = limsSampleInfoService.updateById(limsSampleInfo);

        return "{\"success\":" + (result ? "\"true\"" : "\"false\"") + "}";
    }

    @RequestMapping(value = "/update.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String update(HttpServletRequest request, HttpServletResponse response,
                         @RequestBody ConsignmentDataModel consignmentDataModel) {
        try {
            boolean result = limsCaseInfoService.Update(consignmentDataModel);
            return "{\"success\":" + (result ? "\"true\"" : "\"false\"") + "}";
        } catch (Exception ex) {
            return "{\"success\":\"false\"}";
        }
    }


    public ModelAndView initDict() {
        ModelAndView modelAndView = new ModelAndView();

        /*  字典 */
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DictItem> caseLevelList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_LEVEL);
        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personRelationList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_RELATION);
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<DictItem> personGenderList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);
        List<DictItem> samplePropertiesList = dictService.selectDictItemListByType(Constants.DICT_TYPE_SAMPLE_PROPERTIES);
        List<DictItem> identifyRequirementList = dictService.selectDictItemListByType(Constants.DICT_TPYE_IDENTIFY_REQUIREMENT);
        List<DictItem> dictItemDutyList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_DUTY);
        List<DictItem> dictItemCertificateList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_CERTIFICATE_TYPE);

        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseLevelList", caseLevelList);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("personRelationList", personRelationList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("personGenderList", personGenderList);
        modelAndView.addObject("samplePropertiesList", samplePropertiesList);
        modelAndView.addObject("identifyRequirementList", identifyRequirementList);
        modelAndView.addObject("dictItemDutyList", dictItemDutyList);
        modelAndView.addObject("dictItemCertificateList", dictItemCertificateList);

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(LimsSecurityUtils.getLoginDelegateOrg().getId());

        modelAndView.addObject("delegatorList", CosignmentInfoHelper.getDelegatorList(consignmentInfoList));
        modelAndView.addObject("delegatorCertificateNoList", CosignmentInfoHelper.getDelegatorCertificateNoList(consignmentInfoList));
        modelAndView.addObject("delegatorPhoneList", CosignmentInfoHelper.getDelegatorPhoneList(consignmentInfoList));

        /*  委托单位 */
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        modelAndView.addObject("delegateOrgList", delegateOrgList);

        /*  鉴定机构名称 */
        List<IdentifyKernel> identifyKernelList = identifyKernelService.selectAll();
        modelAndView.addObject("identifyKernelList", identifyKernelList);

        return modelAndView;
    }


    @RequestMapping("/delegateDoc.html")
    public void generateAndDownload(HttpServletRequest request, HttpServletResponse response,
                                    Integer consignmentId) {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);

        Map<String, Object> params = new HashMap<String, Object>();

//        params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());
        params.put("caseXkNo", StringUtils.isBlank(caseInfo.getCaseXkNo()) ? "" : caseInfo.getCaseXkNo());
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        params.put("caseBrief", StringUtils.isBlank(caseInfo.getCaseBrief()) ? "" : caseInfo.getCaseBrief());
        params.put("delegateDate", DateUtils.dateToString(consignment.getDelegateDatetime(), "yyyy年MM月dd日"));
        params.put("delegateOrgName", StringUtils.isBlank(consignment.getDelegateOrgDesc()) ? "" : consignment.getDelegateOrgDesc());
        params.put("delegator1", StringUtils.isBlank(consignment.getDelegator1()) ? "" : consignment.getDelegator1());
        params.put("delegator1Position", StringUtils.isBlank(consignment.getDelegator1Position()) ? "" : consignment.getDelegator1Position());
        params.put("delegator1Cname", StringUtils.isBlank(consignment.getDelegator1Cname()) ? "" : consignment.getDelegator1Cname());
        params.put("delegator1Cno", StringUtils.isBlank(consignment.getDelegator1Cno()) ? "" : consignment.getDelegator1Cno());
        params.put("delegator1Phone", StringUtils.isBlank(consignment.getDelegator1Phone()) ? "" : consignment.getDelegator1Phone());
        params.put("delegator2", StringUtils.isBlank(consignment.getDelegator2()) ? "" : consignment.getDelegator2());
        params.put("delegator2Position", StringUtils.isBlank(consignment.getDelegator2Position()) ? "" : consignment.getDelegator2Position());
        params.put("delegator2Cname", StringUtils.isBlank(consignment.getDelegator2Cname()) ? "" : consignment.getDelegator2Cname());
        params.put("delegator2Cno", StringUtils.isBlank(consignment.getDelegator2Cno()) ? "" : consignment.getDelegator2Cno());
        params.put("delegator2Phone", StringUtils.isBlank(consignment.getDelegator2Phone()) ? "" : consignment.getDelegator2Phone());
        params.put("postalAddress", StringUtils.isBlank(consignment.getPostalAddress()) ? "" : consignment.getPostalAddress());
        params.put("postNo", StringUtils.isBlank(consignment.getPostNo()) ? "" : consignment.getPostNo());
        params.put("faxNo", StringUtils.isBlank(consignment.getFaxNo()) ? "" : consignment.getFaxNo());
        params.put("identifyKernelName", StringUtils.isBlank(consignment.getIdentifyKernelName()) ? "" : consignment.getIdentifyKernelName());
        params.put("identifyRequirement", StringUtils.isBlank(consignment.getIdentifyRequirement()) ? "" : consignment.getIdentifyRequirement());
        params.put("identifyRequirementOther", StringUtils.isBlank(consignment.getIdentifyRequirementOther()) ? null : consignment.getIdentifyRequirementOther());
        params.put("preIdentifyDesc", StringUtils.isBlank(consignment.getPreIdentifyDesc()) ? "" : consignment.getPreIdentifyDesc());
        params.put("reidentifyReason", StringUtils.isBlank(consignment.getReidentifyReason()) ? "" : consignment.getReidentifyReason());

        boolean bloodFlag = false;
        boolean seminalFlag = false;
        for (LimsSampleInfo sampleInfo : sampleInfoList) {
            sampleInfo.setSampleName(StringUtils.isBlank(sampleInfo.getSampleName()) ? "" : sampleInfo.getSampleName());
            sampleInfo.setSamplePacking(StringUtils.isBlank(sampleInfo.getSamplePacking()) ? "" : sampleInfo.getSamplePacking());
            sampleInfo.setSampleProperties(StringUtils.isBlank(sampleInfo.getSampleProperties()) ? "" : sampleInfo.getSampleProperties());
            sampleInfo.setOtherPropertiesDesc(StringUtils.isBlank(sampleInfo.getOtherPropertiesDesc()) ? null : sampleInfo.getOtherPropertiesDesc());
            sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)) {
                bloodFlag = true;
            }
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
                seminalFlag = true;
            }

            List<DictItem> dictItemList = dictService.selectByParentDictTypeCode(Constants.DICT_TPYE_SAMPLE_TYPE);
            for (DictItem dictItem : dictItemList) {
                if (dictItem.getDictCode().equals(sampleInfo.getSampleType())) {
                    sampleInfo.setSampleType(dictItem.getDictName());
                }
            }

            if (StringUtils.isNotBlank(sampleInfo.getSampleDesc())) {
                String enterCount = "0";
                if (HasDigit(sampleInfo.getSampleDesc().trim())) {
                    enterCount = getNumbers(sampleInfo.getSampleDesc().trim());
                    sampleInfo.setEnterCount(Integer.parseInt(enterCount));
                } else {
                    String sampleDesc = sampleInfo.getSampleDesc().trim();
                    String countStr = "";
                    for (int i = 0; i < sampleDesc.length(); i++) {
                        countStr = countStr + getNumber(sampleDesc.substring(i, i + 1));
                    }

                    int count = CNNMFilter.cnNumericToArabic(countStr, true);

                    if (count > 0) {
                        sampleInfo.setEnterCount(count);
                    } else {
                        sampleInfo.setEnterCount(1);
                    }
                }
            }
        }

        if (bloodFlag) {
            params.put("bloodFlag", "blood");
        } else {
            params.put("bloodFlag", null);
        }

        if (seminalFlag) {
            params.put("seminalFlag", "seminal");
        } else {
            params.put("seminalFlag", null);
        }
        List<LimsSampleInfo> sampleInfoList1 = new ArrayList<>();
        if (sampleInfoList.size() >= 9) {
            for (int i = 0; i < 9; i++) {
                sampleInfoList1.add(sampleInfoList.get(i));
            }
        } else {
            sampleInfoList1 = sampleInfoList;
        }
        if (sampleInfoList1.size() < 9) {
            int num = 9 - sampleInfoList.size();
            LimsSampleInfo tmpSample = null;
            for (int i = 0; i < num; i++) {
                tmpSample = new LimsSampleInfo();
                tmpSample.setSampleName("");
                tmpSample.setSampleNo("");
                tmpSample.setSamplePacking("");
                tmpSample.setSamplePropertiesName("");
                tmpSample.setEnterCount(null);
                sampleInfoList1.add(tmpSample);
            }
        }

        params.put("sampleList", sampleInfoList);

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("delegateInfo.ftl", "UTF-8");

            String filename = "委托书" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    @RequestMapping("/editCaseInformation.html")
    public ModelAndView editCaseInformation(HttpServletRequest request, Integer caseId) {
        ModelAndView modelAndView = new ModelAndView();
        LimsConsignment limsConsignment = new LimsConsignment();
        List<LimsPersonSample> personsampleList = new ArrayList<>();
        LimsPersonSample personsample = null;
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsConsignment> limsConsignmentList = limsConsignmentService.selectListByCaseId(caseId);
        for (LimsConsignment consignment : limsConsignmentList) {
            limsConsignment = consignment;
        }
        List<LimsPersonInfo> limsPersonInfoList = limsPersonInfoService.selectListByCaseId(caseId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByCaseId(caseId);
        for (LimsPersonInfo limsPersonInfo : limsPersonInfoList) {
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                personsample = new LimsPersonSample();
                if (limsPersonInfo.getId().equals(sampleInfo.getRefPersonId())) {
                    personsample.setId(limsPersonInfo.getId());
                    personsample.setSampleId(sampleInfo.getId());
                    personsample.setPersonType(limsPersonInfo.getPersonType());
                    personsample.setPersonName(limsPersonInfo.getPersonName());
                    personsample.setOneselfContact(limsPersonInfo.getRelativeIdentify());
                    personsample.setSampleType(sampleInfo.getSampleType());
                    personsample.setSampleName(sampleInfo.getSampleName());
                    personsample.setSampleLaboratoryNo(sampleInfo.getSampleNo());
                    personsample.setCreateDatetime(sampleInfo.getCreateDatetime());
                    personsample.setCaseInformationId(sampleInfo.getCaseId());
                    personsample.setEntrustmentId(sampleInfo.getConsignmentId());
                    personsampleList.add(personsample);
                }
            }
        }

        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personRelationList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_RELATION);
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<DictItem> dictItemCertificateList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_CERTIFICATE_TYPE);
        DelegateOrg delegateOrg = delegateOrgService.selectByOrgCode(limsConsignment.getDelegateOrgDesc());

        for (DictItem personType : personTypeList) {
            for (LimsPersonSample person : personsampleList) {
                if (personType.getDictCode().equals(person.getPersonType())) {
                    person.setPersonTypeCode(personType.getDictName());
                }
            }
        }
        for (DictItem personRelation : personRelationList) {
            for (LimsPersonSample person : personsampleList) {
                if (personRelation.getDictCode().equals(person.getOneselfContact())) {
                    person.setOneselfContactCode(personRelation.getDictName());
                }
            }
        }
        for (DictItem sampleType : sampleTypeList) {
            for (LimsPersonSample person : personsampleList) {
                if (sampleType.getDictCode().equals(person.getSampleType())) {
                    person.setSampleTypeCode(sampleType.getDictName());
                }
            }
        }

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(delegateOrg.getId());
        modelAndView.addObject("delegatorList", CosignmentInfoHelper.getDelegatorList(consignmentInfoList));
        modelAndView.addObject("delegatorCertificateNoList", CosignmentInfoHelper.getDelegatorCertificateNoList(consignmentInfoList));
        modelAndView.addObject("delegatorPhoneList", CosignmentInfoHelper.getDelegatorPhoneList(consignmentInfoList));
        modelAndView.addObject("dictItemCertificateList", dictItemCertificateList);

        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("personRelationList", personRelationList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("limsConsignment", limsConsignment);
        modelAndView.addObject("personSampleList", personsampleList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/updateCaseInfo");
        return modelAndView;
    }

    public boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    public static String getNumbers(String content) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "1";
    }

    public static String getNumber(String content) {
        if (content.contains("零")) {
            return "零";
        } else if (content.contains("一")) {
            return "一";
        } else if (content.contains("二")) {
            return "二";
        } else if (content.contains("三")) {
            return "三";
        } else if (content.contains("四")) {
            return "四";
        } else if (content.contains("五")) {
            return "五";
        } else if (content.contains("六")) {
            return "六";
        } else if (content.contains("七")) {
            return "七";
        } else if (content.contains("八")) {
            return "八";
        } else if (content.contains("九")) {
            return "九";
        } else if (content.contains("十")) {
            return "十";
        } else if (content.contains("百")) {
            return "百";
        }

        return "";
    }

}
