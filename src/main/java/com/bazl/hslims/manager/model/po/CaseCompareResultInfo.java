package com.bazl.hslims.manager.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/10.
 */
public class CaseCompareResultInfo {

    private Integer id;

    private Integer caseId;

    private String fatherSampleNo;

    private String motherSampleNo;

    private String childSampleNo;

    private String fatherSampleName;

    private String motherSampleName;

    private String childSampleName;

    private Integer compareGeneNum;

    private String compareTotalProbability;

    private Integer comparePopulationId;

    private String comparePopulationName;

    private Date createDatetime;

    private String createPerson;

    private String referenceId;

    private Integer sameCount;

    private Integer diffCount;

    private Integer matchedMode;

    private String reagentNameF;

    private String reagentNameM;

    private String reagentNameC;

    private String bacgroundF;

    private String bacgroundM;

    private String relationProbability1;

    private String relationProbability2;

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

    public String getFatherSampleNo() {
        return fatherSampleNo;
    }

    public void setFatherSampleNo(String fatherSampleNo) {
        this.fatherSampleNo = fatherSampleNo;
    }

    public String getMotherSampleNo() {
        return motherSampleNo;
    }

    public void setMotherSampleNo(String motherSampleNo) {
        this.motherSampleNo = motherSampleNo;
    }

    public String getChildSampleNo() {
        return childSampleNo;
    }

    public void setChildSampleNo(String childSampleNo) {
        this.childSampleNo = childSampleNo;
    }

    public String getFatherSampleName() {
        return fatherSampleName;
    }

    public void setFatherSampleName(String fatherSampleName) {
        this.fatherSampleName = fatherSampleName;
    }

    public String getMotherSampleName() {
        return motherSampleName;
    }

    public void setMotherSampleName(String motherSampleName) {
        this.motherSampleName = motherSampleName;
    }

    public String getChildSampleName() {
        return childSampleName;
    }

    public void setChildSampleName(String childSampleName) {
        this.childSampleName = childSampleName;
    }

    public Integer getCompareGeneNum() {
        return compareGeneNum;
    }

    public void setCompareGeneNum(Integer compareGeneNum) {
        this.compareGeneNum = compareGeneNum;
    }

    public String getCompareTotalProbability() {
        return compareTotalProbability;
    }

    public void setCompareTotalProbability(String compareTotalProbability) {
        this.compareTotalProbability = compareTotalProbability;
    }

    public Integer getComparePopulationId() {
        return comparePopulationId;
    }

    public void setComparePopulationId(Integer comparePopulationId) {
        this.comparePopulationId = comparePopulationId;
    }

    public String getComparePopulationName() {
        return comparePopulationName;
    }

    public void setComparePopulationName(String comparePopulationName) {
        this.comparePopulationName = comparePopulationName;
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

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getSameCount() {
        return sameCount;
    }

    public void setSameCount(Integer sameCount) {
        this.sameCount = sameCount;
    }

    public Integer getDiffCount() {
        return diffCount;
    }

    public void setDiffCount(Integer diffCount) {
        this.diffCount = diffCount;
    }

    public Integer getMatchedMode() {
        return matchedMode;
    }

    public void setMatchedMode(Integer matchedMode) {
        this.matchedMode = matchedMode;
    }

    public String getReagentNameF() {
        return reagentNameF;
    }

    public void setReagentNameF(String reagentNameF) {
        this.reagentNameF = reagentNameF;
    }

    public String getReagentNameM() {
        return reagentNameM;
    }

    public void setReagentNameM(String reagentNameM) {
        this.reagentNameM = reagentNameM;
    }

    public String getReagentNameC() {
        return reagentNameC;
    }

    public void setReagentNameC(String reagentNameC) {
        this.reagentNameC = reagentNameC;
    }

    public String getBacgroundF() {
        return bacgroundF;
    }

    public void setBacgroundF(String bacgroundF) {
        this.bacgroundF = bacgroundF;
    }

    public String getBacgroundM() {
        return bacgroundM;
    }

    public void setBacgroundM(String bacgroundM) {
        this.bacgroundM = bacgroundM;
    }

    public String getRelationProbability1() {
        return relationProbability1;
    }

    public void setRelationProbability1(String relationProbability1) {
        this.relationProbability1 = relationProbability1;
    }

    public String getRelationProbability2() {
        return relationProbability2;
    }

    public void setRelationProbability2(String relationProbability2) {
        this.relationProbability2 = relationProbability2;
    }
}
