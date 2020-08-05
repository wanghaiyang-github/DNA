package com.bazl.hslims.manager.dao;


import com.bazl.hslims.manager.model.po.LimsSyRecord;
import com.bazl.hslims.manager.model.po.LimsSyRecordSample;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public interface LimsSyRecordDao extends BaseDao<LimsSyRecord, Integer> {


    public int insertLimsSyRecordSample(LimsSyRecordSample sample);

    public int updateLimsSyRecordSample(LimsSyRecordSample sample);

    public int deleteLimsSyRecordSample(LimsSyRecordSample sample);

    public List<LimsSyRecordSample> selectSampleListBySyRecordId(Integer syRecordId);

    public int deleteLimsSyRecordSamplesByRecordId(Integer syRecordId);

    public int selectCountByCaseId(Integer caseId);

    public List<LimsSyRecord> selectListByCaseId(Integer caseId);

    int selectCountBySampleNo(String sampleNo);

    public List<LimsSyRecordSample> selectListBySampleNo(String sampleNo);

    List<LimsSyRecord> selectSampleNoByTaskId(String sampleNo);

    LimsSyRecord selectByTaskId(Integer taskId);
}
