package com.bazl.hslims.manager.dao;


import com.bazl.hslims.manager.model.po.QueueDetail;

import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */
public interface QueueDetailDao extends BaseDao<QueueDetail, Integer> {

    public List<QueueDetail> selectListByQueueId(Integer queueId);

    public List<QueueDetail> selectListBySampleId(Integer sampleId);

}
