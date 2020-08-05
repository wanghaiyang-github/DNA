package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.CaseCompareResultInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */
public interface CaseCompareResultInfoService {

    public int deleteByCaseId (Integer caseId);

    public int insert (CaseCompareResultInfo compareResultInfo);

    public List<CaseCompareResultInfo> selectListByCaseId(Integer caseId);

}
