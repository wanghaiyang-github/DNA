package com.bazl.hslims.manager.model.po;

import java.io.Serializable;

/**
 * @author wangliu
 */
public class MatchGroup implements Serializable {

    private Integer  id;
    private String  groupNo;
    private Integer  matchedMode;

    private String caseName;
    private String sampleName;
    private String sampleNo;
    private String sampleEntryType;

    private int offset;
    private int rows;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public Integer getMatchedMode() {
        return matchedMode;
    }

    public void setMatchedMode(Integer matchedMode) {
        this.matchedMode = matchedMode;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
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

    public String getSampleEntryType() {
        return sampleEntryType;
    }

    public void setSampleEntryType(String sampleEntryType) {
        this.sampleEntryType = sampleEntryType;
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