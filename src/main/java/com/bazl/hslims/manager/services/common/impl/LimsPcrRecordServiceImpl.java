package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.LimsPcrRecordDao;
import com.bazl.hslims.manager.model.po.LimsPcrRecord;
import com.bazl.hslims.manager.model.po.LimsPcrRecordSample;
import com.bazl.hslims.manager.model.vo.LimsPcrRecordVO;
import com.bazl.hslims.manager.services.common.LimsPcrRecordService;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.web.datamodel.PcrRecordDataModel;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
@Service
public class LimsPcrRecordServiceImpl implements LimsPcrRecordService {


    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LimsPcrRecordDao limsPcrRecordDao;

    @Autowired
    SeqNoGenerateService seqNoGenerateService;

    public int selectCount(LimsPcrRecordVO query) {
        return this.limsPcrRecordDao.selectCount(query);
    }

    public List<LimsPcrRecord> selectPaginationList(LimsPcrRecordVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return limsPcrRecordDao.selectPaginationList(query);
    }


    public LimsPcrRecord selectById(Integer id) {
        return limsPcrRecordDao.selectById(id);
    }

    public List<LimsPcrRecordSample> selectSampleListByPcrRecordId(Integer pcrRecordId) {
        return limsPcrRecordDao.selectSampleListByPcrRecordId(pcrRecordId);
    }


    @Override
    public int selectCountByCaseId(Integer caseId) {
        return limsPcrRecordDao.selectCountByCaseId(caseId);
    }

    public List<LimsPcrRecord> selectListByCaseId(Integer caseId) {
        return limsPcrRecordDao.selectListByCaseId(caseId);
    }

    /**
     * 保存提取记录
     * @param dataModel
     * @return
     */
    @Transactional
    public Integer addRecord(PcrRecordDataModel dataModel) {
        LimsPcrRecord record = dataModel.getLimsPcrRecord();
        List<LimsPcrRecordSample> sampleList = dataModel.getLimsPcrRecordSampleList();

        record.setSampleCount(sampleList.size());
        record.setCreatePerson(LimsSecurityUtils.getLoginName());
        record.setPcrTaskNo(seqNoGenerateService.getNextPcrTaskNoVal(DateUtils.getCurrentYearStr()));
        limsPcrRecordDao.insert(record);

        for(LimsPcrRecordSample samples : sampleList) {
            samples.setLimsPcrRecordId(record.getId());
            samples.setCreatePerson(LimsSecurityUtils.getLoginName());

            limsPcrRecordDao.insertLimsPcrRecordSample(samples);
        }

        return record.getId();
    }

    /**
     * 保存提取记录
     * @param dataModel
     * @return
     */
    @Transactional
    public Integer updateRecord(PcrRecordDataModel dataModel) {
        LimsPcrRecord record = dataModel.getLimsPcrRecord();
        List<LimsPcrRecordSample> sampleList = dataModel.getLimsPcrRecordSampleList();

        Integer recordId = record.getId();
        LimsPcrRecord recordInDB = limsPcrRecordDao.selectById(recordId);
        recordInDB.setBoardNo(record.getBoardNo());
        recordInDB.setPcrProgram(record.getPcrProgram());
        recordInDB.setPcrSystem(record.getPcrSystem());
        recordInDB.setPcrReagent(record.getPcrReagent());
        recordInDB.setPcrInstrument(record.getPcrInstrument());
        recordInDB.setSampleCount(sampleList.size());
        recordInDB.setPcrPersonId1(record.getPcrPersonId1());
        recordInDB.setPcrPersonName1(record.getPcrPersonName1());
        recordInDB.setPcrPersonId2(record.getPcrPersonId2());
        recordInDB.setPcrPersonName2(record.getPcrPersonName2());
        recordInDB.setPcrStartDatetime(record.getPcrStartDatetime());
        recordInDB.setPcrEndDatetime(record.getPcrEndDatetime());
        limsPcrRecordDao.update(recordInDB);

        List<LimsPcrRecordSample> sampleListInDB = limsPcrRecordDao.selectSampleListByPcrRecordId(recordId);
        List<LimsPcrRecordSample> needAddList = new ArrayList<>();
        List<LimsPcrRecordSample> needUpdateList = new ArrayList<>();
        List<LimsPcrRecordSample> needDelList = new ArrayList<>();

        for(LimsPcrRecordSample sampleFromPage : sampleList) {
            if(sampleFromPage.getId() == null || sampleFromPage.getId() == 0) {
                sampleFromPage.setLimsPcrRecordId(recordId);
                sampleFromPage.setCreatePerson(LimsSecurityUtils.getLoginName());
                needAddList.add(sampleFromPage);
            } else {
                for(LimsPcrRecordSample sampleInDB : sampleListInDB) {
                    if(sampleInDB.getId().equals(sampleFromPage.getId())) {
                        sampleInDB.setPrimerUl(sampleFromPage.getPrimerUl());
                        sampleInDB.setBufferUl(sampleFromPage.getBufferUl());
                        sampleInDB.setTemplateUl(sampleFromPage.getTemplateUl());
                        sampleInDB.setDdwUl(sampleFromPage.getDdwUl());
                        needUpdateList.add(sampleInDB);
                        break;
                    }
                }
            }
        }

        for(LimsPcrRecordSample sampleInDB : sampleListInDB) {
            boolean hasDel = false;
            for(LimsPcrRecordSample tmpSample : needUpdateList) {
                if(tmpSample.getId().equals(sampleInDB.getId())) {
                    hasDel = true;
                    break;
                }
            }

            if(!hasDel) {
                needDelList.add(sampleInDB);
            }
        }

        //add
        for (LimsPcrRecordSample sampleAdd : needAddList) {
            limsPcrRecordDao.insertLimsPcrRecordSample(sampleAdd);
        }
        //update
        for (LimsPcrRecordSample sampleUpadte : needUpdateList) {
            limsPcrRecordDao.updateLimsPcrRecordSample(sampleUpadte);
        }
        //del
        for (LimsPcrRecordSample sampleDel : needDelList) {
            limsPcrRecordDao.deleteLimsPcrRecordSample(sampleDel);
        }

        return record.getId();
    }

    @Transactional
    public boolean deleteById(Integer extractRecordId) {
        try {
            limsPcrRecordDao.deleteById(extractRecordId);
            limsPcrRecordDao.deleteLimsPcrRecordSamplesByRecordId(extractRecordId);
        }catch(Exception ex){
            logger.error("删除提取记录错误！", ex);
            return false;
        }

        return true;
    }

    public LimsPcrRecordSample selectListBySampleId(Integer sampleId) {
        return limsPcrRecordDao.selectListBySampleId(sampleId);
    }

    @Override
    public int selectCountBySampleNo(String sampleNo) {
        return limsPcrRecordDao.selectCountBySampleNo(sampleNo);
    }

    @Override
    public List<LimsPcrRecordSample> selectListBySampleNo(String sampleNo) {
        return limsPcrRecordDao.selectListBySampleNo(sampleNo);
    }

    @Override
    public List<LimsPcrRecord> selectSampleNoByTaskId(String sampleNo) {
        return limsPcrRecordDao.selectSampleNoByTaskId(sampleNo);
    }

    @Override
    public LimsPcrRecord selectByTaskId(Integer taskId) {
        return limsPcrRecordDao.selectByTaskId(taskId);
    }

}
