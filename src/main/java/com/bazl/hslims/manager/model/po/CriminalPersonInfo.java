package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018-05-11.
 */
public class CriminalPersonInfo implements Serializable {

    protected int id;
    protected String personNo;
    protected String personType;
    protected String personName;
    protected String idcardNo;
    protected String noIdcardRemark;
    protected String gender;
    protected String race;
    protected String domicile;
    protected String height;
    protected String weight;
    protected int collectOrgId;
    protected String collectOrgName;
    protected Date collectDatetime;
    protected String collectPerson;
    protected String collectorPhone;
    protected int status;
    protected String sampleType;
    protected String remark;

    protected Date createDatetime;
    protected String createPerson;
    protected Date updateDatetime;
    protected String updatePerson;

    /**
     * 删除状态 0-未删除 1-已删除
     */
    protected int isDeleted;

    private int sampleGeneId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public int getCollectOrgId() {
        return collectOrgId;
    }

    public void setCollectOrgId(int collectOrgId) {
        this.collectOrgId = collectOrgId;
    }

    public String getCollectOrgName() {
        return collectOrgName;
    }

    public void setCollectOrgName(String collectOrgName) {
        this.collectOrgName = collectOrgName;
    }

    public Date getCollectDatetime() {
        return collectDatetime;
    }

    public void setCollectDatetime(Date collectDatetime) {
        this.collectDatetime = collectDatetime;
    }

    public String getCollectPerson() {
        return collectPerson;
    }

    public void setCollectPerson(String collectPerson) {
        this.collectPerson = collectPerson;
    }

    public String getCollectorPhone() {
        return collectorPhone;
    }

    public void setCollectorPhone(String collectorPhone) {
        this.collectorPhone = collectorPhone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
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

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getNoIdcardRemark() {
        return noIdcardRemark;
    }

    public void setNoIdcardRemark(String noIdcardRemark) {
        this.noIdcardRemark = noIdcardRemark;
    }

    public int getSampleGeneId() {
        return sampleGeneId;
    }

    public void setSampleGeneId(int sampleGeneId) {
        this.sampleGeneId = sampleGeneId;
    }

}
