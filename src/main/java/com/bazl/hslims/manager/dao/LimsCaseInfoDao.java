package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.XkFeedbackCaseInfo;
import com.bazl.hslims.manager.model.po.LimsCaseInfo;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface LimsCaseInfoDao extends BaseDao<LimsCaseInfo, Integer> {

    public LimsCaseInfo selectByConsignmentId(Integer consignmentId);

    public LimsCaseInfo selectListByConsignmentId(Integer consignmentId);

    public LimsCaseInfo selectListByCaseNo(String caseNo);

    public List<LimsCaseInfo> selectListByCaseXkNo(String caseXkNo);

    public List<LimsCaseInfo> selectNotConsignmentNoList(LimsCaseInfo limsCaseInfo);

    public int updateCaseXkAno(LimsCaseInfo limsCaseInfo);

    public int selectVOCount(LimsCaseInfoVO limsCaseInfoVO);

    public List<LimsCaseInfoVO> selectVOPaginationList(LimsCaseInfoVO limsCaseInfoVO);

    public List<LimsCaseInfoVO> selectVOAllList(LimsCaseInfoVO limsCaseInfoVO);
    public int selectVOCnt(LimsCaseInfoVO limsCaseInfoVO);

    public List<LimsCaseInfoVO> selectVOList(LimsCaseInfoVO limsCaseInfoVO);

    public int refuseCase(LimsCaseInfo caseInfo);

    public List<LimsCaseInfoVO> selectVOGeneralQueryList(LimsCaseInfoVO limsCaseInfoVO);

    public int selectVOGeneralQueryCount(LimsCaseInfoVO limsCaseInfoVO);

    public int deleteByConsignmentId(Integer consignmentId);

    public List<LimsCaseInfoVO> selectVOIdentifyBookList(LimsCaseInfoVO limsCaseInfoVO);

    public int selectVOIdentifyBookCount(LimsCaseInfoVO limsCaseInfoVO);

    public List<XkFeedbackCaseInfo> selectAllNotFeedbak();

}
