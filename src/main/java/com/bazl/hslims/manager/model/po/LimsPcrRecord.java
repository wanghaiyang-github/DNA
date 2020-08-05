package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsPcrRecord implements Serializable {
    private Integer id;

    private String pcrTaskNo;

    private Integer caseId;

    private String boardNo;

    private String pcrProgram;

    private String pcrInstrument;

    private String pcrSystem;

    private String pcrReagent;

    private String pcrReagentName;

    private Integer pcrLoopCount;

    private String pcrTemperature;

    private String pcrHumidity;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date pcrStartDatetime;


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date pcrEndDatetime;

    private Integer pcrPersonId1;

    private String pcrPersonName1;


    private Integer pcrPersonId2;

    private String pcrPersonName2;

    private Integer sampleCount;

    private Date createDatetime;

    private String createPerson;

    private int taskId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getPcrProgram() {
        return pcrProgram;
    }

    public void setPcrProgram(String pcrProgram) {
        this.pcrProgram = pcrProgram;
    }

    public String getPcrInstrument() {
        return pcrInstrument;
    }

    public String getPcrTaskNo() {
        return pcrTaskNo;
    }

    public void setPcrTaskNo(String pcrTaskNo) {
        this.pcrTaskNo = pcrTaskNo;
    }

    public void setPcrInstrument(String pcrInstrument) {
        this.pcrInstrument = pcrInstrument;
    }

    public String getPcrSystem() {
        return pcrSystem;
    }

    public void setPcrSystem(String pcrSystem) {
        this.pcrSystem = pcrSystem;
    }

    public String getPcrReagent() {
        return pcrReagent;
    }

    public void setPcrReagent(String pcrReagent) {
        this.pcrReagent = pcrReagent;
    }

    public String getPcrReagentName() {
        return pcrReagentName;
    }

    public void setPcrReagentName(String pcrReagentName) {
        this.pcrReagentName = pcrReagentName;
    }

    public Integer getPcrLoopCount() {
        return pcrLoopCount;
    }

    public void setPcrLoopCount(Integer pcrLoopCount) {
        this.pcrLoopCount = pcrLoopCount;
    }

    public String getPcrTemperature() {
        return pcrTemperature;
    }

    public void setPcrTemperature(String pcrTemperature) {
        this.pcrTemperature = pcrTemperature;
    }

    public String getPcrHumidity() {
        return pcrHumidity;
    }

    public void setPcrHumidity(String pcrHumidity) {
        this.pcrHumidity = pcrHumidity;
    }

    public Date getPcrStartDatetime() {
        return pcrStartDatetime;
    }

    public void setPcrStartDatetime(Date pcrStartDatetime) {
        this.pcrStartDatetime = pcrStartDatetime;
    }

    public Date getPcrEndDatetime() {
        return pcrEndDatetime;
    }

    public void setPcrEndDatetime(Date pcrEndDatetime) {
        this.pcrEndDatetime = pcrEndDatetime;
    }

    public Integer getPcrPersonId1() {
        return pcrPersonId1;
    }

    public void setPcrPersonId1(Integer pcrPersonId1) {
        this.pcrPersonId1 = pcrPersonId1;
    }

    public String getPcrPersonName1() {
        return pcrPersonName1;
    }

    public void setPcrPersonName1(String pcrPersonName1) {
        this.pcrPersonName1 = pcrPersonName1;
    }

    public Integer getPcrPersonId2() {
        return pcrPersonId2;
    }

    public void setPcrPersonId2(Integer pcrPersonId2) {
        this.pcrPersonId2 = pcrPersonId2;
    }

    public String getPcrPersonName2() {
        return pcrPersonName2;
    }

    public void setPcrPersonName2(String pcrPersonName2) {
        this.pcrPersonName2 = pcrPersonName2;
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