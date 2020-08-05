package com.bazl.hslims.web.controller.center.caseinfo;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.center.CenterLoginService;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import com.bazl.hslims.web.helper.CosignmentInfoHelper;
import com.bazl.hslims.web.helper.ExamineHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangliu on 2018/5/15.
 */
@Controller
@RequestMapping("/center/caseInformation")
public class caseInformationController extends BaseController {

    @Autowired
    DictService dictService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    LimsCaseInformationService limsCaseInformationService;
    @Autowired
    LimsEntrustmentInformationService limsEntrustmentInformationService;
    @Autowired
    LimsPersonSampleService limsPersonSampleService;
    @Autowired
    LimsExtractRecordService limsExtractRecordService;
    @Autowired
    CenterLoginService centerLoginService;
    @Autowired
    PanelInfoService panelInfoService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;


    @RequestMapping("/unidentifiedList.html")
    public ModelAndView pendingAcceptList(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();

        if (StringUtils.isBlank(query.getEntity().getCaseNo())) {
            query.getEntity().setCaseNo(null);
        }else{
            query.getEntity().setCaseNo(query.getEntity().getCaseNo());
        }
        if (StringUtils.isBlank(query.getEntity().getCaseName())) {
            query.getEntity().setCaseName(null);
        } else{
            query.getEntity().setCaseName(query.getEntity().getCaseName());
        }
        if (StringUtils.isBlank(query.getDelegatorName())) {
            query.setDelegatorName(null);
        } else {
            query.setDelegatorName(query.getDelegatorName());
        }
        if (query.getDelegateDatetimeStart() == null) {
            query.setDelegateDatetimeStart(null);
        } else {
            query.setDelegateDatetimeStart(query.getDelegateDatetimeStart());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (query.getDelegateDatetimeEnd() == null) {
            query.setDelegateDatetimeEnd(null);
        } else {
            oldTime = sdf.format(query.getDelegateDatetimeEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            query.setDelegateDatetimeEnd(newTime);
        }
        if (StringUtils.isBlank(query.getEntity().getEntrustmentType())) {
            query.getEntity().setEntrustmentType("01");
        } else {
            query.getEntity().setEntrustmentType(query.getEntity().getEntrustmentType());
        }
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOAllList(query, pageInfo);

        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.setViewName("center/caseInformation/unidentifiedList");
        return modelAndView;
    }

    @RequestMapping("/unidentifiedExtractionList.html")
    public ModelAndView unidentifiedExtractionList(HttpServletRequest request, LimsCaseInformation limsCaseInformation, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();
        limsCaseInformation = resetCaseInfoQuery(limsCaseInformation);
        limsCaseInformation.setEntrustmentType("01");
        List<LimsCaseInformation> limsCaseInformationList = limsCaseInformationService.selectPaginationList(limsCaseInformation, pageInfo);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        int totalCnt = limsCaseInformationService.selectCount(limsCaseInformation);
        for (LimsCaseInformation CaseInformation : limsCaseInformationList) {
            CaseInformation.setExtractionState(limsExtractRecordService.selectCountByCaseId(CaseInformation.getId()));
        }

        modelAndView.addObject("limsCaseInformationList", limsCaseInformationList);
        modelAndView.addObject("limsCaseInformation", limsCaseInformation);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/caseInformation/unidentifiedExtractionList");
        return modelAndView;
    }

    @RequestMapping("/deleteByCaseId.html")
    @ResponseBody
    public Map<String, Object> deleteByCaseId(HttpServletRequest request, Integer caseInformationId) {
        List<LimsExtractRecord> recordList = limsExtractRecordService.selectListByCaseId(caseInformationId);
        for (LimsExtractRecord record : recordList) {
            limsExtractRecordService.deleteById(record.getId());
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

    @RequestMapping("/addExtraction.html")
    public ModelAndView add(HttpServletRequest request, Integer caseInformationId) {
        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        LimsExtractRecord limsExtractRecord = new LimsExtractRecord();
        limsExtractRecord.setCaseId(caseInformationId);
        LabUser labUser1 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_2);
        limsExtractRecord.setExtractPersonId1(labUser1.getId());
        limsExtractRecord.setExtractPersonName1(labUser1.getUserName());

        LabUser labUser2 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_1);
        limsExtractRecord.setExtractPersonId2(labUser2.getId());
        limsExtractRecord.setExtractPersonName2(labUser2.getUserName());

        limsExtractRecord.setExtractDatetime(DateUtils.getCurrentDate());

        List<LimsPersonSample> limsPersonSampleList = limsPersonSampleService.selectListByCaseInformationId(caseInformationId);
        List<LimsExtractRecordSample> limsExtractRecordSampleList = new ArrayList<>();
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        LimsExtractRecordSample extractRecordSample = null;
        for (LimsPersonSample sample : limsPersonSampleList) {
            extractRecordSample = new LimsExtractRecordSample();
            extractRecordSample.setSampleId(sample.getId());
            extractRecordSample.setSampleName(sample.getSampleName());
            for (DictItem sampleType : sampleTypeList) {
                if (sample.getSampleType().equals(sampleType.getDictCode())) {
                    extractRecordSample.setSampleTypeName(sampleType.getDictName());
                }
            }
            extractRecordSample.setSampleNo(sample.getSampleLaboratoryNo());
            extractRecordSample.setExtractHb("");
            extractRecordSample.setExtractPsa("");
            extractRecordSample.setExtractMethod(ExamineHelper.getExtractMethodBySampleType(sample.getSampleType()));

            extractRecordSample.setExtractYuFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractZhenFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractJiaoFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractLiFlag(Constants.FLAG_TRUE);
            limsExtractRecordSampleList.add(extractRecordSample);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsExtractRecordSampleList", limsExtractRecordSampleList);
        modelAndView.setViewName("center/caseInformation/unidentifiedExtractInfo");
        return modelAndView;
    }

    @RequestMapping("/editExtractRecord.html")
    public ModelAndView editExtractRecord(HttpServletRequest request, Integer caseInformationId) {
        List<LimsExtractRecord> limsExtractRecordList = limsExtractRecordService.selectListByCaseId(caseInformationId);

        int extractRecordCount = limsExtractRecordService.selectCountByCaseId(caseInformationId);
        if (limsExtractRecordList.size() == 1) {
            return viewOne(limsExtractRecordList.get(0));
        } else {
            return viewList(limsExtractRecordList, caseInformationId);
        }
    }

    private ModelAndView viewOne(LimsExtractRecord limsExtractRecord) {
        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<LimsPersonSample> personSampleList = limsPersonSampleService.selectListByCaseInformationId(limsExtractRecord.getCaseId());

        List<LimsExtractRecordSample> limsExtractRecordSampleList = limsExtractRecordService.selectSampleListByExtractRecordId(limsExtractRecord.getId());
        for (LimsExtractRecordSample ExtractRecordSample : limsExtractRecordSampleList) {
            if (ExtractRecordSample.getSampleName() == null) {
                for (LimsPersonSample personSample : personSampleList) {
                    for (DictItem sampleType : sampleTypeList) {
                        if (personSample.getSampleType().equals(sampleType.getDictCode())) {
                            ExtractRecordSample.setSampleName(personSample.getSampleName());
                            ExtractRecordSample.setSampleTypeName(sampleType.getDictName());
                        }
                    }
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("caseId", limsExtractRecord.getCaseId());
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsExtractRecordSampleList", limsExtractRecordSampleList);
        modelAndView.setViewName("center/caseInformation/unidentifiedExtractInfo");
        return modelAndView;
    }

    private ModelAndView viewList(List<LimsExtractRecord> limsExtractRecordList, Integer caseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsExtractRecordList", limsExtractRecordList);
        modelAndView.setViewName("center/caseInformation/extractList");
        return modelAndView;
    }

    @RequestMapping("/editExtract.html")
    public ModelAndView editExtract(HttpServletRequest request, Integer extractRecordId) {
        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();
        LimsExtractRecord limsExtractRecord = limsExtractRecordService.selectById(extractRecordId);
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<LimsPersonSample> personSampleList = limsPersonSampleService.selectListByCaseInformationId(limsExtractRecord.getCaseId());

        List<LimsExtractRecordSample> limsExtractRecordSampleList = limsExtractRecordService.selectSampleListByExtractRecordId(extractRecordId);
        for (LimsExtractRecordSample ExtractRecordSample : limsExtractRecordSampleList) {
            if (ExtractRecordSample.getSampleName() == null) {
                for (LimsPersonSample personSample : personSampleList) {
                    for (DictItem sampleType : sampleTypeList) {
                        if (personSample.getSampleType().equals(sampleType.getDictCode())) {
                            ExtractRecordSample.setSampleName(personSample.getSampleName());
                            ExtractRecordSample.setSampleTypeName(sampleType.getDictName());
                        }
                    }
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsExtractRecordSampleList", limsExtractRecordSampleList);
        modelAndView.setViewName("center/caseInformation/unidentifiedExtractInfo");
        return modelAndView;
    }

    @RequestMapping("/delCaseInformation.html")
    public ModelAndView delPersonSample(HttpServletRequest request, Integer caseInformationId) {
        ModelAndView modelAndView = new ModelAndView();
        limsCaseInformationService.deleteById(caseInformationId);
        LimsEntrustmentInformation limsEntrustmentInformation = limsEntrustmentInformationService.selectByCaseInformationId(caseInformationId);
        if (limsEntrustmentInformation != null) {
            limsEntrustmentInformationService.deleteById(limsEntrustmentInformation.getId());
        }
        modelAndView.setViewName("redirect:/center/caseInformation/unidentifiedList.html");
        return modelAndView;
    }

    @RequestMapping("/deleteCaseInformation.html")
    public ModelAndView deleteCaseInformation(HttpServletRequest request, Integer caseInformationId) {
        ModelAndView modelAndView = new ModelAndView();
        limsCaseInformationService.deleteById(caseInformationId);
        LimsEntrustmentInformation limsEntrustmentInformation = limsEntrustmentInformationService.selectByCaseInformationId(caseInformationId);
        if (limsEntrustmentInformation != null) {
            limsEntrustmentInformationService.deleteById(limsEntrustmentInformation.getId());
        }
        modelAndView.setViewName("redirect:/center/caseInformation/missingList.html");
        return modelAndView;
    }

    @RequestMapping("/editCaseInformation.html")
    public ModelAndView editCaseInformation(HttpServletRequest request, Integer caseId) {
        ModelAndView modelAndView = new ModelAndView();
        LimsConsignment limsConsignment = new LimsConsignment();
        List<LimsPersonSample> personsampleList = new ArrayList<>();
        LimsPersonSample personsample = new LimsPersonSample();
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
        DelegateOrg delegateOrg = delegateOrgService.selectByDelegateOrgCode(limsConsignment.getDelegateOrg());

        for (DictItem personType:personTypeList) {
            for (LimsPersonSample person:personsampleList) {
                if(personType.getDictCode().equals(person.getPersonType())){
                    person.setPersonTypeCode(personType.getDictName());
                }
            }
        }
        for (DictItem personRelation:personRelationList) {
            for (LimsPersonSample person:personsampleList) {
                if(personRelation.getDictCode().equals(person.getOneselfContact())){
                    person.setOneselfContactCode(personRelation.getDictName());
                }
            }
        }
        for (DictItem sampleType:sampleTypeList) {
            for (LimsPersonSample person:personsampleList) {
                if(sampleType.getDictCode().equals(person.getSampleType())){
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
        modelAndView.setViewName("center/caseInformation/editCaseInformation");
        return modelAndView;
    }

    @RequestMapping(value = "/delPerson.html")
    @ResponseBody
    public Map<String, Object> delPerson(HttpServletRequest request, Integer personId) {
        Map<String, Object> result = new HashMap<>();
        try {
            limsPersonSampleService.deleteById(personId);
            result.put("success", true);
        } catch (Exception ex) {
            result.put("success", false);
        }
        return result;
    }

    @RequestMapping(value = "/updateCase.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addCase(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                       @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInformationService.updateCaseReg(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/updatePersonSample.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addPersonSample(HttpServletRequest request, HttpServletResponse response, String operateType,
                                               @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsPersonSampleService.updatePersonSample(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        return map;
    }

    @RequestMapping("/missingList.html")
    public ModelAndView misList(HttpServletRequest request, LimsCaseInformation limsCaseInformation, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();
        limsCaseInformation = resetCaseInfoQuery(limsCaseInformation);
        limsCaseInformation.setEntrustmentType("02");
        List<LimsCaseInformation> limsCaseInformationList = limsCaseInformationService.selectPaginationList(limsCaseInformation, pageInfo);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        int totalCnt = limsCaseInformationService.selectCount(limsCaseInformation);

        modelAndView.addObject("limsCaseInformationList", limsCaseInformationList);
        modelAndView.addObject("limsCaseInformation", limsCaseInformation);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/caseInformation/missingList");
        return modelAndView;
    }

    @RequestMapping("/missingExtractionList.html")
    public ModelAndView missingExtractionList(HttpServletRequest request, LimsCaseInformation limsCaseInformation, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();
        limsCaseInformation = resetCaseInfoQuery(limsCaseInformation);
        limsCaseInformation.setEntrustmentType("02");
        List<LimsCaseInformation> limsCaseInformationList = limsCaseInformationService.selectPaginationList(limsCaseInformation, pageInfo);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        int totalCnt = limsCaseInformationService.selectCount(limsCaseInformation);
        for (LimsCaseInformation CaseInformation : limsCaseInformationList) {
            CaseInformation.setExtractionState(limsExtractRecordService.selectCountByCaseId(CaseInformation.getId()));
        }

        modelAndView.addObject("limsCaseInformationList", limsCaseInformationList);
        modelAndView.addObject("limsCaseInformation", limsCaseInformation);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/caseInformation/missingExtractionList");
        return modelAndView;
    }

    @RequestMapping("/addMissingExtraction.html")
    public ModelAndView addMissingExtraction(HttpServletRequest request, Integer caseInformationId) {
        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        LimsExtractRecord limsExtractRecord = new LimsExtractRecord();
        limsExtractRecord.setCaseId(caseInformationId);
        LabUser labUser1 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_2);
        limsExtractRecord.setExtractPersonId1(labUser1.getId());
        limsExtractRecord.setExtractPersonName1(labUser1.getUserName());

        LabUser labUser2 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_1);
        limsExtractRecord.setExtractPersonId2(labUser2.getId());
        limsExtractRecord.setExtractPersonName2(labUser2.getUserName());

        limsExtractRecord.setExtractDatetime(DateUtils.getCurrentDate());

        List<LimsPersonSample> limsPersonSampleList = limsPersonSampleService.selectListByCaseInformationId(caseInformationId);
        List<LimsExtractRecordSample> limsExtractRecordSampleList = new ArrayList<>();
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        LimsExtractRecordSample extractRecordSample = null;
        for (LimsPersonSample sample : limsPersonSampleList) {
            extractRecordSample = new LimsExtractRecordSample();
            extractRecordSample.setSampleId(sample.getId());
            extractRecordSample.setSampleName(sample.getSampleName());
            for (DictItem sampleType : sampleTypeList) {
                if (sample.getSampleType().equals(sampleType.getDictCode())) {
                    extractRecordSample.setSampleTypeName(sampleType.getDictName());
                }
            }
            extractRecordSample.setSampleNo(sample.getSampleLaboratoryNo());
            extractRecordSample.setExtractHb("");
            extractRecordSample.setExtractPsa("");
            extractRecordSample.setExtractMethod(ExamineHelper.getExtractMethodBySampleType(sample.getSampleType()));

            extractRecordSample.setExtractYuFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractZhenFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractJiaoFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractLiFlag(Constants.FLAG_TRUE);
            limsExtractRecordSampleList.add(extractRecordSample);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsExtractRecordSampleList", limsExtractRecordSampleList);
        modelAndView.setViewName("center/caseInformation/missingExtractInfo");
        return modelAndView;
    }

    public LimsCaseInformation resetCaseInfoQuery(LimsCaseInformation limsCaseInformation) {
        if (StringUtils.isBlank(limsCaseInformation.getCaseNo())) {
            limsCaseInformation.setCaseNo(null);
        } else {
            limsCaseInformation.setCaseNo(limsCaseInformation.getCaseNo());
        }
        if (StringUtils.isBlank(limsCaseInformation.getCaseName())) {
            limsCaseInformation.setCaseName(null);
        } else {
            limsCaseInformation.setCaseName(limsCaseInformation.getCaseName());
        }

        if (StringUtils.isBlank(limsCaseInformation.getEntrustCompanyCode())) {
            limsCaseInformation.setEntrustCompanyCode(null);
        } else {
            limsCaseInformation.setEntrustCompanyCode(limsCaseInformation.getEntrustCompanyCode());
        }

        if (StringUtils.isBlank(limsCaseInformation.getEntrustingSerial())) {
            limsCaseInformation.setEntrustingSerial(null);
        } else {
            limsCaseInformation.setEntrustingSerial(limsCaseInformation.getEntrustingSerial());
        }
        if (limsCaseInformation.getEntrustmentDateStart() == null) {
            limsCaseInformation.setEntrustmentDateStart(null);
        } else {
            limsCaseInformation.setEntrustmentDateStart(limsCaseInformation.getEntrustmentDateStart());
        }
        if (limsCaseInformation.getEntrustmentDateEnd() == null) {
            limsCaseInformation.setEntrustmentDateEnd(null);
        } else {
            limsCaseInformation.setEntrustmentDateEnd(limsCaseInformation.getEntrustmentDateEnd());
        }
        return limsCaseInformation;
    }

}
