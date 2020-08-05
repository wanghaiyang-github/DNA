package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class DictInfo implements Serializable {
    private Integer id;

    private String dictTypeCode;

    private String dictTypeName;

    private Date createDatetime;

    private String createPerson;

    private String oldDictTypeCode;

    public String getOldDictTypeCode() {
        return oldDictTypeCode;
    }

    public void setOldDictTypeCode(String oldDictTypeCode) {
        this.oldDictTypeCode = oldDictTypeCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
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
}