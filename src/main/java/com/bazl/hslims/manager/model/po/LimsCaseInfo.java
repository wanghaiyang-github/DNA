package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsCaseInfo implements Serializable {
    private Integer id;

    private String caseNo;

    private String caseXkNo;

    private String caseXkAno;

    private String entrustmentType;

    private String caseName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date caseDatetime;

    private String caseLocation;

    private String caseLocationDesc;

    private String caseBrief;

    private String caseType;

    private String caseProperty;

    private String caseLevel;

    private String caseSpecialRemark;

    private String caseStatus;

    private String instoredFlag;

    private Date createDatetime;

    private String createPerson;

    private Date updateDatetime;

    private String updatePerson;

    private String deleteFlag;

    private Date deleteDatetime;

    private String jiajiFlag;

    private String remark;

    private Integer matchLimit;

    private String raceName;

    public String getEntrustmentType() {
        return entrustmentType;
    }

    public void setEntrustmentType(String entrustmentType) {
        this.entrustmentType = entrustmentType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseXkNo() {
        return caseXkNo;
    }

    public void setCaseXkNo(String caseXkNo) {
        this.caseXkNo = caseXkNo;
    }

    public String getCaseXkAno() {
        return caseXkAno;
    }

    public void setCaseXkAno(String caseXkAno) {
        this.caseXkAno = caseXkAno;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Date getCaseDatetime() {
        return caseDatetime;
    }

    public void setCaseDatetime(Date caseDatetime) {
        this.caseDatetime = caseDatetime;
    }

    public String getCaseLocation() {
        return caseLocation;
    }

    public void setCaseLocation(String caseLocation) {
        this.caseLocation = caseLocation;
    }

    public String getCaseLocationDesc() {
        return caseLocationDesc;
    }

    public void setCaseLocationDesc(String caseLocationDesc) {
        this.caseLocationDesc = caseLocationDesc;
    }

    public String getCaseBrief() {
        return caseBrief;
    }

    public void setCaseBrief(String caseBrief) {
        this.caseBrief = caseBrief;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String getCaseLevel() {
        return caseLevel;
    }

    public void setCaseLevel(String caseLevel) {
        this.caseLevel = caseLevel;
    }

    public String getCaseSpecialRemark() {
        return caseSpecialRemark;
    }

    public void setCaseSpecialRemark(String caseSpecialRemark) {
        this.caseSpecialRemark = caseSpecialRemark;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getInstoredFlag() {
        return instoredFlag;
    }

    public void setInstoredFlag(String instoredFlag) {
        this.instoredFlag = instoredFlag;
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

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteDatetime() {
        return deleteDatetime;
    }

    public void setDeleteDatetime(Date deleteDatetime) {
        this.deleteDatetime = deleteDatetime;
    }

    public String getJiajiFlag() {
        return jiajiFlag;
    }

    public void setJiajiFlag(String jiajiFlag) {
        this.jiajiFlag = jiajiFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMatchLimit() {
        return matchLimit;
    }

    public void setMatchLimit(Integer matchLimit) {
        this.matchLimit = matchLimit;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }
}