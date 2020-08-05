package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.ReagentSuppliesInfoDao;
import com.bazl.hslims.manager.dao.StorageInfoDao;
import com.bazl.hslims.manager.dao.StorageRecordInfoDao;
import com.bazl.hslims.manager.model.po.ReagentSuppliesInfo;
import com.bazl.hslims.manager.model.po.StorageInfo;
import com.bazl.hslims.manager.model.po.StorageRecordInfo;
import com.bazl.hslims.manager.services.common.StorageRecordInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/9/7.
 */
@Service
public class StorageRecordInfoServiceImpl implements StorageRecordInfoService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StorageRecordInfoDao storageRecordInfoDao;
    @Autowired
    ReagentSuppliesInfoDao reagentSuppliesInfoDao;
    @Autowired
    StorageInfoDao storageInfoDao;

    public int insert(StorageRecordInfo storageRecordInfo){
        return storageRecordInfoDao.insert(storageRecordInfo);
    }

    public int update(StorageRecordInfo storageRecordInfo) {
        return storageRecordInfoDao.update(storageRecordInfo);
    }

    @Override
    public List<StorageRecordInfo> selectList(StorageRecordInfo storageRecordInfo) {
        try {
            return storageRecordInfoDao.selectList(storageRecordInfo);
        } catch(Exception ex) {
            logger.error("storageRecordInfoService.selectList error!", ex);
            return null;
        }
    }

    public int selectCount(StorageRecordInfo storageRecordInfo) {
        try {
            return storageRecordInfoDao.selectCount(storageRecordInfo);
        }catch(Exception ex){
            logger.error("UserInfoService.selectCount error!", ex);
            return 0;
        }
    }

    public List<StorageRecordInfo> selectPaginationList(StorageRecordInfo storageRecordInfo,PageInfo pageInfo){
        List<StorageRecordInfo> storeOperList = null;

        try {
            int pageNo = pageInfo.getPage();
            int pageSize = pageInfo.getEvePageRecordCnt();
            storageRecordInfo.setOffset((pageNo - 1) * pageSize);
            storageRecordInfo.setRows(storageRecordInfo.getOffset() + pageSize);

            storeOperList = storageRecordInfoDao.selectPaginationList(storageRecordInfo);

        } catch(Exception ex) {
            logger.error("StorageRecordInfoService.selectPaginationList error!", ex);
            return null;
        }

        return storeOperList;
    }

    public StorageRecordInfo selectById (Integer id) {
        return storageRecordInfoDao.selectById(id);
    }

    public int updateInStorage(StorageRecordInfo storageRecordInfo) {
        return storageRecordInfoDao.updateInStorage(storageRecordInfo);
    }

    public int updateOutStorage(StorageRecordInfo storageRecordInfo) {
        return storageRecordInfoDao.updateOutStorage(storageRecordInfo);
    }

}
