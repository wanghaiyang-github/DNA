package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.QueueDetailDao;
import com.bazl.hslims.manager.model.po.QueueDetail;
import com.bazl.hslims.manager.services.common.QueueDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */
@Service
public class QueueDetailServiceImpl implements QueueDetailService {

    @Autowired
    QueueDetailDao queueDetailDao;

    public int insert(QueueDetail queueDetail) {
        return queueDetailDao.insert(queueDetail);
    }

    public List<QueueDetail> selectListByQueueId (Integer queueId) {
        return queueDetailDao.selectListByQueueId(queueId);
    }

    public List<QueueDetail> selectListBySampleId(Integer sampleId) {
        return queueDetailDao.selectListBySampleId(sampleId);
    }

}
