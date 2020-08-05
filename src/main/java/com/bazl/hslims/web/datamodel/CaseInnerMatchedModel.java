package com.bazl.hslims.web.datamodel;

/**
 * Created by Administrator on 2017/5/15.
 */
public class CaseInnerMatchedModel {

    private String samplePersonNo;

    private String samplePersonName;

    private String sampleObjectNo;

    private String sampleObjectName;

    private String sampleObjectNoPSA;

    private String sampleObjectNamePSA;

    private String sampleObjectNoHB;

    private String sampleObjectNameHB;

    private String sampleObjectNoOther;

    private String sampleObjectNameOther;

    private String totalProbability1;

    private String totalProbability2;

    /**
     * 比中数
     */
    private int sameCount;

    public String getSamplePersonNo() {
        return samplePersonNo;
    }

    public void setSamplePersonNo(String samplePersonNo) {
        this.samplePersonNo = samplePersonNo;
    }

    public String getSamplePersonName() {
        return samplePersonName;
    }

    public void setSamplePersonName(String samplePersonName) {
        this.samplePersonName = samplePersonName;
    }

    public String getSampleObjectNo() {
        return sampleObjectNo;
    }

    public void setSampleObjectNo(String sampleObjectNo) {
        this.sampleObjectNo = sampleObjectNo;
    }

    public String getSampleObjectName() {
        return sampleObjectName;
    }

    public void setSampleObjectName(String sampleObjectName) {
        this.sampleObjectName = sampleObjectName;
    }

    public String getSampleObjectNoPSA() {
        return sampleObjectNoPSA;
    }

    public void setSampleObjectNoPSA(String sampleObjectNoPSA) {
        this.sampleObjectNoPSA = sampleObjectNoPSA;
    }

    public String getSampleObjectNamePSA() {
        return sampleObjectNamePSA;
    }

    public void setSampleObjectNamePSA(String sampleObjectNamePSA) {
        this.sampleObjectNamePSA = sampleObjectNamePSA;
    }

    public String getSampleObjectNoHB() {
        return sampleObjectNoHB;
    }

    public void setSampleObjectNoHB(String sampleObjectNoHB) {
        this.sampleObjectNoHB = sampleObjectNoHB;
    }

    public String getSampleObjectNameHB() {
        return sampleObjectNameHB;
    }

    public void setSampleObjectNameHB(String sampleObjectNameHB) {
        this.sampleObjectNameHB = sampleObjectNameHB;
    }

    public String getSampleObjectNoOther() {
        return sampleObjectNoOther;
    }

    public void setSampleObjectNoOther(String sampleObjectNoOther) {
        this.sampleObjectNoOther = sampleObjectNoOther;
    }

    public String getSampleObjectNameOther() {
        return sampleObjectNameOther;
    }

    public void setSampleObjectNameOther(String sampleObjectNameOther) {
        this.sampleObjectNameOther = sampleObjectNameOther;
    }

    public String getTotalProbability1() {
        return totalProbability1;
    }

    public void setTotalProbability1(String totalProbability1) {
        this.totalProbability1 = totalProbability1;
    }

    public String getTotalProbability2() {
        return totalProbability2;
    }

    public void setTotalProbability2(String totalProbability2) {
        this.totalProbability2 = totalProbability2;
    }

    public int getSameCount() {
        return sameCount;
    }

    public void setSameCount(int sameCount) {
        this.sameCount = sameCount;
    }
}
