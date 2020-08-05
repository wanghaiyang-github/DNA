package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.*;
import com.bazl.hslims.manager.model.po.LimsCaseInfo;
import com.bazl.hslims.manager.model.po.LimsCaseInformation;
import com.bazl.hslims.manager.model.po.LimsConsignment;
import com.bazl.hslims.manager.services.common.LimsCaseInformationService;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangliu on 2018/5/13.
 */
@Service
public class LimsCaseInformationServiceImpl implements LimsCaseInformationService {

    @Autowired
    LimsCaseInformationDao limsCaseInformationDao;
    @Autowired
    LimsCaseInfoDao limsCaseInfoDao;
    @Autowired
    LimsEntrustmentInformationDao limsEntrustmentInformationDao;
    @Autowired
    LimsConsignmentDao limsConsignmentDao;
    @Autowired
    LimsPersonSampleDao limsPersonSampleDao;
    @Autowired
    DictItemDao dictItemDao;
    @Autowired
    SeqNoGenerateService seqNoGenerateService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public LimsCaseInformation selectByEntrustmentId(Integer entrustmentId) {
        return limsCaseInformationDao.selectByEntrustmentId(entrustmentId);
    }

    @Override
    public LimsCaseInformation selectByCaseNo(String caseNo) {
        return limsCaseInformationDao.selectByCaseNo(caseNo);
    }

    @Override
    public int refuseCase(LimsCaseInformation limsCaseInformation) {
        return limsCaseInformationDao.refuseCase(limsCaseInformation);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map identifyCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        try {
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();
            LimsConsignment consignment = consignmentDataModel.getConsignment();

            caseInfo.setEntrustmentType(Constants.CASE_INFORMATION_UNIDENTFIED);
            caseInfo.setDeleteFlag(Constants.FLAG_FALSE);
            caseInfo.setCaseStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
            caseInfo.setCaseLocation(caseInfo.getCaseLocationDesc());
            caseInfo.setCaseNo(DateUtils.getCurrentYearStr() + seqNoGenerateService.getNextAcceptCaseNoVal("W"));
            if (caseInfo != null) {
                try {
                    limsCaseInfoDao.insert(caseInfo);
                    dataMap.put("caseId", caseInfo.getId());
                } catch (Exception e) {
                    logger.error("添加身份不明人员案件信息失败！", e);
                    throw e;
                }
            }
            consignment.setDeleteFlag(Constants.FLAG_FALSE);
            consignment.setStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
            consignment.setAdditionalFlag(Constants.FLAG_FALSE);
            consignment.setCaseId(caseInfo.getId());
            consignment.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
            if (consignment != null) {
                try {
                    limsConsignmentDao.insert(consignment);
                    dataMap.put("consignmentId", consignment.getId());
                    dataMap.put("delegateOrgDesc", consignment.getDelegateOrgDesc());

                } catch (Exception e) {
                    logger.error("添加身份不明人员委托信息失败！", e);
                    throw e;
                }
            }
        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }
        return dataMap;
    }

    @Override
    public Map updateCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        try {
            LimsConsignment consignment = consignmentDataModel.getConsignment();
            LimsCaseInformation caseInformation = consignmentDataModel.getCaseInformation();

            LimsCaseInfo caseInfo = new LimsCaseInfo();
            caseInfo.setId(caseInformation.getId());
            caseInfo.setCaseNo(caseInformation.getCaseNo());
            caseInfo.setCaseName(caseInformation.getCaseName());
            caseInfo.setCaseDatetime(caseInformation.getCaseDatetime());
            caseInfo.setCaseLocationDesc(caseInformation.getCaseLocationDesc());
            caseInfo.setCaseBrief(caseInformation.getCaseBrief());
            caseInfo.setEntrustmentType(caseInformation.getEntrustmentType());
            caseInfo.setCaseStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
            caseInfo.setDeleteFlag(Constants.FLAG_FALSE);
            if (caseInfo != null) {
                try {
                    limsCaseInfoDao.update(caseInfo);
                } catch (Exception e) {
                    logger.error("修改案件信息失败！", e);
                    throw e;
                }
                dataMap.put("caseId",caseInfo.getId());
            }
            if (consignment != null) {
                try {
                    consignment.setDeleteFlag(Constants.FLAG_FALSE);
                    consignment.setAdditionalFlag(Constants.FLAG_FALSE);
                    limsConsignmentDao.update(consignment);
                } catch (Exception e) {
                    logger.error("修改委托信息失败！", e);
                    throw e;
                }

            }
        } catch (Exception e) {
            logger.error("修改委托登记信息出错！", e);
            throw e;
        }
        dataMap.put("operateType", operateType);
        return dataMap;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Map identifyMissingCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception {
        Map<String, Object> dataMap = new HashMap<>();

        try {
            LimsCaseInfo caseInfo = consignmentDataModel.getCaseInfo();
            LimsConsignment consignment = consignmentDataModel.getConsignment();

            caseInfo.setCaseLocation(caseInfo.getCaseLocationDesc());
            caseInfo.setCaseStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
            caseInfo.setEntrustmentType(Constants.CASE_INFORMATION_BE_LOST);
            caseInfo.setDeleteFlag(Constants.FLAG_FALSE);
            caseInfo.setCaseNo(DateUtils.getCurrentYearStr() +seqNoGenerateService.getNextAcceptCaseNoVal("S"));
            if (caseInfo != null) {
                try {
                    limsCaseInfoDao.insert(caseInfo);
                    dataMap.put("caseId", caseInfo.getId());
                } catch (Exception e) {
                    logger.error("添加失踪人口案件信息失败！", e);
                    throw e;
                }
            }

            try {

                consignment.setDeleteFlag(Constants.FLAG_FALSE);
                consignment.setStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
                consignment.setAdditionalFlag(Constants.FLAG_FALSE);
                consignment.setCaseId(caseInfo.getId());
                consignment.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
                if (consignment != null) {
                    limsConsignmentDao.insert(consignment);
                    dataMap.put("consignmentId", consignment.getId());
                }

            } catch (Exception e) {
                logger.error("添加失踪人口委托信息失败！", e);
                throw e;
            }

        } catch (Exception e) {
            logger.error("保存委托登记信息出错！", e);
            throw e;
        }
        dataMap.put("operateType", operateType);
        return dataMap;
    }


    @Override
    public LimsCaseInformation selectById(Integer caseId) {
        return limsCaseInformationDao.selectById(caseId);
    }

    @Override
    public List<LimsCaseInformation> selectPaginationList(LimsCaseInformation query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);
        return limsCaseInformationDao.selectPaginationList(query);
    }

    @Override
    public int selectCount(LimsCaseInformation limsCaseInformation) {
        return limsCaseInformationDao.selectCount(limsCaseInformation);
    }

    @Override
    public boolean deleteById(Integer caseId) {
        try {
            limsCaseInformationDao.deleteById(caseId);
        } catch (Exception ex) {
            logger.error("删除案件错误！", ex);
            return false;
        }
        return true;
    }
}
