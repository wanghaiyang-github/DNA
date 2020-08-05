package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.CaseInnerMatchedDao;
import com.bazl.hslims.manager.model.po.CaseInnerMatched;
import com.bazl.hslims.manager.services.common.CaseInnerMatchedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
@Service
public class CaseInnerMatchedServiceImpl implements CaseInnerMatchedService {

    @Autowired
    CaseInnerMatchedDao caseInnerMatchedDao;

    @Override
    public int insert(CaseInnerMatched caseInnerMatched){
        int result = 0;
        result = caseInnerMatchedDao.insert(caseInnerMatched);
        return result;
    }

    @Override
    public int delete(CaseInnerMatched caseInnerMatched){
        return caseInnerMatchedDao.delete(caseInnerMatched);
    }

    @Override
    public List<CaseInnerMatched> selectListByCaseId(Integer caseId){
        return caseInnerMatchedDao.selectListByCaseId(caseId);
    }

    @Override
    public List<CaseInnerMatched> selectListSampleId(Integer sampleId) {
        return caseInnerMatchedDao.selectListSampleId(sampleId);
    }

}
