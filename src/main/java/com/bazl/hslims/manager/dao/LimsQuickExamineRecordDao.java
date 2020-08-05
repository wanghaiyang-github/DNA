package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsQuickExamineRecord;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
public interface LimsQuickExamineRecordDao extends BaseDao<LimsQuickExamineRecord, Integer> {

    public int selectCountByCaseId(Integer caseId);

    public List<LimsQuickExamineRecord> selectListByCaseId(Integer caseId);

}
