package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.StorageRecordInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
public interface StorageRecordInfoService {

    public int insert(StorageRecordInfo storageRecordInfo);

    public int update(StorageRecordInfo storageRecordInfo);

    public List<StorageRecordInfo> selectList(StorageRecordInfo storageRecordInfo);

    public int selectCount(StorageRecordInfo storageRecordInfo);

    public List<StorageRecordInfo> selectPaginationList(StorageRecordInfo storageRecordInfo, PageInfo pageInfo);

    public StorageRecordInfo selectById (Integer id);

    public int updateInStorage(StorageRecordInfo storageRecordInfo);

    public int updateOutStorage(StorageRecordInfo storageRecordInfo);
}
