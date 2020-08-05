package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.CaseCompareResultInfoDao;
import com.bazl.hslims.manager.model.po.CaseCompareResultInfo;
import com.bazl.hslims.manager.services.common.CaseCompareResultInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */
@Service
public class CaseCompareResultInfoServiceImpl implements CaseCompareResultInfoService {

    @Autowired
    CaseCompareResultInfoDao caseCompareResultInfoDao;

    public int deleteByCaseId (Integer caseId) {
        return caseCompareResultInfoDao.deleteByCaseId(caseId);
    }

    public int insert (CaseCompareResultInfo compareResultInfo) {
        return caseCompareResultInfoDao.insert(compareResultInfo);
    }

    public List<CaseCompareResultInfo> selectListByCaseId(Integer caseId) {
        return caseCompareResultInfoDao.selectListByCaseId(caseId);
    }

}
