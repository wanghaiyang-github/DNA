package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.LimsSyRecord;
import com.bazl.hslims.manager.model.po.LimsSyRecordSample;
import com.bazl.hslims.manager.model.vo.LimsSyRecordVO;
import com.bazl.hslims.web.datamodel.SyRecordDataModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public interface LimsSyRecordService {

    public LimsSyRecord selectById(Integer id);
    public List<LimsSyRecordSample> selectSampleListBySyRecordId(Integer extractRecordId);

    public int selectCount(LimsSyRecordVO query);
    public List<LimsSyRecord> selectPaginationList(LimsSyRecordVO query, PageInfo pageInfo);


    public int selectCountByCaseId(Integer caseId);

    public List<LimsSyRecord> selectListByCaseId(Integer caseId);

    public Integer addRecord(SyRecordDataModel dataModel, String boardNo);

    public Integer updateRecord(SyRecordDataModel dataModel, String boardNo);

    public boolean deleteById(Integer extractRecordId);

    int selectCountBySampleNo(String sampleNo);

    public List<LimsSyRecordSample> selectListBySampleNo(String sampleNo);

    List<LimsSyRecord> selectSampleNoByTaskId(String sampleNo);

    LimsSyRecord selectByTaskId(Integer taskId);

}
