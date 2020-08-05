package com.bazl.hslims.manager.model.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/16.
 */
public class AllCheckPrint implements Serializable {

    private Integer caseId;

    private Integer consignmentId;

    private Integer extractRecordId;

    private Integer pcrRecordId;

    private Integer syRecordId;

    private String printManage;

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    public Integer getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(Integer consignmentId) {
        this.consignmentId = consignmentId;
    }

    public Integer getExtractRecordId() {
        return extractRecordId;
    }

    public void setExtractRecordId(Integer extractRecordId) {
        this.extractRecordId = extractRecordId;
    }

    public Integer getPcrRecordId() {
        return pcrRecordId;
    }

    public void setPcrRecordId(Integer pcrRecordId) {
        this.pcrRecordId = pcrRecordId;
    }

    public Integer getSyRecordId() {
        return syRecordId;
    }

    public void setSyRecordId(Integer syRecordId) {
        this.syRecordId = syRecordId;
    }

    public String getPrintManage() {
        return printManage;
    }

    public void setPrintManage(String printManage) {
        this.printManage = printManage;
    }
}
