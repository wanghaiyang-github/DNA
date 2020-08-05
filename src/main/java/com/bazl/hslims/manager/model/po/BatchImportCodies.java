package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/4/20.
 */
public class BatchImportCodies implements Serializable {

    private Integer id;

    private String codiesName;

    private String sampleBarcode;

    private int status;

    private String importReason;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodiesName() {
        return codiesName;
    }

    public void setCodiesName(String codiesName) {
        this.codiesName = codiesName;
    }

    public String getSampleBarcode() {
        return sampleBarcode;
    }

    public void setSampleBarcode(String sampleBarcode) {
        this.sampleBarcode = sampleBarcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImportReason() {
        return importReason;
    }

    public void setImportReason(String importReason) {
        this.importReason = importReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
