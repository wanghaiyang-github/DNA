package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.LimsConsignmentDao;
import com.bazl.hslims.manager.dao.LimsExtractRecordDao;
import com.bazl.hslims.manager.dao.LimsSampleInfoDao;
import com.bazl.hslims.manager.model.po.LimsExtractRecord;
import com.bazl.hslims.manager.model.po.LimsExtractRecordSample;
import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.manager.model.vo.LimsExtractRecordVO;
import com.bazl.hslims.manager.services.common.LimsExtractRecordService;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.web.datamodel.ExtractRecordDataModel;
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
public class LimsExtractRecordServiceImpl implements LimsExtractRecordService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LimsExtractRecordDao limsExtractRecordDao;

    @Autowired
    LimsSampleInfoDao limsSampleInfoDao;

    @Autowired
    SeqNoGenerateService seqNoGenerateService;

    @Autowired
    LimsConsignmentDao limsConsignmentDao;

    public int selectCount(LimsExtractRecordVO query) {
        return this.limsExtractRecordDao.selectCount(query);
    }

    public List<LimsExtractRecord> selectPaginationList(LimsExtractRecordVO query, PageInfo pageInfo) {
            int pageNo = pageInfo.getPage();
            int pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(pageSize);

            return limsExtractRecordDao.selectPaginationList(query);
    }

    @Override
    public int selectCountByCaseId(Integer caseId) {
        return limsExtractRecordDao.selectCountByCaseId(caseId);
    }

    public List<LimsExtractRecord> selectListByCaseId(Integer caseId) {
        return limsExtractRecordDao.selectListByCaseId(caseId);
    }


    @Override
    public LimsExtractRecord selectById(Integer id) {
        return limsExtractRecordDao.selectById(id);
    }

    @Override
    public List<LimsExtractRecordSample> selectSampleListByExtractRecordId(Integer extractRecordId) {
        return limsExtractRecordDao.selectSampleListByExtractRecordId(extractRecordId);
    }

    /**
     * 保存提取记录
     * @param dataModel
     * @return
     */
    @Override
    @Transactional
    public Integer addRecord(ExtractRecordDataModel dataModel) {
        LimsExtractRecord record = dataModel.getLimsExtractRecord();
        List<LimsExtractRecordSample> sampleList = dataModel.getLimsExtractRecordSampleList();
        LimsSampleInfo sample = null;
        for(LimsExtractRecordSample samples : sampleList) {
            if(samples.getSampleId() == null || samples.getSampleId() == 0) {
                String no = samples.getSampleNo();
                String newStr = null;
                int i = no.indexOf("-");
                if(no.length() == 18){
                    newStr = no.substring(0,i+13);
                }else if(no.length() > 18){
                    newStr = no.substring(0,i+14);
                }else if(no.length() == 17){
                    newStr = no.substring(0,i+12);
                }

                sample = new LimsSampleInfo();
                List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoDao.selectListBySampleNo(newStr);
                for (LimsSampleInfo limsSampleInfo:limsSampleInfoList) {
                    sample = limsSampleInfo;
                }

                sample.setId(null);
                sample.setSampleNo(samples.getSampleNo());
                sample.setSampleName(samples.getSampleName());

                try {
                    limsSampleInfoDao.insert(sample);
                }catch (Exception e){
                    logger.error("插入样本信息失败！", e);
                    throw e;
                }
            }
        }

        record.setSampleCount(sampleList.size());
        record.setCreatePerson(LimsSecurityUtils.getLoginName());
        record.setExtractTaskNo(seqNoGenerateService.getNextExtractTaskNoVal(DateUtils.getCurrentYearStr()));
        limsExtractRecordDao.insert(record);

        for(LimsExtractRecordSample samples : sampleList) {

            samples.setLimsExtractRecordId(record.getId());
            samples.setCreatePersonId(LimsSecurityUtils.getLoginLabUser().getId());
            if(samples.getSampleId() == null){
                samples.setSampleId(sample.getId());
            }
            samples.setCreatePersonName(LimsSecurityUtils.getLoginName());

            limsExtractRecordDao.insertLimsExtractRecordSample(samples);

            LimsSampleInfo sampleInfo = new LimsSampleInfo();
            sampleInfo.setSampleType(samples.getSampleType());
            sampleInfo.setId(samples.getSampleId());
            limsSampleInfoDao.updateSampleType(sampleInfo);

        }

        return record.getId();
    }

    /**
     * 保存提取记录
     * @param dataModel
     * @return
     */
    @Override
    @Transactional
    public Integer updateRecord(ExtractRecordDataModel dataModel) {
        LimsExtractRecord record = dataModel.getLimsExtractRecord();
        List<LimsExtractRecordSample> sampleList = dataModel.getLimsExtractRecordSampleList();

        Integer recordId = record.getId();
        LimsExtractRecord recordInDB = limsExtractRecordDao.selectById(recordId);
        recordInDB.setSampleCount(sampleList.size());
        recordInDB.setExtractPersonId1(record.getExtractPersonId1());
        recordInDB.setExtractPersonName1(record.getExtractPersonName1());
        recordInDB.setExtractPersonId2(record.getExtractPersonId2());
        recordInDB.setExtractPersonName2(record.getExtractPersonName2());
        recordInDB.setExtractDatetime(record.getExtractDatetime());
        limsExtractRecordDao.update(recordInDB);

        List<LimsExtractRecordSample> sampleListInDB = limsExtractRecordDao.selectSampleListByExtractRecordId(recordId);
        List<LimsExtractRecordSample> needAddList = new ArrayList<>();
        List<LimsExtractRecordSample> needUpdateList = new ArrayList<>();
        List<LimsExtractRecordSample> needDelList = new ArrayList<>();

        for(LimsExtractRecordSample sampleFromPage : sampleList) {
            if(sampleFromPage.getId() == null || sampleFromPage.getId() == 0) {
                sampleFromPage.setLimsExtractRecordId(recordId);
                sampleFromPage.setCreatePersonId(LimsSecurityUtils.getLoginLabUser().getId());
                sampleFromPage.setCreatePersonName(LimsSecurityUtils.getLoginName());
                needAddList.add(sampleFromPage);
            } else {
                for(LimsExtractRecordSample sampleInDB : sampleListInDB) {
                    if(sampleInDB.getId().equals(sampleFromPage.getId())) {
                        sampleInDB.setExtractMethod(sampleFromPage.getExtractMethod());
                        sampleInDB.setExtractHb(sampleFromPage.getExtractHb());
                        sampleInDB.setExtractPsa(sampleFromPage.getExtractPsa());
                        sampleInDB.setExtractLiFlag(sampleFromPage.getExtractLiFlag());
                        sampleInDB.setExtractZhenFlag(sampleFromPage.getExtractZhenFlag());
                        sampleInDB.setExtractJiaoFlag(sampleFromPage.getExtractJiaoFlag());
                        sampleInDB.setExtractYuFlag(sampleFromPage.getExtractYuFlag());
                        sampleInDB.setExtractPosition(sampleFromPage.getExtractPosition());
                        sampleInDB.setChangeMethod(sampleFromPage.getChangeMethod());
                        sampleInDB.setOtherChangeMethod(sampleFromPage.getOtherChangeMethod());
                        needUpdateList.add(sampleInDB);
                        break;
                    }
                }
            }

            LimsSampleInfo sampleInfo = new LimsSampleInfo();
            sampleInfo.setId(sampleFromPage.getSampleId());
            sampleInfo.setSampleType(sampleFromPage.getSampleType());
            limsSampleInfoDao.updateSampleType(sampleInfo);
        }

        for(LimsExtractRecordSample sampleInDB : sampleListInDB) {
            boolean hasDel = false;
            for(LimsExtractRecordSample tmpSample : needUpdateList) {
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
        for (LimsExtractRecordSample sampleAdd : needAddList) {
            limsExtractRecordDao.insertLimsExtractRecordSample(sampleAdd);
        }
        //update
        for (LimsExtractRecordSample sampleUpadte : needUpdateList) {
            limsExtractRecordDao.updateLimsExtractRecordSample(sampleUpadte);
        }
        //del
        for (LimsExtractRecordSample sampleDel : needDelList) {
            limsExtractRecordDao.deleteLimsExtractRecordSample(sampleDel);
        }

        return record.getId();
    }

    @Transactional
    public boolean deleteById(Integer extractRecordId) {
        try {
            limsExtractRecordDao.deleteById(extractRecordId);
            limsExtractRecordDao.deleteLimsExtractRecordSamplesByRecordId(extractRecordId);
        }catch(Exception ex){
            logger.error("删除提取记录错误！", ex);
            return false;
        }

        return true;
    }

    public List<LimsExtractRecordSample> selectListBySampleId(Integer sampleId){
        return limsExtractRecordDao.selectListBySampleId(sampleId);
    }

    @Override
    public int selectCountBySampleNo(String sampleNo) {
        return limsExtractRecordDao.selectCountBySampleNo(sampleNo);
    }

    @Override
    public List<LimsExtractRecordSample> selectListBySampleNo(String sampleNo) {
        return limsExtractRecordDao.selectListBySampleNo(sampleNo);
    }

    @Override
    public List<LimsExtractRecordSample> selectListSpitSampleNo(String sampleNo) {
        return limsExtractRecordDao.selectListSpitSampleNo(sampleNo);
    }

    @Override
    public List<LimsExtractRecord> selectSampleNoByTaskId(String sampleNo) {
        return limsExtractRecordDao.selectSampleNoByTaskId(sampleNo);
    }

    @Override
    public LimsExtractRecord selectByTaskId(Integer taskId) {
        return limsExtractRecordDao.selectByTaskId(taskId);
    }

}
