package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsPcrRecordDao;
import com.bazl.hslims.manager.model.po.LimsPcrRecord;
import com.bazl.hslims.manager.model.po.LimsPcrRecordSample;
import com.bazl.hslims.utils.ListUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
@Repository
public class LimsPcrRecordDaoImpl extends BaseDaoImpl<LimsPcrRecord, Integer> implements LimsPcrRecordDao {

    @Override
    public String namespace() {
        return LimsPcrRecord.class.getName();
    }


    @Override
    public int insertLimsPcrRecordSample(LimsPcrRecordSample sample) {
        return this.getSqlSessionTemplate().insert(this.namespace() + ".insertLimsPcrRecordSample", sample);
    }


    @Override
    public int updateLimsPcrRecordSample(LimsPcrRecordSample sample) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".updateLimsPcrRecordSample", sample);
    }


    @Override
    public int deleteLimsPcrRecordSample(LimsPcrRecordSample sample) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteLimsPcrRecordSample", sample);
    }


    @Override
    public List<LimsPcrRecordSample> selectSampleListByPcrRecordId(Integer pcrRecordId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectSampleListByPcrRecordId", pcrRecordId);
    }

    @Override
    public int deleteLimsPcrRecordSamplesByRecordId(Integer pcrRecordId) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteLimsPcrRecordSamplesByRecordId", pcrRecordId);
    }


    @Override
    public int selectCountByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectCountByCaseId", caseId);
    }

    @Override
    public List<LimsPcrRecord> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCaseId", caseId);
    }

    @Override
    public LimsPcrRecordSample selectListBySampleId(Integer sampleId) {
        List<LimsPcrRecordSample> list = this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListBySampleId", sampleId);
        return ListUtils.isNotNullAndEmptyList(list) ? list.get(0) : null;
    }

    @Override
    public int selectCountBySampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selecCountBySampleNo",sampleNo);
    }

    @Override
    public List<LimsPcrRecordSample> selectListBySampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListBySampleNo",sampleNo);
    }

    @Override
    public List<LimsPcrRecord> selectSampleNoByTaskId(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectSampleNoByTaskId",sampleNo);
    }

    @Override
    public LimsPcrRecord selectByTaskId(Integer taskId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectByTaskId",taskId);
    }
}
