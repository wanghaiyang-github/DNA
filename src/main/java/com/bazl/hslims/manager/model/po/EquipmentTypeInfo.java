package com.bazl.hslims.manager.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/25.
 */
public class EquipmentTypeInfo {

    private Integer id;

    private String equipmentTypeNo;

    private String equipmentTypeName;

    private String experimentalStage;

    private String experimentalStageName;

    private Integer useBlueWarn;

    private Integer useRedWarn;

    private Integer repairBlueWarn;

    private Integer repairRedWarn;

    private String remark;

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

    public String getEquipmentTypeNo() {
        return equipmentTypeNo;
    }

    public void setEquipmentTypeNo(String equipmentTypeNo) {
        this.equipmentTypeNo = equipmentTypeNo;
    }

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
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

    public Integer getUseBlueWarn() {
        return useBlueWarn;
    }

    public void setUseBlueWarn(Integer useBlueWarn) {
        this.useBlueWarn = useBlueWarn;
    }

    public Integer getUseRedWarn() {
        return useRedWarn;
    }

    public void setUseRedWarn(Integer useRedWarn) {
        this.useRedWarn = useRedWarn;
    }

    public Integer getRepairBlueWarn() {
        return repairBlueWarn;
    }

    public void setRepairBlueWarn(Integer repairBlueWarn) {
        this.repairBlueWarn = repairBlueWarn;
    }

    public Integer getRepairRedWarn() {
        return repairRedWarn;
    }

    public void setRepairRedWarn(Integer repairRedWarn) {
        this.repairRedWarn = repairRedWarn;
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
}
