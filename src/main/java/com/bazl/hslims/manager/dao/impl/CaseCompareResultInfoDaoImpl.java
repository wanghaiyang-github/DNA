package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.CaseCompareResultInfoDao;
import com.bazl.hslims.manager.model.po.CaseCompareResultInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */
@Repository
public class CaseCompareResultInfoDaoImpl extends BaseDaoImpl<CaseCompareResultInfo, Integer> implements CaseCompareResultInfoDao {

    public String namespace() { return CaseCompareResultInfo.class.getName(); }

    public int deleteByCaseId (Integer caseId) {
        return this.getSqlSessionTemplate().delete(this.namespace() + ".deleteByCaseId", caseId);
    }

    public List<CaseCompareResultInfo> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCaseId", caseId);
    }

}
