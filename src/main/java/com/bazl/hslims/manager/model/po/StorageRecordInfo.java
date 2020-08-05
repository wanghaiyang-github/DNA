package com.bazl.hslims.manager.model.po;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/6.
 */
public class StorageRecordInfo implements Serializable {
    private Integer id;

    private Integer reagentSuppliesInfoId;

    private Integer storageInfoId;

    private String barcode;

    private String recordType;

    private int storageNum;

    private String storagePerson;

    private Date storageDatetime;

    private String storageTime;

    private Date effectiveDatetime;

    private String effectiveTime;

    private String storageRemark;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date storageDatetimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date storageDatetimeEnd;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date effectiveDatetimeStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date effectiveDatetimeEnd;

    private String reagentNo;

    private String reagentName;

    private String operateType;

    private int offset;

    private int rows;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReagentSuppliesInfoId() {
        return reagentSuppliesInfoId;
    }

    public void setReagentSuppliesInfoId(Integer reagentSuppliesInfoId) {
        this.reagentSuppliesInfoId = reagentSuppliesInfoId;
    }

    public Integer getStorageInfoId() {
        return storageInfoId;
    }

    public void setStorageInfoId(Integer storageInfoId) {
        this.storageInfoId = storageInfoId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public int getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(int storageNum) {
        this.storageNum = storageNum;
    }

    public String getStoragePerson() {
        return storagePerson;
    }

    public void setStoragePerson(String storagePerson) {
        this.storagePerson = storagePerson;
    }

    public Date getStorageDatetime() {
        return storageDatetime;
    }

    public void setStorageDatetime(Date storageDatetime) {
        this.storageDatetime = storageDatetime;
    }

    public String getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(String storageTime) {
        this.storageTime = storageTime;
    }

    public Date getEffectiveDatetime() {
        return effectiveDatetime;
    }

    public void setEffectiveDatetime(Date effectiveDatetime) {
        this.effectiveDatetime = effectiveDatetime;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(String effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getStorageRemark() {
        return storageRemark;
    }

    public void setStorageRemark(String storageRemark) {
        this.storageRemark = storageRemark;
    }

    public Date getStorageDatetimeStart() {
        return storageDatetimeStart;
    }

    public void setStorageDatetimeStart(Date storageDatetimeStart) {
        this.storageDatetimeStart = storageDatetimeStart;
    }

    public Date getStorageDatetimeEnd() {
        return storageDatetimeEnd;
    }

    public void setStorageDatetimeEnd(Date storageDatetimeEnd) {
        this.storageDatetimeEnd = storageDatetimeEnd;
    }

    public Date getEffectiveDatetimeStart() {
        return effectiveDatetimeStart;
    }

    public void setEffectiveDatetimeStart(Date effectiveDatetimeStart) {
        this.effectiveDatetimeStart = effectiveDatetimeStart;
    }

    public Date getEffectiveDatetimeEnd() {
        return effectiveDatetimeEnd;
    }

    public void setEffectiveDatetimeEnd(Date effectiveDatetimeEnd) {
        this.effectiveDatetimeEnd = effectiveDatetimeEnd;
    }

    public String getReagentNo() {
        return reagentNo;
    }

    public void setReagentNo(String reagentNo) {
        this.reagentNo = reagentNo;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
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
