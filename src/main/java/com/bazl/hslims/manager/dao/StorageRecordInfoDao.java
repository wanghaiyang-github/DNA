package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.StorageRecordInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public interface StorageRecordInfoDao extends BaseDao<StorageRecordInfo, Integer>{
    public int selectCount(StorageRecordInfo storageRecordInfo);

    public List<StorageRecordInfo> selectPaginationList(StorageRecordInfo storageRecordInfo);

    public int updateInStorage(StorageRecordInfo storageRecordInfo);

    public int updateOutStorage(StorageRecordInfo storageRecordInfo);

}
