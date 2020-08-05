package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsExtractRecord implements Serializable {
    private Integer id;

    private String extractTaskNo;

    private Integer caseId;

    private String boardNo;

    private Integer sampleCount;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date extractDatetime;

    private Integer extractPersonId1;

    private String extractPersonName1;

    private Integer extractPersonId2;

    private String extractPersonName2;

    private Date createDatetime;

    private String createPerson;

    private int taskId;

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

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public String getExtractTaskNo() {
        return extractTaskNo;
    }

    public void setExtractTaskNo(String extractTaskNo) {
        this.extractTaskNo = extractTaskNo;
    }

    public Date getExtractDatetime() {
        return extractDatetime;
    }

    public void setExtractDatetime(Date extractDatetime) {
        this.extractDatetime = extractDatetime;
    }

    public Integer getExtractPersonId1() {
        return extractPersonId1;
    }

    public void setExtractPersonId1(Integer extractPersonId1) {
        this.extractPersonId1 = extractPersonId1;
    }

    public String getExtractPersonName1() {
        return extractPersonName1;
    }

    public void setExtractPersonName1(String extractPersonName1) {
        this.extractPersonName1 = extractPersonName1;
    }

    public Integer getExtractPersonId2() {
        return extractPersonId2;
    }

    public void setExtractPersonId2(Integer extractPersonId2) {
        this.extractPersonId2 = extractPersonId2;
    }

    public String getExtractPersonName2() {
        return extractPersonName2;
    }

    public void setExtractPersonName2(String extractPersonName2) {
        this.extractPersonName2 = extractPersonName2;
    }

    public Integer getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(Integer sampleCount) {
        this.sampleCount = sampleCount;
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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}