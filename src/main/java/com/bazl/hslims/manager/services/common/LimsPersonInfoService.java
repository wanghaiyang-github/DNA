package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsPersonInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface LimsPersonInfoService {

    public List<LimsPersonInfo> selectListByCaseId(Integer caseId);

    public List<LimsPersonInfo> selectListByConsignmentId(Integer consignmentId);

    public boolean deleteById(Integer personId);

    public LimsPersonInfo selectById(Integer personId);

    public int deleteByCaseId(Integer caseId);

    public int deleteByConsignmentId(Integer consignmentId);

}
