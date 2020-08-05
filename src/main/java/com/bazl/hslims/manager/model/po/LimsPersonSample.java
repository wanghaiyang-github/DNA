package com.bazl.hslims.manager.model.po;


import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsPersonSample implements Serializable {
    private Integer id;

    private Integer sampleId;

    private Integer entrustmentId;

    private Integer caseInformationId;

    private String personName;

    private String personGenderName;
    private String personGender;
    private String personIdcardNo;
    private String noIdcardRemark;
    private String personRace;

    private String sampleName;

    private String sampleType;

    private String sampleTypeCode;

    private String oneselfContact;
    private String oneselfContactCode;

    private String sampleLaboratoryNo;

    private String personType;
    private String personTypeCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date createDatetime;

    private String acceptanceType;

    private String deleteFlag;

    private String remark;

    public String getPersonRace() {
        return personRace;
    }

    public void setPersonRace(String personRace) {
        this.personRace = personRace;
    }

    public String getPersonGenderName() {
        return personGenderName;
    }

    public void setPersonGenderName(String personGenderName) {
        this.personGenderName = personGenderName;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    public String getPersonIdcardNo() {
        return personIdcardNo;
    }

    public void setPersonIdcardNo(String personIdcardNo) {
        this.personIdcardNo = personIdcardNo;
    }

    public String getNoIdcardRemark() {
        return noIdcardRemark;
    }

    public void setNoIdcardRemark(String noIdcardRemark) {
        this.noIdcardRemark = noIdcardRemark;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getCaseInformationId() {
        return caseInformationId;
    }

    public void setCaseInformationId(Integer caseInformationId) {
        this.caseInformationId = caseInformationId;
    }

    public String getSampleTypeCode() {
        return sampleTypeCode;
    }

    public void setSampleTypeCode(String sampleTypeCode) {
        this.sampleTypeCode = sampleTypeCode;
    }

    public String getPersonTypeCode() {
        return personTypeCode;
    }

    public void setPersonTypeCode(String personTypeCode) {
        this.personTypeCode = personTypeCode;
    }

    public String getOneselfContactCode() {
        return oneselfContactCode;
    }

    public void setOneselfContactCode(String oneselfContactCode) {
        this.oneselfContactCode = oneselfContactCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEntrustmentId() {
        return entrustmentId;
    }

    public void setEntrustmentId(Integer entrustmentId) {
        this.entrustmentId = entrustmentId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getOneselfContact() {
        return oneselfContact;
    }

    public void setOneselfContact(String oneselfContact) {
        this.oneselfContact = oneselfContact;
    }

    public String getSampleLaboratoryNo() {
        return sampleLaboratoryNo;
    }

    public void setSampleLaboratoryNo(String sampleLaboratoryNo) {
        this.sampleLaboratoryNo = sampleLaboratoryNo;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getAcceptanceType() {
        return acceptanceType;
    }

    public void setAcceptanceType(String acceptanceType) {
        this.acceptanceType = acceptanceType;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}