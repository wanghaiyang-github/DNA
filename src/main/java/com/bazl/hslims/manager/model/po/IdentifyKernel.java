package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/5.
 */
public class IdentifyKernel implements Serializable {

    private Integer id;

    private String identifyKernelName;

    private Date createDatetime;

    private String createPerson;

    private Date updateDatetime;

    private String updatePerson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifyKernelName() {
        return identifyKernelName;
    }

    public void setIdentifyKernelName(String identifyKernelName) {
        this.identifyKernelName = identifyKernelName;
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

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }
}
