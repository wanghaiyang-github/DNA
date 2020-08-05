package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.LimsEntrustmentInformationDao;
import com.bazl.hslims.manager.model.po.LimsEntrustmentInformation;
import com.bazl.hslims.manager.services.common.LimsEntrustmentInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangliu on 2018/5/13.
 */
@Service
public class LimsEntrustmentInformationServiceImpl implements LimsEntrustmentInformationService {

    @Autowired
    LimsEntrustmentInformationDao limsEntrustmentInformationDao;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public int insert(LimsEntrustmentInformation limsEntrustmentInformation) {
        return limsEntrustmentInformationDao.insert(limsEntrustmentInformation);
    }

    @Override
    public boolean deleteById(Integer caseInformationId) {
        try {
            limsEntrustmentInformationDao.deleteById(caseInformationId);
        } catch (Exception ex) {
            logger.error("删除案件错误！", ex);
            return false;
        }
        return true;
    }

    @Override
    public LimsEntrustmentInformation selectById(Integer id) {
        return limsEntrustmentInformationDao.selectById(id);
    }

    @Override
    public LimsEntrustmentInformation selectByCaseInformationId(Integer caseInformationId) {
        return limsEntrustmentInformationDao.selectByCaseInformationId(caseInformationId);
    }
}
