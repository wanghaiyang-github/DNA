package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsSyRecord implements Serializable {
    private Integer id;

    private String syTaskNo;

    private Integer caseId;

    private String boardNo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date startDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date endDatetime;

    private Integer operatePersonId1;

    private String operatePersonName1;


    private Integer operatePersonId2;

    private String operatePersonName2;

    private String chanwuFlag;

    private String jiaxiananFlag;

    private String neibiaoUlFlag;

    private String neibiaoFlag;

    private String elecInstrument;

    private String elecInstrumentName;

    private int sampleCount;

    private Date createDatetime;

    private String createPersonId;

    private String createPersonName;

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

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Integer getOperatePersonId1() {
        return operatePersonId1;
    }

    public void setOperatePersonId1(Integer operatePersonId1) {
        this.operatePersonId1 = operatePersonId1;
    }

    public String getOperatePersonName1() {
        return operatePersonName1;
    }

    public void setOperatePersonName1(String operatePersonName1) {
        this.operatePersonName1 = operatePersonName1;
    }

    public Integer getOperatePersonId2() {
        return operatePersonId2;
    }

    public void setOperatePersonId2(Integer operatePersonId2) {
        this.operatePersonId2 = operatePersonId2;
    }

    public String getOperatePersonName2() {
        return operatePersonName2;
    }

    public void setOperatePersonName2(String operatePersonName2) {
        this.operatePersonName2 = operatePersonName2;
    }

    public String getChanwuFlag() {
        return chanwuFlag;
    }

    public void setChanwuFlag(String chanwuFlag) {
        this.chanwuFlag = chanwuFlag;
    }

    public String getJiaxiananFlag() {
        return jiaxiananFlag;
    }

    public void setJiaxiananFlag(String jiaxiananFlag) {
        this.jiaxiananFlag = jiaxiananFlag;
    }

    public String getNeibiaoUlFlag() {
        return neibiaoUlFlag;
    }

    public void setNeibiaoUlFlag(String neibiaoUlFlag) {
        this.neibiaoUlFlag = neibiaoUlFlag;
    }

    public String getNeibiaoFlag() {
        return neibiaoFlag;
    }

    public void setNeibiaoFlag(String neibiaoFlag) {
        this.neibiaoFlag = neibiaoFlag;
    }

    public String getElecInstrument() {
        return elecInstrument;
    }

    public void setElecInstrument(String elecInstrument) {
        this.elecInstrument = elecInstrument;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getSyTaskNo() {
        return syTaskNo;
    }

    public void setSyTaskNo(String syTaskNo) {
        this.syTaskNo = syTaskNo;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public String getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(String createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public String getElecInstrumentName() {
        return elecInstrumentName;
    }

    public void setElecInstrumentName(String elecInstrumentName) {
        this.elecInstrumentName = elecInstrumentName;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}