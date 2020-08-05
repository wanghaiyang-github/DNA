package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.LimsSampleGene;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/11.
 */
public class LimsSampleGeneVO extends AbstractBaseVO<LimsSampleGene> implements Serializable {

    public LimsSampleGeneVO() {
        this.entity = new LimsSampleGene();
    }


    public LimsSampleGeneVO(LimsSampleGene entity) {
        this.entity = entity;
    }


    private String sampleName;

    private Integer caseId;

    private String caseNo;

    private String caseName;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }
}
