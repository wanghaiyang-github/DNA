package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.LimsSyRecordDao;
import com.bazl.hslims.manager.model.po.LimsSyRecord;
import com.bazl.hslims.manager.model.po.LimsSyRecordSample;
import com.bazl.hslims.manager.model.vo.LimsSyRecordVO;
import com.bazl.hslims.manager.services.common.LimsSyRecordService;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.web.datamodel.SyRecordDataModel;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
@Service
public class LimsSyRecordServiceImpl implements LimsSyRecordService {


    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    LimsSyRecordDao limsSyRecordDao;


    @Autowired
    SeqNoGenerateService seqNoGenerateService;

    public int selectCount(LimsSyRecordVO query) {
        return this.limsSyRecordDao.selectCount(query);
    }

    public List<LimsSyRecord> selectPaginationList(LimsSyRecordVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsSyRecordDao.selectPaginationList(query);
    }


    public LimsSyRecord selectById(Integer id) {
        return limsSyRecordDao.selectById(id);
    }

    public List<LimsSyRecordSample> selectSampleListBySyRecordId(Integer pcrRecordId) {
        return limsSyRecordDao.selectSampleListBySyRecordId(pcrRecordId);
    }


    @Override
    public int selectCountByCaseId(Integer caseId) {
        return limsSyRecordDao.selectCountByCaseId(caseId);
    }

    public List<LimsSyRecord> selectListByCaseId(Integer caseId) {
        return limsSyRecordDao.selectListByCaseId(caseId);
    }

    /**
     * 保存提取记录
     * @param dataModel
     * @return
     */
    @Transactional
    public Integer addRecord(SyRecordDataModel dataModel, String boardNo) {
        LimsSyRecord record = dataModel.getLimsSyRecord();
        List<LimsSyRecordSample> sampleModelList = dataModel.getLimsSyRecordSampleList();

        LimsSyRecordSample syRecordSample = null;
        List<LimsSyRecordSample> sampleList = new ArrayList<>();
        for(Iterator<LimsSyRecordSample> it = sampleModelList.iterator(); it.hasNext();){
            syRecordSample = it.next();

            if (boardNo.equals(syRecordSample.getBoardNo()))
                sampleList.add(syRecordSample);
        }

        if(StringUtils.isBlank(record.getOperatePersonName1())){
            record.setOperatePersonId1(LimsSecurityUtils.getLoginLabUser().getId());
            record.setOperatePersonName1(LimsSecurityUtils.getLoginLabUser().getUserName());
        }

        if(StringUtils.isBlank(record.getOperatePersonName2())){
            record.setOperatePersonId2(LimsSecurityUtils.getLoginLabUser().getId());
            record.setOperatePersonName2(LimsSecurityUtils.getLoginLabUser().getUserName());
        }

        record.setBoardNo(boardNo);
        record.setSampleCount(sampleList.size());
        record.setCreatePersonName(LimsSecurityUtils.getLoginName());
        record.setSyTaskNo(seqNoGenerateService.getNextSyTaskNoVal(DateUtils.getCurrentYearStr()));
        limsSyRecordDao.insert(record);

        for(LimsSyRecordSample samples : sampleList) {
            samples.setLimsSyRecordId(record.getId());
            samples.setCreatePerson(LimsSecurityUtils.getLoginName());

            limsSyRecordDao.insertLimsSyRecordSample(samples);
        }

        return record.getId();
    }

    /**
     * 保存提取记录
     * @param dataModel
     * @return
     */
    @Transactional
    public Integer updateRecord(SyRecordDataModel dataModel, String boardNo) {
        LimsSyRecord record = dataModel.getLimsSyRecord();
        List<LimsSyRecordSample> sampleList = dataModel.getLimsSyRecordSampleList();

        Integer recordId = record.getId();
        LimsSyRecord recordInDB = limsSyRecordDao.selectById(recordId);
        recordInDB.setSampleCount(sampleList.size());
        recordInDB.setBoardNo(boardNo);
        recordInDB.setOperatePersonId1(record.getOperatePersonId1());
        recordInDB.setOperatePersonName1(record.getOperatePersonName1());
        recordInDB.setOperatePersonId2(record.getOperatePersonId2());
        recordInDB.setOperatePersonName2(record.getOperatePersonName2());
        recordInDB.setStartDatetime(record.getStartDatetime());
        recordInDB.setEndDatetime(record.getEndDatetime());
        limsSyRecordDao.update(recordInDB);


        limsSyRecordDao.deleteLimsSyRecordSamplesByRecordId(recordId);
        for(LimsSyRecordSample samples : sampleList) {
            samples.setLimsSyRecordId(record.getId());
            samples.setCreatePerson(LimsSecurityUtils.getLoginName());
            limsSyRecordDao.insertLimsSyRecordSample(samples);
        }

        return record.getId();
    }

    @Transactional
    public boolean deleteById(Integer extractRecordId) {
        try {
            limsSyRecordDao.deleteById(extractRecordId);
            limsSyRecordDao.deleteLimsSyRecordSamplesByRecordId(extractRecordId);
        }catch(Exception ex){
            logger.error("删除提取记录错误！", ex);
            return false;
        }

        return true;
    }

    @Override
    public int selectCountBySampleNo(String sampleNo) {
        return limsSyRecordDao.selectCountBySampleNo(sampleNo);
    }

    @Override
    public List<LimsSyRecordSample> selectListBySampleNo(String sampleNo) {
        return limsSyRecordDao.selectListBySampleNo(sampleNo);
    }

    @Override
    public List<LimsSyRecord> selectSampleNoByTaskId(String sampleNo) {
        return limsSyRecordDao.selectSampleNoByTaskId(sampleNo);
    }

    @Override
    public LimsSyRecord selectByTaskId(Integer taskId) {
        return limsSyRecordDao.selectByTaskId(taskId);
    }

}
