package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.LimsCaseInfo;
import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public class LimsCaseInfoVO extends AbstractBaseVO<LimsCaseInfo> implements Serializable {

    public LimsCaseInfoVO() {
        super();
        this.entity = new LimsCaseInfo();
    }


    public LimsCaseInfoVO(LimsCaseInfo entity) {
        super();
        this.entity = entity;
    }

    private List<String> caseStatusList;
    private List<String> consignmentStatusList;

    private String delegateOrg;
    private String delegateOrgName;
    private String delegatorName;
    private String delegator1Name;
    private String delegator2Name;
    private String refuseReason;
    private String entrustingSerial;

    private String  caseStatusName;

    private String caseTypeName;

    private String casePropertyName;

    private String caseLevelName;

    private Integer consignmentId;
    private String additionalFlag;
    private Date delegateDatetime;
    private String delegateAcceptor;


    private Date delegateAcceptDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date caseDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date caseDateEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptDateEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateDatetimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateDatetimeEnd;

    private String identifyBookStatus;

    private String identifyBookStatusName;

    private String consignmentStatus;

    private Integer extractRecordCount;

    private Integer appExtractRecordCount;

    private Integer pcrRecordCount;

    private Integer appPcrRecordCount;

    private Integer syRecordCount;

    private Integer appSyRecordCount;

    private Integer quickExamineCount;

    private Integer checkCount;

    private Integer caseId;

    private String extractRecordId;

    private String pcrRecordId;

    private String syRecordId;

    private int taskId;

    private String cno;

    public String getEntrustingSerial() {
        return entrustingSerial;
    }

    public void setEntrustingSerial(String entrustingSerial) {
        this.entrustingSerial = entrustingSerial;
    }

    public Date getCaseDateStart() {
        return caseDateStart;
    }

    public void setCaseDateStart(Date caseDateStart) {
        this.caseDateStart = caseDateStart;
    }

    public Date getCaseDateEnd() {
        return caseDateEnd;
    }

    public void setCaseDateEnd(Date caseDateEnd) {
        this.caseDateEnd = caseDateEnd;
    }

    public Integer getPcrRecordCount() {
        return pcrRecordCount;
    }

    public void setPcrRecordCount(Integer pcrRecordCount) {
        this.pcrRecordCount = pcrRecordCount;
    }

    public Integer getSyRecordCount() {
        return syRecordCount;
    }

    public void setSyRecordCount(Integer syRecordCount) {
        this.syRecordCount = syRecordCount;
    }

    public Integer getExtractRecordCount() {
        return extractRecordCount;
    }

    public void setExtractRecordCount(Integer extractRecordCount) {
        this.extractRecordCount = extractRecordCount;
    }

    public Integer getQuickExamineCount() {
        return quickExamineCount;
    }

    public void setQuickExamineCount(Integer quickExamineCount) {
        this.quickExamineCount = quickExamineCount;
    }

    public Integer getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(Integer consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getAdditionalFlag() {
        return additionalFlag;
    }

    public void setAdditionalFlag(String additionalFlag) {
        this.additionalFlag = additionalFlag;
    }

    public String getDelegatorName() {
        return delegatorName;
    }

    public void setDelegatorName(String delegatorName) {
        this.delegatorName = delegatorName;
    }

    public String getDelegateOrgName() {
        return delegateOrgName;
    }

    public void setDelegateOrgName(String delegateOrgName) {
        this.delegateOrgName = delegateOrgName;
    }

    public String getRefuseReason() { return refuseReason; }

    public void setRefuseReason(String refuseReason) { this.refuseReason = refuseReason; }

    public Date getDelegateAcceptDate() { return delegateAcceptDate; }

    public void setDelegateAcceptDate(Date delegateAcceptDate) {
        this.delegateAcceptDate = delegateAcceptDate;
    }

    public Date getAcceptDateStart() {
        return acceptDateStart;
    }

    public void setAcceptDateStart(Date acceptDateStart) {
        this.acceptDateStart = acceptDateStart;
    }

    public Date getAcceptDateEnd() {
        return acceptDateEnd;
    }

    public void setAcceptDateEnd(Date acceptDateEnd) {
        this.acceptDateEnd = acceptDateEnd;
    }

    public Date getDelegateDatetime() {
        return delegateDatetime;
    }

    public void setDelegateDatetime(Date delegateDatetime) {
        this.delegateDatetime = delegateDatetime;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getCaseTypeName() {
        return caseTypeName;
    }

    public void setCaseTypeName(String caseTypeName) {
        this.caseTypeName = caseTypeName;
    }

    public String getCasePropertyName() {
        return casePropertyName;
    }

    public void setCasePropertyName(String casePropertyName) {
        this.casePropertyName = casePropertyName;
    }

    public Date getDelegateDatetimeStart() {
        return delegateDatetimeStart;
    }

    public void setDelegateDatetimeStart(Date delegateDatetimeStart) {
        this.delegateDatetimeStart = delegateDatetimeStart;
    }

    public Date getDelegateDatetimeEnd() {
        return delegateDatetimeEnd;
    }

    public void setDelegateDatetimeEnd(Date delegateDatetimeEnd) {
        this.delegateDatetimeEnd = delegateDatetimeEnd;
    }

    public String getDelegateOrg() {
        return delegateOrg;
    }

    public void setDelegateOrg(String delegateOrg) {
        this.delegateOrg = delegateOrg;
    }

    public List<String> getCaseStatusList() {
        return caseStatusList;
    }

    public void setCaseStatusList(List<String> caseStatusList) {
        this.caseStatusList = caseStatusList;
    }

    public String getCaseLevelName() {
        return caseLevelName;
    }

    public void setCaseLevelName(String caseLevelName) {
        this.caseLevelName = caseLevelName;
    }

    public String getDelegateAcceptor() {
        return delegateAcceptor;
    }

    public void setDelegateAcceptor(String delegateAcceptor) {
        this.delegateAcceptor = delegateAcceptor;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getIdentifyBookStatus() {
        return identifyBookStatus;
    }

    public void setIdentifyBookStatus(String identifyBookStatus) {
        this.identifyBookStatus = identifyBookStatus;
    }

    public String getIdentifyBookStatusName() {
        return identifyBookStatusName;
    }

    public void setIdentifyBookStatusName(String identifyBookStatusName) {
        this.identifyBookStatusName = identifyBookStatusName;
    }

    public String getConsignmentStatus() {
        return consignmentStatus;
    }

    public void setConsignmentStatus(String consignmentStatus) {
        this.consignmentStatus = consignmentStatus;
    }

    public String getDelegator1Name() {
        return delegator1Name;
    }

    public void setDelegator1Name(String delegator1Name) {
        this.delegator1Name = delegator1Name;
    }

    public String getDelegator2Name() {
        return delegator2Name;
    }

    public void setDelegator2Name(String delegator2Name) {
        this.delegator2Name = delegator2Name;
    }

    public List<String> getConsignmentStatusList() {
        return consignmentStatusList;
    }

    public void setConsignmentStatusList(List<String> consignmentStatusList) {
        this.consignmentStatusList = consignmentStatusList;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getExtractRecordId() {
        return extractRecordId;
    }

    public void setExtractRecordId(String extractRecordId) {
        this.extractRecordId = extractRecordId;
    }

    public Integer getAppExtractRecordCount() {
        return appExtractRecordCount;
    }

    public void setAppExtractRecordCount(Integer appExtractRecordCount) {
        this.appExtractRecordCount = appExtractRecordCount;
    }

    public Integer getAppPcrRecordCount() {
        return appPcrRecordCount;
    }

    public void setAppPcrRecordCount(Integer appPcrRecordCount) {
        this.appPcrRecordCount = appPcrRecordCount;
    }

    public Integer getAppSyRecordCount() {
        return appSyRecordCount;
    }

    public void setAppSyRecordCount(Integer appSyRecordCount) {
        this.appSyRecordCount = appSyRecordCount;
    }

    public String getPcrRecordId() {
        return pcrRecordId;
    }

    public void setPcrRecordId(String pcrRecordId) {
        this.pcrRecordId = pcrRecordId;
    }

    public String getSyRecordId() {
        return syRecordId;
    }

    public void setSyRecordId(String syRecordId) {
        this.syRecordId = syRecordId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }
}
