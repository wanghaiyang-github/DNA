package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class LimsIdentifyBook implements Serializable {
    private Integer id;

    private Integer caseId;

    private String caseNo;

    private String filePath;

    private String status;

    private String statusName;

    private Date createDatetime;

//    private String createPersonId;

    private String createPersonName;

    private Date updateDatetime;

    private String updatePersonId;

    private String updatePersonName;

    public String signedPerson;
    public Date signedDatetime;

    private String sendNoticeFlag;

    private Date sendNoticeDatetime;

    private String sendNoticePerson;

    private Date fetchedDatetime;

    private String fetchedBy;

    private String deleteFlag;

    private String uploadPath;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getSignedPerson() {
        return signedPerson;
    }

    public void setSignedPerson(String signedPerson) {
        this.signedPerson = signedPerson;
    }

    public Date getSignedDatetime() {
        return signedDatetime;
    }

    public void setSignedDatetime(Date signedDatetime) {
        this.signedDatetime = signedDatetime;
    }
//    public String getCreatePersonId() {
//        return createPersonId;
//    }
//
//    public void setCreatePersonId(String createPersonId) {
//        this.createPersonId = createPersonId;
//    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdatePersonId() {
        return updatePersonId;
    }

    public void setUpdatePersonId(String updatePersonId) {
        this.updatePersonId = updatePersonId;
    }

    public String getUpdatePersonName() {
        return updatePersonName;
    }

    public void setUpdatePersonName(String updatePersonName) {
        this.updatePersonName = updatePersonName;
    }

    public String getSendNoticeFlag() {
        return sendNoticeFlag;
    }

    public void setSendNoticeFlag(String sendNoticeFlag) {
        this.sendNoticeFlag = sendNoticeFlag;
    }

    public Date getSendNoticeDatetime() {
        return sendNoticeDatetime;
    }

    public void setSendNoticeDatetime(Date sendNoticeDatetime) {
        this.sendNoticeDatetime = sendNoticeDatetime;
    }

    public String getSendNoticePerson() {
        return sendNoticePerson;
    }

    public void setSendNoticePerson(String sendNoticePerson) {
        this.sendNoticePerson = sendNoticePerson;
    }

    public Date getFetchedDatetime() {
        return fetchedDatetime;
    }

    public void setFetchedDatetime(Date fetchedDatetime) {
        this.fetchedDatetime = fetchedDatetime;
    }

    public String getFetchedBy() {
        return fetchedBy;
    }

    public void setFetchedBy(String fetchedBy) {
        this.fetchedBy = fetchedBy;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}