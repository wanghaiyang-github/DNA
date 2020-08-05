package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class LimsPcrRecordSample implements Serializable {
    private Integer id;

    private Integer limsPcrRecordId;

    private Integer sampleId;

    private String sampleNo;

    private String sampleName;

    private String sampleTypeName;

    private String standardFlag;

    private String primerUl;

    private String bufferUl;

    private String templateUl;

    private String ddwUl;

    private String createPerson;

    private Date createDatetime;

    private Date extractDatetime;

    private String pcrReagent;

    private String samplePosition;

    public String getBufferUl() {
        return bufferUl;
    }

    public void setBufferUl(String bufferUl) {
        this.bufferUl = bufferUl;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getDdwUl() {
        return ddwUl;
    }

    public void setDdwUl(String ddwUl) {
        this.ddwUl = ddwUl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLimsPcrRecordId() {
        return limsPcrRecordId;
    }

    public void setLimsPcrRecordId(Integer limsPcrRecordId) {
        this.limsPcrRecordId = limsPcrRecordId;
    }

    public String getPrimerUl() {
        return primerUl;
    }

    public void setPrimerUl(String primerUl) {
        this.primerUl = primerUl;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getStandardFlag() {
        return standardFlag;
    }

    public void setStandardFlag(String standardFlag) {
        this.standardFlag = standardFlag;
    }

    public String getTemplateUl() {
        return templateUl;
    }

    public void setTemplateUl(String templateUl) {
        this.templateUl = templateUl;
    }

    public Date getExtractDatetime() { return extractDatetime; }

    public void setExtractDatetime (Date extractDatetime) { this.extractDatetime = extractDatetime; }

    public String getPcrReagent() {
        return pcrReagent;
    }

    public void setPcrReagent(String pcrReagent) {
        this.pcrReagent = pcrReagent;
    }

    public String getSamplePosition() {
        return samplePosition;
    }

    public void setSamplePosition(String samplePosition) {
        this.samplePosition = samplePosition;
    }
}