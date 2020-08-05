package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/8.
 */
public class LimsExtractRecordSample implements Serializable {

    private Integer id;

    private Integer limsExtractRecordId;

    private Integer sampleId;

    private String sampleNo;

    private String sampleFlag;

    private String sampleName;

    private String sampleType;

    private String sampleTypeName;

    private String extractMethod;

    private String extractHb;

    private String extractPsa;

    private String extractLiFlag;

    private String extractZhenFlag;

    private String extractJiaoFlag;

    private String extractYuFlag;

    private String extractOtherInstrument;

    private Integer createPersonId;

    private String createPersonName;

    private Date createDatetime;

    private String extractPosition;

    private String changeMethod;

    private String otherChangeMethod;

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getCreatePersonId() {
        return createPersonId;
    }

    public void setCreatePersonId(Integer createPersonId) {
        this.createPersonId = createPersonId;
    }

    public String getCreatePersonName() {
        return createPersonName;
    }

    public void setCreatePersonName(String createPersonName) {
        this.createPersonName = createPersonName;
    }

    public String getExtractHb() {
        return extractHb;
    }

    public void setExtractHb(String extractHb) {
        this.extractHb = extractHb;
    }

    public String getExtractJiaoFlag() {
        return extractJiaoFlag;
    }

    public void setExtractJiaoFlag(String extractJiaoFlag) {
        this.extractJiaoFlag = extractJiaoFlag;
    }

    public String getExtractLiFlag() {
        return extractLiFlag;
    }

    public void setExtractLiFlag(String extractLiFlag) {
        this.extractLiFlag = extractLiFlag;
    }

    public String getExtractMethod() {
        return extractMethod;
    }

    public void setExtractMethod(String extractMethod) {
        this.extractMethod = extractMethod;
    }

    public String getExtractOtherInstrument() {
        return extractOtherInstrument;
    }

    public void setExtractOtherInstrument(String extractOtherInstrument) {
        this.extractOtherInstrument = extractOtherInstrument;
    }

    public String getExtractPsa() {
        return extractPsa;
    }

    public void setExtractPsa(String extractPsa) {
        this.extractPsa = extractPsa;
    }

    public String getExtractYuFlag() {
        return extractYuFlag;
    }

    public void setExtractYuFlag(String extractYuFlag) {
        this.extractYuFlag = extractYuFlag;
    }

    public String getExtractZhenFlag() {
        return extractZhenFlag;
    }

    public void setExtractZhenFlag(String extractZhenFlag) {
        this.extractZhenFlag = extractZhenFlag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLimsExtractRecordId() {
        return limsExtractRecordId;
    }

    public void setLimsExtractRecordId(Integer limsExtractRecordId) {
        this.limsExtractRecordId = limsExtractRecordId;
    }

    public Integer getSampleId() {
        return sampleId;
    }

    public void setSampleId(Integer sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getExtractPosition() {
        return extractPosition;
    }

    public void setExtractPosition(String extractPosition) {
        this.extractPosition = extractPosition;
    }

    public String getChangeMethod() {
        return changeMethod;
    }

    public void setChangeMethod(String changeMethod) {
        this.changeMethod = changeMethod;
    }

    public String getOtherChangeMethod() {
        return otherChangeMethod;
    }

    public void setOtherChangeMethod(String otherChangeMethod) {
        this.otherChangeMethod = otherChangeMethod;
    }

}
