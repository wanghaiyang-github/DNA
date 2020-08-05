package com.bazl.hslims.manager.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/16.
 */
public class LimsConsignmentInfo {

    private Integer id;

    private Integer delegateOrgId;

    private String delegateOrgName;

    private String delegator;

    private String delegatorDuty;

    private String delegatorDutyName;

    private String delegatorCertificate;

    private String delegatorCertificateName;

    private String delegatorCertificateNo;

    private String delegatorPhone;

    private String createPerson;

    private Date createDatetime;

    private String updatePerson;

    private Date updateDatetime;

    private String deleteFlag;

    private String deletePerson;

    private Date deleteDatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDelegateOrgId() {
        return delegateOrgId;
    }

    public void setDelegateOrgId(Integer delegateOrgId) {
        this.delegateOrgId = delegateOrgId;
    }

    public String getDelegateOrgName() {
        return delegateOrgName;
    }

    public void setDelegateOrgName(String delegateOrgName) {
        this.delegateOrgName = delegateOrgName;
    }

    public String getDelegator() {
        return delegator;
    }

    public void setDelegator(String delegator) {
        this.delegator = delegator;
    }

    public String getDelegatorDuty() {
        return delegatorDuty;
    }

    public void setDelegatorDuty(String delegatorDuty) {
        this.delegatorDuty = delegatorDuty;
    }

    public String getDelegatorDutyName() {
        return delegatorDutyName;
    }

    public void setDelegatorDutyName(String delegatorDutyName) {
        this.delegatorDutyName = delegatorDutyName;
    }

    public String getDelegatorCertificate() {
        return delegatorCertificate;
    }

    public void setDelegatorCertificate(String delegatorCertificate) {
        this.delegatorCertificate = delegatorCertificate;
    }

    public String getDelegatorCertificateName() {
        return delegatorCertificateName;
    }

    public void setDelegatorCertificateName(String delegatorCertificateName) {
        this.delegatorCertificateName = delegatorCertificateName;
    }

    public String getDelegatorCertificateNo() {
        return delegatorCertificateNo;
    }

    public void setDelegatorCertificateNo(String delegatorCertificateNo) {
        this.delegatorCertificateNo = delegatorCertificateNo;
    }

    public String getDelegatorPhone() {
        return delegatorPhone;
    }

    public void setDelegatorPhone(String delegatorPhone) {
        this.delegatorPhone = delegatorPhone;
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

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDeletePerson() {
        return deletePerson;
    }

    public void setDeletePerson(String deletePerson) {
        this.deletePerson = deletePerson;
    }

    public Date getDeleteDatetime() {
        return deleteDatetime;
    }

    public void setDeleteDatetime(Date deleteDatetime) {
        this.deleteDatetime = deleteDatetime;
    }
}
