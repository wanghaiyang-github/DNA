package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class QcPolluteRecord implements Serializable {
    private Integer id;

    private Integer qcPersonId;

    private String qcPersonNo;

    private String qcPersonName;

    private String qcPersonGeneInfo;

    private String pollutedSampleNo;

    private Integer pollutedSampleGeneId;

    private String pollutedSampleGeneInfo;

    private Date createDatetime;

    private String createPerson;

    private String description;

    private String deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQcPersonId() {
        return qcPersonId;
    }

    public void setQcPersonId(Integer qcPersonId) {
        this.qcPersonId = qcPersonId;
    }

    public String getPollutedSampleNo() {
        return pollutedSampleNo;
    }

    public void setPollutedSampleNo(String pollutedSampleNo) {
        this.pollutedSampleNo = pollutedSampleNo;
    }

    public Integer getPollutedSampleGeneId() {
        return pollutedSampleGeneId;
    }

    public void setPollutedSampleGeneId(Integer pollutedSampleGeneId) {
        this.pollutedSampleGeneId = pollutedSampleGeneId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public String getQcPersonGeneInfo() {
        return qcPersonGeneInfo;
    }

    public void setQcPersonGeneInfo(String qcPersonGeneInfo) {
        this.qcPersonGeneInfo = qcPersonGeneInfo;
    }

    public String getPollutedSampleGeneInfo() {
        return pollutedSampleGeneInfo;
    }

    public void setPollutedSampleGeneInfo(String pollutedSampleGeneInfo) {
        this.pollutedSampleGeneInfo = pollutedSampleGeneInfo;
    }
}