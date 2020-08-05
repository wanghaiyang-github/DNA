package com.bazl.hslims.web.helper.codis;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
public class CodisContentModel implements Serializable {
    private String sampleNo;

    private List<CodisGeneInfo> codisGeneInfoList;

    private String importFlag;

    private String failedReason;

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public List<CodisGeneInfo> getCodisGeneInfoList() {
        return codisGeneInfoList;
    }

    public void setCodisGeneInfoList(List<CodisGeneInfo> codisGeneInfoList) {
        this.codisGeneInfoList = codisGeneInfoList;
    }

    public String getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(String importFlag) {
        this.importFlag = importFlag;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }
}
