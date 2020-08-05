package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsPersonSample;

import java.util.List;

/**
 * Created by wangliu on 2018/5/13.
 */
public interface LimsPersonSampleDao extends BaseDao<LimsPersonSample, Integer>{

    public List<LimsPersonSample> selectListByEntrustmentId(Integer entrustmentId);

    public List<LimsPersonSample> selectListByCaseInformationId(Integer caseInformationId);

    public List<LimsPersonSample> selectListBySampleLaboratoryNo(String sampleLaboratoryNo);


}
