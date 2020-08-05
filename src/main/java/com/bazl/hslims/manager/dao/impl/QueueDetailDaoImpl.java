package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.QueueDetailDao;
import com.bazl.hslims.manager.model.po.QueueDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/11/23.
 */
@Repository
public class QueueDetailDaoImpl extends BaseDaoImpl<QueueDetail, Integer> implements QueueDetailDao {

    public String namespace() { return QueueDetail.class.getName(); }

    public List<QueueDetail> selectListByQueueId(Integer queueId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByQueueId", queueId);
    }

    public List<QueueDetail> selectListBySampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListBySampleId", sampleId);
    }

}
