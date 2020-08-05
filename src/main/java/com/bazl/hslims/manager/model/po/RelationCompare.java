package com.bazl.hslims.manager.model.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/21.
 */
public class RelationCompare implements Serializable {

    private int matchRelationLimit;// 匹配下限

    private int halfDiffCount;// 半容差

    private int refrenceId;// 参照

    private String populationId;

    private String populationName;

    private String fatherSampleNo;

    private String motherSampleNo;

    private String childSampleNoStr;

    public int getMatchRelationLimit() {
        return matchRelationLimit;
    }

    public void setMatchRelationLimit(int matchRelationLimit) {
        this.matchRelationLimit = matchRelationLimit;
    }

    public int getHalfDiffCount() {
        return halfDiffCount;
    }

    public void setHalfDiffCount(int halfDiffCount) {
        this.halfDiffCount = halfDiffCount;
    }

    public int getRefrenceId() {
        return refrenceId;
    }

    public void setRefrenceId(int refrenceId) {
        this.refrenceId = refrenceId;
    }

    public String getPopulationId() {
        return populationId;
    }

    public void setPopulationId(String populationId) {
        this.populationId = populationId;
    }

    public String getPopulationName() {
        return populationName;
    }

    public void setPopulationName(String populationName) {
        this.populationName = populationName;
    }

    public String getFatherSampleNo() {
        return fatherSampleNo;
    }

    public void setFatherSampleNo(String fatherSampleNo) {
        this.fatherSampleNo = fatherSampleNo;
    }

    public String getMotherSampleNo() {
        return motherSampleNo;
    }

    public void setMotherSampleNo(String motherSampleNo) {
        this.motherSampleNo = motherSampleNo;
    }

    public String getChildSampleNoStr() {
        return childSampleNoStr;
    }

    public void setChildSampleNoStr(String childSampleNoStr) {
        this.childSampleNoStr = childSampleNoStr;
    }
}
