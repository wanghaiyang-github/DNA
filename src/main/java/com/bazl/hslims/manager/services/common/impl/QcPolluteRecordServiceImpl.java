package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.QcPolluteRecordDao;
import com.bazl.hslims.manager.model.po.QcPolluteRecord;
import com.bazl.hslims.manager.model.vo.QcPolluteRecordVO;
import com.bazl.hslims.manager.services.common.QcPolluteRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
@Service
public class QcPolluteRecordServiceImpl implements QcPolluteRecordService {

    @Autowired
    QcPolluteRecordDao qcPolluteRecordDao;

    public int selectCount(QcPolluteRecordVO query){
        return qcPolluteRecordDao.selectCount(query);
    }

    public List<QcPolluteRecord> selectPaginationList(QcPolluteRecordVO query, PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return qcPolluteRecordDao.selectPaginationList(query);
    }

    public int insert(QcPolluteRecord qcPolluteRecord){
        int result = 0;
        result = qcPolluteRecordDao.insert(qcPolluteRecord);
        return result;
    }

}
