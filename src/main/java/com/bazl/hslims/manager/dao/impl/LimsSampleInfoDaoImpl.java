package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsSampleInfoDao;
import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Repository
public class LimsSampleInfoDaoImpl extends BaseDaoImpl<LimsSampleInfo, Integer>  implements LimsSampleInfoDao {


    @Override
    public String namespace() {
        return LimsSampleInfo.class.getName();
    }

    public List<LimsSampleInfo> selectListBySampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListBySampleNo", sampleNo);
    }

    public List<LimsSampleInfo> selectListByCaseNo(String caseNo) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByCaseNo", caseNo);
    }

    public List<LimsSampleInfo> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByCaseId", caseId);
    }

    public List<LimsSampleInfo> selectAcceptStatusListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAcceptStatusListByCaseId", caseId);
    }

    public List<LimsSampleInfo> selectHasGeneListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectHasGeneListByCaseId", caseId);
    }

    @Override
    public List<LimsSampleInfo> selectListByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByConsignmentId", consignmentId);
    }

    @Override
    public List<LimsSampleInfo> selectAcceptStatusListByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAcceptStatusListByConsignmentId", consignmentId);
    }

    @Override
    public List<LimsSampleInfo> selectAuditStatusListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAuditStatusListByCaseId", caseId);
    }

    @Override
    public int selectVOCount(LimsSampleInfoVO limsSampleInfoVO) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOCount", limsSampleInfoVO);
    }

    @Override
    public int selectVOSampleCount(LimsSampleInfoVO query) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOSampleCount", query);
    }

    @Override
    public int selectVOPersonSampleCount(LimsSampleInfoVO query) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOPersonSampleCount", query);
    }

    @Override
    public List<LimsSampleInfoVO> selectVOPaginationList(LimsSampleInfoVO limsSampleInfoVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOPaginationList", limsSampleInfoVO);
    }

    @Override
    public List<LimsSampleInfoVO> selectVOPersonSampleList(LimsSampleInfoVO query) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOPersonSampleList", query);
    }

    @Override
    public List<LimsSampleInfoVO> selectVOSampleInfoList(LimsSampleInfoVO query) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOSampleInfoList", query);
    }

    public int deleteByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().update(namespace() + ".deleteByCaseId", caseId);
    }

    public int deleteByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().update(namespace() + ".deleteByConsignmentId", consignmentId);
    }

    public boolean deleteByPersonId(Integer personId) {
        return this.getSqlSessionTemplate().update(namespace() + ".deleteByPersonId", personId) > 0;
    }

    public boolean updateById(LimsSampleInfo limsSampleInfo) {
        return this.getSqlSessionTemplate().update(namespace() + ".updateById", limsSampleInfo) > 0;
    }

    @Override
    public boolean updateSampleType(LimsSampleInfo sampleInfo) {
        return this.getSqlSessionTemplate().update(namespace() + ".updateSampleType", sampleInfo) > 0;
    }

    public List<LimsSampleInfo> selectListBySampleId(Integer sampleId){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListBySampleId",sampleId);
    }

    public int updatePhotoPath(LimsSampleInfo limsSampleInfo) {
        return this.getSqlSessionTemplate().update(namespace() + ".updatePhotoPath", limsSampleInfo);
    }

    public List<LimsSampleInfo> selectAcceptStatusSortListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAcceptStatusSortListByCaseId", caseId);
    }

    public List<LimsSampleInfo> selectList(LimsSampleInfo evidenceLimsSampleInfo) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectList", evidenceLimsSampleInfo);
    }

    @Override
    public boolean updateSubmitFlag(LimsSampleInfo limsSampleInfo) {
        return this.getSqlSessionTemplate().update(namespace()+".updateSubmitFlag",limsSampleInfo)>0;
    }

    @Override
    public int updateByEvidenceNo(LimsSampleInfo limsSampleInfo) {
        return this.getSqlSessionTemplate().update(this.namespace()+".updateByEvidenceNo",limsSampleInfo);
    }

}
