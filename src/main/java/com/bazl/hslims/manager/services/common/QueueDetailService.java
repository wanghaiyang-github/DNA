package com.bazl.hslims.manager.services.common;


import com.bazl.hslims.manager.model.po.QueueDetail;

import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */
public interface QueueDetailService {

    public int insert(QueueDetail queueDetail);

    public List<QueueDetail> selectListByQueueId(Integer queueId);

    public List<QueueDetail> selectListBySampleId(Integer sampleId);

}
