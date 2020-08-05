package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class LimsConsignment implements Serializable {
    private Integer id;

    private Integer caseId;

    private String consignmentNo;

    private String additionalFlag;

    private String entrustingSerial;

    private String delegateOrg;

    private String delegateOrgDesc;

    private String delegateOrgPhone;

    private String delegator1;

    private String delegator1Cno;

    private String delegator1Phone;

    private String delegator2;

    private String delegator2Cno;

    private String delegator2Phone;

    private Date delegateDatetime;

    private String preIdentifyDesc;

    private String reidentifyReason;

    private String status;

    private String acceptor;

    private Date acceptDatetime;

    private String refuseReason;

    private String identifyRequirement;

    private String matchCaseNo;

    private String identifyRequirementOther;

    private String instoredFlag;

    private Date createDatetime;

    private String createPerson;

    private Date updateDatetime;

    private String updatePerson;

    private String deleteFlag;

    private Date deleteDatetime;

    private String remark;

    private String delegator1Position;

    private String delegator2Position;

    private String delegator1Cname;

    private String delegator2Cname;

    private String postalAddress;

    private String postNo;

    private String faxNo;

    private String identifyKernelName;

    public String getEntrustingSerial() {
        return entrustingSerial;
    }

    public void setEntrustingSerial(String entrustingSerial) {
        this.entrustingSerial = entrustingSerial;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getAdditionalFlag() {
        return additionalFlag;
    }

    public void setAdditionalFlag(String additionalFlag) {
        this.additionalFlag = additionalFlag;
    }

    public String getDelegateOrg() {
        return delegateOrg;
    }

    public void setDelegateOrg(String delegateOrg) {
        this.delegateOrg = delegateOrg;
    }

    public String getDelegateOrgDesc() {
        return delegateOrgDesc;
    }

    public void setDelegateOrgDesc(String delegateOrgDesc) {
        this.delegateOrgDesc = delegateOrgDesc;
    }

    public String getDelegateOrgPhone() {
        return delegateOrgPhone;
    }

    public void setDelegateOrgPhone(String delegateOrgPhone) {
        this.delegateOrgPhone = delegateOrgPhone;
    }

    public String getDelegator1() {
        return delegator1;
    }

    public void setDelegator1(String delegator1) {
        this.delegator1 = delegator1;
    }

    public String getDelegator1Phone() {
        return delegator1Phone;
    }

    public void setDelegator1Phone(String delegator1Phone) {
        this.delegator1Phone = delegator1Phone;
    }

    public String getDelegator2() {
        return delegator2;
    }

    public void setDelegator2(String delegator2) {
        this.delegator2 = delegator2;
    }

    public String getDelegator2Phone() {
        return delegator2Phone;
    }

    public void setDelegator2Phone(String delegator2Phone) {
        this.delegator2Phone = delegator2Phone;
    }

    public Date getDelegateDatetime() {
        return delegateDatetime;
    }

    public void setDelegateDatetime(Date delegateDatetime) {
        this.delegateDatetime = delegateDatetime;
    }

    public String getPreIdentifyDesc() {
        return preIdentifyDesc;
    }

    public void setPreIdentifyDesc(String preIdentifyDesc) {
        this.preIdentifyDesc = preIdentifyDesc;
    }

    public String getReidentifyReason() {
        return reidentifyReason;
    }

    public void setReidentifyReason(String reidentifyReason) {
        this.reidentifyReason = reidentifyReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public Date getAcceptDatetime() {
        return acceptDatetime;
    }

    public void setAcceptDatetime(Date acceptDatetime) {
        this.acceptDatetime = acceptDatetime;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public String getMatchCaseNo() {
        return matchCaseNo;
    }

    public void setMatchCaseNo(String matchCaseNo) {
        this.matchCaseNo = matchCaseNo;
    }

    public String getIdentifyRequirement() {
        return identifyRequirement;
    }

    public void setIdentifyRequirement(String identifyRequirement) {
        this.identifyRequirement = identifyRequirement;
    }

    public String getIdentifyRequirementOther() {
        return identifyRequirementOther;
    }

    public void setIdentifyRequirementOther(String identifyRequirementOther) {
        this.identifyRequirementOther = identifyRequirementOther;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDelegator1Cno() {
        return delegator1Cno;
    }

    public void setDelegator1Cno(String delegator1Cno) {
        this.delegator1Cno = delegator1Cno;
    }

    public String getDelegator2Cno() {
        return delegator2Cno;
    }

    public void setDelegator2Cno(String delegator2Cno) {
        this.delegator2Cno = delegator2Cno;
    }

    public String getDelegator1Position() {
        return delegator1Position;
    }

    public void setDelegator1Position(String delegator1Position) {
        this.delegator1Position = delegator1Position;
    }

    public String getDelegator2Position() {
        return delegator2Position;
    }

    public void setDelegator2Position(String delegator2Position) {
        this.delegator2Position = delegator2Position;
    }

    public String getDelegator1Cname() {
        return delegator1Cname;
    }

    public void setDelegator1Cname(String delegator1Cname) {
        this.delegator1Cname = delegator1Cname;
    }

    public String getDelegator2Cname() {
        return delegator2Cname;
    }

    public void setDelegator2Cname(String delegator2Cname) {
        this.delegator2Cname = delegator2Cname;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getIdentifyKernelName() {
        return identifyKernelName;
    }

    public void setIdentifyKernelName(String identifyKernelName) {
        this.identifyKernelName = identifyKernelName;
    }
}