package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.LimsPcrRecord;
import com.bazl.hslims.manager.model.po.LimsPcrRecordSample;
import com.bazl.hslims.manager.model.vo.LimsPcrRecordVO;
import com.bazl.hslims.web.datamodel.PcrRecordDataModel;

import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public interface LimsPcrRecordService {

    public LimsPcrRecord selectById(Integer id);
    public List<LimsPcrRecordSample> selectSampleListByPcrRecordId(Integer extractRecordId);

    public int selectCount(LimsPcrRecordVO query);
    public List<LimsPcrRecord> selectPaginationList(LimsPcrRecordVO query, PageInfo pageInfo);

    public int selectCountByCaseId(Integer caseId);

    public List<LimsPcrRecord> selectListByCaseId(Integer caseId);

    public Integer addRecord(PcrRecordDataModel dataModel);

    public Integer updateRecord(PcrRecordDataModel dataModel);

    public boolean deleteById(Integer extractRecordId);

    public LimsPcrRecordSample selectListBySampleId(Integer sampleId);

    int selectCountBySampleNo(String sampleNo);

    public List<LimsPcrRecordSample> selectListBySampleNo(String sampleNo);

    List<LimsPcrRecord> selectSampleNoByTaskId(String sampleNo);

    LimsPcrRecord selectByTaskId(Integer taskId);

}
