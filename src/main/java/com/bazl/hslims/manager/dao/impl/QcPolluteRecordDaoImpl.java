package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.QcPolluteRecordDao;
import com.bazl.hslims.manager.model.po.QcPolluteRecord;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/2/16.
 */
@Repository
public class QcPolluteRecordDaoImpl extends BaseDaoImpl<QcPolluteRecord, Integer> implements QcPolluteRecordDao {


    @Override
    public String namespace() {
        return QcPolluteRecord.class.getName();
    }

}
