package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.StorageInfoDao;
import com.bazl.hslims.manager.model.po.StorageInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/9/17.
 */
@Repository
public class StorageInfoDaoImpl extends BaseDaoImpl<StorageInfo,Integer> implements StorageInfoDao {
    @Override
    public String namespace() {
        return StorageInfo.class.getName();
    }

    public StorageInfo selectStorageInfoByReagentSuppliesInfoId(Integer reagentSuppliesInfoId){
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectStorageInfoByReagentSuppliesInfoId",reagentSuppliesInfoId);
    }

}
