package com.bazl.hslims.web.controller.center.caseinfo;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import com.bazl.hslims.web.helper.CosignmentInfoHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/3.
 */
@Controller
@RequestMapping("/center/caseinfo")
public class CaseInfoController extends BaseController {

    @Autowired
    DictService dictService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LabUserService labUserService;
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

    @RequestMapping("/pendingAcceptList.html")
    public ModelAndView pendingAcceptList(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {

        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();

        query = resetCaseInfoQuery(query);

        query.setConsignmentStatus(Constants.CASE_INFO_STATUS_PENDING);
        query.setOrderByClause(" lci.CREATE_DATETIME DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        for (LimsCaseInfoVO caseInfo : caseInfoList) {
            for (DictItem caseType : caseTypeList) {
                if (caseInfo.getEntity().getCaseType() != null) {
                    if (caseInfo.getEntity().getCaseType().equals(caseType.getDictCode())) {
                        caseInfo.setCaseTypeName(caseType.getDictName());
                    }
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/caseinfo/pendingAcceptList");
        return modelAndView;
    }


    @RequestMapping("/acceptCase.html")
    public ModelAndView intoAccept(HttpServletRequest request, Integer consignmentId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = initDictList(modelAndView);

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

        //如果补送时，加载该案件其他委托中已受理的人员和检材列表
        if (Constants.FLAG_TRUE.equals(consignment.getAdditionalFlag())) {
            List<LimsPersonInfo> acceptedPersonInfoList = limsPersonInfoService.selectListByCaseId(caseInfo.getId());
            List<LimsSampleInfo> acceptedSampleInfoList = limsSampleInfoService.selectAcceptedListByCaseId(caseInfo.getId());

            modelAndView.addObject("otherPersonInfoList", acceptedPersonInfoList);
            modelAndView.addObject("otherSampleInfoList", acceptedSampleInfoList);
        }

        String xkLoginName = SystemUtil.getSystemConfig().getProperty("SJloginName");
        String xkLoginPassword = SystemUtil.getSystemConfig().getProperty("SJpassword");

        modelAndView.addObject("xkLoginName", xkLoginName);
        modelAndView.addObject("xkLoginPassword", xkLoginPassword);

        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("delegateOrg", delegateOrg);
        modelAndView.addObject("delegatorList", CosignmentInfoHelper.getDelegatorList(consignmentInfoList));
        modelAndView.addObject("delegatorCertificateNoList", CosignmentInfoHelper.getDelegatorCertificateNoList(consignmentInfoList));
        modelAndView.addObject("delegatorPhoneList", CosignmentInfoHelper.getDelegatorPhoneList(consignmentInfoList));
        modelAndView.setViewName("center/caseinfo/acceptCase");
        return modelAndView;
    }


    @RequestMapping("/editCase.html")
    public ModelAndView editCase(HttpServletRequest request, Integer consignmentId, String fromUrl) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = initDictList(modelAndView);

        LimsCaseInfo caseInfo = new LimsCaseInfo();
        if ("query".equals(fromUrl)) {
            caseInfo = limsCaseInfoService.selectListByConsignmentId(consignmentId);
        } else {
            caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        }
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

        modelAndView.addObject("fromUrl", fromUrl);
        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("delegateOrg", delegateOrg);
        modelAndView.addObject("delegatorList", CosignmentInfoHelper.getDelegatorList(consignmentInfoList));
        modelAndView.addObject("delegatorCertificateNoList", CosignmentInfoHelper.getDelegatorCertificateNoList(consignmentInfoList));
        modelAndView.addObject("delegatorPhoneList", CosignmentInfoHelper.getDelegatorPhoneList(consignmentInfoList));
        modelAndView.setViewName("center/caseinfo/editCase");
        return modelAndView;
    }

    private ModelAndView initDictList(ModelAndView modelAndView) {
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

        /*  委托单位 */
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        modelAndView.addObject("delegateOrgList", delegateOrgList);

        /*  鉴定机构名称 */
        List<IdentifyKernel> identifyKernelList = identifyKernelService.selectAll();
        modelAndView.addObject("identifyKernelList", identifyKernelList);

        return modelAndView;
    }

    @RequestMapping(value = "/submitAcceptPerson.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitAcceptPerson(HttpServletRequest request, HttpServletResponse response, @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.acceptSavePerson(consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/submitAcceptSample.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitAcceptSample(HttpServletRequest request, HttpServletResponse response, @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.acceptSaveSample(consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/accept.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> accept(HttpServletRequest request, @RequestBody ConsignmentDataModel consignmentDataModel) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            limsCaseInfoService.Accept(consignmentDataModel);
            result.put("success", true);
        } catch (Exception ex) {
            result.put("success", false);
        }

        return result;
    }


    @RequestMapping(value = "/update.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> update(HttpServletRequest request, @RequestBody ConsignmentDataModel consignmentDataModel) {

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            limsCaseInfoService.Update(consignmentDataModel);
            result.put("success", true);
        } catch (Exception ex) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping(value = "/refuseCase.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> refuseCase(HttpServletRequest request, @RequestBody LimsConsignment consignment) {

        Map<String, Object> result = new HashMap<>();
        try {
            consignment.setStatus("04");
            limsConsignmentService.refuseCase(consignment);
            if (consignment.getAdditionalFlag().equals("0")) {
                LimsCaseInfo caseInfo = new LimsCaseInfo();
                caseInfo.setId(consignment.getCaseId());
                caseInfo.setCaseStatus("04");
                limsCaseInfoService.refuseCase(caseInfo);
            }
            result.put("success", true);
        } catch (Exception e) {
            logger.error("退案失败!", e);
            result.put("error", false);
        }

        return result;

    }

    @RequestMapping("/acceptDoc.html")
    public void generateAndDownload(HttpServletRequest request, HttpServletResponse response,
                                    Integer consignmentId) {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectAcceptStatusListByConsignmentId(consignmentId);
        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);

        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
        if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                int length = caseNo.split("-").length;
                if (length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }
        params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        params.put("caseBrief", StringUtils.isBlank(caseInfo.getCaseBrief()) ? "" : caseInfo.getCaseBrief());
        params.put("acceptDate", DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy年MM月dd日"));
        params.put("acceptor", StringUtils.isBlank(consignment.getAcceptor()) ? "" : consignment.getAcceptor());
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
            sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)) {
                bloodFlag = true;
            }
            if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
                seminalFlag = true;
            }
        }

        for (LimsPersonInfo personInfo : personInfoList) {
            personInfo.setPersonName(StringUtils.isBlank(personInfo.getPersonName()) ? "" : personInfo.getPersonName());
            personInfo.setPersonGenderName(StringUtils.isBlank(personInfo.getPersonGenderName()) ? "" : personInfo.getPersonGenderName());
            personInfo.setPersonIdcardNo(StringUtils.isBlank(personInfo.getPersonIdcardNo()) ? "" : personInfo.getPersonIdcardNo());
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

        if (sampleInfoList.size() < 11) {
            int num = 11 - sampleInfoList.size();
            LimsSampleInfo tmpSample = null;
            for (int i = 0; i < num; i++) {
                tmpSample = new LimsSampleInfo();
                tmpSample.setSampleName("");
                tmpSample.setSampleNo("");
                tmpSample.setSamplePacking("");
                tmpSample.setSamplePropertiesName("");
                sampleInfoList.add(tmpSample);
            }
        }

        params.put("sampleList", sampleInfoList);
        params.put("personList", personInfoList);

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("acceptInfo.ftl", "UTF-8");

            String filename = "-鉴定事项确认书" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    @RequestMapping("/sampleExchangeDoc.html")
    public void sampleExchangeDoc(HttpServletRequest request, HttpServletResponse response,
                                  Integer consignmentId) {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        //LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectAcceptStatusListByConsignmentId(consignmentId);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("caseNo", StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo());
        //params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());

        LimsSampleInfo firstSample = sampleInfoList.get(0);
        LimsSampleInfo lastSample = sampleInfoList.get(sampleInfoList.size() - 1);

//        for(LimsSampleInfo sampleInfo : sampleInfoList){
//            sampleInfo.setSampleNo(StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo());
//        }
        List<String> sampleNoList = new ArrayList();
        for (int i = 0; i < 15; i++) {
            if (i == 0) {
                sampleNoList.add(firstSample.getSampleNo() + " — " + lastSample.getSampleNo());
            } else {
                sampleNoList.add("");
            }
        }
//        //保证导出word的格式至少保证第一页满页，不足15个检材，添加空的sampleInfo
//        if(sampleInfoList.size() < 15){
//            int num = 15 - sampleInfoList.size();
//            LimsSampleInfo tmpSample = null;
//            for(int i = 0; i < num; i++){
//                tmpSample = new LimsSampleInfo();
//                tmpSample.setSampleNo("");
//                sampleInfoList.add(tmpSample);
//            }
//        }

        params.put("sampleNoList", sampleNoList);

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("sampleExchangeRecord.ftl", "UTF-8");

            String filename = "-检材及样本流转记录" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    @RequestMapping("/acceptedList.html")
    public ModelAndView acceptedList(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        query = resetCaseInfoQuery(query);
//        query.getEntity().setCaseStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        query.setConsignmentStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/caseinfo/acceptedList");
        return modelAndView;
    }

    @RequestMapping("/delCaseInfoByCaseId.html")
    @ResponseBody
    public Map<String, Object> delCaseInfoByCaseId(HttpServletRequest request, Integer caseId, Integer consignmentId, String additionalFlag) {
        if (additionalFlag.equals("0")) {
            limsCaseInfoService.deleteById(caseId);
            limsConsignmentService.deleteByCaseId(caseId);
            limsSampleInfoService.deleteByCaseId(caseId);
            limsPersonInfoService.deleteByCaseId(caseId);
        } else {
            limsConsignmentService.deleteById(consignmentId);
            limsSampleInfoService.deleteByConsignmentId(consignmentId);
            limsPersonInfoService.deleteByConsignmentId(consignmentId);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    @RequestMapping("/finishedList.html")
    public ModelAndView finishedList(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        query = resetCaseInfoQuery(query);
        query.getEntity().setCaseStatus(Constants.CASE_INFO_STATUS_FINISHED);
//        query.setConsignmentStatus(Constants.CASE_INFO_STATUS_FINISHED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/caseinfo/finishedList");
        return modelAndView;
    }

    @RequestMapping("/selectCaseNoIsRepeat.html")
    @ResponseBody
    public Map<String, Object> selectCaseNoIsRepeat(HttpServletRequest request, String caseNo, Integer caseId) {

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectListByCaseNo(caseNo);
        Map<String, Object> result = new HashMap<String, Object>();
        if (limsCaseInfo == null) {
            result.put("success", true);
        } else {
            if (caseId.equals(limsCaseInfo.getId())) {
                result.put("success", true);
            } else {
                result.put("success", false);
            }
        }
        return result;
    }

    @RequestMapping("/delSample.html")
    @ResponseBody
    public Map<String, Object> delSample(HttpServletRequest request, Integer sampleId) {
        this.limsSampleInfoService.deleteById(sampleId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    @RequestMapping("/delPerson.html")
    @ResponseBody
    public Map<String, Object> delPerson(HttpServletRequest request, Integer personId) {
        this.limsPersonInfoService.deleteById(personId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    @RequestMapping("/uploadPhoto.html")
    public
    @ResponseBody
    Map<String, Object> uploadCodis(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("photoFile") MultipartFile photoFile, Integer sampleId) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            String uploadPhotoPath = SystemUtil.getSystemConfig().getProperty("uploadPhotoPath");

            if (StringUtils.isBlank(uploadPhotoPath)) {
                uploadPhotoPath = "e:\\uploadPhotoPath\\";
            }

            if (!new File(uploadPhotoPath).exists()) {
                new File(uploadPhotoPath).mkdir();
            }

            LimsSampleInfo limsSampleInfo = limsSampleInfoService.selectById(sampleId);

            String caseIdPath = uploadPhotoPath + limsSampleInfo.getCaseId() + "\\";

            if (!new File(caseIdPath).exists()) {
                new File(caseIdPath).mkdir();
            }

            String filePath = caseIdPath + sampleId + "\\";

            if (!new File(filePath).exists()) {
                new File(filePath).mkdir();// 创建远程文件夹
            }

            if (new File(filePath).exists()) {
                String filename = photoFile.getOriginalFilename();
                InputStream is = photoFile.getInputStream();

                // 开始保存文件到服务器
                if (StringUtils.isNotBlank(filename)) {
                    FileOutputStream fos = new FileOutputStream(filePath + filename);
                    byte[] buffer = new byte[2048];
                    int count = 0;
                    // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count); // 向服务端文件写入字节流
                    }
                    fos.close(); // 关闭FileOutputStream对象
                    is.close(); // InputStream对象
                }
                LimsSampleInfo sampleInfo = new LimsSampleInfo();
                sampleInfo.setId(sampleId);
                sampleInfo.setPhotoPath(filePath + filename);

                limsSampleInfoService.updatePhotoPath(sampleInfo);

            }
        } catch (Exception ex) {
            resultMap.put("success", false);
            resultMap.put("errMsg", ex.getMessage());
            return resultMap;
        }

        resultMap.put("success", true);
        return resultMap;
    }

    @RequestMapping("/viewPhoto.html")
    public void viewPhoto(HttpServletRequest request, HttpServletResponse response, Integer sampleId) {
        FileInputStream fis = null;
        OutputStream os = null;
        try {

            LimsSampleInfo sampleInfo = limsSampleInfoService.selectById(sampleId);

            fis = new FileInputStream(sampleInfo.getPhotoPath());
            os = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024 * 8];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
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

        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseLevelList", caseLevelList);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("personRelationList", personRelationList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("personGenderList", personGenderList);

        /*  委托单位 */
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        modelAndView.addObject("delegateOrgList", delegateOrgList);

        /*  鉴定机构名称 */
        List<IdentifyKernel> identifyKernelList = identifyKernelService.selectAll();
        modelAndView.addObject("identifyKernelList", identifyKernelList);

        return modelAndView;
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO) throws ParseException {
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseNo())) {
            caseInfoVO.getEntity().setCaseNo(null);
        } else {
            caseInfoVO.getEntity().setCaseNo(caseInfoVO.getEntity().getCaseNo());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())) {
            caseInfoVO.getEntity().setCaseXkNo(null);
        } else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo().trim());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())) {
            caseInfoVO.getEntity().setCaseName(null);
        } else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegatorName())) {
            caseInfoVO.setDelegatorName(null);
        } else {
            caseInfoVO.setDelegatorName(caseInfoVO.getDelegatorName());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateAcceptor())) {
            caseInfoVO.setDelegateAcceptor(null);
        } else {
            caseInfoVO.setDelegateAcceptor(caseInfoVO.getDelegateAcceptor());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseProperty())) {
            caseInfoVO.getEntity().setCaseProperty(null);
        } else {
            caseInfoVO.getEntity().setCaseProperty(caseInfoVO.getEntity().getCaseProperty());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateOrg())) {
            caseInfoVO.setDelegateOrg(null);
        } else {
            caseInfoVO.setDelegateOrg(caseInfoVO.getDelegateOrg());
        }

        if (caseInfoVO.getDelegateDatetimeStart() == null) {
            caseInfoVO.setDelegateDatetimeStart(null);
        } else {
            caseInfoVO.setDelegateDatetimeStart(caseInfoVO.getDelegateDatetimeStart());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (caseInfoVO.getDelegateDatetimeEnd() == null) {
            caseInfoVO.setDelegateDatetimeEnd(null);
        } else {
            oldTime = sdf.format(caseInfoVO.getDelegateDatetimeEnd());
            newTime = sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setDelegateDatetimeEnd(newTime);
        }

        if (caseInfoVO.getAcceptDateStart() == null) {
            caseInfoVO.setAcceptDateStart(null);
        } else {
            caseInfoVO.setAcceptDateStart(caseInfoVO.getAcceptDateStart());
        }

        if (caseInfoVO.getAcceptDateEnd() == null) {
            caseInfoVO.setAcceptDateEnd(null);
        } else {
            oldTime = sdf.format(caseInfoVO.getAcceptDateEnd());
            newTime = sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setAcceptDateEnd(newTime);
        }

        return caseInfoVO;
    }

    @RequestMapping("/CirculationRecordDoc.html")
    public void CirculationRecordDoc(HttpServletRequest request, HttpServletResponse response,
                                     Integer caseId, Integer consignmentId) {
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByCaseId(caseId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);

        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
        if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                int length = caseNo.split("-").length;
                if (length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }
        params.put("delegateOrgName", StringUtils.isBlank(consignment.getDelegateOrgDesc()) ? "" : consignment.getDelegateOrgDesc());
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        ;

        String sampleNo = "";
        if (ListUtils.isNotNullAndEmptyList(limsSampleInfoList)) {
            sampleNo = limsSampleInfoList.get(0).getSampleNo() + " ~ " + limsSampleInfoList.get(limsSampleInfoList.size() - 1).getSampleNo();
            params.put("sampleNo", StringUtils.isBlank(sampleNo) ? "" : sampleNo);
        } else {
            params.put("sampleNo", "");
        }

        params.put("delegator1", StringUtils.isBlank(consignment.getDelegator1()) ? "" : consignment.getDelegator1());
        params.put("delegator2", StringUtils.isBlank(consignment.getDelegator2()) ? "" : consignment.getDelegator2());
        params.put("acceptor", StringUtils.isBlank(consignment.getAcceptor()) ? "" : consignment.getAcceptor());
        params.put("acceptDatetime", consignment.getAcceptDatetime() == null ? "" : DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy年MM月dd日"));

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("circulationRecord.ftl", "UTF-8");

            String filename = "-检材样本流转记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    //补送下载样本流转记录表
    @RequestMapping("/repairCirculationRecordDoc.html")
    public void repairCirculationRecordDoc(HttpServletRequest request, HttpServletResponse response,
                                           Integer caseId, Integer consignmentId) {
        List<LimsSampleInfo> limsSampleInfoList = new ArrayList<>();
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        if (consignment.getAdditionalFlag().equals("1")) {
            limsSampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignment.getId());
        }

        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
        if (caseNo == null || caseNo == "") {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                int length = caseNo.split("-").length;
                if (length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }
        params.put("delegateOrgName", StringUtils.isBlank(consignment.getDelegateOrgDesc()) ? "" : consignment.getDelegateOrgDesc());
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        ;

        String sampleNo = "";
        if (ListUtils.isNotNullAndEmptyList(limsSampleInfoList)) {
            sampleNo = limsSampleInfoList.get(0).getSampleNo() + " ~ " + limsSampleInfoList.get(limsSampleInfoList.size() - 1).getSampleNo();
            params.put("sampleNo", StringUtils.isBlank(sampleNo) ? "" : sampleNo);
        } else {
            params.put("sampleNo", "");
        }

        params.put("delegator1", StringUtils.isBlank(consignment.getDelegator1()) ? "" : consignment.getDelegator1());
        params.put("delegator2", StringUtils.isBlank(consignment.getDelegator2()) ? "" : consignment.getDelegator2());
        params.put("acceptor", StringUtils.isBlank(consignment.getAcceptor()) ? "" : consignment.getAcceptor());
        params.put("acceptDatetime", consignment.getAcceptDatetime() == null ? "" : DateUtils.dateToString(consignment.getAcceptDatetime(), "yyyy年MM月dd日"));

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("circulationRecord.ftl", "UTF-8");

            String filename = "-检材样本流转记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseInfo.getCaseNo() + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

}
