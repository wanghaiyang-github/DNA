package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.SyncDna36Queue;

import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */
public interface SyncDna36QueueDao extends BaseDao<SyncDna36Queue, Integer> {

    public List<Integer> selectPendingCaseIdList();
    public List<Integer> selectFailedCaseIdList();

    public int insertQueue(SyncDna36Queue syncDna36Queue);

    public void deleteByCaseId(Integer caseId);

    public SyncDna36Queue selectBySampleId(Integer sampleId);

}
