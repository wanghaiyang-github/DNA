package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import com.bazl.hslims.web.security.LimsSecurityUtils;
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
public class UploadCountryDBController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    DictService dictService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    QueueSampleService queueSampleService;
    @Autowired
    QueueDetailService queueDetailService;

    @RequestMapping("/uploadCountryDB.html")
    public ModelAndView uploadCountry(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo,Integer caseId) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        if (StringUtils.isBlank(query.getEntity().getCaseName())){
            query.getEntity().setCaseName(null);
        }
        if (StringUtils.isBlank(query.getEntity().getCaseXkNo())){
            query.getEntity().setCaseXkNo(null);
        }
        if (StringUtils.isBlank(query.getEntity().getEntrustmentType())){
            query.getEntity().setEntrustmentType(null);
        }else{
            query.getEntity().setEntrustmentType(query.getEntity().getEntrustmentType());
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
        }else {
            oldTime = sdf.format(query.getAcceptDateEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            query.setAcceptDateEnd(newTime);
        }

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(caseId);

        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED,Constants.CASE_INFO_STATUS_FINISHED));
//        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setConsignmentStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        if(limsCaseInfo!=null){
            query.getEntity().setCaseNo(limsCaseInfo.getCaseNo());
        }
        /*List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);*/
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOAllList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("limsCaseInfo",limsCaseInfo);
        modelAndView.setViewName("center/result/uploadCountryDB");
        return modelAndView;
    }

    @RequestMapping("/upload.html")
    public ModelAndView upload(HttpServletRequest request, Integer consignmentId) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView = initDictList(modelAndView);

        LimsCaseInfo caseInfo = new LimsCaseInfo();
        caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);

        QueueDetail queueDetail;
        if (ListUtils.isNotNullAndEmptyList(sampleInfoList)){
            for (LimsSampleInfo sampleInfo:sampleInfoList){
                if (sampleInfo.getId() != null){
                    List<QueueDetail> queueDetailList = queueDetailService.selectListBySampleId(sampleInfo.getId());
                    if (ListUtils.isNullOrEmptyList(queueDetailList)) {
                        sampleInfo.setUploadStatus(0);
                    }else {
                        queueDetail = queueDetailList.get(0);
                        if (queueDetail.getStatus().equals(Constants.FLAG_TRUE)) {
                            sampleInfo.setUploadStatus(1);
                        }else {
                            sampleInfo.setUploadStatus(0);
                        }
                    }
                }
            }
        }

        //只显示已受理的检材
        for(Iterator<LimsSampleInfo> it = sampleInfoList.iterator(); it.hasNext();){
            LimsSampleInfo si = it.next();
            if(!Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED.equals(si.getAcceptStatus())){
                it.remove();
            }
        }

        //如果补送时，加载该案件其他委托中已受理的人员和检材列表
        if(Constants.FLAG_TRUE.equals(consignment.getAdditionalFlag())){
            List<LimsPersonInfo> acceptedPersonInfoList = limsPersonInfoService.selectListByCaseId(caseInfo.getId());
            List<LimsSampleInfo> acceptedSampleInfoList = limsSampleInfoService.selectAcceptedListByCaseId(caseInfo.getId());

            if (ListUtils.isNotNullAndEmptyList(acceptedSampleInfoList)){
                for (LimsSampleInfo sampleInfo:acceptedSampleInfoList){
                    if (sampleInfo.getId() != null){
                        List<QueueDetail> queueDetailList = queueDetailService.selectListBySampleId(sampleInfo.getId());
                        if (ListUtils.isNotNullAndEmptyList(queueDetailList)) {
                            queueDetail = queueDetailList.get(0);
                            if (queueDetail.getStatus().equals(Constants.FLAG_TRUE)) {
                                sampleInfo.setUploadStatus(1);
                            }else {
                                sampleInfo.setUploadStatus(0);
                            }
                        }else {
                            sampleInfo.setUploadStatus(0);
                        }
                    }
                }
            }
            //去掉本次委托的人员、检材
            LimsPersonInfo tmpPerson = null;
            for(Iterator<LimsPersonInfo> personInfoIterator = acceptedPersonInfoList.iterator(); personInfoIterator.hasNext();){
                tmpPerson = personInfoIterator.next();
                if(tmpPerson.getConsignmentId().equals(consignmentId)){
                    personInfoIterator.remove();
                }
            }

            LimsSampleInfo tmpSample = null;
            for(Iterator<LimsSampleInfo> sampleInfoIterator = acceptedSampleInfoList.iterator(); sampleInfoIterator.hasNext();){
                tmpSample = sampleInfoIterator.next();
                if(tmpSample.getConsignmentId().equals(consignmentId)){
                    sampleInfoIterator.remove();
                }
            }

            modelAndView.addObject("otherPersonInfoList", acceptedPersonInfoList);
            modelAndView.addObject("otherSampleInfoList", acceptedSampleInfoList);
        }

        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.setViewName("center/result/uploadDataBase");
        return modelAndView;

    }

    @RequestMapping(value = "/uploadDataBase.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> querySample(HttpServletRequest request, @RequestBody ConsignmentDataModel consignmentDataModel) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();
            LimsConsignment consignment = consignmentDataModel.getConsignment();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();
            if (caseInfo != null && ListUtils.isNotNullAndEmptyList(sampleInfoList)) {
                QueueSample queueSample = new QueueSample();
                queueSample.setCaseId(caseInfo.getId());
                queueSample.setConsignmentId(consignment.getId());
                queueSample.setQueueType(Constants.TO_NATION_15);
                queueSample.setStatus(Constants.FLAG_FALSE);
                queueSample.setOperatePerson(LimsSecurityUtils.getLoginName());

                queueSampleService.insert(queueSample);
                for (LimsSampleInfo sampleInfo : sampleInfoList) {
                   QueueDetail queueDetail = new QueueDetail();

                    queueDetail.setQueueId(queueSample.getId());
                    queueDetail.setCaseId(sampleInfo.getCaseId());
                    queueDetail.setSampleId(String.valueOf(sampleInfo.getId()));
                    queueDetail.setStatus(Constants.FLAG_FALSE);
                    queueDetail.setCreatePerson(LimsSecurityUtils.getLoginName());

                    queueDetailService.insert(queueDetail);
                }

               /* // 现堪反馈已鉴定状态
                QueueSample queueSample1 = new QueueSample();
                //已鉴定
                queueSample1.setStatus(Constants.CASE_INFO_IDENTIFIED);
                queueSample1.setCaseId(caseInfo.getId());
                queueSample1.setConsignmentId(consignment.getId());
                queueSample1.setCreateDatetime(new Date());
                queueSample1.setOperatePerson(consignment.getAcceptor());
                queueSampleService.insert(queueSample1);*/

                map.put("success", true);
            }else {
                map.put("success", false);
            }

        }catch (Exception e){
            map.put("success", false);
        }
        return map;
    }

    private ModelAndView initDictList(ModelAndView modelAndView){
        /*  字典 */
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DictItem> caseLevelList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_LEVEL);
        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personRelationList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_RELATION);
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<DictItem> personGenderList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);
        /*  委托单位 */
        List<DelegateOrg> delegateOrgList = new ArrayList<DelegateOrg>();
        delegateOrgList = delegateOrgService.selectAll();
        List<DictItem> samplePropertiesList = dictService.selectDictItemListByType(Constants.DICT_TYPE_SAMPLE_PROPERTIES);

        modelAndView.addObject("samplePropertiesList", samplePropertiesList);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseLevelList", caseLevelList);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("personRelationList", personRelationList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("personGenderList", personGenderList);

        return modelAndView;
    }

}
