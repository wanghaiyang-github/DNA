package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.SyncDna36QueueDao;
import com.bazl.hslims.manager.model.po.SyncDna36Queue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */
@Repository
public class SyncDna36QueueDaoImpl extends BaseDaoImpl<SyncDna36Queue, Integer> implements SyncDna36QueueDao {


    @Override
    public String namespace() {
        return SyncDna36Queue.class.getName();
    }

    public List<Integer> selectPendingCaseIdList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectPendingCaseIdList");
    }

    public List<Integer> selectFailedCaseIdList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectFailedCaseIdList");
    }


    public void deleteByCaseId(Integer caseId) {
        this.getSqlSessionTemplate().delete(this.namespace() + ".deleteByCaseId", caseId);
    }

    public int insertQueue(SyncDna36Queue syncDna36Queue) {
        return this.getSqlSessionTemplate().insert(this.namespace() + ".insertQueue",syncDna36Queue);
    }

    public SyncDna36Queue selectBySampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectBySampleId", sampleId);
    }

}
