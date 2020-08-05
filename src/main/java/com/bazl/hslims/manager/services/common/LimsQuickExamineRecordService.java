package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsQuickExamineRecord;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
public interface LimsQuickExamineRecordService {

    public LimsQuickExamineRecord selectById(Integer id);

    public int deleteById(Integer id);

    public int selectCountByCaseId(Integer caseId);

    public List<LimsQuickExamineRecord> selectListByCaseId(Integer caseId);

}
