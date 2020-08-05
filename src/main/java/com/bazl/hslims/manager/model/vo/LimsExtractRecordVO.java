package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.LimsExtractRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/8.
 */
public class LimsExtractRecordVO extends AbstractBaseVO<LimsExtractRecord> implements Serializable {


    public LimsExtractRecordVO() {
        this.entity = new LimsExtractRecord();
    }

    public LimsExtractRecordVO(LimsExtractRecord entity) {
        this.entity = entity;
    }

    private Date extractDatetimeStart;

    private Date extractDatetimeEnd;

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

    public Date getExtractDatetimeStart() {
        return extractDatetimeStart;
    }

    public void setExtractDatetimeStart(Date extractDatetimeStart) {
        this.extractDatetimeStart = extractDatetimeStart;
    }

    public Date getExtractDatetimeEnd() {
        return extractDatetimeEnd;
    }

    public void setExtractDatetimeEnd(Date extractDatetimeEnd) {
        this.extractDatetimeEnd = extractDatetimeEnd;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }
}
