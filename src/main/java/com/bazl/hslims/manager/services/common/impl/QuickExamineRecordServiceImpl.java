package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.*;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.services.common.QuickExamineRecordService;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.web.datamodel.QuickExamineRecordModel;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
public class QuickExamineRecordServiceImpl implements QuickExamineRecordService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    LimsExtractRecordDao limsExtractRecordDao;
    @Autowired
    LimsPcrRecordDao limsPcrRecordDao;
    @Autowired
    LimsSyRecordDao limsSyRecordDao;
    @Autowired
    SeqNoGenerateService seqNoGenerateService;
    @Autowired
    LimsQuickExamineRecordDao limsQuickExamineRecordDao;
    @Autowired
    LimsSampleInfoDao limsSampleInfoDao;

    public Map<String, Object> addRecord(QuickExamineRecordModel quickExamineRecordModel) {
        Map<String, Object> result = new HashMap<>();

        try {
            LimsExtractRecord extractRecord = quickExamineRecordModel.getLimsExtractRecord();
            LimsPcrRecord pcrRecord = quickExamineRecordModel.getLimsPcrRecord();
            LimsSyRecord syRecord = quickExamineRecordModel.getLimsSyRecord();
            List<LimsQuickExamineRecordSample> quickExamineRecordSampleList = quickExamineRecordModel.getLimsQuickExamineRecordSampleList();

            List<LimsExtractRecordSample> extractRecordSampleList = new ArrayList<>();
            List<LimsPcrRecordSample> pcrRecordSampleList = new ArrayList<>();
            List<LimsSyRecordSample> syRecordSampleList = new ArrayList<>();
            LimsExtractRecordSample extractRecordSample;
            LimsPcrRecordSample pcrRecordSample;
            LimsSyRecordSample syRecordSample;
            for (LimsQuickExamineRecordSample lqers : quickExamineRecordSampleList) {
                extractRecordSample = new LimsExtractRecordSample();
                extractRecordSample.setSampleId(lqers.getSampleId());
                extractRecordSample.setSampleNo(lqers.getSampleNo());
                extractRecordSample.setExtractHb(lqers.getExtractHb());
                extractRecordSample.setExtractPsa(lqers.getExtractPsa());
                extractRecordSample.setExtractPosition(lqers.getExtractPosition());
                extractRecordSample.setChangeMethod(lqers.getChangeMethod());
                extractRecordSample.setOtherChangeMethod(lqers.getOtherChangeMethod());
                extractRecordSample.setExtractMethod(lqers.getExtractMethod());
                extractRecordSample.setExtractLiFlag(lqers.getExtractLiFlag());
                extractRecordSample.setExtractZhenFlag(lqers.getExtractZhenFlag());
                extractRecordSample.setExtractJiaoFlag(lqers.getExtractJiaoFlag());
                extractRecordSample.setExtractYuFlag(lqers.getExtractYuFlag());

                extractRecordSampleList.add(extractRecordSample);

                pcrRecordSample = new LimsPcrRecordSample();
                pcrRecordSample.setSampleId(lqers.getSampleId());
                pcrRecordSample.setSampleNo(lqers.getSampleNo());
                pcrRecordSample.setStandardFlag(lqers.getStandardPcrFlag());
                pcrRecordSample.setPrimerUl(lqers.getPrimerUl());
                pcrRecordSample.setBufferUl(lqers.getBufferUl());
                pcrRecordSample.setTemplateUl(lqers.getTemplateUl());
                pcrRecordSample.setDdwUl(lqers.getDdwUl());
                pcrRecordSample.setSamplePosition(lqers.getSamplePosition());

                pcrRecordSampleList.add(pcrRecordSample);

                syRecordSample = new LimsSyRecordSample();
                syRecordSample.setSampleId(lqers.getSampleId());
                syRecordSample.setSampleNo(lqers.getSampleNo());
                syRecordSample.setStandardFlag(lqers.getStandardSyFlag());
                syRecordSample.setBoardNo(lqers.getSyBoardNo());
                syRecordSample.setSamplePosition(lqers.getSamplePosition());

                syRecordSampleList.add(syRecordSample);

                LimsSampleInfo sampleInfo = new LimsSampleInfo();
                sampleInfo.setId(lqers.getSampleId());
                sampleInfo.setSampleType(lqers.getSampleType());
                limsSampleInfoDao.updateSampleType(sampleInfo);
            }

            extractRecord.setSampleCount(extractRecordSampleList.size());
            extractRecord.setCreatePerson(LimsSecurityUtils.getLoginName());
            extractRecord.setExtractTaskNo(seqNoGenerateService.getNextExtractTaskNoVal(DateUtils.getCurrentYearStr()));
            limsExtractRecordDao.insert(extractRecord);

            for(LimsExtractRecordSample lers : extractRecordSampleList) {
                lers.setLimsExtractRecordId(extractRecord.getId());
                lers.setCreatePersonName(LimsSecurityUtils.getLoginName());
                lers.setCreatePersonId(LimsSecurityUtils.getLoginLabUser().getId());

                limsExtractRecordDao.insertLimsExtractRecordSample(lers);
            }

            result.put("extractRecordId", extractRecord.getId());

            pcrRecord.setSampleCount(pcrRecordSampleList.size());
            pcrRecord.setCreatePerson(LimsSecurityUtils.getLoginName());
            pcrRecord.setPcrTaskNo(seqNoGenerateService.getNextPcrTaskNoVal(DateUtils.getCurrentYearStr()));
            limsPcrRecordDao.insert(pcrRecord);

            for(LimsPcrRecordSample lprs : pcrRecordSampleList) {
                lprs.setLimsPcrRecordId(pcrRecord.getId());
                lprs.setCreatePerson(LimsSecurityUtils.getLoginName());

                limsPcrRecordDao.insertLimsPcrRecordSample(lprs);
            }

            result.put("pcrRecordId", pcrRecord.getId());


            String syRecordIds = "";
            String boardNo = syRecord.getBoardNo();
            String[] boardNoStr = boardNo.split(",");

            for (int i = 0; i < boardNoStr.length; i ++) {

                LimsSyRecordSample limsSyRecordSample = null;
                List<LimsSyRecordSample> limsSyRecordSampleList = new ArrayList<>();
                for(Iterator<LimsSyRecordSample> it = syRecordSampleList.iterator(); it.hasNext();){
                    limsSyRecordSample = it.next();

                    if (boardNoStr[i].equals(limsSyRecordSample.getBoardNo()))
                        limsSyRecordSampleList.add(limsSyRecordSample);
                }

                if(StringUtils.isBlank(syRecord.getOperatePersonName1())){
                    syRecord.setOperatePersonId1(LimsSecurityUtils.getLoginLabUser().getId());
                    syRecord.setOperatePersonName1(LimsSecurityUtils.getLoginLabUser().getUserName());
                }

                if(StringUtils.isBlank(syRecord.getOperatePersonName2())){
                    syRecord.setOperatePersonId2(LimsSecurityUtils.getLoginLabUser().getId());
                    syRecord.setOperatePersonName2(LimsSecurityUtils.getLoginLabUser().getUserName());
                }

                syRecord.setBoardNo(boardNoStr[i]);
                syRecord.setSampleCount(limsSyRecordSampleList.size());
                syRecord.setCreatePersonName(LimsSecurityUtils.getLoginName());
                syRecord.setSyTaskNo(seqNoGenerateService.getNextSyTaskNoVal(DateUtils.getCurrentYearStr()));
                limsSyRecordDao.insert(syRecord);

                for(LimsSyRecordSample lsrs : limsSyRecordSampleList) {
                    lsrs.setLimsSyRecordId(syRecord.getId());
                    lsrs.setCreatePerson(LimsSecurityUtils.getLoginName());

                    limsSyRecordDao.insertLimsSyRecordSample(lsrs);
                }

                syRecordIds += syRecord.getId() + ",";
            }

            result.put("syRecordIds", syRecordIds);

            LimsQuickExamineRecord limsQuickExamineRecord = new LimsQuickExamineRecord();
            if (extractRecord.getCaseId() != null) {
                limsQuickExamineRecord.setCaseId(extractRecord.getCaseId());
            }else if (pcrRecord.getCaseId() != null) {
                limsQuickExamineRecord.setCaseId(pcrRecord.getCaseId());
            }else if (syRecord.getCaseId() != null) {
                limsQuickExamineRecord.setCaseId(syRecord.getCaseId());
            }
            limsQuickExamineRecord.setLimsExtractRecordId(extractRecord.getId());
            limsQuickExamineRecord.setLimsPcrRecordId(pcrRecord.getId());
            limsQuickExamineRecord.setLimsSyRecordId(syRecordIds.substring(0, syRecordIds.length() - 1));
            limsQuickExamineRecord.setCreatePerson(LimsSecurityUtils.getLoginName());

            limsQuickExamineRecordDao.insert(limsQuickExamineRecord);

            result.put("success", true);
        }catch (Exception e) {
            logger.error("保存失败！", e);
            result.put("success", false);
        }

        return result;
    }

    public Map<String, Object> updateRecord(QuickExamineRecordModel quickExamineRecordModel) {
        Map<String, Object> result = new HashMap<>();

        try {

            LimsQuickExamineRecord quickExamineRecord = limsQuickExamineRecordDao.selectById(quickExamineRecordModel.getQuickExamineRecordId());

            LimsExtractRecord extractRecord = quickExamineRecordModel.getLimsExtractRecord();
            LimsPcrRecord pcrRecord = quickExamineRecordModel.getLimsPcrRecord();
            LimsSyRecord syRecord = quickExamineRecordModel.getLimsSyRecord();
            List<LimsQuickExamineRecordSample> quickExamineRecordSampleList = quickExamineRecordModel.getLimsQuickExamineRecordSampleList();

            List<LimsExtractRecordSample> extractRecordSampleList = new ArrayList<>();
            List<LimsPcrRecordSample> pcrRecordSampleList = new ArrayList<>();
            List<LimsSyRecordSample> syRecordSampleList = new ArrayList<>();
            LimsExtractRecordSample extractRecordSample;
            LimsPcrRecordSample pcrRecordSample;
            LimsSyRecordSample syRecordSample;
            for (LimsQuickExamineRecordSample lqers : quickExamineRecordSampleList) {
                extractRecordSample = new LimsExtractRecordSample();
                extractRecordSample.setId(lqers.getExtractRecordSampleId());
                extractRecordSample.setLimsExtractRecordId(lqers.getExtractRecordId());
                extractRecordSample.setSampleId(lqers.getSampleId());
                extractRecordSample.setSampleNo(lqers.getSampleNo());
                extractRecordSample.setExtractPsa(lqers.getExtractPsa());
                extractRecordSample.setExtractHb(lqers.getExtractHb());
                extractRecordSample.setExtractPosition(lqers.getExtractPosition());
                extractRecordSample.setChangeMethod(lqers.getChangeMethod());
                extractRecordSample.setOtherChangeMethod(lqers.getOtherChangeMethod());
                extractRecordSample.setExtractMethod(lqers.getExtractMethod());
                extractRecordSample.setExtractLiFlag(lqers.getExtractLiFlag());
                extractRecordSample.setExtractZhenFlag(lqers.getExtractZhenFlag());
                extractRecordSample.setExtractJiaoFlag(lqers.getExtractJiaoFlag());
                extractRecordSample.setExtractYuFlag(lqers.getExtractYuFlag());

                extractRecordSampleList.add(extractRecordSample);

                pcrRecordSample = new LimsPcrRecordSample();
                pcrRecordSample.setId(lqers.getPcrRecordSampleId());
                pcrRecordSample.setLimsPcrRecordId(lqers.getPcrRecordId());
                pcrRecordSample.setSampleId(lqers.getSampleId());
                pcrRecordSample.setSampleNo(lqers.getSampleNo());
                pcrRecordSample.setStandardFlag(lqers.getStandardPcrFlag());
                pcrRecordSample.setPrimerUl(lqers.getPrimerUl());
                pcrRecordSample.setBufferUl(lqers.getBufferUl());
                pcrRecordSample.setTemplateUl(lqers.getTemplateUl());
                pcrRecordSample.setDdwUl(lqers.getDdwUl());
                pcrRecordSample.setSamplePosition(lqers.getSamplePosition());

                pcrRecordSampleList.add(pcrRecordSample);

                syRecordSample = new LimsSyRecordSample();
                syRecordSample.setId(lqers.getSyRecordSampleId());
                syRecordSample.setLimsSyRecordId(lqers.getSyRecordId());
                syRecordSample.setSampleId(lqers.getSampleId());
                syRecordSample.setSampleNo(lqers.getSampleNo());
                syRecordSample.setStandardFlag(lqers.getStandardSyFlag());
                syRecordSample.setBoardNo(lqers.getSyBoardNo());
                syRecordSample.setSamplePosition(lqers.getSamplePosition());

                syRecordSampleList.add(syRecordSample);

                LimsSampleInfo sampleInfo = new LimsSampleInfo();
                sampleInfo.setId(lqers.getSampleId());
                sampleInfo.setSampleType(lqers.getSampleType());
                limsSampleInfoDao.updateSampleType(sampleInfo);
            }

            LimsExtractRecord extractRecordInDB = limsExtractRecordDao.selectById(quickExamineRecord.getLimsExtractRecordId());
            extractRecordInDB.setSampleCount(extractRecordSampleList.size());
            extractRecordInDB.setExtractPersonId1(extractRecord.getExtractPersonId1());
            extractRecordInDB.setExtractPersonName1(extractRecord.getExtractPersonName1());
            extractRecordInDB.setExtractPersonId2(extractRecord.getExtractPersonId2());
            extractRecordInDB.setExtractPersonName2(extractRecord.getExtractPersonName2());
            extractRecordInDB.setExtractDatetime(extractRecord.getExtractDatetime());
            limsExtractRecordDao.update(extractRecordInDB);

            List<LimsExtractRecordSample> extractRecordSampleListInDB = limsExtractRecordDao.selectSampleListByExtractRecordId(quickExamineRecord.getLimsExtractRecordId());
            List<LimsExtractRecordSample> needExtractSampleAddList = new ArrayList<>();
            List<LimsExtractRecordSample> needExtractSampleUpdateList = new ArrayList<>();
            List<LimsExtractRecordSample> needExtractSampleDelList = new ArrayList<>();

            for(LimsExtractRecordSample sampleFromPage : extractRecordSampleList) {
                if(sampleFromPage.getId() == null || sampleFromPage.getId() == 0) {
                    sampleFromPage.setLimsExtractRecordId(quickExamineRecord.getLimsExtractRecordId());
                    sampleFromPage.setCreatePersonId(LimsSecurityUtils.getLoginLabUser().getId());
                    sampleFromPage.setCreatePersonName(LimsSecurityUtils.getLoginName());
                    needExtractSampleAddList.add(sampleFromPage);
                } else {
                    for(LimsExtractRecordSample sampleInDB : extractRecordSampleListInDB) {
                        if(sampleInDB.getId().equals(sampleFromPage.getId())) {
                            sampleInDB.setExtractMethod(sampleFromPage.getExtractMethod());
                            sampleInDB.setExtractPsa(sampleFromPage.getExtractPsa());
                            sampleInDB.setExtractHb(sampleFromPage.getExtractHb());
                            sampleInDB.setExtractLiFlag(sampleFromPage.getExtractLiFlag());
                            sampleInDB.setExtractZhenFlag(sampleFromPage.getExtractZhenFlag());
                            sampleInDB.setExtractJiaoFlag(sampleFromPage.getExtractJiaoFlag());
                            sampleInDB.setExtractYuFlag(sampleFromPage.getExtractYuFlag());
                            sampleInDB.setExtractPosition(sampleFromPage.getExtractPosition());
                            sampleInDB.setChangeMethod(sampleFromPage.getChangeMethod());
                            sampleInDB.setOtherChangeMethod(sampleFromPage.getOtherChangeMethod());
                            needExtractSampleUpdateList.add(sampleInDB);
                            break;
                        }
                    }
                }
            }

            for(LimsExtractRecordSample lersDB : extractRecordSampleListInDB) {
                boolean hasDel = false;
                for(LimsExtractRecordSample tmpSample : needExtractSampleUpdateList) {
                    if(tmpSample.getId().equals(lersDB.getId())) {
                        hasDel = true;
                        break;
                    }
                }

                if(!hasDel)
                    needExtractSampleDelList.add(lersDB);
            }

            result.put("extractRecordId", quickExamineRecord.getLimsExtractRecordId());

            //add
            for (LimsExtractRecordSample sampleAdd : needExtractSampleAddList) {
                limsExtractRecordDao.insertLimsExtractRecordSample(sampleAdd);
            }
            //update
            for (LimsExtractRecordSample sampleUpadte : needExtractSampleUpdateList) {
                limsExtractRecordDao.updateLimsExtractRecordSample(sampleUpadte);
            }
            //del
            for (LimsExtractRecordSample sampleDel : needExtractSampleDelList) {
                limsExtractRecordDao.deleteLimsExtractRecordSample(sampleDel);
            }

            LimsPcrRecord pcrRecordInDB = limsPcrRecordDao.selectById(quickExamineRecord.getLimsPcrRecordId());
            pcrRecordInDB.setBoardNo(pcrRecord.getBoardNo());
            pcrRecordInDB.setPcrProgram(pcrRecord.getPcrProgram());
            pcrRecordInDB.setPcrSystem(pcrRecord.getPcrSystem());
            pcrRecordInDB.setPcrReagent(pcrRecord.getPcrReagent());
            pcrRecordInDB.setPcrInstrument(pcrRecord.getPcrInstrument());
            pcrRecordInDB.setSampleCount(pcrRecordSampleList.size());
            pcrRecordInDB.setPcrPersonId1(pcrRecord.getPcrPersonId1());
            pcrRecordInDB.setPcrPersonName1(pcrRecord.getPcrPersonName1());
            pcrRecordInDB.setPcrPersonId2(pcrRecord.getPcrPersonId2());
            pcrRecordInDB.setPcrPersonName2(pcrRecord.getPcrPersonName2());
            pcrRecordInDB.setPcrStartDatetime(pcrRecord.getPcrStartDatetime());
            pcrRecordInDB.setPcrEndDatetime(pcrRecord.getPcrEndDatetime());
            limsPcrRecordDao.update(pcrRecordInDB);

            List<LimsPcrRecordSample> pcrRecordSampleListInDB = limsPcrRecordDao.selectSampleListByPcrRecordId(quickExamineRecord.getLimsPcrRecordId());
            List<LimsPcrRecordSample> needPcrSampleAddList = new ArrayList<>();
            List<LimsPcrRecordSample> needPcrSampleUpdateList = new ArrayList<>();
            List<LimsPcrRecordSample> needPcrSampleDelList = new ArrayList<>();

            for(LimsPcrRecordSample sampleFromPage : pcrRecordSampleList) {
                if(sampleFromPage.getId() == null || sampleFromPage.getId() == 0) {
                    sampleFromPage.setLimsPcrRecordId(quickExamineRecord.getLimsPcrRecordId());
                    sampleFromPage.setCreatePerson(LimsSecurityUtils.getLoginName());
                    needPcrSampleAddList.add(sampleFromPage);
                } else {
                    for(LimsPcrRecordSample sampleInDB : pcrRecordSampleListInDB) {
                        if(sampleInDB.getId().equals(sampleFromPage.getId())) {
                            sampleInDB.setBufferUl(sampleFromPage.getBufferUl());
                            sampleInDB.setPrimerUl(sampleFromPage.getPrimerUl());
                            sampleInDB.setTemplateUl(sampleFromPage.getTemplateUl());
                            sampleInDB.setDdwUl(sampleFromPage.getDdwUl());
                            sampleInDB.setSamplePosition(sampleFromPage.getSamplePosition());
                            needPcrSampleUpdateList.add(sampleInDB);
                            break;
                        }
                    }
                }
            }

            for(LimsPcrRecordSample sampleInDB : pcrRecordSampleListInDB) {
                boolean hasDel = false;
                for(LimsPcrRecordSample tmpSample : needPcrSampleUpdateList) {
                    if(tmpSample.getId().equals(sampleInDB.getId())) {
                        hasDel = true;
                        break;
                    }
                }

                if(!hasDel)
                    needPcrSampleDelList.add(sampleInDB);
            }

            //add
            for (LimsPcrRecordSample sampleAdd : needPcrSampleAddList) {
                limsPcrRecordDao.insertLimsPcrRecordSample(sampleAdd);
            }
            //update
            for (LimsPcrRecordSample sampleUpadte : needPcrSampleUpdateList) {
                limsPcrRecordDao.updateLimsPcrRecordSample(sampleUpadte);
            }
            //del
            for (LimsPcrRecordSample sampleDel : needPcrSampleDelList) {
                limsPcrRecordDao.deleteLimsPcrRecordSample(sampleDel);
            }

            result.put("pcrRecordId", quickExamineRecord.getLimsPcrRecordId());

            String boardNo = syRecord.getBoardNo();
            String[] boardNoStr = boardNo.split(",");

            String syRecordIds = quickExamineRecord.getLimsSyRecordId();
            String[] syRecordStr = syRecordIds.split(",");
            for (int i = 0; i < boardNoStr.length; i ++) {

                LimsSyRecordSample limsSyRecordSample = null;
                List<LimsSyRecordSample> limsSyRecordSampleList = new ArrayList<>();
                for(Iterator<LimsSyRecordSample> it = syRecordSampleList.iterator(); it.hasNext();){
                    limsSyRecordSample = it.next();

                    if (boardNoStr[i].equals(limsSyRecordSample.getBoardNo()))
                        limsSyRecordSampleList.add(limsSyRecordSample);
                }

                Integer syRecordId = Integer.parseInt(syRecordStr[i]);
                LimsSyRecord syRecordInDB = limsSyRecordDao.selectById(syRecordId);
                syRecordInDB.setSampleCount(limsSyRecordSampleList.size());
                syRecordInDB.setBoardNo(boardNoStr[i]);
                syRecordInDB.setOperatePersonId1(syRecord.getOperatePersonId1());
                syRecordInDB.setOperatePersonName1(syRecord.getOperatePersonName1());
                syRecordInDB.setOperatePersonId2(syRecord.getOperatePersonId2());
                syRecordInDB.setOperatePersonName2(syRecord.getOperatePersonName2());
                syRecordInDB.setStartDatetime(syRecord.getStartDatetime());
                syRecordInDB.setEndDatetime(syRecord.getEndDatetime());
                limsSyRecordDao.update(syRecordInDB);

                limsSyRecordDao.deleteLimsSyRecordSamplesByRecordId(syRecordId);
                for(LimsSyRecordSample samples : limsSyRecordSampleList) {
                    if (syRecordId.equals(samples.getLimsSyRecordId())) {
                        samples.setLimsSyRecordId(Integer.parseInt(syRecordStr[i]));
                        samples.setCreatePerson(LimsSecurityUtils.getLoginName());
                        limsSyRecordDao.insertLimsSyRecordSample(samples);
                    }
                }

            }

            result.put("syRecordIds", syRecordIds);

            LimsQuickExamineRecord limsQuickExamineRecord = new LimsQuickExamineRecord();
            limsQuickExamineRecord.setId(quickExamineRecordModel.getQuickExamineRecordId());
            if (extractRecord.getCaseId() != null) {
                limsQuickExamineRecord.setCaseId(extractRecord.getCaseId());
            }else if (pcrRecord.getCaseId() != null) {
                limsQuickExamineRecord.setCaseId(pcrRecord.getCaseId());
            }else {
                limsQuickExamineRecord.setCaseId(syRecord.getCaseId());
            }
            limsQuickExamineRecord.setLimsExtractRecordId(quickExamineRecord.getLimsExtractRecordId());
            limsQuickExamineRecord.setLimsPcrRecordId(quickExamineRecord.getLimsPcrRecordId());
            limsQuickExamineRecord.setLimsSyRecordId(syRecordIds);
            limsQuickExamineRecord.setUpdatePerson(LimsSecurityUtils.getLoginName());

            limsQuickExamineRecordDao.update(limsQuickExamineRecord);

            result.put("success", true);
        }catch (Exception e) {
            logger.error("保存失败！", e);
            result.put("success", false);
        }

        return result;
    }

}
