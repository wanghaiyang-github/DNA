package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

public class ReagentSuppliesInfo implements Serializable {
    private Integer id;

    private String reagentType;

    private String reagentName;

    private String reagentNo;

    private String experimentalStage;

    private String experimentalStageName;

    private String reagentBrand;

    private String reagentModel;

    private String reagentPrice;

    private String reagentFirm;

    private String remark;

    private String createPerson;

    private Date createDatetime;

    private String updatePerson;

    private Date updateDatetime;

    private int storageNum;

    private int offset;

    private int rows;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReagentType() {
        return reagentType;
    }

    public void setReagentType(String reagentType) {
        this.reagentType = reagentType;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public String getReagentNo() {
        return reagentNo;
    }

    public void setReagentNo(String reagentNo) {
        this.reagentNo = reagentNo;
    }

    public String getExperimentalStage() {
        return experimentalStage;
    }

    public void setExperimentalStage(String experimentalStage) {
        this.experimentalStage = experimentalStage;
    }

    public String getExperimentalStageName() {
        return experimentalStageName;
    }

    public void setExperimentalStageName(String experimentalStageName) {
        this.experimentalStageName = experimentalStageName;
    }

    public String getReagentBrand() {
        return reagentBrand;
    }

    public void setReagentBrand(String reagentBrand) {
        this.reagentBrand = reagentBrand;
    }

    public String getReagentModel() {
        return reagentModel;
    }

    public void setReagentModel(String reagentModel) {
        this.reagentModel = reagentModel;
    }

    public String getReagentPrice() {
        return reagentPrice;
    }

    public void setReagentPrice(String reagentPrice) {
        this.reagentPrice = reagentPrice;
    }

    public String getReagentFirm() {
        return reagentFirm;
    }

    public void setReagentFirm(String reagentFirm) {
        this.reagentFirm = reagentFirm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(int storageNum) {
        this.storageNum = storageNum;
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