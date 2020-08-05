package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.*;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.LimsCaseInfoService;
import com.bazl.hslims.manager.services.common.QueueDetailService;
import com.bazl.hslims.manager.services.common.QueueSampleService;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.LocalBeanUtils;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2017/1/4.
 */
@Service
@Transactional
public class LimsCaseInfoServiceImpl implements LimsCaseInfoService {

    @Autowired
    LimsCaseInfoDao limsCaseInfoDao;
    @Autowired
    LimsConsignmentDao limsConsignmentDao;
    @Autowired
    LimsPersonInfoDao limsPersonInfoDao;
    @Autowired
    LimsSampleInfoDao limsSampleInfoDao;
    @Autowired
    LimsIdentifyBookDao limsIdentifyBookDao;
    @Autowired
    SyncDna36QueueDao syncDna36QueueDao;
    @Autowired
    SeqNoGenerateService seqNoGenerateService;
    @Autowired
    DelegateOrgDao delegateOrgDao;
    @Autowired
    SeqNoGenerateDao seqNoGenerateDao;
    @Autowired
    QueueSampleService queueSampleService;
    @Autowired
    QueueDetailService queueDetailService;


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public LimsCaseInfo selectById(Integer caseId) {
        return limsCaseInfoDao.selectById(caseId);
    }

    public LimsCaseInfo selectByConsignmentId(Integer consignmentId) {
        return limsCaseInfoDao.selectByConsignmentId(consignmentId);
    }

    public LimsCaseInfo selectListByConsignmentId(Integer consignmentId) {
        return limsCaseInfoDao.selectListByConsignmentId(consignmentId);
    }

    public LimsCaseInfo selectListByCaseNo(String caseNo) {
        return limsCaseInfoDao.selectListByCaseNo(caseNo);
    }

    @Override
    public List<LimsCaseInfo> selectListByCaseXkNo(String caseXkNo) {
        return limsCaseInfoDao.selectListByCaseXkNo(caseXkNo);
    }

    @Override
    public List<LimsCaseInfo> selectNotConsignmentNoList(LimsCaseInfo limsCaseInfo) {
        return limsCaseInfoDao.selectNotConsignmentNoList(limsCaseInfo);
    }

    @Override
    public int updateCaseXkAno(LimsCaseInfo limsCaseInfo) {
        return limsCaseInfoDao.updateCaseXkAno(limsCaseInfo);
    }

    /**
     * 删除委托
     * 及关联人员、样本
     * 如果当前委托不是补送， 则删除对应的案件信息
     *
     * @param consignmentId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteByConsignmentId(Integer consignmentId) throws Exception {
        LimsConsignment consignment = this.limsConsignmentDao.selectById(consignmentId);

        try {
            limsPersonInfoDao.deleteByConsignmentId(consignmentId);

            limsSampleInfoDao.deleteByConsignmentId(consignmentId);

            limsConsignmentDao.deleteById(consignmentId);

            //不是补送委托时，删除案件
            if (StringUtils.isNotBlank(consignment.getAdditionalFlag())
                    && Constants.FLAG_FALSE.equals(consignment.getAdditionalFlag())) {
                limsCaseInfoDao.deleteByConsignmentId(consignment.getId());
            }
        } catch (Exception e) {
            logger.info("--------删除委托id为[" + consignment.getId() + "]，登记单位为[" + consignment.getCreatePerson() + "]的信息失败！----");
            logger.error("删除委托信息失败！", e);
            throw e;
        }

        return true;
    }

    /**
     * 新增委托
     *
     * @param consignmentDataModel
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> identifyCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        try {
            LimsConsignment consignment = consignmentDataModel.getConsignment();
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();
            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            LimsCaseInfo caseInfoInDb = null;
            LimsConsignment consignmentInDb = null;
            if (operateType.equals(Constants.OPERATE_TYPE_EDIT)) {
                caseInfoInDb = this.limsCaseInfoDao.selectById(caseInfo.getId());
                if (!caseInfoInDb.getCaseStatus().equals(Constants.CASE_INFO_STATUS_PENDING)
                        && !caseInfoInDb.getCaseStatus().equals(Constants.CASE_INFO_STATUS_REFUSED)) {
                    logger.info("案件已经受理，不可以再次修改！ [caseId=" + caseInfo.getId() + "]");

                    dataMap.put("operateType", operateType);
                    dataMap.put("caseId", caseInfo.getId());
                    dataMap.put("consignmentId", consignment.getId());
                }

                consignmentInDb = this.limsConsignmentDao.selectById(consignment.getId());
            }

            //该委托为非补送时，插入案件信息
            if (caseInfoInDb == null) {
                caseInfo.setCaseStatus(Constants.CASE_INFO_STATUS_PENDING);
                caseInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                caseInfo.setInstoredFlag(Constants.FLAG_FALSE);
                try {
                    limsCaseInfoDao.insert(caseInfo);
                } catch (Exception e) {
                    logger.info("--------插入案件id为[" + caseInfo.getId() + "]，登记单位为[" + caseInfo.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入案件信息失败！", e);
                    throw e;
                }
            } else {
                LocalBeanUtils.copyPropertiesIgnoreNull(caseInfo, caseInfoInDb);

                try {
                    limsCaseInfoDao.update(caseInfoInDb);
                } catch (Exception e) {
                    logger.info("----------更新案件id为[" + caseInfoInDb.getId() + "]，登记单位为[" + caseInfoInDb.getCreatePerson() + "]的信息失败！----");
                    logger.error("更新案件信息失败！", e);
                    throw e;
                }
            }

            if (consignmentInDb == null) {
                consignment.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
                if (StringUtils.isBlank(consignment.getDelegateOrgDesc())) {
                    consignment.setDelegateOrgDesc(LimsSecurityUtils.getLoginDelegateOrg().getOrgName());
                }
                /*consignment.setDelegateOrgPhone(LimsSecurityUtils.getLoginDelegateOrg().getContactPhonenum());*/
                if (null == consignment.getConsignmentNo()) {
                    consignment.setConsignmentNo(seqNoGenerateService.getNextConsignmentNoVal(Constants.CONSIGNMENT_NO_RULE + consignment.getDelegateOrg() + DateUtils.getCurrentYear()));
                }

                consignment.setCaseId(caseInfo.getId());
                consignment.setDelegateDatetime(new Date());
                consignment.setInstoredFlag(Constants.FLAG_FALSE);
                consignment.setCreatePerson(LimsSecurityUtils.getLoginName());
                consignment.setStatus(Constants.CASE_INFO_STATUS_PENDING);
                try {
                    limsConsignmentDao.insert(consignment);
                    dataMap.put("consignmentStatus", consignment.getStatus());
                } catch (Exception e) {
                    logger.info("---------插入委托id为[" + consignment.getId() + "]，登记单位为[" + consignment.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入委托信息失败！", e);
                    throw e;
                }
            } else {
                LocalBeanUtils.copyPropertiesIgnoreNull(consignment, consignmentInDb);

                try {
                    limsConsignmentDao.update(consignmentInDb);
                    dataMap.put("consignmentStatus", consignmentInDb.getStatus());
                } catch (Exception e) {
                    logger.info("--------更新委托id为[" + consignmentInDb.getId() + "]，登记单位为[" + consignmentInDb.getCreatePerson() + "]的信息失败！----");
                    logger.error("更新委托信息失败！", e);
                    throw e;
                }
            }

            dataMap.put("operateType", operateType);
            dataMap.put("caseId", caseInfo.getId());
            dataMap.put("consignmentId", consignment.getId());

            //添加人员信息
            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(caseInfo.getId());
                personInfo.setConsignmentId(consignment.getId());
                personInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                personInfo.setInstoredFlag(Constants.FLAG_FALSE);
                try {
                    if (personInfo.getId() == null) {
                        limsPersonInfoDao.insert(personInfo);
                    }
                } catch (Exception e) {
                    logger.info("--------插入人员id为[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入人员信息失败！", e);
                    throw e;
                }
            }
            //添加检材信息
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                sampleInfo.setCaseId(caseInfo.getId());
                sampleInfo.setConsignmentId(consignment.getId());
                sampleInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_PENDING);
                sampleInfo.setInstoredFlag(Constants.FLAG_FALSE);
                sampleInfo.setDeleteFlag(Constants.FLAG_FALSE);
                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                }
                try {
                    if (sampleInfo.getId() == null) {
                        limsSampleInfoDao.insert(sampleInfo);
                    } else {
                        limsSampleInfoDao.update(sampleInfo);
                    }
                } catch (Exception e) {
                    logger.info("--------插入样本id为[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入样本信息失败！", e);
                    throw e;
                }
            }

            //委托成功插入队列
            if (operateType.equals(Constants.OPERATE_TYPE_ADD)) {
                try {
                    QueueSample queueSample = new QueueSample();
                    //已委托
                    for (LimsSampleInfo sampleInfo : sampleInfoList) {
                        if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != sampleInfo.getEvidenceNo()) {
                            queueSample.setCaseId(caseInfo.getId());
                            queueSample.setConsignmentId(consignment.getId());
                            queueSample.setCreateDatetime(new Date());
                            queueSample.setStatus("0");
                            queueSample.setQueueType(Constants.CASE_INFO_COMMISSIONED);
                            queueSample.setOperatePerson(consignment.getDelegateOrgDesc());
                            queueSampleService.insert(queueSample);
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("插入委托队列错误！", e);
                }
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return dataMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> identifyPersonReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();

            LimsPersonInfo personInfoInDB = null;
            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(personInfo.getCaseId());
                personInfo.setConsignmentId(personInfo.getConsignmentId());
                personInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                personInfo.setInstoredFlag(Constants.FLAG_FALSE);
                if (personInfo.getId() == null || personInfo.getId() == 0) {
                    try {
                        limsPersonInfoDao.insert(personInfo);
                    } catch (Exception e) {
                        logger.info("--------插入人员id为[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("插入人员信息失败！", e);
                        throw e;
                    }
                } else {
                    personInfoInDB = limsPersonInfoDao.selectById(personInfo.getId());
                    copyPersonInfoBeanPropertiesFromPage(personInfo, personInfoInDB);

                    try {
                        limsPersonInfoDao.update(personInfoInDB);
                    } catch (Exception e) {
                        logger.info("---------更新人员id[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！");
                        logger.error("更新人员信息失败！", e);
                        throw e;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return dataMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> identifySampleReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        try {

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            LimsSampleInfo sampleInfoInDB = null;
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                sampleInfo.setCaseId(sampleInfo.getCaseId());
                sampleInfo.setConsignmentId(sampleInfo.getConsignmentId());
                sampleInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_PENDING);
                sampleInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                }

                if (sampleInfo.getId() == null || sampleInfo.getId() == 0) {
                    try {
                        limsSampleInfoDao.insert(sampleInfo);
                    } catch (Exception e) {
                        logger.info("--------插入样本id为[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("插入样本信息失败！", e);
                        throw e;
                    }
                } else {
                    sampleInfoInDB = limsSampleInfoDao.selectById(sampleInfo.getId());
                    copySampleInfoBeanPropertiesFromPage(sampleInfo, sampleInfoInDB);

                    try {
                        limsSampleInfoDao.update(sampleInfoInDB);
                    } catch (Exception e) {
                        logger.info("--------更新样本id[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("更新样本信息失败！", e);
                        throw e;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return dataMap;
    }


    /**
     * 新增委托
     *
     * @param consignmentDataModel
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer identifyReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        LimsConsignment consignment = consignmentDataModel.getConsignment();
        try {
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();

            LimsCaseInfo caseInfoInDb = null;
            LimsConsignment consignmentInDb = null;
            if (operateType.equals(Constants.OPERATE_TYPE_EDIT)) {
                caseInfoInDb = this.limsCaseInfoDao.selectById(caseInfo.getId());
                if (!caseInfoInDb.getCaseStatus().equals(Constants.CASE_INFO_STATUS_PENDING)
                        && !caseInfoInDb.getCaseStatus().equals(Constants.CASE_INFO_STATUS_REFUSED)) {
                    logger.info("案件已经受理，不可以再次修改！ [caseId=" + caseInfo.getId() + "]");
                    return 0;
                }

                consignmentInDb = this.limsConsignmentDao.selectById(consignment.getId());
            }

            //该委托为非补送时，插入案件信息
            if (caseInfoInDb == null) {
                caseInfo.setCaseStatus(Constants.CASE_INFO_STATUS_PENDING);
                caseInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                caseInfo.setInstoredFlag(Constants.FLAG_FALSE);
                try {
                    limsCaseInfoDao.insert(caseInfo);
                } catch (Exception e) {
                    logger.info("---------插入案件id为[" + caseInfo.getId() + "]，登记单位为[" + caseInfo.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入案件信息失败！", e);
                    throw e;
                }
            } else {
                LocalBeanUtils.copyPropertiesIgnoreNull(caseInfo, caseInfoInDb);
                try {
                    limsCaseInfoDao.update(caseInfoInDb);
                } catch (Exception e) {
                    logger.info("-----------更新案件id为[" + caseInfoInDb.getId() + "]，登记单位为[" + caseInfoInDb.getCreatePerson() + "]的信息失败！----");
                    logger.error("更新案件信息失败！", e);
                    throw e;
                }
            }

            if (consignmentInDb == null) {
                consignment.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
                consignment.setDelegateOrgDesc(LimsSecurityUtils.getLoginDelegateOrg().getOrgName());
                consignment.setDelegateOrgPhone(LimsSecurityUtils.getLoginDelegateOrg().getContactPhonenum());
                consignment.setCaseId(caseInfo.getId());
                consignment.setDelegateDatetime(new Date());
                consignment.setInstoredFlag(Constants.FLAG_FALSE);
                consignment.setCreatePerson(LimsSecurityUtils.getLoginName());
                consignment.setStatus(Constants.CASE_INFO_STATUS_PENDING);
                if (StringUtils.isNotBlank(caseInfo.getCaseXkNo())
                        && !"无".equals(caseInfo.getCaseXkNo())) {
                    consignment.setConsignmentNo("WT" + LimsSecurityUtils.getLoginDelegateOrg().getOrgCode() + DateUtils.dateToString(new Date(), "yyyyMMdd") + caseInfo.getId());
                }

                try {
                    limsConsignmentDao.insert(consignment);
                } catch (Exception e) {
                    logger.info("-----------插入委托id为[" + consignment.getId() + "]，登记单位为[" + consignment.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入委托信息失败！", e);
                    throw e;
                }
            } else {
                LocalBeanUtils.copyPropertiesIgnoreNull(consignment, consignmentInDb);
                try {
                    limsConsignmentDao.update(consignmentInDb);
                } catch (Exception e) {
                    logger.info("-----------更新委托id为[" + consignmentInDb.getId() + "]，登记单位为[" + consignmentInDb.getCreatePerson() + "]的信息失败！----");
                    logger.error("更新委托信息失败！", e);
                    throw e;
                }
            }

            limsPersonInfoDao.deleteByConsignmentId(consignment.getId());
            limsSampleInfoDao.deleteByConsignmentId(consignment.getId());

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(caseInfo.getId());
                personInfo.setConsignmentId(consignment.getId());
                personInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                personInfo.setInstoredFlag(Constants.FLAG_FALSE);
                try {
                    limsPersonInfoDao.insert(personInfo);
                } catch (Exception e) {
                    logger.info("-----------插入人员id为[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入人员信息失败！", e);
                    throw e;
                }
            }

            LimsSampleInfo sampleInfo = null;
            for (int i = sampleInfoList.size() - 1; i >= 0; i--) {
                sampleInfo = sampleInfoList.get(i);
                sampleInfo.setCaseId(caseInfo.getId());
                sampleInfo.setConsignmentId(consignment.getId());
                sampleInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_PENDING);
                sampleInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                }

                try {
                    limsSampleInfoDao.insert(sampleInfo);
                } catch (Exception e) {
                    logger.info("---------插入样本id为[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！---");
                    logger.error("插入样本信息失败！", e);
                    throw e;
                }
            }
        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return consignment.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> identifyPersonRegSupply(Integer newConsignmentId, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        LimsConsignment newConsignment = null;
        try {

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();

            Integer caseId = personInfoList.get(0).getCaseId();
            //新增补送时，id代表主委托ID；修改补送时，id代表补送委托id
            Integer consignmentId = personInfoList.get(0).getConsignmentId();

            //已登记的人员列表，包括已受理的
            List<LimsPersonInfo> savedPersonInfoList = limsPersonInfoDao.selectListByCaseId(caseId);

            if (newConsignmentId == null || newConsignmentId == 0) {
                LimsConsignment mainConsignment = this.limsConsignmentDao.selectById(consignmentId);

                newConsignment = new LimsConsignment();
                LocalBeanUtils.copyPropertiesIgnoreNull(mainConsignment, newConsignment);
                newConsignment.setAdditionalFlag(Constants.FLAG_TRUE);
                newConsignment.setStatus(Constants.CASE_INFO_STATUS_PENDING);
                newConsignment.setId(null);
                try {
                    limsConsignmentDao.insert(newConsignment);
                } catch (Exception e) {
                    logger.info("------插入委托id为[" + newConsignment.getId() + "]，登记单位为[" + newConsignment.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入委托信息失败！", e);
                    throw e;
                }
            }

            if (newConsignmentId == null || newConsignmentId == 0)
                newConsignmentId = newConsignment.getId();

            LimsPersonInfo personInfoInDB = null;
            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(caseId);
                personInfo.setConsignmentId(newConsignmentId);
                personInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                personInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (personInfo.getId() == null || personInfo.getId() == 0) {
                    try {
                        limsPersonInfoDao.insert(personInfo);
                    } catch (Exception e) {
                        logger.info("-------插入人员id为[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("插入人员信息失败！", e);
                        throw e;
                    }
                } else {
                    try {
                        personInfoInDB = limsPersonInfoDao.selectById(personInfo.getId());
                        copyPersonInfoBeanPropertiesFromPage(personInfo, personInfoInDB);

                        limsPersonInfoDao.update(personInfoInDB);
                    } catch (Exception e) {
                        logger.info("------更新人员id[" + personInfoInDB.getId() + "]，登记单位为[" + personInfoInDB.getCreatePerson() + "]的信息失败！---");
                        logger.error("更新人员信息失败！", e);
                        throw e;
                    }
                }

                dataMap.put("personId", personInfo.getId());

                savedPersonInfoList.add(personInfo);
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        dataMap.put("newConsignmentId", newConsignmentId);

        return dataMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> identifyEditPersonRegSupply(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        LimsConsignment newConsignment = null;
        try {

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();

            Integer caseId = personInfoList.get(0).getCaseId();
            //新增补送时，id代表主委托ID；修改补送时，id代表补送委托id
            Integer consignmentId = personInfoList.get(0).getConsignmentId();

            //已登记的人员列表，包括已受理的
            List<LimsPersonInfo> savedPersonInfoList = limsPersonInfoDao.selectListByCaseId(caseId);

            LimsPersonInfo personInfoInDB = null;
            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(caseId);
                personInfo.setConsignmentId(consignmentId);
                personInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                personInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (personInfo.getId() == null || personInfo.getId() == 0) {
                    try {
                        limsPersonInfoDao.insert(personInfo);
                    } catch (Exception e) {
                        logger.info("---------插入人员id为[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！-----");
                        logger.error("插入人员信息失败！", e);
                        throw e;
                    }
                } else {
                    try {
                        personInfoInDB = limsPersonInfoDao.selectById(personInfo.getId());
                        copyPersonInfoBeanPropertiesFromPage(personInfo, personInfoInDB);

                        limsPersonInfoDao.update(personInfoInDB);
                    } catch (Exception e) {
                        logger.info("------更新人员id[" + personInfoInDB.getId() + "]，登记单位为[" + personInfoInDB.getCreatePerson() + "]的信息失败！----");
                        logger.error("更新人员信息失败！", e);
                        throw e;
                    }
                }

                savedPersonInfoList.add(personInfo);
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return dataMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> identifySampleRegSupply(Integer newConsignmentId, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        List<LimsPersonInfo> personInfoList = new ArrayList<>();
        List<LimsSampleInfo> sampleInfoList = new ArrayList<>();
        LimsConsignment newConsignment = null;
        try {

            if (consignmentDataModel.getPersonInfoList() != null) {
                personInfoList = consignmentDataModel.getPersonInfoList();
            }

            if (consignmentDataModel.getSampleInfoList() != null) {
                sampleInfoList = consignmentDataModel.getSampleInfoList();
            }
            Integer caseId = null;
            if (sampleInfoList.size() > 0) {
                caseId = sampleInfoList.get(0).getCaseId();
            }
            Integer consignmentId = null;
            if (sampleInfoList.size() > 0) {
                //新增补送时，id代表主委托ID；修改补送时，id代表补送委托id
                consignmentId = sampleInfoList.get(0).getConsignmentId();
            }

            if (newConsignmentId == null || newConsignmentId == 0) {
                LimsConsignment mainConsignment = this.limsConsignmentDao.selectById(consignmentId);

                newConsignment = new LimsConsignment();
                LocalBeanUtils.copyPropertiesIgnoreNull(mainConsignment, newConsignment);
                newConsignment.setAdditionalFlag(Constants.FLAG_TRUE);
                newConsignment.setStatus(Constants.CASE_INFO_STATUS_PENDING);
                newConsignment.setId(null);
                try {
                    limsConsignmentDao.insert(newConsignment);
                } catch (Exception e) {
                    logger.info("-------插入委托id为[" + newConsignment.getId() + "]，登记单位为[" + newConsignment.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入委托信息失败！", e);
                    throw e;
                }
            }

            if (newConsignmentId == null || newConsignmentId == 0) {
                newConsignmentId = newConsignment.getId();
            }

            LimsSampleInfo sampleInfoInDB = new LimsSampleInfo();
            String oldSampleFlag = null;
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoDao.selectListBySampleId(sampleInfo.getId());
                if (ListUtils.isNotNullAndEmptyList(limsSampleInfoList))
                    oldSampleFlag = limsSampleInfoList.get(0).getSampleFlag();

                sampleInfo.setCaseId(caseId);
                sampleInfo.setConsignmentId(newConsignmentId);
                sampleInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_PENDING);
                sampleInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                }

                if (sampleInfo.getId() == null || sampleInfo.getId() == 0) {
                    try {
                        limsSampleInfoDao.insert(sampleInfo);
                    } catch (Exception e) {
                        logger.info("-----插入样本id为[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("插入样本信息失败！", e);
                        throw e;
                    }
                } else {
                    sampleInfoInDB = limsSampleInfoDao.selectById(sampleInfo.getId());
                    copySampleInfoBeanPropertiesFromPage(sampleInfo, sampleInfoInDB);

                    try {
                        limsSampleInfoDao.update(sampleInfoInDB);
                    } catch (Exception e) {
                        logger.info("-----更新样本id[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！---");
                        logger.error("更新样本信息失败！", e);
                        throw e;
                    }
                }

                dataMap.put("oldSampleFlag", oldSampleFlag);
                dataMap.put("sampleId", sampleInfo.getId());
            }

            //补送委托成功插入队列
            /*try {
                QueueSample queueSample = new QueueSample();
                //已委托
                queueSample.setStatus(Constants.CASE_INFO_COMMISSIONED);
                queueSample.setCaseId(caseId);
                queueSample.setConsignmentId(newConsignment.getId());
                queueSample.setCreateDatetime(new Date());
                queueSample.setOperatePerson(newConsignment.getDelegateOrgDesc());
                queueSampleService.insert(queueSample);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("插入委托队列错误！", e);
            }*/

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        dataMap.put("newConsignmentId", newConsignmentId);

        return dataMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> identifyEditSampleRegSupply(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        LimsConsignment newConsignment = null;
        try {

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            Integer caseId = sampleInfoList.get(0).getCaseId();
            //新增补送时，id代表主委托ID；修改补送时，id代表补送委托id
            Integer consignmentId = sampleInfoList.get(0).getConsignmentId();

            LimsSampleInfo sampleInfoInDB = null;
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                sampleInfo.setCaseId(caseId);
                sampleInfo.setConsignmentId(consignmentId);
                sampleInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_PENDING);
                sampleInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                }

                if (sampleInfo.getId() == null || sampleInfo.getId() == 0) {
                    try {
                        limsSampleInfoDao.insert(sampleInfo);
                    } catch (Exception e) {
                        logger.info("------插入样本id为[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("插入样本信息失败！", e);
                        throw e;
                    }
                } else {
                    sampleInfoInDB = limsSampleInfoDao.selectById(sampleInfo.getId());
                    copySampleInfoBeanPropertiesFromPage(sampleInfo, sampleInfoInDB);

                    try {
                        limsSampleInfoDao.update(sampleInfoInDB);
                    } catch (Exception e) {
                        logger.info("-----更新样本id[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("更新样本信息失败！", e);
                        throw e;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return dataMap;
    }

    /**
     * 补送委托
     *
     * @param consignmentDataModel
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Integer identifyRegSupply(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        LimsConsignment newConsignment = null;
        try {
            Integer caseId = consignmentDataModel.getCaseInfo().getId();
            //新增补送时，id代表主委托ID；修改补送时，id代表补送委托id
            Integer consignmentId = consignmentDataModel.getConsignment().getId();

            if (operateType.equals(Constants.OPERATE_TYPE_EDIT)) {
                newConsignment = this.limsConsignmentDao.selectById(consignmentId);
            } else {
                LimsConsignment mainConsignment = this.limsConsignmentDao.selectById(consignmentId);

                newConsignment = new LimsConsignment();
                LocalBeanUtils.copyPropertiesIgnoreNull(mainConsignment, newConsignment);
                newConsignment.setAdditionalFlag(Constants.FLAG_TRUE);
                newConsignment.setStatus(Constants.CASE_INFO_STATUS_PENDING);
                newConsignment.setId(null);
                try {
                    limsConsignmentDao.insert(newConsignment);
                } catch (Exception e) {
                    logger.info("----------插入委托id为[" + newConsignment.getId() + "]，登记单位为[" + newConsignment.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入委托信息失败！", e);
                    throw e;
                }
            }

            limsPersonInfoDao.deleteByConsignmentId(newConsignment.getId());
            limsSampleInfoDao.deleteByConsignmentId(newConsignment.getId());

            //已登记的人员列表，包括已受理的
            List<LimsPersonInfo> savedPersonInfoList = limsPersonInfoDao.selectListByCaseId(caseId);

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(caseId);
                personInfo.setConsignmentId(newConsignment.getId());
                personInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                personInfo.setInstoredFlag(Constants.FLAG_FALSE);

                try {
                    limsPersonInfoDao.insert(personInfo);
                } catch (Exception e) {
                    logger.info("----------插入人员id为[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入人员信息失败！", e);
                    throw e;
                }

                savedPersonInfoList.add(personInfo);
            }

            LimsSampleInfo sampleInfo = null;
            for (int i = sampleInfoList.size() - 1; i >= 0; i--) {
                sampleInfo = sampleInfoList.get(i);
                sampleInfo.setCaseId(caseId);
                sampleInfo.setConsignmentId(newConsignment.getId());
                sampleInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_PENDING);
                sampleInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, savedPersonInfoList);
                }

                try {
                    limsSampleInfoDao.insert(sampleInfo);
                } catch (Exception e) {
                    logger.info("----------插入样本id为[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                    logger.error("插入样本信息失败！", e);
                    throw e;
                }
            }
        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return newConsignment.getId();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> acceptSavePerson(ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        try {

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();

            LimsPersonInfo personInfoInDB = null;
            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(personInfo.getCaseId());
                personInfo.setConsignmentId(personInfo.getConsignmentId());
                personInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                personInfo.setInstoredFlag(Constants.FLAG_FALSE);
                if (personInfo.getId() == null || personInfo.getId() == 0) {
                    try {
                        limsPersonInfoDao.insert(personInfo);
                    } catch (Exception e) {
                        logger.info("-----插入人员id为[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("插入人员信息失败！", e);
                        throw e;
                    }
                } else {
                    personInfoInDB = limsPersonInfoDao.selectById(personInfo.getId());
                    copyPersonInfoBeanPropertiesFromPage(personInfo, personInfoInDB);

                    try {
                        limsPersonInfoDao.update(personInfoInDB);
                    } catch (Exception e) {
                        logger.info("------更新人员id[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！");
                        logger.error("更新人员信息失败！", e);
                        throw e;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return dataMap;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map<String, Object> acceptSaveSample(ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        try {

            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            LimsSampleInfo sampleInfoInDB = null;
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                sampleInfo.setCaseId(sampleInfo.getCaseId());
                sampleInfo.setConsignmentId(sampleInfo.getConsignmentId());
                sampleInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_PENDING);
                sampleInfo.setInstoredFlag(Constants.FLAG_FALSE);

                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                }

                if (sampleInfo.getId() == null || sampleInfo.getId() == 0) {
                    try {
                        limsSampleInfoDao.insert(sampleInfo);
                    } catch (Exception e) {
                        logger.info("----插入样本id为[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("插入样本信息失败！", e);
                        throw e;
                    }
                } else {
                    sampleInfoInDB = limsSampleInfoDao.selectById(sampleInfo.getId());
                    copySampleInfoBeanPropertiesFromPage(sampleInfo, sampleInfoInDB);

                    try {
                        limsSampleInfoDao.update(sampleInfoInDB);
                    } catch (Exception e) {
                        logger.info("----更新样本id[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！----");
                        logger.error("更新样本信息失败！", e);
                        throw e;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }

        return dataMap;
    }

    @Override
    public int updateCaseStatus(Integer caseId) {
        LimsCaseInfo limsCaseInfo = limsCaseInfoDao.selectById(caseId);
        limsCaseInfo.setCaseStatus(Constants.CASE_INFO_STATUS_FINISHED);
        return limsCaseInfoDao.update(limsCaseInfo);
    }


    /**
     * 变更委托
     *
     * @param consignmentDataModel
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean Update(ConsignmentDataModel consignmentDataModel) throws Exception {
        try {
            LimsConsignment consignment = consignmentDataModel.getConsignment();
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();
            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            LimsCaseInfo caseInfoInDB = limsCaseInfoDao.selectById(caseInfo.getId());
            //补送受理时，不更新案件信息
            if (Constants.FLAG_FALSE.equals(consignment.getAdditionalFlag())) {

                seqNoGenerateService.deleteCode(caseInfo.getCaseNo() + "-" + Constants.PERSON_NO_PREFIX);
                caseInfoInDB = copyCaseBeanPropertiesFromPage(caseInfo, caseInfoInDB);
                caseInfoInDB.setUpdatePerson(LimsSecurityUtils.getLoginLabUser().getUserName());
                try {
                    limsCaseInfoDao.update(caseInfoInDB);
                } catch (Exception e) {
                    logger.info("------更新案件id为[" + caseInfo.getId() + "]，登记单位为[" + caseInfo.getCreatePerson() + "]的信息失败！---");
                    logger.error("更新案件信息失败！", e);
                    throw e;
                }
            }

            LimsConsignment consignmentInDB = limsConsignmentDao.selectById(consignment.getId());
            consignmentInDB = copyConsignmentBeanPropertiesFromPage(consignment, consignmentInDB);
            consignmentInDB.setUpdatePerson(LimsSecurityUtils.getLoginLabUser().getUserName());
            try {
                limsConsignmentDao.update(consignmentInDB);
            } catch (Exception e) {
                logger.info("--------更新委托id为[" + consignment.getId() + "]，登记单位为[" + caseInfo.getCreatePerson() + "]的信息失败！---");
                logger.error("更新委托信息失败！", e);
                throw e;
            }

            int pcnt = personInfoList.size();
            int scnt = sampleInfoList.size();

            LimsPersonInfo personInfo = null;
            LimsPersonInfo personInfoInDB = null;
            for (int i = 0; i < pcnt; i++) {
                personInfo = personInfoList.get(i);
                personInfo.setCaseId(caseInfo.getId());
                personInfo.setConsignmentId(consignment.getId());

                if (personInfo.getId() == null || personInfo.getId() == 0) {
                    /*if(StringUtils.isNotBlank(caseInfoInDB.getCaseNo())) {
                        personInfo.setPersonNo(seqNoGenerateService.getNextPersonNoVal(caseInfoInDB.getCaseNo()));
                    }*/

                    try {
                        limsPersonInfoDao.insert(personInfo);
                    } catch (Exception e) {
                        logger.info("-------插入人员id[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！-----");
                        logger.error("插入人员信息失败！", e);
                        throw e;
                    }
                } else {
                    personInfoInDB = limsPersonInfoDao.selectById(personInfo.getId());
                    copyPersonInfoBeanPropertiesFromPage(personInfo, personInfoInDB);
                    personInfoInDB.setUpdatePerson(LimsSecurityUtils.getLoginLabUser().getUserName());
                    try {
                        limsPersonInfoDao.update(personInfoInDB);
                    } catch (Exception e) {
                        logger.info("-----更新人员id[" + personInfoInDB.getId() + "]，登记单位为[" + personInfoInDB.getCreatePerson() + "]的信息失败！---");
                        logger.error("更新人员信息失败！", e);
                        throw e;
                    }
                }
            }

            LimsSampleInfo sampleInfo = null;
            LimsSampleInfo sampleInfoInDB = null;
            for (int j = 0; j < scnt; j++) {
                sampleInfo = sampleInfoList.get(j);
                sampleInfo.setCaseId(caseInfo.getId());
                sampleInfo.setConsignmentId(consignment.getId());

                if (Constants.SAMPLE_FLAG_PERSON.equals(sampleInfo.getSampleFlag())) {
                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                }

                LimsSampleInfo limsSampleInfo = limsSampleInfoDao.selectById(sampleInfo.getId());

                if (sampleInfo.getId() == null || sampleInfo.getId() == 0) {
                    sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED);
                    sampleInfo.setAcceptDatetime(new Date());
                    sampleInfo.setAcceptPerson(LimsSecurityUtils.getLoginName());
                    if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_PERSON)) {
                        sampleInfo.setSampleNo(seqNoGenerateService.getNextPersonNoVal(caseInfoInDB.getCaseNo()));
                    } else {
                        sampleInfo.setSampleNo(seqNoGenerateService.getNextSampleNoVal(caseInfoInDB.getCaseNo()));
                    }
                    try {
                        limsSampleInfoDao.insert(sampleInfo);
                    } catch (Exception e) {
                        logger.error("插入样本信息失败！", e);
                        logger.info("-----------插入样本id[" + sampleInfo.getId() + "]信息失败！");
                        throw e;
                    }
                } else if (!limsSampleInfo.getSampleNo().equals(sampleInfo.getSampleNo())) {
                    sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED);
                    sampleInfo.setAcceptDatetime(new Date());
                    sampleInfo.setAcceptPerson(LimsSecurityUtils.getLoginName());

                    sampleInfo.setSampleNo(sampleInfo.getSampleNo());
                    sampleInfo.setSampleName(sampleInfo.getSampleName());
                    sampleInfo.setSampleDesc(sampleInfo.getSampleDesc());
                    limsSampleInfoDao.insert(sampleInfo);

                } else if (limsSampleInfo.getSampleNo().equals(sampleInfo.getSampleNo())) {
                    sampleInfoInDB = limsSampleInfoDao.selectById(sampleInfo.getId());
                    copySampleInfoBeanPropertiesFromPage(sampleInfo, sampleInfoInDB);
                    sampleInfoInDB.setUpdatePerson(LimsSecurityUtils.getLoginLabUser().getUserName());
                    try {
                        limsSampleInfoDao.update(sampleInfoInDB);
                    } catch (Exception e) {
                        logger.info("-------更新样本id[" + sampleInfo.getId() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！---");
                        logger.error("更新样本信息失败！", e);
                        throw e;
                    }
                }
            }

            /*//插入数据同步队列表sync_dna36_queue表, 操作类型为“更新”
            SyncDna36Queue syncDna36Queue = new SyncDna36Queue();
            syncDna36Queue.setCaseId(caseInfoInDB.getId());
            syncDna36Queue.setOperateType(Constants.OPERATE_TYPE_EDIT);
            syncDna36Queue.setOperateDate(new Date());
            syncDna36Queue.setOperatePerson(LimsSecurityUtils.getLoginLabUser().getUserName());
            syncDna36Queue.setSyncFailedFlag(Constants.FLAG_FALSE);

            try {
                syncDna36QueueDao.insert(syncDna36Queue);
            }catch (Exception e){
                logger.error("插入Queue队列失败！", e);
                logger.info("插入Queue案件id["+ syncDna36Queue.getId() + "]，登记单位为[" + syncDna36Queue.getOperatePerson() + "]的队列失败！");
                throw e;
            }*/
        } catch (Exception e) {
            logger.error("更新委托登记信息出错！", e);
            throw e;
        }

        return true;
    }

    private LimsSampleInfo setRefPersonInfoForSample(LimsSampleInfo sampleInfo, List<LimsPersonInfo> personInfoList) {
        for (LimsPersonInfo person : personInfoList) {
            if (person.getPersonName().equals(sampleInfo.getRefPersonName())) {
                sampleInfo.setRefPersonId(person.getId());
                sampleInfo.setRefPersonName(person.getPersonName());
                break;
            }
        }

        return sampleInfo;
    }

    /**
     * 受理委托
     *
     * @param consignmentDataModel
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean Accept(ConsignmentDataModel consignmentDataModel) throws Exception {
        logger.info("---------------受理案件Start---------");
        try {
            LimsConsignment consignment = consignmentDataModel.getConsignment();
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();
            List<LimsPersonInfo> personInfoList = consignmentDataModel.getPersonInfoList();
            List<LimsSampleInfo> sampleInfoList = consignmentDataModel.getSampleInfoList();

            logger.info("---------------受理案件id[" + caseInfo.getId() + "]，委托id[" + consignment.getId() + "]---------");

            //补送受理时，不更新案件信息
            LimsCaseInfo caseInfoInDB = limsCaseInfoDao.selectById(caseInfo.getId());
            if (Constants.FLAG_FALSE.equals(consignment.getAdditionalFlag())) {
                caseInfoInDB = copyCaseBeanPropertiesFromPage(caseInfo, caseInfoInDB);
                while (StringUtils.isBlank(caseInfoInDB.getCaseNo())
                        || "null".equals(caseInfoInDB.getCaseNo())) {
                    caseInfoInDB.setCaseNo(seqNoGenerateService.getNextCaseNoVal(Constants.CASE_NO_RULE + "-" + DateUtils.getCurrentYearStr()));
                }
                caseInfoInDB.setCaseStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
                try {
                    limsCaseInfoDao.update(caseInfoInDB);
                } catch (Exception e) {
                    logger.info("-----------更新案件id为[" + caseInfo.getId() + "]，编号为[" + caseInfo.getCaseNo() + "]，登记单位为[" + caseInfo.getCreatePerson() + "]的信息失败！---");
                    logger.error("更新案件信息失败！", e);
                    throw e;
                }
            }

            LimsConsignment consignmentInDB = limsConsignmentDao.selectById(consignment.getId());
            consignmentInDB = copyConsignmentBeanPropertiesFromPage(consignment, consignmentInDB);
            consignmentInDB.setStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
            consignmentInDB.setAcceptDatetime(new Date());
            consignmentInDB.setAcceptor(LimsSecurityUtils.getLoginName());
            try {
                limsConsignmentDao.update(consignmentInDB);
            } catch (Exception e) {
                logger.info("-----------更新委托id为[" + consignment.getId() + "]，登记单位为[" + consignment.getCreatePerson() + "]的信息失败！---");
                logger.error("更新委托信息失败！", e);
                throw e;
            }

            LimsPersonInfo personInfoInDB = null;
            for (LimsPersonInfo personInfo : personInfoList) {
                personInfo.setCaseId(caseInfo.getId());
                personInfo.setConsignmentId(consignment.getId());

                if (personInfo.getId() == null || personInfo.getId() == 0) {
                    //personInfo.setPersonNo(seqNoGenerateService.getNextPersonNoVal(caseInfoInDB.getCaseNo()));
                    try {
                        limsPersonInfoDao.insert(personInfo);
                    } catch (Exception e) {
                        logger.info("--------插入人员id[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！");
                        logger.error("插入人员信息失败！", e);
                        throw e;
                    }
                } else {
                    personInfoInDB = limsPersonInfoDao.selectById(personInfo.getId());
                    copyPersonInfoBeanPropertiesFromPage(personInfo, personInfoInDB);

                    //personInfoInDB.setPersonNo(seqNoGenerateService.getNextPersonNoVal(caseInfoInDB.getCaseNo()));
                    try {
                        limsPersonInfoDao.update(personInfoInDB);
                    } catch (Exception e) {
                        logger.info("-----------更新人员id[" + personInfo.getId() + "]，登记单位为[" + personInfo.getCreatePerson() + "]的信息失败！");
                        logger.error("更新人员信息失败！", e);
                        throw e;
                    }
                }
            }

            LimsSampleInfo sampleInfoInDB = null;
            //人员检材  为了让人员检材的编号靠前，需要做两次循环
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                sampleInfo.setCaseId(caseInfo.getId());
                sampleInfo.setConsignmentId(consignment.getId());
                sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED);
                sampleInfo.setAcceptPerson(LimsSecurityUtils.getLoginName());
                sampleInfo.setAcceptDatetime(new Date());

                if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_PERSON)) {
                    while (StringUtils.isBlank(sampleInfo.getSampleNo())
                            || "null".equals(sampleInfo.getSampleNo())) {
                        sampleInfo.setSampleNo(seqNoGenerateService.getNextPersonNoVal(caseInfoInDB.getCaseNo()));
                    }

                    sampleInfo = setRefPersonInfoForSample(sampleInfo, personInfoList);
                    if (sampleInfo.getId() == null || sampleInfo.getId() == 0) {
                        try {
                            limsSampleInfoDao.insert(sampleInfo);
                        } catch (Exception e) {
                            logger.info("-----------插入人员样本id[" + sampleInfo.getId() + "]，编号为[" + sampleInfo.getSampleNo() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！");
                            logger.error("插入人员样本信息失败！", e);
                            throw e;
                        }
                    } else {
                        sampleInfoInDB = limsSampleInfoDao.selectById(sampleInfo.getId());
                        copySampleInfoBeanPropertiesFromPage(sampleInfo, sampleInfoInDB);
                        try {
                            limsSampleInfoDao.update(sampleInfoInDB);
                        } catch (Exception e) {
                            logger.info("-----------更新人员样本id[" + sampleInfo.getId() + "]，编号为[" + sampleInfo.getSampleNo() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！");
                            logger.error("更新人员样本信息失败！", e);
                            throw e;
                        }
                    }

                    logger.info("---------------受理检材id[" + sampleInfo.getId() + "]，检材编号[" + sampleInfo.getSampleNo() + "]---------");
                }
            }
            //物证检材
            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                    while (StringUtils.isBlank(sampleInfo.getSampleNo())
                            || "null".equals(sampleInfo.getSampleNo())) {
                        sampleInfo.setSampleNo(seqNoGenerateService.getNextSampleNoVal(caseInfoInDB.getCaseNo()));
                    }

                    if (sampleInfo.getId() == null || sampleInfo.getId() == 0) {
                        try {
                            limsSampleInfoDao.insert(sampleInfo);
                        } catch (Exception e) {
                            logger.info("-----------插入物证样本id[" + sampleInfo.getId() + "]，编号为[" + sampleInfo.getSampleNo() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！");
                            logger.error("插入物证样本信息失败！", e);
                            throw e;
                        }
                    } else {
                        sampleInfoInDB = limsSampleInfoDao.selectById(sampleInfo.getId());
                        copySampleInfoBeanPropertiesFromPage(sampleInfo, sampleInfoInDB);
                        try {
                            limsSampleInfoDao.update(sampleInfoInDB);
                        } catch (Exception e) {
                            logger.info("-----------更新物证样本id[" + sampleInfo.getId() + "]，编号为[" + sampleInfo.getSampleNo() + "]，登记单位为[" + sampleInfo.getCreatePerson() + "]的信息失败！");
                            logger.error("更新物证样本信息失败！", e);
                            throw e;
                        }
                    }

                    logger.info("---------------受理检材id[" + sampleInfo.getId() + "]，检材编号[" + sampleInfo.getSampleNo() + "]---------");
                }
            }

            try {
                if (Constants.FLAG_FALSE.equals(consignment.getAdditionalFlag())) {
                    limsIdentifyBookDao.insert(getIdentifyBookByCaseInfo(caseInfoInDB));
                }
            } catch (Exception e) {
                logger.info("插入鉴定书失败！");
                logger.error("插入鉴定书案件id为[" + caseInfoInDB.getId() + "],编号为[" + caseInfoInDB.getCaseNo() + "]，登记单位[" + caseInfoInDB.getCreatePerson() + "]失败！", e);
                throw e;
            }

            //案件已受理
            try {
                QueueSample queueSample = new QueueSample();
                //已委托
                queueSample.setStatus("0");
                queueSample.setQueueType(Constants.CASE_INFO_ACCEPTED);//21，案件已受理
                queueSample.setCaseId(caseInfoInDB.getId());
                queueSample.setConsignmentId(consignmentInDB.getId());
                queueSample.setCreateDatetime(new Date());
                queueSample.setOperatePerson(consignmentInDB.getAcceptor());
                queueSampleService.insert(queueSample);

            } catch (Exception e) {
                e.printStackTrace();
                logger.error("插入队列错误！", e);
            }

            //物证已受理
            try {
                QueueSample queue = new QueueSample();
                for (LimsSampleInfo sampleInfo : sampleInfoList) {
                    if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != sampleInfo.getEvidenceNo()) {

                        queue.setStatus("0");
                        queue.setQueueType(Constants.CASE_INFO_IDENTIFIED);//22,物证已受理
                        queue.setCaseId(caseInfoInDB.getId());
                        queue.setConsignmentId(consignmentInDB.getId());
                        queue.setCreateDatetime(new Date());
                        queue.setOperatePerson(consignmentInDB.getAcceptor());
                        queueSampleService.insert(queue);
                        break;
                    }
                }
                for (LimsSampleInfo sampleInfo : sampleInfoList) {
                    if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != sampleInfo.getEvidenceNo()) {
                        QueueDetail queueDetail = new QueueDetail();
                        queueDetail.setQueueId(queue.getId());
                        queueDetail.setCaseId(caseInfoInDB.getId());
                        queueDetail.setSampleId(sampleInfo.getEvidenceNo());
                        queueDetail.setStatus("0");
                        queueDetail.setCreatePerson(consignmentInDB.getAcceptor());
                        queueDetail.setCreateDatetime(new Date());
                        queueDetailService.insert(queueDetail);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("插入委托队列错误！", e);
            }

            logger.info("---------------受理案件End-----------------");
        } catch (Exception e) {
            logger.info("---------------受理案件Error！-----------------");
            logger.error("受理委托登记信息出错！", e);
            throw e;
        }

        return true;
    }

    private LimsIdentifyBook getIdentifyBookByCaseInfo(LimsCaseInfo caseInfo) {
        LimsIdentifyBook identifyBook = new LimsIdentifyBook();

        identifyBook.setCaseId(caseInfo.getId());
        identifyBook.setCaseNo(caseInfo.getCaseNo());
        identifyBook.setStatus(Constants.IDENTIFY_BOOK_PENDING);
        identifyBook.setStatusName(Constants.IDENTIFY_BOOK_PENDING_NAME);
        identifyBook.setCreatePersonName(LimsSecurityUtils.getLoginName());
        identifyBook.setSendNoticeFlag(Constants.FLAG_FALSE);
        identifyBook.setDeleteFlag(Constants.FLAG_FALSE);

        return identifyBook;
    }


    private LimsCaseInfo copyCaseBeanPropertiesFromPage(LimsCaseInfo pageForm, LimsCaseInfo dbForm) {
        dbForm.setCaseNo(pageForm.getCaseNo());
        dbForm.setCaseName(pageForm.getCaseName());
        dbForm.setCaseXkNo(pageForm.getCaseXkNo());
        dbForm.setCaseLocation(pageForm.getCaseLocation());
        dbForm.setCaseDatetime(pageForm.getCaseDatetime());
        dbForm.setCaseBrief(pageForm.getCaseBrief());
        dbForm.setCaseType(pageForm.getCaseType());
        dbForm.setCaseProperty(pageForm.getCaseProperty());
        dbForm.setCaseLevel(pageForm.getCaseLevel());
        dbForm.setJiajiFlag(pageForm.getJiajiFlag());
        dbForm.setCaseSpecialRemark(pageForm.getCaseSpecialRemark());
        dbForm.setRemark(pageForm.getRemark());

        return dbForm;
    }

    private LimsConsignment copyConsignmentBeanPropertiesFromPage(LimsConsignment pageForm, LimsConsignment dbForm) {
        dbForm.setDelegateOrgDesc(pageForm.getDelegateOrgDesc());
        dbForm.setDelegateOrgPhone(pageForm.getDelegateOrgPhone());
        dbForm.setDelegator1(pageForm.getDelegator1());
        dbForm.setDelegator1Cno(pageForm.getDelegator1Cno());
        dbForm.setDelegator1Phone(pageForm.getDelegator1Phone());
        dbForm.setDelegator2(pageForm.getDelegator2());
        dbForm.setDelegator2Cno(pageForm.getDelegator2Cno());
        dbForm.setDelegator2Phone(pageForm.getDelegator2Phone());
        dbForm.setIdentifyRequirement(pageForm.getIdentifyRequirement());
        dbForm.setMatchCaseNo(pageForm.getMatchCaseNo());
        dbForm.setIdentifyRequirementOther(pageForm.getIdentifyRequirementOther());
        dbForm.setPreIdentifyDesc(pageForm.getPreIdentifyDesc());
        dbForm.setReidentifyReason(pageForm.getReidentifyReason());
        dbForm.setDelegator1Position(pageForm.getDelegator1Position());
        dbForm.setDelegator2Position(pageForm.getDelegator2Position());
        dbForm.setDelegator1Cname(pageForm.getDelegator1Cname());
        dbForm.setDelegator2Cname(pageForm.getDelegator2Cname());
        dbForm.setPostalAddress(pageForm.getPostalAddress());
        dbForm.setPostNo(pageForm.getPostNo());
        dbForm.setFaxNo(pageForm.getFaxNo());
        dbForm.setIdentifyKernelName(pageForm.getIdentifyKernelName());

        return dbForm;
    }

    private LimsPersonInfo copyPersonInfoBeanPropertiesFromPage(LimsPersonInfo pageForm, LimsPersonInfo dbForm) {
        dbForm.setPersonName(pageForm.getPersonName());
        dbForm.setPersonType(pageForm.getPersonType());
        dbForm.setPersonGender(pageForm.getPersonGender());
        dbForm.setPersonIdcardNo(pageForm.getPersonIdcardNo());
        dbForm.setNoIdcardRemark(pageForm.getNoIdcardRemark());
        dbForm.setRelativeIdentify(pageForm.getRelativeIdentify());

        return dbForm;
    }

    private LimsSampleInfo copySampleInfoBeanPropertiesFromPage(LimsSampleInfo pageForm, LimsSampleInfo dbForm) {
        dbForm.setSampleType(pageForm.getSampleType());
        dbForm.setSampleName(pageForm.getSampleName());
        dbForm.setExtractDatetime(pageForm.getExtractDatetime());
        dbForm.setExtractPerson(pageForm.getExtractPerson());
        dbForm.setSampleDesc(pageForm.getSampleDesc());
        dbForm.setSamplePacking(pageForm.getSamplePacking());
        dbForm.setSampleProperties(pageForm.getSampleProperties());
        dbForm.setOtherPropertiesDesc(pageForm.getOtherPropertiesDesc());
        dbForm.setRefPersonId(pageForm.getRefPersonId());
        dbForm.setRefPersonName(pageForm.getRefPersonName());
        dbForm.setSampleNo(pageForm.getSampleNo());
        dbForm.setSampleFlag(pageForm.getSampleFlag());
        dbForm.setAtomFlag(pageForm.getAtomFlag());
        dbForm.setUrgentFlag(pageForm.getUrgentFlag());
        dbForm.setDifficultFlag(pageForm.getDifficultFlag());
        if (Constants.SAMPLE_ACCEPT_STATUS_PENDING.equals(dbForm.getAcceptStatus())) {
            dbForm.setAcceptStatus(pageForm.getAcceptStatus());
            dbForm.setAcceptPerson(pageForm.getAcceptPerson());
            dbForm.setAcceptDatetime(pageForm.getAcceptDatetime());
        }
        return dbForm;
    }


    public int selectCount(LimsCaseInfoVO query) {
        return limsCaseInfoDao.selectCount(query);
    }

    public List<LimsCaseInfo> selectPaginationList(LimsCaseInfoVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsCaseInfoDao.selectPaginationList(query);
    }

    @Override
    public int selectVOCount(LimsCaseInfoVO query) {
        query = resetQueryParams(query);
        return limsCaseInfoDao.selectVOCount(query);
    }

    @Override
    public List<LimsCaseInfoVO> selectVOPaginationList(LimsCaseInfoVO query, PageInfo pageInfo) {
        query = resetQueryParams(query);

        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsCaseInfoDao.selectVOPaginationList(query);
    }

    @Override
    public List<LimsCaseInfoVO> selectVOAllList(LimsCaseInfoVO query, PageInfo pageInfo) {
        query = resetQueryParams(query);

        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsCaseInfoDao.selectVOAllList(query);
    }

    @Override
    public int selectVOCnt(LimsCaseInfoVO query) {
        query = resetQueryParams(query);
        return limsCaseInfoDao.selectVOCnt(query);
    }

    @Override
    public List<LimsCaseInfoVO> selectVOList(LimsCaseInfoVO query) {
        return limsCaseInfoDao.selectVOList(query);
    }

    public List<LimsCaseInfoVO> selectVOGeneralQueryList(LimsCaseInfoVO query, PageInfo pageInfo) {
        query = resetQueryParams(query);

        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsCaseInfoDao.selectVOGeneralQueryList(query);
    }

    public int selectVOGeneralQueryCount(LimsCaseInfoVO query) {
        query = resetQueryParams(query);
        return limsCaseInfoDao.selectVOGeneralQueryCount(query);
    }

    public List<LimsCaseInfoVO> selectVOIdentifyBookList(LimsCaseInfoVO query, PageInfo pageInfo) {
        query = resetQueryParams(query);

        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsCaseInfoDao.selectVOIdentifyBookList(query);
    }

    public int selectVOIdentifyBookCount(LimsCaseInfoVO query) {
        query = resetQueryParams(query);
        return limsCaseInfoDao.selectVOIdentifyBookCount(query);
    }

    private LimsCaseInfoVO resetQueryParams(LimsCaseInfoVO query) {
        if (StringUtils.isBlank(query.getDelegateOrg())) {
            query.setDelegateOrg(null);
        } else {
            query.setDelegateOrg(query.getDelegateOrg().trim());
        }

        if (StringUtils.isBlank(query.getDelegateAcceptor())) {
            query.setDelegateAcceptor(null);
        } else {
            query.setDelegateAcceptor(query.getDelegateAcceptor().trim());
        }

        if (StringUtils.isBlank(query.getDelegatorName())) {
            query.setDelegatorName(null);
        } else {
            query.setDelegatorName(query.getDelegatorName().trim());
        }

        LimsCaseInfo entity = query.getEntity();
        if (entity != null) {
            if (StringUtils.isBlank(entity.getCaseName())) {
                entity.setCaseName(null);
            } else {
                entity.setCaseName(entity.getCaseName().trim());
            }

            if (StringUtils.isBlank(entity.getCaseNo())) {
                entity.setCaseNo(null);
            } else {
                entity.setCaseNo(entity.getCaseNo().trim());
            }

            if (StringUtils.isBlank(entity.getCaseProperty())) {
                entity.setCaseProperty(null);
            } else {
                entity.setCaseProperty(entity.getCaseProperty().trim());
            }

            query.setEntity(entity);
        }

        return query;
    }

//    public List<LimsCaseInfo> selectAcceptedList(int offset, int count) {
//        LimsCaseInfo caseInfo = new LimsCaseInfo();
//        LimsCaseInfoVO query = new LimsCaseInfoVO(caseInfo);
//        query.setCaseStatusIn("'" + Constants.CASE_INFO_STATUS_ACCEPTED + "','" + Constants.CASE_INFO_STATUS_FINISHED + "'");
//        query.setOffset(offset);
//        query.setRows(count);
//
//        return limsCaseInfoDao.selectPaginationList(query);
//    }

    public int selectCountByProperty(String caseProperty) {
        LimsCaseInfo caseInfo = new LimsCaseInfo();
        caseInfo.setCaseProperty(caseProperty);

        LimsCaseInfoVO query = new LimsCaseInfoVO(caseInfo);

        List<String> caseStatusList = new ArrayList<>();
        caseStatusList.add(Constants.CASE_INFO_STATUS_ACCEPTED);
        caseStatusList.add(Constants.CASE_INFO_STATUS_FINISHED);
        query.setConsignmentStatusList(caseStatusList);

        query.setAdditionalFlag(Constants.FLAG_FALSE);

        return this.selectVOCount(query);
    }

    public int refuseCase(LimsCaseInfo caseInfo) {
        return limsCaseInfoDao.refuseCase(caseInfo);
    }

    @Transactional
    public boolean deleteById(Integer extractRecordId) {
        try {
            limsCaseInfoDao.deleteById(extractRecordId);
        } catch (Exception ex) {
            logger.error("删除案件错误！", ex);
            return false;
        }

        return true;
    }

}
