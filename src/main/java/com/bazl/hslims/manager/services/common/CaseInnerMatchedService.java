package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.CaseInnerMatched;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
public interface CaseInnerMatchedService {

    public int insert(CaseInnerMatched caseInnerMatched);

    public int delete(CaseInnerMatched caseInnerMatched);

    public List<CaseInnerMatched> selectListByCaseId(Integer caseId);

    public List<CaseInnerMatched> selectListSampleId(Integer sampleId);

}
