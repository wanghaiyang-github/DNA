package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class LimsSampleGene implements Serializable {
    private Integer id;

    private Integer consignmentId;

    private Integer sampleId;

    private Integer caseId;

    private Integer panelId;

    private String sampleNo;

    private String sampleName;

    private String sampleType;

    private String sampleTypeName;

    private String geneType;

    private String reagentName;

    private String geneInfo;

    private String geneImgPath;

    private String auditStatus;

    private String auditPerson;

    private Date auditDatetime;

    private String auditDeniedReason;

    private String instoredFlag;

    private String deleteFlag;

    private Date createDatetime;

    private String createPerson;

    private Date updateDatetime;

    private String updatePerson;

    private Integer enterCount;

    private Integer refPersonId;

    private String refPersonName;

    private String totalProbability;

    private String sampleFlag;

    private String codisFileName;
    private String geneCount;

    private int criminalPersonId;
    private String criminalPersonNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { this.id = id; }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public Integer getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(Integer consignmentId) {
        this.consignmentId = consignmentId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
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

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getGeneImgPath() {
        return geneImgPath;
    }

    public void setGeneImgPath(String geneImgPath) {
        this.geneImgPath = geneImgPath;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditPerson() {
        return auditPerson;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson;
    }

    public Date getAuditDatetime() {
        return auditDatetime;
    }

    public void setAuditDatetime(Date auditDatetime) {
        this.auditDatetime = auditDatetime;
    }

    public String getAuditDeniedReason() {
        return auditDeniedReason;
    }

    public void setAuditDeniedReason(String auditDeniedReason) {
        this.auditDeniedReason = auditDeniedReason;
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

    public Integer getEnterCount() {
        return enterCount;
    }

    public void setEnterCount(Integer enterCount) {
        this.enterCount = enterCount;
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

    public String getTotalProbability() {
        return totalProbability;
    }

    public void setTotalProbability(String totalProbability) {
        this.totalProbability = totalProbability;
    }

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getGeneCount() {
        return geneCount;
    }

    public void setGeneCount(String geneCount) {
        this.geneCount = geneCount;
    }

    public String getCodisFileName() {
        return codisFileName;
    }

    public void setCodisFileName(String codisFileName) {
        this.codisFileName = codisFileName;
    }

    public int getCriminalPersonId() {
        return criminalPersonId;
    }

    public void setCriminalPersonId(int criminalPersonId) {
        this.criminalPersonId = criminalPersonId;
    }

    public String getCriminalPersonNo() {
        return criminalPersonNo;
    }

    public void setCriminalPersonNo(String criminalPersonNo) {
        this.criminalPersonNo = criminalPersonNo;
    }
}