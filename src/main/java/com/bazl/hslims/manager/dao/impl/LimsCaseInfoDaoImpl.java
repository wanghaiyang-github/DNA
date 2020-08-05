package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsCaseInfoDao;
import com.bazl.hslims.manager.model.XkFeedbackCaseInfo;
import com.bazl.hslims.manager.model.po.LimsCaseInfo;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
@Repository
public class LimsCaseInfoDaoImpl extends BaseDaoImpl<LimsCaseInfo, Integer> implements LimsCaseInfoDao {


    @Override
    public String namespace() {
        return LimsCaseInfo.class.getName();
    }

    public LimsCaseInfo selectByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectByConsignmentId", consignmentId);
    }

    public LimsCaseInfo selectListByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectListByConsignmentId", consignmentId);
    }

    public LimsCaseInfo selectListByCaseNo(String caseNo) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectListByCaseNo", caseNo);
    }

    @Override
    public List<LimsCaseInfo> selectListByCaseXkNo(String caseXkNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListByCaseXkNo",caseXkNo);
    }

    @Override
    public List<LimsCaseInfo> selectNotConsignmentNoList(LimsCaseInfo limsCaseInfo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectNotConsignmentNoList",limsCaseInfo);
    }

    @Override
    public int updateCaseXkAno(LimsCaseInfo limsCaseInfo) {
        return this.getSqlSessionTemplate().update(this.namespace()+".updateCaseXkAno",limsCaseInfo);
    }


    @Override
    public int selectVOCount(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOCount", limsCaseInfoVO);
    }

    @Override
    public List<LimsCaseInfoVO> selectVOPaginationList(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOPaginationList", limsCaseInfoVO);
    }

    @Override
    public List<LimsCaseInfoVO> selectVOAllList(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOAllList", limsCaseInfoVO);
    }
    @Override
    public int selectVOCnt(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOCnt", limsCaseInfoVO);
    }

    public List<LimsCaseInfoVO> selectVOList(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOList", limsCaseInfoVO);
    }

    public int refuseCase(LimsCaseInfo caseInfo){
        return this.getSqlSessionTemplate().update(this.namespace() + ".refuseCase", caseInfo);
    }

    public List<LimsCaseInfoVO> selectVOGeneralQueryList(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOGeneralQueryList", limsCaseInfoVO);
    }

    public int selectVOGeneralQueryCount(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOGeneralQueryCount", limsCaseInfoVO);
    }

    public int deleteByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteByConsignmentId",consignmentId);
    }

    public List<LimsCaseInfoVO> selectVOIdentifyBookList(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOIdentifyBookList", limsCaseInfoVO);
    }

    public int selectVOIdentifyBookCount(LimsCaseInfoVO limsCaseInfoVO) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOIdentifyBookCount", limsCaseInfoVO);
    }

    public List<XkFeedbackCaseInfo> selectAllNotFeedbak() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectNotFeedbackCaseList");
    }

}
