package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/12.
 */
public class SyncDna36Queue implements Serializable {

    private Integer id;
    private Integer caseId;
    private Integer sampleId;
    private String operateType;
    private Date operateDate;
    private String operatePerson;
    private String syncFailedFlag;
    private String syncFailedMsg;

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

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperatePerson() {
        return operatePerson;
    }

    public void setOperatePerson(String operatePerson) {
        this.operatePerson = operatePerson;
    }

    public String getSyncFailedFlag() {
        return syncFailedFlag;
    }

    public void setSyncFailedFlag(String syncFailedFlag) {
        this.syncFailedFlag = syncFailedFlag;
    }

    public String getSyncFailedMsg() {
        return syncFailedMsg;
    }

    public void setSyncFailedMsg(String syncFailedMsg) {
        this.syncFailedMsg = syncFailedMsg;
    }
}
