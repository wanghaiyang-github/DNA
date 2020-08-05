package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018-05-11.
 */
public class CriminalAcceptInfo implements Serializable {

    protected int id;
    protected int pid;
    protected String personNo;
    protected int orgId;
    protected String orgName;
    protected Date scanedDatetime;
    protected String scanedPerson;
    protected String type;
    protected String typeDesc;
    protected String errorTypeCode;
    protected String errorTypeName;
    protected String errorRecordPerson;
    protected Date errorRecordDatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getScanedDatetime() {
        return scanedDatetime;
    }

    public void setScanedDatetime(Date scanedDatetime) {
        this.scanedDatetime = scanedDatetime;
    }

    public String getScanedPerson() {
        return scanedPerson;
    }

    public void setScanedPerson(String scanedPerson) {
        this.scanedPerson = scanedPerson;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public String getErrorTypeCode() {
        return errorTypeCode;
    }

    public void setErrorTypeCode(String errorTypeCode) {
        this.errorTypeCode = errorTypeCode;
    }

    public String getErrorTypeName() {
        return errorTypeName;
    }

    public void setErrorTypeName(String errorTypeName) {
        this.errorTypeName = errorTypeName;
    }

    public String getErrorRecordPerson() {
        return errorRecordPerson;
    }

    public void setErrorRecordPerson(String errorRecordPerson) {
        this.errorRecordPerson = errorRecordPerson;
    }

    public Date getErrorRecordDatetime() {
        return errorRecordDatetime;
    }

    public void setErrorRecordDatetime(Date errorRecordDatetime) {
        this.errorRecordDatetime = errorRecordDatetime;
    }
}
