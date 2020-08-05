package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/15.
 */
public class StorageInfo implements Serializable {
    private Integer id;

    private Integer reagentSuppliesInfoId;

    private int storageNum;

    private Date createDatetime;

    private Date updateDatetime;

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

    public int getStorageNum() {
        return storageNum;
    }

    public void setStorageNum(int storageNum) {
        this.storageNum = storageNum;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
