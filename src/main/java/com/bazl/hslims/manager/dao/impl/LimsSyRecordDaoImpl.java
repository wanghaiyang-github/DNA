package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsSyRecordDao;
import com.bazl.hslims.manager.model.po.LimsSyRecord;
import com.bazl.hslims.manager.model.po.LimsSyRecordSample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
@Repository
public class LimsSyRecordDaoImpl extends BaseDaoImpl<LimsSyRecord, Integer> implements LimsSyRecordDao {


    @Override
    public String namespace() {
        return LimsSyRecord.class.getName();
    }



    public int insertLimsSyRecordSample(LimsSyRecordSample sample) {
        return this.getSqlSessionTemplate().insert(this.namespace() + ".insertLimsSyRecordSample", sample);
    }


    public int updateLimsSyRecordSample(LimsSyRecordSample sample) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".updateLimsSyRecordSample", sample);
    }


    public int deleteLimsSyRecordSample(LimsSyRecordSample sample) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteLimsSyRecordSample", sample);
    }


    public List<LimsSyRecordSample> selectSampleListBySyRecordId(Integer syRecordId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectSampleListBySyRecordId", syRecordId);
    }

    public int deleteLimsSyRecordSamplesByRecordId(Integer syRecordId) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteLimsSyRecordSamplesByRecordId", syRecordId);
    }


    public int selectCountByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectCountByCaseId", caseId);
    }

    public List<LimsSyRecord> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCaseId", caseId);
    }

    @Override
    public int selectCountBySampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectCountBySampleNo",sampleNo);
    }

    @Override
    public List<LimsSyRecordSample> selectListBySampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListBySampleNo",sampleNo);
    }

    @Override
    public List<LimsSyRecord> selectSampleNoByTaskId(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectSampleNoByTaskId",sampleNo);
    }

    @Override
    public LimsSyRecord selectByTaskId(Integer taskId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectByTaskId",taskId);
    }
}
