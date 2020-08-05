package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.CriminalPersonInfo;
import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018-05-11.
 */
public class CriminalPersonInfoVo extends  AbstractBaseVO<CriminalPersonInfo> implements Serializable {

    public CriminalPersonInfoVo() {
        super();
        this.entity = new CriminalPersonInfo();
    }


    public CriminalPersonInfoVo(CriminalPersonInfo entity) {
        super();
        this.entity = entity;
    }


    protected int id;
    protected String personTypeName;
    protected String genderName;
    protected String raceName;
    protected String statusName;
    protected Date collectDatetimeStart;
    protected Date collectDatetimeEnd;
    protected String collectOrgName;
    protected String personNoStart;
    protected String personNoEnd;
    protected String dnaDbRepeatDesc;
    protected int acceptOrgId;
    protected List collectOrgIdList;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    protected Date scanedDatetimeStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    protected Date scanedDatetimeEnd;
    protected Date scanedDatetime;
    protected String scanedPerson;
    protected String personNo;

    protected int personGeneFlag;
    protected int queueDetaStatus;

    protected String collectOrgId;
    protected int sampleGeneId;
    protected String geneInfo;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    protected Date geneCreateDatetime;

    protected String codisFileName;
    protected String instoredFlag;

    protected String geneCount;


    protected int offset;
    protected int rows;

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Date getCollectDatetimeStart() {
        return collectDatetimeStart;
    }

    public void setCollectDatetimeStart(Date collectDatetimeStart) {
        this.collectDatetimeStart = collectDatetimeStart;
    }

    public Date getCollectDatetimeEnd() {
        return collectDatetimeEnd;
    }

    public void setCollectDatetimeEnd(Date collectDatetimeEnd) {
        this.collectDatetimeEnd = collectDatetimeEnd;
    }

    public String getPersonNoStart() {
        return personNoStart;
    }

    public void setPersonNoStart(String personNoStart) {
        this.personNoStart = personNoStart;
    }

    public String getPersonNoEnd() {
        return personNoEnd;
    }

    public void setPersonNoEnd(String personNoEnd) {
        this.personNoEnd = personNoEnd;
    }



    public String getDnaDbRepeatDesc() {
        return dnaDbRepeatDesc;
    }

    public void setDnaDbRepeatDesc(String dnaDbRepeatDesc) {
        this.dnaDbRepeatDesc = dnaDbRepeatDesc;
    }

    public int getAcceptOrgId() {
        return acceptOrgId;
    }

    public void setAcceptOrgId(int acceptOrgId) {
        this.acceptOrgId = acceptOrgId;
    }

    public List getCollectOrgIdList() {
        return collectOrgIdList;
    }

    public void setCollectOrgIdList(List collectOrgIdList) {
        this.collectOrgIdList = collectOrgIdList;
    }

    public Date getScanedDatetimeStart() {
        return scanedDatetimeStart;
    }

    public void setScanedDatetimeStart(Date scanedDatetimeStart) {
        this.scanedDatetimeStart = scanedDatetimeStart;
    }

    public Date getScanedDatetimeEnd() {
        return scanedDatetimeEnd;
    }

    public void setScanedDatetimeEnd(Date scanedDatetimeEnd) {
        this.scanedDatetimeEnd = scanedDatetimeEnd;
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

    public int getPersonGeneFlag() {
        return personGeneFlag;
    }

    public void setPersonGeneFlag(int personGeneFlag) {
        this.personGeneFlag = personGeneFlag;
    }

    public int getQueueDetaStatus() {
        return queueDetaStatus;
    }

    public void setQueueDetaStatus(int queueDetaStatus) {
        this.queueDetaStatus = queueDetaStatus;
    }

    public int getSampleGeneId() {
        return sampleGeneId;
    }

    public void setSampleGeneId(int sampleGeneId) {
        this.sampleGeneId = sampleGeneId;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getCodisFileName() {
        return codisFileName;
    }

    public void setCodisFileName(String codisFileName) {
        this.codisFileName = codisFileName;
    }

    public Date getGeneCreateDatetime() {
        return geneCreateDatetime;
    }

    public void setGeneCreateDatetime(Date geneCreateDatetime) {
        this.geneCreateDatetime = geneCreateDatetime;
    }

    public String getInstoredFlag() {
        return instoredFlag;
    }

    public void setInstoredFlag(String instoredFlag) {
        this.instoredFlag = instoredFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectOrgName() {
        return collectOrgName;
    }

    public void setCollectOrgName(String collectOrgName) {
        this.collectOrgName = collectOrgName;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
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

    public String getGeneCount() {
        return geneCount;
    }

    public void setGeneCount(String geneCount) {
        this.geneCount = geneCount;
    }

    public String getCollectOrgId() {
        return collectOrgId;
    }

    public void setCollectOrgId(String collectOrgId) {
        this.collectOrgId = collectOrgId;
    }
}
