package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.ReagentSuppliesInfoDao;
import com.bazl.hslims.manager.model.po.ReagentSuppliesInfo;
import com.bazl.hslims.manager.services.common.ReagentSuppliesInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Service
public class ReagentSuppliesInfoServiceImpl implements ReagentSuppliesInfoService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ReagentSuppliesInfoDao reagentSuppliesInfoDao;

    public ReagentSuppliesInfo selectById(Integer id){
        try {
            return reagentSuppliesInfoDao.selectById(id);
        } catch(Exception ex) {
            logger.error("reagentSuppliesInfoService.selectById error!", ex);
            return null;
        }
    }

    public int selectCount(ReagentSuppliesInfo reagentSuppliesInfo) {
        try {
            return reagentSuppliesInfoDao.selectCount(reagentSuppliesInfo);
        }catch(Exception ex){
            logger.error("reagentSuppliesInfoService.selectCount error!", ex);
            return 0;
        }
    }

    public List<ReagentSuppliesInfo> selectPaginationList(ReagentSuppliesInfo reagentSuppliesInfo,PageInfo pageInfo){
        List<ReagentSuppliesInfo> reagentSuppliesInfoList = null;
        try {
            int pageNo = pageInfo.getPage();
            int pageSize = pageInfo.getEvePageRecordCnt();
            reagentSuppliesInfo.setOffset((pageNo - 1) * pageSize);
            reagentSuppliesInfo.setRows(reagentSuppliesInfo.getOffset() + pageSize);

            reagentSuppliesInfoList = reagentSuppliesInfoDao.selectPaginationList(reagentSuppliesInfo);
        } catch(Exception ex) {
            logger.error("reagentSuppliesInfoService.selectPaginationList error!", ex);
            return null;
        }

        return reagentSuppliesInfoList;
    }

    public List<ReagentSuppliesInfo> selectList(ReagentSuppliesInfo reagentSuppliesInfo) {
        try {
            return reagentSuppliesInfoDao.selectList(reagentSuppliesInfo);
        } catch(Exception ex) {
            logger.error("reagentSuppliesInfoService.selectList error!", ex);
            return null;
        }
    }

    @Override
    public int add(ReagentSuppliesInfo reagentSuppliesInfo) {
        try {
            reagentSuppliesInfoDao.insert(reagentSuppliesInfo);
            return 1;
        }catch(Exception ex){
            logger.error("新增耗材错误！", ex);
            throw ex;
        }
    }

    @Override
    public int update(ReagentSuppliesInfo reagentSuppliesInfo) {
        try {
            reagentSuppliesInfoDao.update(reagentSuppliesInfo);
            return 1;
        }catch(Exception ex){
            logger.error("更新耗材错误！", ex);
            throw ex;
        }
    }

    public int deleteById(Integer id) {
        try {
            return reagentSuppliesInfoDao.deleteById(id);
        }catch(Exception ex){
            logger.error("删除耗材错误！", ex);
            return 0;
        }
    }
}
