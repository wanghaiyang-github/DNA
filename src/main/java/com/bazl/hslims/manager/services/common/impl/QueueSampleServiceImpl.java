package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.QueueSampleDao;
import com.bazl.hslims.manager.model.po.QueueSample;
import com.bazl.hslims.manager.services.common.QueueSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */
@Service
public class QueueSampleServiceImpl implements QueueSampleService {

    @Autowired
    QueueSampleDao queueSampleDao;

    @Override
    public int insert(QueueSample queueSample) {
        return queueSampleDao.insert(queueSample);
    }

    @Override
    public List<QueueSample> selectList(QueueSample queueSample) {
        return queueSampleDao.selectList(queueSample);
    }

    @Override
    public boolean updateSuccessQueueSample(String queueType, List<QueueSample> successQueueSampleList, String status) {
        boolean flag = true;

        try {
            for (QueueSample m : successQueueSampleList) {
                m.setQueueType(queueType);
                m.setStatus(status);


                queueSampleDao.updateSuccessQueueSample(m);
            }
        }catch (Exception e) {
            e.getStackTrace();
            System.out.println("更新失败！");
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean updateFaileQueueSample(String queueType, List<Integer> faileSampleList, String status) {
        boolean flag = true;

        try {
            QueueSample queueSample;
            for (Integer i : faileSampleList) {
                queueSample = new QueueSample();
                queueSample.setCaseId(i);
                queueSample.setQueueType(queueType);
                queueSample.setStatus(status);

                queueSampleDao.updateFaileQueueSample(queueSample);
            }
        }catch (Exception e) {
            e.getStackTrace();
            System.out.println("更新失败！");
            flag = false;
        }
        return flag;
    }

    @Override
    public int updateByStatus(QueueSample queueSample) {
        return queueSampleDao.updateByStatus(queueSample);
    }

}
