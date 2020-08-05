package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.LimsPersonInfoDao;
import com.bazl.hslims.manager.dao.LimsSampleInfoDao;
import com.bazl.hslims.manager.model.po.LimsPersonInfo;
import com.bazl.hslims.manager.services.common.LimsPersonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Service
public class LimsPersonInfoServiceImpl implements LimsPersonInfoService {


    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    LimsPersonInfoDao limsPersonInfoDao;
    @Autowired
    LimsSampleInfoDao limsSampleInfoDao;

    @Override
    public List<LimsPersonInfo> selectListByCaseId(Integer caseId) {
        return limsPersonInfoDao.selectListByCaseId(caseId);
    }

    @Override
    public List<LimsPersonInfo> selectListByConsignmentId(Integer consignmentId) {
        return limsPersonInfoDao.selectListByConsignmentId(consignmentId);
    }

    @Override
    @Transactional
    public boolean deleteById(Integer personId) {
        try {
            limsPersonInfoDao.deleteById(personId);

            limsSampleInfoDao.deleteByPersonId(personId);

            return true;
        } catch (Exception ex) {
            logger.error("删除人员信息错误！", ex);

            return false;
        }
    }

    public LimsPersonInfo selectById(Integer personId) {
        return limsPersonInfoDao.selectById(personId);
    }

    public int deleteByCaseId(Integer caseId) {
        return limsPersonInfoDao.deleteByCaseId(caseId);
    }

    public int deleteByConsignmentId(Integer consignmentId) {
        return limsPersonInfoDao.deleteByConsignmentId(consignmentId);
    }

}
