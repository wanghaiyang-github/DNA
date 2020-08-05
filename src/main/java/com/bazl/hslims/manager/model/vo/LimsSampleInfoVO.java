package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.web.helper.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 2017/1/11.
 */
public class LimsSampleInfoVO extends AbstractBaseVO<LimsSampleInfo> {

    public LimsSampleInfoVO(){
        this.entity = new LimsSampleInfo();
    }


    public LimsSampleInfoVO(LimsSampleInfo entity){
        this.entity = entity;
    }

    private String caseName;

    private String caseNo;

    private String caseProperty;

    private Integer sampleGeneCount;

    private String sampleTypeName;

    /**
     * 受理时间（起）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date acceptDatetimeStart;
    /**
     * 受理时间（止）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date acceptDatetimeEnd;

    private String geneInfo;

    private String reagentName;

    private Integer geneInfoId;

    private Integer panelInfoId;

    private String personName;
    private String personGender;
    private String personIdcardNo;
    private String personType;
    private String personTypeName;

    private int offset;                  //分页参数：偏移量

    private int rows;                    //分页参数：获取数据条数

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public Integer getSampleGeneCount() {
        return sampleGeneCount;
    }

    public void setSampleGeneCount(Integer sampleGeneCount) {
        this.sampleGeneCount = sampleGeneCount;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public Date getAcceptDatetimeStart() {
        return acceptDatetimeStart;
    }

    public void setAcceptDatetimeStart(Date acceptDatetimeStart) {
        this.acceptDatetimeStart = acceptDatetimeStart;
    }

    public Date getAcceptDatetimeEnd() {
        return acceptDatetimeEnd;
    }

    public void setAcceptDatetimeEnd(Date acceptDatetimeEnd) {
        this.acceptDatetimeEnd = acceptDatetimeEnd;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public Integer getGeneInfoId() {
        return geneInfoId;
    }

    public void setGeneInfoId(Integer geneInfoId) {
        this.geneInfoId = geneInfoId;
    }

    public Integer getPanelInfoId() {
        return panelInfoId;
    }

    public void setPanelInfoId(Integer panelInfoId) {
        this.panelInfoId = panelInfoId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    public String getPersonIdcardNo() {
        return personIdcardNo;
    }

    public void setPersonIdcardNo(String personIdcardNo) {
        this.personIdcardNo = personIdcardNo;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
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

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }
}
