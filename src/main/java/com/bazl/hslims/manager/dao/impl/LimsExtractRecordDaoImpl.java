package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsExtractRecordDao;
import com.bazl.hslims.manager.model.po.LimsExtractRecord;
import com.bazl.hslims.manager.model.po.LimsExtractRecordSample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
@Repository
public class LimsExtractRecordDaoImpl extends BaseDaoImpl<LimsExtractRecord, Integer> implements LimsExtractRecordDao {

    @Override
    public String namespace() {
        return LimsExtractRecord.class.getName();
    }


    public int insertLimsExtractRecordSample(LimsExtractRecordSample sample) {
        return this.getSqlSessionTemplate().insert(this.namespace() + ".insertLimsExtractRecordSample", sample);
    }


    public int updateLimsExtractRecordSample(LimsExtractRecordSample sample) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".updateLimsExtractRecordSample", sample);
    }


    public int deleteLimsExtractRecordSample(LimsExtractRecordSample sample) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteLimsExtractRecordSample", sample);
    }


    public List<LimsExtractRecordSample> selectSampleListByExtractRecordId(Integer extractRecordId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectSampleListByExtractRecordId", extractRecordId);
    }

    public int deleteLimsExtractRecordSamplesByRecordId(Integer extractRecordId) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteLimsExtractRecordSamplesByRecordId", extractRecordId);
    }

    public int selectCountByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectCountByCaseId", caseId);
    }

    public List<LimsExtractRecord> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCaseId", caseId);
    }

    public List<LimsExtractRecordSample> selectListBySampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListBySampleId",sampleId);
    }

    @Override
    public int selectCountBySampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectCountBySampleNo",sampleNo);
    }

    @Override
    public List<LimsExtractRecordSample> selectListBySampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListBySampleNo",sampleNo);
    }

    @Override
    public List<LimsExtractRecordSample> selectListSpitSampleNo(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListSpitSampleNo",sampleNo);
    }

    @Override
    public List<LimsExtractRecord> selectSampleNoByTaskId(String sampleNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectSampleNoByTaskId",sampleNo);
    }

    @Override
    public LimsExtractRecord selectByTaskId(Integer taskId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectByTaskId",taskId);
    }
}
