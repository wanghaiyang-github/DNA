package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.LimsQuickExamineRecordDao;
import com.bazl.hslims.manager.model.po.LimsQuickExamineRecord;
import com.bazl.hslims.manager.services.common.LimsQuickExamineRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
public class LimsQuickExamineRecordServiceImpl implements LimsQuickExamineRecordService {

    @Autowired
    LimsQuickExamineRecordDao limsQuickExamineRecordDao;

    public LimsQuickExamineRecord selectById(Integer id) {
        return limsQuickExamineRecordDao.selectById(id);
    }

    public int deleteById(Integer id) {
        return limsQuickExamineRecordDao.deleteById(id);
    }

    public  int selectCountByCaseId(Integer caseId) {
        return limsQuickExamineRecordDao.selectCountByCaseId(caseId);
    }

    public List<LimsQuickExamineRecord> selectListByCaseId(Integer caseId) {
        return limsQuickExamineRecordDao.selectListByCaseId(caseId);
    }
}
