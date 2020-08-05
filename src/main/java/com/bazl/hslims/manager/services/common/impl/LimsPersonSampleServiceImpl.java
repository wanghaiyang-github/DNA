package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.dao.*;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.services.common.LimsCaseInfoService;
import com.bazl.hslims.manager.services.common.LimsPersonSampleService;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliu on 2018/5/13.
 */
@Service
public class LimsPersonSampleServiceImpl implements LimsPersonSampleService {

    @Autowired
    LimsPersonSampleDao limsPersonSampleDao;
    @Autowired
    DictItemDao dictItemDao;
    @Autowired
    LimsEntrustmentInformationDao limsEntrustmentInformationDao;
    @Autowired
    LimsSampleInfoDao limsSampleInfoDao;
    @Autowired
    LimsPersonInfoDao limsPersonInfoDao;
    @Autowired
    LimsConsignmentDao limsConsignmentDao;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    SeqNoGenerateService seqNoGenerateService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int insert(LimsPersonSample limsPersonSample) {
        return limsPersonSampleDao.insert(limsPersonSample);
    }

    @Override
    public boolean deleteById(Integer personId) {

        try {
            limsPersonSampleDao.deleteById(personId);
        } catch (Exception e) {
            logger.error("删除人员检材失败！", e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map identifyPersonReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        List<DictItem> dictPersonTypeList = dictItemDao.selectListByDictType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> dictPersonRelationList = dictItemDao.selectListByDictType(Constants.DICT_TPYE_PERSON_RELATION);
        List<DictItem> dictSampleTypeList = dictItemDao.selectListByDictType(Constants.DICT_TPYE_SAMPLE_TYPE);

        List<LimsPersonSample> personSampleList = consignmentDataModel.getPersonSampleList();
        /*LimsPersonSample personSample = new ;*/

        if (personSampleList.size() > 0) {
            /*for (int i = 0; i < personSampleList.size(); i++) {*/
            for(LimsPersonSample personSample:personSampleList){
                //personSample = personSampleList.get(i);
                personSample.setDeleteFlag("0");
                personSample.setAcceptanceType("0");
                LimsConsignment limsConsignment = limsConsignmentDao.selectById(personSample.getEntrustmentId());
                for (DictItem dictPersonRelation : dictPersonRelationList) {
                    if (personSample.getOneselfContact().equals(dictPersonRelation.getDictName())) {
                        personSample.setOneselfContact(dictPersonRelation.getDictCode());
                    }
                }
                for (DictItem dictPersonType : dictPersonTypeList) {
                    if (personSample.getPersonType().equals(dictPersonType.getDictName())) {
                        personSample.setPersonType(dictPersonType.getDictCode());
                    }
                }
                for (DictItem dictSampleType : dictSampleTypeList) {
                    if (personSample.getSampleType().equals(dictSampleType.getDictName())) {
                        personSample.setSampleType(dictSampleType.getDictCode());
                    }
                }
                LimsPersonInfo personInfo = new LimsPersonInfo();
                if (personSample != null) {
                    personInfo.setCaseId(limsConsignment.getCaseId());
                    personInfo.setConsignmentId(limsConsignment.getId());
                    personInfo.setPersonType(personSample.getPersonType());
                    personInfo.setPersonName(personSample.getPersonName());
                    personInfo.setDeleteFlag("0");

                    personInfo.setPersonGender(personSample.getPersonGender());
                    personInfo.setPersonGenderName(personSample.getPersonGenderName());
                    personInfo.setPersonRace(personSample.getPersonRace());
                    personInfo.setPersonIdcardNo(personSample.getPersonIdcardNo());
                    personInfo.setNoIdcardRemark(personSample.getNoIdcardRemark());

                    personInfo.setCreatePerson(limsConsignment.getDelegateOrgDesc());
                    personInfo.setRelativeIdentify(personSample.getOneselfContact());
                    try {
                        personInfo.setCreateDatetime(new Date());
                        limsPersonInfoDao.insert(personInfo);
                    } catch (Exception e) {
                        logger.error("添加人员信息失败！", e);
                        throw e;
                    }
                    LimsSampleInfo sampleInfo = new LimsSampleInfo();
                    sampleInfo.setCaseId(limsConsignment.getCaseId());
                    sampleInfo.setConsignmentId(limsConsignment.getId());
                    LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsConsignment.getCaseId());
                    sampleInfo.setSampleNo(seqNoGenerateService.getNextPersonNoVal(caseInfo.getCaseNo()));
                    sampleInfo.setSampleType(personSample.getSampleType());
                    sampleInfo.setSampleName(personSample.getSampleName());
                    sampleInfo.setSampleFlag(Constants.SAMPLE_FLAG_PERSON);
                    sampleInfo.setRefPersonId(personInfo.getId());
                    sampleInfo.setRefPersonName(personSample.getPersonName());
                    sampleInfo.setCreatePerson(limsConsignment.getDelegateOrgDesc());
                    sampleInfo.setAcceptStatus(Constants.SAMPLE_FLAG_PERSON);
                    try {
                        sampleInfo.setCreateDatetime(new Date());
                        limsSampleInfoDao.insert(sampleInfo);
                    } catch (Exception e) {
                        logger.error("添加人员检材失败！", e);
                        throw e;
                    }
                }
            }
        }
        dataMap.put("operateType", operateType);
        return dataMap;
    }

    @Override
    public Map updatePersonSample(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        LimsPersonSample personSample = consignmentDataModel.getPersonSample();
        personSample.setDeleteFlag("0");
        personSample.setAcceptanceType("0");
        LimsConsignment limsConsignment = limsConsignmentDao.selectById(personSample.getEntrustmentId());
        LimsPersonInfo personInfo = new LimsPersonInfo();
        personInfo.setId(personSample.getId());
        personInfo.setCaseId(limsConsignment.getCaseId());
        personInfo.setConsignmentId(limsConsignment.getId());
        personInfo.setPersonType(personSample.getPersonType());
        personInfo.setPersonName(personSample.getPersonName());
        personInfo.setDeleteFlag("0");
        personInfo.setCreateDatetime(personSample.getCreateDatetime());
        personInfo.setCreatePerson(limsConsignment.getDelegateOrgDesc());
        personInfo.setRelativeIdentify(personSample.getOneselfContact());
        try {
            limsPersonInfoDao.update(personInfo);
        } catch (Exception e) {
            logger.error("更新人员信息失败！", e);
            throw e;
        }
        LimsSampleInfo sampleInfo = new LimsSampleInfo();
        sampleInfo.setId(personSample.getSampleId());
        sampleInfo.setCaseId(limsConsignment.getCaseId());
        sampleInfo.setConsignmentId(limsConsignment.getId());
        sampleInfo.setSampleNo(personSample.getSampleLaboratoryNo());
        sampleInfo.setSampleType(personSample.getSampleType());
        sampleInfo.setSampleName(personSample.getSampleName());
        sampleInfo.setSampleFlag(Constants.SAMPLE_FLAG_PERSON);
        sampleInfo.setRefPersonId(personInfo.getId());
        sampleInfo.setRefPersonName(personSample.getPersonName());
        sampleInfo.setCreatePerson(limsConsignment.getDelegateOrgDesc());
        sampleInfo.setDeleteFlag("0");
        try {
            sampleInfo.setCreateDatetime(personSample.getCreateDatetime());
            limsSampleInfoDao.update(sampleInfo);
        } catch (Exception e) {
            logger.error("更新检材信息失败！", e);
            throw e;
        }

        dataMap.put("operateType", operateType);
        return dataMap;
    }

    @Override
    public List<LimsPersonSample> selectListByEntrustmentId(Integer entrustmentId) {
        return limsPersonSampleDao.selectListByEntrustmentId(entrustmentId);
    }

    @Override
    public List<LimsPersonSample> selectListByCaseInformationId(Integer caseInformationId) {
        return limsPersonSampleDao.selectListByCaseInformationId(caseInformationId);
    }

    @Override
    public List<LimsPersonSample> selectListBySampleLaboratoryNo(String sampleLaboratoryNo) {
        return limsPersonSampleDao.selectListBySampleLaboratoryNo(sampleLaboratoryNo);
    }


}
