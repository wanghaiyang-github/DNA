package com.bazl.hslims.manager.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/24.
 */
public class EquipmentInfo {

    private Integer id;

    private Integer equipmentNameId;

    private String equipmentNo;

    private String equipmentSpecification;

    private String equipmentName;

    private String equipmentStatus;

    private Integer equipmentNum;

    private Integer useBlueWarn;

    private Integer useRedWarn;

    private Integer repairBlueWarn;

    private Integer repairRedWarn;

    private String remark;

    private String createPerson;

    private Date createDatetime;

    private String updatePerson;

    private Date updateDatetime;

    private String equipmentStatusName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentNameId() {
        return equipmentNameId;
    }

    public void setEquipmentNameId(Integer equipmentNameId) {
        this.equipmentNameId = equipmentNameId;
    }

    public String getEquipmentNo() {
        return equipmentNo;
    }

    public void setEquipmentNo(String equipmentNo) {
        this.equipmentNo = equipmentNo;
    }

    public String getEquipmentSpecification() {
        return equipmentSpecification;
    }

    public void setEquipmentSpecification(String equipmentSpecification) {
        this.equipmentSpecification = equipmentSpecification;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(String equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }

    public Integer getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(Integer equipmentNum) {
        this.equipmentNum = equipmentNum;
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

    public String getEquipmentStatusName() {
        return equipmentStatusName;
    }

    public void setEquipmentStatusName(String equipmentStatusName) {
        this.equipmentStatusName = equipmentStatusName;
    }
}
