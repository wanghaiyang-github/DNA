package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.dao.SyncDna36QueueDao;
import com.bazl.hslims.manager.dao.SyncDna36RecordDao;
import com.bazl.hslims.manager.model.po.SyncDna36Queue;
import com.bazl.hslims.manager.model.po.SyncDna36Record;
import com.bazl.hslims.manager.services.common.SyncDna36DataService;
import com.bazl.hslims.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */
@Service
public class SyncDna36DataServiceImpl implements SyncDna36DataService {

    final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    SyncDna36QueueDao syncDna36QueueDao;
    @Autowired
    SyncDna36RecordDao syncDna36RecordDao;

    public List<Integer> selectPendingCaseIdList() {
        return syncDna36QueueDao.selectPendingCaseIdList();
    }

    public List<Integer> selectFailedCaseIdList() {
        return syncDna36QueueDao.selectFailedCaseIdList();
    }

    public List<SyncDna36Queue> selectPendingSyncDna36QueueList() {
        SyncDna36Queue queue = new SyncDna36Queue();
        queue.setSyncFailedFlag(Constants.FLAG_FALSE);

        return syncDna36QueueDao.selectList(queue);
    }

    public List<SyncDna36Queue> selectFailedSyncDna36QueueList() {
        SyncDna36Queue queue = new SyncDna36Queue();
        queue.setSyncFailedFlag(Constants.FLAG_TRUE);

        return syncDna36QueueDao.selectList(queue);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateQueueSucceedByCaseId(Integer caseId) {
        SyncDna36Queue syncDna36Queue = new SyncDna36Queue();
        syncDna36Queue.setCaseId(caseId);
        List<SyncDna36Queue> queueList = syncDna36QueueDao.selectList(syncDna36Queue);

        Date syncDate = DateUtils.getCurrentDate();
        for(SyncDna36Queue queue : queueList){
            SyncDna36Record record = new SyncDna36Record();
            record.setCaseId(queue.getCaseId());
            record.setSampleId(queue.getSampleId());
            record.setOperateType(queue.getOperateType());
            record.setOperatePerson(queue.getOperatePerson());
            record.setOperateDate(queue.getOperateDate());
            record.setSyncDate(syncDate);
            syncDna36RecordDao.insert(record);
        }

        syncDna36QueueDao.deleteByCaseId(caseId);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateQueueFailedByCaseId(Integer caseId, String faildMsg) {
        SyncDna36Queue syncDna36Queue = new SyncDna36Queue();
        syncDna36Queue.setCaseId(caseId);
        List<SyncDna36Queue> queueList = syncDna36QueueDao.selectList(syncDna36Queue);

        for(SyncDna36Queue queue : queueList){
            queue.setSyncFailedFlag(Constants.FLAG_TRUE);
            queue.setSyncFailedMsg(faildMsg);

            syncDna36QueueDao.update(queue);
        }
    }

    public void updateQueueFailed(SyncDna36Queue syncDna36Queue) {
        try {
            syncDna36QueueDao.update(syncDna36Queue);
        } catch (Exception ex) {
            log.error("update syncDna36Queue to failed error!", ex);
        }
    }

    public void addSyncDna36Record(SyncDna36Queue syncDna36Queue) {

        SyncDna36Record record = new SyncDna36Record();
        record.setCaseId(syncDna36Queue.getCaseId());
        record.setSampleId(syncDna36Queue.getSampleId());
        record.setOperateType(syncDna36Queue.getOperateType());
        record.setOperatePerson(syncDna36Queue.getOperatePerson());
        record.setOperateDate(syncDna36Queue.getOperateDate());
        record.setSyncDate(new Date());

        syncDna36RecordDao.insert(record);

        syncDna36QueueDao.deleteById(syncDna36Queue.getId());
    }

    public int insertQueue(SyncDna36Queue syncDna36Queue){
        return syncDna36QueueDao.insertQueue(syncDna36Queue);
    }

    public SyncDna36Queue selectBySampleId(Integer sampleId) {
        return syncDna36QueueDao.selectBySampleId(sampleId);
    }

    public List<SyncDna36Queue> selectList(SyncDna36Queue queue) {
        return syncDna36QueueDao.selectList(queue);
    }

}
