package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.StorageInfo;

/**
 * Created by Administrator on 2017/9/17.
 */
public interface StorageInfoDao extends BaseDao<StorageInfo, Integer>{

    public StorageInfo selectStorageInfoByReagentSuppliesInfoId(Integer reagentSuppliesInfoId);

}
