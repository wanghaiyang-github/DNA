package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.web.datamodel.QuickExamineRecordModel;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/13.
 */
public interface QuickExamineRecordService {

    public Map<String, Object> addRecord(QuickExamineRecordModel quickExamineRecordModel);

    public Map<String, Object> updateRecord(QuickExamineRecordModel quickExamineRecordModel);

}
