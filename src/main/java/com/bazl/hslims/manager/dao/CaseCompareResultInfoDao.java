package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.CaseCompareResultInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */
public interface CaseCompareResultInfoDao extends BaseDao<CaseCompareResultInfo, Integer> {

    public int deleteByCaseId (Integer caseId);

    public List<CaseCompareResultInfo> selectListByCaseId(Integer caseId);

}
