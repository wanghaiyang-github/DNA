package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.LimsSyRecord;
import com.bazl.hslims.manager.model.po.LimsSyRecordSample;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public class SyRecordDataModel implements Serializable {


    private LimsSyRecord limsSyRecord;

    private List<LimsSyRecordSample> limsSyRecordSampleList;

    public LimsSyRecord getLimsSyRecord() {
        return limsSyRecord;
    }

    public void setLimsSyRecord(LimsSyRecord limsSyRecord) {
        this.limsSyRecord = limsSyRecord;
    }

    public List<LimsSyRecordSample> getLimsSyRecordSampleList() {
        return limsSyRecordSampleList;
    }

    public void setLimsSyRecordSampleList(List<LimsSyRecordSample> limsSyRecordSampleList) {
        this.limsSyRecordSampleList = limsSyRecordSampleList;
    }
}
