package com.bazl.hslims.manager.services.common;


import com.bazl.hslims.manager.model.po.QueueSample;

import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */
public interface QueueSampleService {

    public int insert(QueueSample queueSample);

    public List<QueueSample> selectList(QueueSample queueSample);

    public boolean updateSuccessQueueSample(String queueType, List<QueueSample> successQueueSampleList, String status);

    public boolean updateFaileQueueSample(String queueType, List<Integer> faileSampleList, String status);

    public int updateByStatus(QueueSample queueSample);

}
