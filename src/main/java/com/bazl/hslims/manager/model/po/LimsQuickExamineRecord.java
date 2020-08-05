package com.bazl.hslims.manager.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/13.
 */
public class LimsQuickExamineRecord {

    private Integer id;

    private Integer caseId;

    private Integer limsExtractRecordId;

    private Integer limsPcrRecordId;

    private String limsSyRecordId;

    private String createPerson;

    private Date createDatetime;

    private String updatePerson;

    private Date updateDatetime;

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

    public Integer getLimsExtractRecordId() {
        return limsExtractRecordId;
    }

    public void setLimsExtractRecordId(Integer limsExtractRecordId) {
        this.limsExtractRecordId = limsExtractRecordId;
    }

    public Integer getLimsPcrRecordId() {
        return limsPcrRecordId;
    }

    public void setLimsPcrRecordId(Integer limsPcrRecordId) {
        this.limsPcrRecordId = limsPcrRecordId;
    }

    public String getLimsSyRecordId() {
        return limsSyRecordId;
    }

    public void setLimsSyRecordId(String limsSyRecordId) {
        this.limsSyRecordId = limsSyRecordId;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
