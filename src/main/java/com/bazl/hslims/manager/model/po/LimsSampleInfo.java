package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsSampleInfo implements Serializable {
    private Integer id;

    private Integer consignmentId;

    private Integer caseId;

    private String sampleType;

    private String sampleTypeName;

    private String sampleName;

    private String sampleNo;

    private String samplePacking;

    private String sampleProperties;
    private String samplePropertiesName;
    private String otherPropertiesDesc;

    private String sampleDesc;

    private String sampleFlag;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date extractDatetime;

    private String extractPerson;

    private String acceptStatus;

    private Date acceptDatetime;

    private String acceptPerson;

    private String refuseReason;

    private String sampleState;

    private String instoredFlag;

    private String deleteFlag;

    private Date deleteDatetime;

    private Date createDatetime;

    private String createPerson;

    private Date updateDatetime;

    private String updatePerson;

    private Integer refPersonId;

    private String refPersonName;

    private String photoPath;

    private String remark;

    private String samplePostion;

    private String atomFlag;
    private String urgentFlag;
    private String difficultFlag;

    private Integer enterCount;

    private String personSystemNo;

    private String evidenceSysremNo;

    private Integer uploadStatus;

    private String auditStatus;

    private Integer sampleGeneId;

    private String extractPositionAndChangeMethod;

    private String sampleNoTemp;

    private String submitFlag;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date submitDatetime;

    private String submitPerson;

    private String sampleEntryType;

    private String targetSampleRole;

    private int sameGroupSample;

    private String sameGroupSampleRole;
    //物证编号
    private String evidenceNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(Integer consignmentId) {
        this.consignmentId = consignmentId;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleName() {
        return sampleName;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
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

    public String getSamplePacking() {
        return samplePacking;
    }

    public void setSamplePacking(String samplePacking) {
        this.samplePacking = samplePacking;
    }

    public String getSampleProperties() {
        return sampleProperties;
    }

    public void setSampleProperties(String sampleProperties) {
        this.sampleProperties = sampleProperties;
    }

    public String getSampleDesc() {
        return sampleDesc;
    }

    public void setSampleDesc(String sampleDesc) {
        this.sampleDesc = sampleDesc;
    }

    public String getDifficultFlag() {
        return difficultFlag;
    }

    public void setDifficultFlag(String difficultFlag) {
        this.difficultFlag = difficultFlag;
    }

    public String getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(String acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public Date getAcceptDatetime() {
        return acceptDatetime;
    }

    public void setAcceptDatetime(Date acceptDatetime) {
        this.acceptDatetime = acceptDatetime;
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getSampleState() {
        return sampleState;
    }

    public void setSampleState(String sampleState) {
        this.sampleState = sampleState;
    }

    public String getInstoredFlag() {
        return instoredFlag;
    }

    public void setInstoredFlag(String instoredFlag) {
        this.instoredFlag = instoredFlag;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getExtractDatetime() {
        return extractDatetime;
    }

    public void setExtractDatetime(Date extractDatetime) {
        this.extractDatetime = extractDatetime;
    }

    public Integer getRefPersonId() {
        return refPersonId;
    }

    public void setRefPersonId(Integer refPersonId) {
        this.refPersonId = refPersonId;
    }

    public String getRefPersonName() {
        return refPersonName;
    }

    public void setRefPersonName(String refPersonName) {
        this.refPersonName = refPersonName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getSamplePropertiesName() {
        return samplePropertiesName;
    }

    public void setSamplePropertiesName(String samplePropertiesName) {
        this.samplePropertiesName = samplePropertiesName;
    }

    public String getOtherPropertiesDesc() {
        return otherPropertiesDesc;
    }

    public void setOtherPropertiesDesc(String otherPropertiesDesc) {
        this.otherPropertiesDesc = otherPropertiesDesc;
    }

    public String getAtomFlag() {
        return atomFlag;
    }

    public void setAtomFlag(String atomFlag) {
        this.atomFlag = atomFlag;
    }

    public String getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(String urgentFlag) {
        this.urgentFlag = urgentFlag;
    }

    public String getExtractPerson() {
        return extractPerson;
    }

    public void setExtractPerson(String extractPerson) {
        this.extractPerson = extractPerson;
    }

    public Integer getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(Integer enterCount) {
        this.enterCount = enterCount;
    }

    public String getPersonSystemNo() {
        return personSystemNo;
    }

    public void setPersonSystemNo(String personSystemNo) {
        this.personSystemNo = personSystemNo;
    }

    public String getEvidenceSysremNo() {
        return evidenceSysremNo;
    }

    public void setEvidenceSysremNo(String evidenceSysremNo) {
        this.evidenceSysremNo = evidenceSysremNo;
    }

    public Integer getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(Integer uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public Date getDeleteDatetime() {
        return deleteDatetime;
    }

    public void setDeleteDatetime(Date deleteDatetime) {
        this.deleteDatetime = deleteDatetime;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getSampleGeneId() {
        return sampleGeneId;
    }

    public void setSampleGeneId(Integer sampleGeneId) {
        this.sampleGeneId = sampleGeneId;
    }

    public String getExtractPositionAndChangeMethod() {
        return extractPositionAndChangeMethod;
    }

    public void setExtractPositionAndChangeMethod(String extractPositionAndChangeMethod) {
        this.extractPositionAndChangeMethod = extractPositionAndChangeMethod;
    }
    public String getSampleNoTemp() {
        return sampleNoTemp;
    }

    public void setSampleNoTemp(String sampleNoTemp) {
        this.sampleNoTemp = sampleNoTemp;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    public Date getSubmitDatetime() {
        return submitDatetime;
    }

    public void setSubmitDatetime(Date submitDatetime) {
        this.submitDatetime = submitDatetime;
    }

    public String getSubmitPerson() {
        return submitPerson;
    }

    public void setSubmitPerson(String submitPerson) {
        this.submitPerson = submitPerson;
    }

    public String getSampleEntryType() {
        return sampleEntryType;
    }

    public void setSampleEntryType(String sampleEntryType) {
        this.sampleEntryType = sampleEntryType;
    }

    public String getTargetSampleRole() {
        return targetSampleRole;
    }

    public void setTargetSampleRole(String targetSampleRole) {
        this.targetSampleRole = targetSampleRole;
    }

    public int getSameGroupSample() {
        return sameGroupSample;
    }

    public void setSameGroupSample(int sameGroupSample) {
        this.sameGroupSample = sameGroupSample;
    }

    public String getSameGroupSampleRole() {
        return sameGroupSampleRole;
    }

    public void setSameGroupSampleRole(String sameGroupSampleRole) {
        this.sameGroupSampleRole = sameGroupSampleRole;
    }

    public String getSamplePostion() {
        return samplePostion;
    }

    public void setSamplePostion(String samplePostion) {
        this.samplePostion = samplePostion;
    }

    public String getEvidenceNo() {
        return evidenceNo;
    }

    public void setEvidenceNo(String evidenceNo) {
        this.evidenceNo = evidenceNo;
    }
}
