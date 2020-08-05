package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.SyncDna36RecordDao;
import com.bazl.hslims.manager.model.po.SyncDna36Record;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/3/12.
 */
@Repository
public class SyncDna36RecordDaoImpl extends BaseDaoImpl<SyncDna36Record, Integer> implements SyncDna36RecordDao {


    @Override
    public String namespace() {
        return SyncDna36Record.class.getName();
    }

}
