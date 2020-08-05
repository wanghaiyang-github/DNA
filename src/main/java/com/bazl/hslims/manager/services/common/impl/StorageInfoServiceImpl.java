package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.StorageInfoDao;
import com.bazl.hslims.manager.model.po.StorageInfo;
import com.bazl.hslims.manager.services.common.StorageInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/9/17.
 */
@Service
public class StorageInfoServiceImpl implements StorageInfoService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StorageInfoDao storageInfoDao;

    @Override
    public StorageInfo selectStorageInfoByReagentSuppliesInfoId(Integer reagentSuppliesInfoId){
        try {
            return storageInfoDao.selectStorageInfoByReagentSuppliesInfoId(reagentSuppliesInfoId);
        } catch(Exception ex) {
            logger.error("storageInfoService.selectStorageInfoByReagentSuppliesInfoId error!", ex);
            return null;
        }
    }

    @Override
    public int add(StorageInfo storageInfo) {
        try {
            storageInfoDao.insert(storageInfo);
            return 1;
        }catch(Exception ex){
            logger.error("新增库存错误！", ex);
            throw ex;
        }
    }

    @Override
    public int update(StorageInfo storageInfo) {
        try {
            storageInfoDao.update(storageInfo);
            return 1;
        }catch(Exception ex){
            logger.error("更新库存错误！", ex);
            throw ex;
        }
    }

}
