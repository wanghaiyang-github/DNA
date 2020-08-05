package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.LimsSyRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/10.
 */
public class LimsSyRecordVO extends AbstractBaseVO<LimsSyRecord> implements Serializable {


    public LimsSyRecordVO() {
        this.entity = new LimsSyRecord();
    }

    public LimsSyRecordVO(LimsSyRecord entity) {
        this.entity = entity;
    }

    private Date syDatetimeStartAt;

    private Date syDatetimeEndAt;

    private String caseNo;

    private Integer sampleId;
    private String sampleNo;

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public Date getSyDatetimeStartAt() {
        return syDatetimeStartAt;
    }

    public void setSyDatetimeStartAt(Date syDatetimeStartAt) {
        this.syDatetimeStartAt = syDatetimeStartAt;
    }

    public Date getSyDatetimeEndAt() {
        return syDatetimeEndAt;
    }

    public void setSyDatetimeEndAt(Date syDatetimeEndAt) {
        this.syDatetimeEndAt = syDatetimeEndAt;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }
}
