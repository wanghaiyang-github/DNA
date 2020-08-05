package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsConsignmentInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */
public interface LimsConsignmentInfoDao extends BaseDao<LimsConsignmentInfo, Integer> {

    public int deleteById(LimsConsignmentInfo limsConsignmentInfo);

    public List<LimsConsignmentInfo> selectListByDelegateOrgId(Integer delegateOrgId);

    public List<LimsConsignmentInfo> selectLimsConsignmentInfoList(LimsConsignmentInfo limsConsignmentInfo);

}
