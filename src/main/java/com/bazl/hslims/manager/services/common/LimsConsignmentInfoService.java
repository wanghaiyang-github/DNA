package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsConsignmentInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */
public interface LimsConsignmentInfoService {

    public int insert(LimsConsignmentInfo limsConsignmentInfo);

    public int update(LimsConsignmentInfo limsConsignmentInfo);

    public int delete(Integer id);

    public int deleteById(LimsConsignmentInfo limsConsignmentInfo);

    public List<LimsConsignmentInfo> selectListByDelegateOrgId(Integer delegateOrgId);

    public List<LimsConsignmentInfo> selectLimsConsignmentInfoList(LimsConsignmentInfo limsConsignmentInfo);

}
