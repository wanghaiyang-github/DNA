package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.manager.model.LoginModel;

import java.io.Serializable;
import java.util.Date;

public class DelegateOrg extends LoginModel implements Serializable {

    public DelegateOrg() {
        setLoginType("2");
    }

    private Integer id;

    private String orgName;

    private String orgCode;

    private String rootFlag;

    private Integer parentId;

    private String loginPassword;

    private String ipaddrRange;

    private String contactPhonenum;

    private Date updateDatetime;

    private String updatePerson;

    private String deleteFlag;

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(String rootFlag) {
        this.rootFlag = rootFlag;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getIpaddrRange() {
        return ipaddrRange;
    }

    public void setIpaddrRange(String ipaddrRange) {
        this.ipaddrRange = ipaddrRange;
    }

    public String getContactPhonenum() {
        return contactPhonenum;
    }

    public void setContactPhonenum(String contactPhonenum) {
        this.contactPhonenum = contactPhonenum;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}