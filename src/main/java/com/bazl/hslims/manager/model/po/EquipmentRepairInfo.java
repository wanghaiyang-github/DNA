package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/25.
 */
public class EquipmentRepairInfo {

    public Integer id;

    private Integer equipmentInfoId;

    private Integer equipmentNameId;

    private String equipmentName;

    private String equipmentRepairPerson;

    private Date equipmentRepairTime;

    private String repairTime;

    private String failureCause;

    private String createPerson;

    private Date createDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date repairTimeDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date repairTimeDateEnd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentInfoId() {
        return equipmentInfoId;
    }

    public void setEquipmentInfoId(Integer equipmentInfoId) {
        this.equipmentInfoId = equipmentInfoId;
    }

    public Integer getEquipmentNameId() {
        return equipmentNameId;
    }

    public void setEquipmentNameId(Integer equipmentNameId) {
        this.equipmentNameId = equipmentNameId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentRepairPerson() {
        return equipmentRepairPerson;
    }

    public void setEquipmentRepairPerson(String equipmentRepairPerson) {
        this.equipmentRepairPerson = equipmentRepairPerson;
    }

    public Date getEquipmentRepairTime() {
        return equipmentRepairTime;
    }

    public void setEquipmentRepairTime(Date equipmentRepairTime) {
        this.equipmentRepairTime = equipmentRepairTime;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public String getFailureCause() {
        return failureCause;
    }

    public void setFailureCause(String failureCause) {
        this.failureCause = failureCause;
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

    public Date getRepairTimeDateStart() {
        return repairTimeDateStart;
    }

    public void setRepairTimeDateStart(Date repairTimeDateStart) {
        this.repairTimeDateStart = repairTimeDateStart;
    }

    public Date getRepairTimeDateEnd() {
        return repairTimeDateEnd;
    }

    public void setRepairTimeDateEnd(Date repairTimeDateEnd) {
        this.repairTimeDateEnd = repairTimeDateEnd;
    }
}
