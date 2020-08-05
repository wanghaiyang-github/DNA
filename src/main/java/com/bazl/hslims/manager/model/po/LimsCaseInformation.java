package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LimsCaseInformation implements Serializable {
    private Integer id;

    private String caseNo;
    /**
     * 案件名称
     */
    private String caseName;
    /**
     * 简要描述
     */
    private String caseBrief;
    /**
     * 案发时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date caseDatetime;
    /**
     * 案发地点
     */
    private String caseLocationDesc;
    /**
     * 委托类型
     */
    private String entrustmentType;
    /**
     * 是否删除
     */
    private String deleteFlag;

    private String entrustingSerial;
    private String entrustCompany;
    private String entrustCompanyCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date entrustmentDateStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date entrustmentDateEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date entrustmentDatetime;

    private int offset;
    private int rows;

    private int extractionState;

    public int getExtractionState() {
        return extractionState;
    }

    public void setExtractionState(int extractionState) {
        this.extractionState = extractionState;
    }

    public Date getEntrustmentDatetime() {
        return entrustmentDatetime;
    }

    public void setEntrustmentDatetime(Date entrustmentDatetime) {
        this.entrustmentDatetime = entrustmentDatetime;
    }

    public String getEntrustCompanyCode() {
        return entrustCompanyCode;
    }

    public void setEntrustCompanyCode(String entrustCompanyCode) {
        this.entrustCompanyCode = entrustCompanyCode;
    }

    public String getEntrustingSerial() {
        return entrustingSerial;
    }

    public void setEntrustingSerial(String entrustingSerial) {
        this.entrustingSerial = entrustingSerial;
    }

    public String getEntrustCompany() {
        return entrustCompany;
    }

    public void setEntrustCompany(String entrustCompany) {
        this.entrustCompany = entrustCompany;
    }

    public Date getEntrustmentDateStart() {
        return entrustmentDateStart;
    }

    public void setEntrustmentDateStart(Date entrustmentDateStart) {
        this.entrustmentDateStart = entrustmentDateStart;
    }

    public Date getEntrustmentDateEnd() {
        return entrustmentDateEnd;
    }

    public void setEntrustmentDateEnd(Date entrustmentDateEnd) {
        this.entrustmentDateEnd = entrustmentDateEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseBrief() {
        return caseBrief;
    }

    public void setCaseBrief(String caseBrief) {
        this.caseBrief = caseBrief;
    }

    public Date getCaseDatetime() {
        return caseDatetime;
    }

    public void setCaseDatetime(Date caseDatetime) {
        this.caseDatetime = caseDatetime;
    }

    public String getCaseLocationDesc() {
        return caseLocationDesc;
    }

    public void setCaseLocationDesc(String caseLocationDesc) {
        this.caseLocationDesc = caseLocationDesc;
    }

    public String getEntrustmentType() {
        return entrustmentType;
    }

    public void setEntrustmentType(String entrustmentType) {
        this.entrustmentType = entrustmentType;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}