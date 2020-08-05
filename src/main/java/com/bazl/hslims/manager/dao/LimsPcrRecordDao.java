package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsPcrRecord;
import com.bazl.hslims.manager.model.po.LimsPcrRecordSample;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public interface LimsPcrRecordDao extends BaseDao<LimsPcrRecord, Integer> {


    public int insertLimsPcrRecordSample(LimsPcrRecordSample sample);

    public int updateLimsPcrRecordSample(LimsPcrRecordSample sample);

    public int deleteLimsPcrRecordSample(LimsPcrRecordSample sample);

    public List<LimsPcrRecordSample> selectSampleListByPcrRecordId(Integer pcrRecordId);

    public int deleteLimsPcrRecordSamplesByRecordId(Integer pcrRecordId);


    public int selectCountByCaseId(Integer caseId);

    public List<LimsPcrRecord> selectListByCaseId(Integer caseId);

    public LimsPcrRecordSample selectListBySampleId(Integer sampleId);

    int selectCountBySampleNo(String sampleNo);

    public List<LimsPcrRecordSample> selectListBySampleNo(String sampleNo);

    List<LimsPcrRecord> selectSampleNoByTaskId(String sampleNo);

    LimsPcrRecord selectByTaskId(Integer taskId);

}
