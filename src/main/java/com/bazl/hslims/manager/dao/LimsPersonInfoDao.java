package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsPersonInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface LimsPersonInfoDao extends BaseDao<LimsPersonInfo, Integer> {

    public List<LimsPersonInfo> selectListByCaseId(Integer caseId);

    public List<LimsPersonInfo> selectListByConsignmentId(Integer consignmentId);

    public int deleteByCaseId(Integer caseId);

    public int deleteByConsignmentId(Integer consignmentId);

}
