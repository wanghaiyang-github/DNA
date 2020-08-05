package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.LimsPcrRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/8.
 */
public class LimsPcrRecordVO extends AbstractBaseVO<LimsPcrRecord> implements Serializable {


    public LimsPcrRecordVO() {
        this.entity = new LimsPcrRecord();
    }

    public LimsPcrRecordVO(LimsPcrRecord entity) {
        this.entity = entity;
    }

    private Date pcrDatetimeStartAt;

    private Date pcrDatetimeEndAt;

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

    public Date getPcrDatetimeStartAt() {
        return pcrDatetimeStartAt;
    }

    public void setPcrDatetimeStartAt(Date pcrDatetimeStartAt) {
        this.pcrDatetimeStartAt = pcrDatetimeStartAt;
    }

    public Date getPcrDatetimeEndAt() {
        return pcrDatetimeEndAt;
    }

    public void setPcrDatetimeEndAt(Date pcrDatetimeEndAt) {
        this.pcrDatetimeEndAt = pcrDatetimeEndAt;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }
}
