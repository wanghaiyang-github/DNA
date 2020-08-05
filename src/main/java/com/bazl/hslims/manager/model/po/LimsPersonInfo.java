package com.bazl.hslims.manager.model.po;


import com.bazl.hslims.common.Constants;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class LimsPersonInfo implements Serializable {
    private Integer id;

    private Integer consignmentId;

    private Integer caseId;

    private String personType;

    private String personTypeName;

    private String personNo;

    private String personName;

    private String personAlias;

    private String personGender;

    private String personGenderName;

    private String personIdcardNo;
    private String noIdcardRemark;

    private String personCountry;

    private String personRace;

    private String personHeight;

    private String personWeight;

    private String hometownAddress;

    private String presentAddress;

    private String relativeIdentify;

    private String relativeIdentifyName;

    private String instoredFlag;

    private Date createDatetime;

    private String createPerson;

    private Date updateDateteime;

    private String updatePerson;

    private String remark;

    private String deleteFlag;

    private Date deleteDatetime;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getRelativeIdentifyName() {
        return relativeIdentifyName;
    }

    public void setRelativeIdentifyName(String relativeIdentifyName) {
        this.relativeIdentifyName = relativeIdentifyName;
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

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getPersonAlias() {
        return personAlias;
    }

    public void setPersonAlias(String personAlias) {
        this.personAlias = personAlias;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    public String getPersonGenderName() {
        if(StringUtils.isNotBlank(this.personGender)){
            if(Constants.GENDER_MALE_CODE.equals(this.personGender)){
                this.personGenderName = Constants.GENDER_MALE_NAME;
            }else if(Constants.GENDER_FEMALE_CODE.equals(this.personGender)){
                this.personGenderName = Constants.GENDER_FEMALE_NAME;
            }else if(Constants.GENDER_OTHERS_CODE.equals(this.personGender)){
                this.personGenderName = Constants.GENDER_OTHERS_NAME;
            }
        }
        return personGenderName;
    }

    public void setPersonGenderName(String personGenderName) {
        this.personGenderName = personGenderName;
    }

    public String getPersonIdcardNo() {
        return personIdcardNo;
    }

    public void setPersonIdcardNo(String personIdcardNo) {
        this.personIdcardNo = personIdcardNo;
    }

    public String getPersonCountry() {
        return personCountry;
    }

    public void setPersonCountry(String personCountry) {
        this.personCountry = personCountry;
    }

    public String getPersonRace() {
        return personRace;
    }

    public void setPersonRace(String personRace) {
        this.personRace = personRace;
    }

    public String getPersonHeight() {
        return personHeight;
    }

    public void setPersonHeight(String personHeight) {
        this.personHeight = personHeight;
    }

    public String getPersonWeight() {
        return personWeight;
    }

    public void setPersonWeight(String personWeight) {
        this.personWeight = personWeight;
    }

    public String getHometownAddress() {
        return hometownAddress;
    }

    public void setHometownAddress(String hometownAddress) {
        this.hometownAddress = hometownAddress;
    }

    public String getPresentAddress() {
        return presentAddress;
    }

    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }

    public String getRelativeIdentify() {
        return relativeIdentify;
    }

    public void setRelativeIdentify(String relativeIdentify) {
        this.relativeIdentify = relativeIdentify;
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

    public Date getUpdateDateteime() {
        return updateDateteime;
    }

    public void setUpdateDateteime(Date updateDateteime) {
        this.updateDateteime = updateDateteime;
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

    public String getNoIdcardRemark() {
        return noIdcardRemark;
    }

    public void setNoIdcardRemark(String noIdcardRemark) {
        this.noIdcardRemark = noIdcardRemark;
    }

    public Date getDeleteDatetime() {
        return deleteDatetime;
    }

    public void setDeleteDatetime(Date deleteDatetime) {
        this.deleteDatetime = deleteDatetime;
    }


    
}