package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class QcPersonGene implements Serializable {
    private Integer id;

    private Integer panelId;

    private String panelName;

    private String qcPersonType;

    private String qcPersonNo;

    private String qcPersonName;

    private String qcPersonGender;

    private String qcPersonCardId;

    private String qcPersonPhonenum;

    private String qcLabUserFlag;

    private Integer qcLabUserId;

    private String qcPersonOrgName;

    private String qcPersonStrGene;

    private Date createDatetime;

    private String createPerson;

    private Date updateDatetime;

    private String updatePerson;

    private String deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getQcPersonType() {
        return qcPersonType;
    }

    public void setQcPersonType(String qcPersonType) {
        this.qcPersonType = qcPersonType;
    }

    public String getQcPersonNo() {
        return qcPersonNo;
    }

    public void setQcPersonNo(String qcPersonNo) {
        this.qcPersonNo = qcPersonNo;
    }

    public String getQcPersonName() {
        return qcPersonName;
    }

    public void setQcPersonName(String qcPersonName) {
        this.qcPersonName = qcPersonName;
    }

    public Integer getQcLabUserId() {
        return qcLabUserId;
    }

    public void setQcLabUserId(Integer qcLabUserId) {
        this.qcLabUserId = qcLabUserId;
    }

    public String getQcPersonOrgName() {
        return qcPersonOrgName;
    }

    public void setQcPersonOrgName(String qcPersonOrgName) {
        this.qcPersonOrgName = qcPersonOrgName;
    }

    public String getQcPersonStrGene() {
        return qcPersonStrGene;
    }

    public void setQcPersonStrGene(String qcPersonStrGene) {
        this.qcPersonStrGene = qcPersonStrGene;
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

    public String getQcLabUserFlag() {
        return qcLabUserFlag;
    }

    public void setQcLabUserFlag(String qcLabUserFlag) {
        this.qcLabUserFlag = qcLabUserFlag;
    }

    public String getQcPersonGender() {
        return qcPersonGender;
    }

    public void setQcPersonGender(String qcPersonGender) {
        this.qcPersonGender = qcPersonGender;
    }

    public String getQcPersonCardId() {
        return qcPersonCardId;
    }

    public void setQcPersonCardId(String qcPersonCardId) {
        this.qcPersonCardId = qcPersonCardId;
    }

    public String getQcPersonPhonenum() {
        return qcPersonPhonenum;
    }

    public void setQcPersonPhonenum(String qcPersonPhonenum) {
        this.qcPersonPhonenum = qcPersonPhonenum;
    }
}