package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.QcPolluteRecord;
import com.bazl.hslims.manager.model.vo.QcPolluteRecordVO;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public interface QcPolluteRecordService {


    public int selectCount(QcPolluteRecordVO query);

    public List<QcPolluteRecord> selectPaginationList(QcPolluteRecordVO query, PageInfo pageInfo);

    public int insert(QcPolluteRecord qcPolluteRecord);

}
