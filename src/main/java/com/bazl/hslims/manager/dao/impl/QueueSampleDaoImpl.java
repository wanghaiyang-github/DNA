package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.QueueSampleDao;
import com.bazl.hslims.manager.model.po.QueueSample;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2017/11/23.
 */
@Repository
public class QueueSampleDaoImpl extends BaseDaoImpl<QueueSample, Integer> implements QueueSampleDao {

    @Override
    public String namespace() { return QueueSample.class.getName(); }

    @Override
    public int updateSuccessQueueSample(QueueSample queueSample) {
        return this.getSqlSessionTemplate().update(namespace() + ".updateSuccessQueueSample", queueSample);
    }

    @Override
    public int updateFaileQueueSample(QueueSample queueSample) {
        return this.getSqlSessionTemplate().update(namespace() + ".updateFaileQueueSample", queueSample);
    }

    @Override
    public int updateByStatus(QueueSample queueSample) {
        return this.getSqlSessionTemplate().update(namespace() + ".updateByStatus", queueSample);
    }

}
