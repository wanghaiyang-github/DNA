package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.LimsExtractRecord;
import com.bazl.hslims.manager.model.po.LimsPcrRecord;
import com.bazl.hslims.manager.model.po.LimsQuickExamineRecordSample;
import com.bazl.hslims.manager.model.po.LimsSyRecord;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
public class QuickExamineRecordModel {

    private Integer quickExamineRecordId;

    private LimsExtractRecord limsExtractRecord;

    private LimsPcrRecord limsPcrRecord;

    private LimsSyRecord limsSyRecord;

    private List<LimsQuickExamineRecordSample> limsQuickExamineRecordSampleList;

    public Integer getQuickExamineRecordId() {
        return quickExamineRecordId;
    }

    public void setQuickExamineRecordId(Integer quickExamineRecordId) {
        this.quickExamineRecordId = quickExamineRecordId;
    }

    public LimsExtractRecord getLimsExtractRecord() {
        return limsExtractRecord;
    }

    public void setLimsExtractRecord(LimsExtractRecord limsExtractRecord) {
        this.limsExtractRecord = limsExtractRecord;
    }

    public LimsPcrRecord getLimsPcrRecord() {
        return limsPcrRecord;
    }

    public void setLimsPcrRecord(LimsPcrRecord limsPcrRecord) {
        this.limsPcrRecord = limsPcrRecord;
    }

    public LimsSyRecord getLimsSyRecord() {
        return limsSyRecord;
    }

    public void setLimsSyRecord(LimsSyRecord limsSyRecord) {
        this.limsSyRecord = limsSyRecord;
    }

    public List<LimsQuickExamineRecordSample> getLimsQuickExamineRecordSampleList() {
        return limsQuickExamineRecordSampleList;
    }

    public void setLimsQuickExamineRecordSampleList(List<LimsQuickExamineRecordSample> limsQuickExamineRecordSampleList) {
        this.limsQuickExamineRecordSampleList = limsQuickExamineRecordSampleList;
    }
}
