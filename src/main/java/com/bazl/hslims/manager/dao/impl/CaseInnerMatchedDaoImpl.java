package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.CaseInnerMatchedDao;
import com.bazl.hslims.manager.model.po.CaseInnerMatched;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
@Repository
public class CaseInnerMatchedDaoImpl extends BaseDaoImpl implements CaseInnerMatchedDao {

    @Override
    public String namespace() {
        return CaseInnerMatched.class.getName();
    }

    public int delete(CaseInnerMatched caseInnerMatched){
        return this.getSqlSessionTemplate().delete(namespace() + ".delete",caseInnerMatched);
    }

    public List<CaseInnerMatched> selectListByCaseId(Integer caseId){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByCaseId", caseId);
    }

    public List<CaseInnerMatched> selectListSampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListSampleId", sampleId);
    }

}
