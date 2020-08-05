package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.LimsExtractRecord;
import com.bazl.hslims.manager.model.po.LimsExtractRecordSample;
import com.bazl.hslims.manager.model.vo.LimsExtractRecordVO;
import com.bazl.hslims.web.datamodel.ExtractRecordDataModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public interface LimsExtractRecordService {

    public LimsExtractRecord selectById(Integer id);
    public List<LimsExtractRecordSample> selectSampleListByExtractRecordId(Integer extractRecordId);

    public int selectCount(LimsExtractRecordVO query);
    public List<LimsExtractRecord> selectPaginationList(LimsExtractRecordVO query, PageInfo pageInfo);

    public int selectCountByCaseId(Integer caseId);

    public List<LimsExtractRecord> selectListByCaseId(Integer caseId);

    public Integer addRecord(ExtractRecordDataModel dataModel);

    public Integer updateRecord(ExtractRecordDataModel dataModel);

    public boolean deleteById(Integer extractRecordId);

    public List<LimsExtractRecordSample> selectListBySampleId(Integer sampleId);

    int selectCountBySampleNo(String sampleNo);

     List<LimsExtractRecordSample> selectListBySampleNo(String sampleNo);

    List<LimsExtractRecordSample> selectListSpitSampleNo(String sampleNo);

    List<LimsExtractRecord> selectSampleNoByTaskId(String sampleNo);

    LimsExtractRecord selectByTaskId(Integer taskId);

}
