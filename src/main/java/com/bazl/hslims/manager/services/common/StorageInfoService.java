package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.StorageInfo;

/**
 * Created by Administrator on 2017/9/17.
 */
public interface StorageInfoService {
    public int add(StorageInfo storageInfo);

    public int update(StorageInfo storageInfo);

    public StorageInfo selectStorageInfoByReagentSuppliesInfoId(Integer reagentSuppliesInfoId);

}
