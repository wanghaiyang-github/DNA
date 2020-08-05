package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.LimsSampleInfoDao;
import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;
import com.bazl.hslims.manager.services.common.LimsSampleInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Service
public class LimsSampleInfoServiceImpl implements LimsSampleInfoService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LimsSampleInfoDao limsSampleInfoDao;

    @Override
    public LimsSampleInfo selectById(Integer id) {
        return limsSampleInfoDao.selectById(id);
    }

    @Override
    public List<LimsSampleInfo> selectListBySampleNo(String sampleNo) {
        return limsSampleInfoDao.selectListBySampleNo(sampleNo);
    }



    @Override
    public List<LimsSampleInfo> selectListByCaseNo(String caseNo) {
        return limsSampleInfoDao.selectListByCaseNo(caseNo);
    }

    @Override
    public List<LimsSampleInfo> selectAcceptedListByCaseId(Integer caseId) {
        List<LimsSampleInfo> sampleInfoList = selectListByCaseId(caseId);
        LimsSampleInfo sampleInfo = null;
        for(Iterator<LimsSampleInfo> it = sampleInfoList.iterator(); it.hasNext();){
            sampleInfo = it.next();
            if(!Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED.equals(sampleInfo.getAcceptStatus())){
                it.remove();
            }
        }

        return sampleInfoList;
    }

    @Override
    public List<LimsSampleInfo> selectListByCaseId(Integer caseId) {
        return limsSampleInfoDao.selectListByCaseId(caseId);
    }



    @Override
    public List<LimsSampleInfo> selectAcceptStatusListByCaseId(Integer caseId) {
        return limsSampleInfoDao.selectAcceptStatusListByCaseId(caseId);
    }

    @Override
    public List<LimsSampleInfo> selectAuditStatusListByCaseId(Integer caseId) {
        return limsSampleInfoDao.selectAuditStatusListByCaseId(caseId);
    }

    @Override
    public List<LimsSampleInfo> selectHasGeneListByCaseId(Integer caseId) {
        return limsSampleInfoDao.selectHasGeneListByCaseId(caseId);
    }

    @Override
    public List<LimsSampleInfo> selectListByConsignmentId(Integer consignmentId) {
        return limsSampleInfoDao.selectListByConsignmentId(consignmentId);
    }

    @Override
    public List<LimsSampleInfo> selectAcceptStatusListByConsignmentId(Integer consignmentId) {
        return limsSampleInfoDao.selectAcceptStatusListByConsignmentId(consignmentId);
    }


    @Override
    public int selectVOCount(LimsSampleInfoVO query) {
        return limsSampleInfoDao.selectVOCount(query);
    }

    @Override
    public int selectVOSampleCount(LimsSampleInfoVO query) {
        return limsSampleInfoDao.selectVOSampleCount(query);
    }

    @Override
    public int selectVOPersonSampleCount(LimsSampleInfoVO query) {
        return limsSampleInfoDao.selectVOPersonSampleCount(query);
    }

    @Override
    public List<LimsSampleInfoVO> selectVOPaginationList(LimsSampleInfoVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsSampleInfoDao.selectVOPaginationList(query);
    }

    @Override
    public List<LimsSampleInfoVO> selectVOSampleInfoList(LimsSampleInfoVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);
        return limsSampleInfoDao.selectVOSampleInfoList(query);
    }

    @Override
    public List<LimsSampleInfoVO> selectVOPersonSampleList(LimsSampleInfoVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsSampleInfoDao.selectVOPersonSampleList(query);
    }

    @Override
    public int selectCountByType(String sampleType) {
        LimsSampleInfo sampleInfo = new LimsSampleInfo();
        sampleInfo.setSampleType(sampleType);
        sampleInfo.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED);
        LimsSampleInfoVO query = new LimsSampleInfoVO(sampleInfo);

        return this.selectVOCount(query);
    }


    @Override
    public boolean deleteById(Integer sampleId) {
        try {
            return this.limsSampleInfoDao.deleteById(sampleId) > 0;
        } catch (Exception ex) {
            logger.error("删除样本信息错误！", ex);

            return false;
        }
    }

    @Override
    public boolean updateById(LimsSampleInfo limsSampleInfo) {
        return limsSampleInfoDao.updateById(limsSampleInfo);
    }

    @Override
    public List<LimsSampleInfo> selectListBySampleId(Integer sampleId){
        return limsSampleInfoDao.selectListBySampleId(sampleId);
    }

    @Override
    public int updatePhotoPath(LimsSampleInfo limsSampleInfo) {
        return limsSampleInfoDao.updatePhotoPath(limsSampleInfo);
    }

    @Override
    public int deleteByCaseId(Integer caseId) {
        return limsSampleInfoDao.deleteByCaseId(caseId);
    }

    @Override
    public int insertSample(LimsSampleInfo limsSampleInfo) {
        return limsSampleInfoDao.insert(limsSampleInfo);
    }

    @Override
    public int deleteByConsignmentId(Integer consignmentId) {
        return limsSampleInfoDao.deleteByConsignmentId(consignmentId);
    }

    @Override
    public List<LimsSampleInfo> selectAcceptStatusSortListByCaseId(Integer caseId) {
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoDao.selectAcceptStatusSortListByCaseId(caseId);
        LimsSampleInfo sampleInfo = null;
        for(Iterator<LimsSampleInfo> it = sampleInfoList.iterator(); it.hasNext();){
            sampleInfo = it.next();
            if(!Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED.equals(sampleInfo.getAcceptStatus())){
                it.remove();
            }
        }
        return sampleInfoList;
    }

    @Override
    public List<LimsSampleInfo> selectList(LimsSampleInfo evidenceLimsSampleInfo) {
        return  limsSampleInfoDao.selectList(evidenceLimsSampleInfo);
    }

    @Override
    public boolean updateSubmitFlag(LimsSampleInfo limsSampleInfo) {
        return limsSampleInfoDao.updateSubmitFlag(limsSampleInfo);
    }

    @Override
    public int updateByEvidenceNo(LimsSampleInfo limsSampleInfo) {
        return limsSampleInfoDao.updateByEvidenceNo(limsSampleInfo);
    }

}
