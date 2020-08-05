package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.DictInfoDao;
import com.bazl.hslims.manager.dao.DictItemDao;
import com.bazl.hslims.manager.model.po.DictInfo;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.services.common.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Service
public class DictServiceImpl implements DictService {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DictInfoDao dictInfoDao;

    @Autowired
    DictItemDao dictItemDao;

    /**
     * 获取指定类型的字典列表
     * @return
     */

    public List<DictInfo> selecList(DictInfo dictInfo){
        try {
            return dictInfoDao.selectList(dictInfo);
        } catch(Exception ex) {
            logger.error("dictService.selectList error!", ex);
            return null;
        }
    }

    public int selectRepeatDictItemQuery(DictItem dictItem){
        return dictItemDao.selectRepeatDictItemQuery(dictItem);
    }

    public DictInfo selectDictInfoById(String dictInfoId){
        return dictInfoDao.selectById(dictInfoId);
    }

    public List<DictItem> selectDictItemListByType(String dictType) {
        return dictItemDao.selectListByDictType(dictType);
    }

    public List<DictItem> selectByParentDictTypeCode(String dictTypeCode){
        return dictItemDao.selectByParentDictTypeCode(dictTypeCode);
    }

    public List<DictInfo> selectByDictInfo(DictInfo dictInfo){
        return dictInfoDao.selectList(dictInfo);
    }

    public int addDictInfo(DictInfo dictInfo){
        try {
            dictInfoDao.insert(dictInfo);
            return 1;
        }catch(Exception ex){
            logger.error("新增字典错误！", ex);
            throw ex;
        }
    }

    public int updateDictInfo(DictInfo dictInfo){
        try {
            dictInfoDao.update(dictInfo);
            return 1;
        }catch(Exception ex){
            logger.error("修改字典错误！", ex);
            throw ex;
        }
    }

    public int addDictItem(DictItem dictItem){
        try {
            dictItemDao.insert(dictItem);
            return 1;
        }catch(Exception ex){
            logger.error("新增字典错误！", ex);
            throw ex;
        }
    }

    public int updateDictItem(DictItem dictItem){
        try {
            dictItemDao.update(dictItem);
            return 1;
        }catch(Exception ex){
            logger.error("修改字典错误！", ex);
            throw ex;
        }
    }

    public int deleteByDictItemId(String dictItemId) {
        try {
            return dictItemDao.deleteById(dictItemId);
        }catch(Exception ex){
            logger.error("dictItemDao.deleteById错误！",ex);
            return 0;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByDictTypeCode(String dictTypeCode){
        try {
            int dictInfoNum=dictInfoDao.deleteByDictTypeCode(dictTypeCode);
            int dictItemNum=dictItemDao.deleteByDictTypeCode(dictTypeCode);

            int resultNum=dictInfoNum+dictItemNum;
            return resultNum;
        }catch(Exception ex){
            logger.error("dictInfoDao.deleteByDictTypeCode错误！",ex);
            return 0;
        }
    }
}
