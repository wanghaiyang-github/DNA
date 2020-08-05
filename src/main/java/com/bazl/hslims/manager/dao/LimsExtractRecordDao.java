package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsExtractRecord;
import com.bazl.hslims.manager.model.po.LimsExtractRecordSample;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public interface LimsExtractRecordDao extends BaseDao<LimsExtractRecord, Integer> {

    public int insertLimsExtractRecordSample(LimsExtractRecordSample sample);

    public int updateLimsExtractRecordSample(LimsExtractRecordSample sample);

    public int deleteLimsExtractRecordSample(LimsExtractRecordSample sample);

    public List<LimsExtractRecordSample> selectSampleListByExtractRecordId(Integer extractRecordId);

    public int deleteLimsExtractRecordSamplesByRecordId(Integer extractRecordId);

    public int selectCountByCaseId(Integer caseId);

    public List<LimsExtractRecord> selectListByCaseId(Integer caseId);

    public List<LimsExtractRecordSample> selectListBySampleId(Integer sampleId);

    int selectCountBySampleNo(String sampleNo);

     List<LimsExtractRecordSample> selectListBySampleNo(String sampleNo);

    List<LimsExtractRecordSample> selectListSpitSampleNo(String sampleNo);

    List<LimsExtractRecord> selectSampleNoByTaskId(String sampleNo);

    LimsExtractRecord selectByTaskId(Integer taskId);
}
