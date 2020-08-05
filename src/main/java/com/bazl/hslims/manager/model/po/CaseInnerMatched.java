package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class CaseInnerMatched implements Serializable {
    private Integer id;

    private Integer caseId;

    private Integer groupId;

    private Integer matchedMode;

    private Integer sample1Id;

    private Integer sample2Id;

    private Integer sample3Id;

    private Integer sameCount;

    private Integer diffCount;

    private String totalProbability;

    private Date createDatetime;

    private String createPerson;

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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getMatchedMode() {
        return matchedMode;
    }

    public void setMatchedMode(Integer matchedMode) {
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
}