package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.SyncDna36Queue;

import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */
public interface SyncDna36DataService {

    public List<Integer> selectPendingCaseIdList();
    public List<Integer> selectFailedCaseIdList();

    public List<SyncDna36Queue> selectPendingSyncDna36QueueList();

    public List<SyncDna36Queue> selectFailedSyncDna36QueueList();

    public void updateQueueSucceedByCaseId(Integer caseId);
    public void updateQueueFailedByCaseId(Integer caseId, String faildMsg);

    public void updateQueueFailed(SyncDna36Queue syncDna36Queue);

    public void addSyncDna36Record(SyncDna36Queue syncDna36Queue);

    public int insertQueue(SyncDna36Queue syncDna36Queue);

    public SyncDna36Queue selectBySampleId(Integer sampleId);

    public List<SyncDna36Queue> selectList(SyncDna36Queue queue);

}
