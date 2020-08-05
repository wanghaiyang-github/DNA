package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangliu
 */
public class MatchResult implements Serializable {

    private Integer  id;
    private Integer  groupId;
    private String matchedMode;
    private String matchedModeName;
    private Integer  sample1Id;
    private Integer  sample2Id;
    private Integer  sample3Id;
    private Integer  sameCount;
    private Integer  diffCount;
    private String totalProbability;
    private String matchResultString;
    private Date createDatetime;
    private String createPerson;

    private String sourceCaseName;
    private String targetCaseName;
    private String sample1No;
    private String sample2No;
    private String sample3No;
    private String sample1Name;
    private String sample2Name;
    private String sample3Name;
    private String sampleGene;
    private String sampleType;
    private String sampleEntryType;
    private String delegateOrgDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getMatchedModeName() {
        return matchedModeName;
    }

    public void setMatchedModeName(String matchedModeName) {
        this.matchedModeName = matchedModeName;
    }

    public String getMatchedMode() {
        return matchedMode;
    }

    public void setMatchedMode(String matchedMode) {
        this.matchedMode = matchedMode;
    }

    public Integer getSample1Id() {
        return sample1Id;
    }

    public void setSample1Id(Integer sample1Id) {
        this.sample1Id = sample1Id;
    }

    public Integer getSample2Id() {
        return sample2Id;
    }

    public void setSample2Id(Integer sample2Id) {
        this.sample2Id = sample2Id;
    }

    public Integer getSample3Id() {
        return sample3Id;
    }

    public void setSample3Id(Integer sample3Id) {
        this.sample3Id = sample3Id;
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

    public String getTotalProbability() {
        return totalProbability;
    }

    public void setTotalProbability(String totalProbability) {
        this.totalProbability = totalProbability;
    }

    public String getMatchResultString() {
        return matchResultString;
    }

    public void setMatchResultString(String matchResultString) {
        this.matchResultString = matchResultString;
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

    public String getSourceCaseName() {
        return sourceCaseName;
    }

    public void setSourceCaseName(String sourceCaseName) {
        this.sourceCaseName = sourceCaseName;
    }

    public String getTargetCaseName() {
        return targetCaseName;
    }

    public void setTargetCaseName(String targetCaseName) {
        this.targetCaseName = targetCaseName;
    }

    public String getSample1No() {
        return sample1No;
    }

    public void setSample1No(String sample1No) {
        this.sample1No = sample1No;
    }

    public String getSample2No() {
        return sample2No;
    }

    public void setSample2No(String sample2No) {
        this.sample2No = sample2No;
    }

    public String getSample3No() {
        return sample3No;
    }

    public void setSample3No(String sample3No) {
        this.sample3No = sample3No;
    }

    public String getSample1Name() {
        return sample1Name;
    }

    public void setSample1Name(String sample1Name) {
        this.sample1Name = sample1Name;
    }

    public String getSample2Name() {
        return sample2Name;
    }

    public void setSample2Name(String sample2Name) {
        this.sample2Name = sample2Name;
    }

    public String getSample3Name() {
        return sample3Name;
    }

    public void setSample3Name(String sample3Name) {
        this.sample3Name = sample3Name;
    }

    public String getSampleGene() {
        return sampleGene;
    }

    public void setSampleGene(String sampleGene) {
        this.sampleGene = sampleGene;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleEntryType() {
        return sampleEntryType;
    }

    public void setSampleEntryType(String sampleEntryType) {
        this.sampleEntryType = sampleEntryType;
    }

    public String getDelegateOrgDesc() {
        return delegateOrgDesc;
    }

    public void setDelegateOrgDesc(String delegateOrgDesc) {
        this.delegateOrgDesc = delegateOrgDesc;
    }
}