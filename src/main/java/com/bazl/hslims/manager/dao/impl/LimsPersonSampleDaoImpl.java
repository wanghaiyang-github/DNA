package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsPersonSampleDao;
import com.bazl.hslims.manager.model.po.LimsPersonSample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangliu on 2018/5/13.
 */
@Repository
public class LimsPersonSampleDaoImpl extends BaseDaoImpl<LimsPersonSample, Integer> implements LimsPersonSampleDao {

    @Override
    public String namespace() {
        return LimsPersonSample.class.getName();
    }

    @Override
    public List<LimsPersonSample> selectListByEntrustmentId(Integer entrustmentId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByEntrustmentId",entrustmentId);
    }

    @Override
    public List<LimsPersonSample> selectListByCaseInformationId(Integer caseInformationId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCaseInformationId",caseInformationId);
    }

    @Override
    public List<LimsPersonSample> selectListBySampleLaboratoryNo(String sampleLaboratoryNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListBySampleLaboratoryNo",sampleLaboratoryNo);
    }
}
