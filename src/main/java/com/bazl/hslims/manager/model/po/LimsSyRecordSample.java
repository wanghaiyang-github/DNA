package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/10.
 */
public class LimsSyRecordSample implements Serializable {
    private Integer id;
    private Integer limsSyRecordId;
    private Integer sampleId;
    private String sampleNo;
    private String boardNo;
    private String sampleName;
    private String sampleTypeName;
    private String samplePosition;
    private String standardFlag;
    private String createPerson;
    private Date createDatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLimsSyRecordId() {
        return limsSyRecordId;
    }

    public void setLimsSyRecordId(Integer limsSyRecordId) {
        this.limsSyRecordId = limsSyRecordId;
    }

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

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public String getSamplePosition() {
        return samplePosition;
    }

    public void setSamplePosition(String samplePosition) {
        this.samplePosition = samplePosition;
    }

    public String getStandardFlag() {
        return standardFlag;
    }

    public void setStandardFlag(String standardFlag) {
        this.standardFlag = standardFlag;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleTypeName() { return sampleTypeName; }

    public void setSampleTypeName(String sampleTypeName) { this.sampleTypeName = sampleTypeName; }
}
