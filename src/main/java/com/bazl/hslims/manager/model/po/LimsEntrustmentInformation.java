package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsEntrustmentInformation implements Serializable {
    private Integer id;

    private Integer caseInformationId;

    private String acceptanceNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptanceDatetime;

    private String appraisalCompany;

    private String acceptancePeople;

    private String entrustCompanyCode;

    private String entrustCompany;

    private String postalAddress;

    private String postalNo;

    private String censorship1;

    private String censorship1Phone;

    private String censorship1CnoName;

    private String censorship1Cno;

    private String censorship2;

    private String censorship2Phone;

    private String censorship2CnoName;

    private String censorship2Cno;

    private String admissibility;

    private String deleteFlag;

    private String entruustCompanyPhone;

    private String entrustingSerial;

    private String faxNo;

    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date entrustmentDatetime;

    private String post;

    private String appraisalPoints;

    private String entrustDetails;

    private String originalIdentification;

    private String reJustification;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseInformationId() {
        return caseInformationId;
    }

    public void setCaseInformationId(Integer caseInformationId) {
        this.caseInformationId = caseInformationId;
    }

    public String getAcceptanceNo() {
        return acceptanceNo;
    }

    public void setAcceptanceNo(String acceptanceNo) {
        this.acceptanceNo = acceptanceNo;
    }

    public Date getAcceptanceDatetime() {
        return acceptanceDatetime;
    }

    public void setAcceptanceDatetime(Date acceptanceDatetime) {
        this.acceptanceDatetime = acceptanceDatetime;
    }

    public String getEntrustCompanyCode() {
        return entrustCompanyCode;
    }

    public void setEntrustCompanyCode(String entrustCompanyCode) {
        this.entrustCompanyCode = entrustCompanyCode;
    }

    public String getAppraisalCompany() {
        return appraisalCompany;
    }

    public void setAppraisalCompany(String appraisalCompany) {
        this.appraisalCompany = appraisalCompany;
    }

    public String getAcceptancePeople() {
        return acceptancePeople;
    }

    public void setAcceptancePeople(String acceptancePeople) {
        this.acceptancePeople = acceptancePeople;
    }

    public String getEntrustCompany() {
        return entrustCompany;
    }

    public void setEntrustCompany(String entrustCompany) {
        this.entrustCompany = entrustCompany;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostalNo() {
        return postalNo;
    }

    public void setPostalNo(String postalNo) {
        this.postalNo = postalNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCensorship1() {
        return censorship1;
    }

    public void setCensorship1(String censorship1) {
        this.censorship1 = censorship1;
    }

    public String getCensorship1Phone() {
        return censorship1Phone;
    }

    public void setCensorship1Phone(String censorship1Phone) {
        this.censorship1Phone = censorship1Phone;
    }

    public String getCensorship1CnoName() {
        return censorship1CnoName;
    }

    public void setCensorship1CnoName(String censorship1CnoName) {
        this.censorship1CnoName = censorship1CnoName;
    }

    public String getCensorship1Cno() {
        return censorship1Cno;
    }

    public void setCensorship1Cno(String censorship1Cno) {
        this.censorship1Cno = censorship1Cno;
    }

    public String getCensorship2() {
        return censorship2;
    }

    public void setCensorship2(String censorship2) {
        this.censorship2 = censorship2;
    }

    public String getCensorship2Phone() {
        return censorship2Phone;
    }

    public void setCensorship2Phone(String censorship2Phone) {
        this.censorship2Phone = censorship2Phone;
    }

    public String getCensorship2CnoName() {
        return censorship2CnoName;
    }

    public void setCensorship2CnoName(String censorship2CnoName) {
        this.censorship2CnoName = censorship2CnoName;
    }

    public String getCensorship2Cno() {
        return censorship2Cno;
    }

    public void setCensorship2Cno(String censorship2Cno) {
        this.censorship2Cno = censorship2Cno;
    }

    public String getAdmissibility() {
        return admissibility;
    }

    public void setAdmissibility(String admissibility) {
        this.admissibility = admissibility;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getEntruustCompanyPhone() {
        return entruustCompanyPhone;
    }

    public void setEntruustCompanyPhone(String entruustCompanyPhone) {
        this.entruustCompanyPhone = entruustCompanyPhone;
    }

    public String getEntrustingSerial() {
        return entrustingSerial;
    }

    public void setEntrustingSerial(String entrustingSerial) {
        this.entrustingSerial = entrustingSerial;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public Date getEntrustmentDatetime() {
        return entrustmentDatetime;
    }

    public void setEntrustmentDatetime(Date entrustmentDatetime) {
        this.entrustmentDatetime = entrustmentDatetime;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getAppraisalPoints() {
        return appraisalPoints;
    }

    public void setAppraisalPoints(String appraisalPoints) {
        this.appraisalPoints = appraisalPoints;
    }

    public String getEntrustDetails() {
        return entrustDetails;
    }

    public void setEntrustDetails(String entrustDetails) {
        this.entrustDetails = entrustDetails;
    }

    public String getOriginalIdentification() {
        return originalIdentification;
    }

    public void setOriginalIdentification(String originalIdentification) {
        this.originalIdentification = originalIdentification;
    }

    public String getReJustification() {
        return reJustification;
    }

    public void setReJustification(String reJustification) {
        this.reJustification = reJustification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}