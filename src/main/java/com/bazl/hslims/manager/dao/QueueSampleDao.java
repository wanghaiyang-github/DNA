package com.bazl.hslims.manager.dao;


import com.bazl.hslims.manager.model.po.QueueSample;

/**
 * Created by admin on 2017/11/23.
 */
public interface QueueSampleDao extends BaseDao<QueueSample, Integer> {

    public int updateSuccessQueueSample(QueueSample queueSample);

    public int updateFaileQueueSample(QueueSample queueSample);

    public int updateByStatus(QueueSample queueSample);

}
