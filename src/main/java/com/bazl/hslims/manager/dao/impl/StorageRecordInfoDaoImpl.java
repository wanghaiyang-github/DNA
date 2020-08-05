package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.StorageRecordInfoDao;
import com.bazl.hslims.manager.model.po.StorageRecordInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */
@Repository
public class StorageRecordInfoDaoImpl extends BaseDaoImpl<StorageRecordInfo, Integer> implements StorageRecordInfoDao {
    @Override
    public String namespace() {
        return StorageRecordInfo.class.getName();
    }

    public int selectCount(StorageRecordInfo storageRecordInfo){
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectCount",storageRecordInfo);
    }

    public List<StorageRecordInfo> selectPaginationList(StorageRecordInfo storageRecordInfo){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectPaginationList", storageRecordInfo);
    }

    public int updateInStorage (StorageRecordInfo storageRecordInfo) {
        return this.getSqlSessionTemplate().update(namespace() + ".updateInStorage", storageRecordInfo);
    }

    public int updateOutStorage (StorageRecordInfo storageRecordInfo) {
        return this.getSqlSessionTemplate().update(namespace() + ".updateOutStorage", storageRecordInfo);
    }
}
