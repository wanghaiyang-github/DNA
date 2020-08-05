package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.LimsConsignmentInfoDao;
import com.bazl.hslims.manager.model.po.LimsConsignmentInfo;
import com.bazl.hslims.manager.services.common.LimsConsignmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */
@Service
public class LimsConsignmentInfoServiceImpl implements LimsConsignmentInfoService {

    @Autowired
    LimsConsignmentInfoDao limsConsignmentInfoDao;

    public int insert(LimsConsignmentInfo limsConsignmentInfo) {
        return limsConsignmentInfoDao.insert(limsConsignmentInfo);
    }

    public int update(LimsConsignmentInfo limsConsignmentInfo) {
        return limsConsignmentInfoDao.update(limsConsignmentInfo);
    }

    public int deleteById(LimsConsignmentInfo limsConsignmentInfo) {
        return limsConsignmentInfoDao.deleteById(limsConsignmentInfo);
    }

    public int delete(Integer id) {
        return limsConsignmentInfoDao.deleteById(id);
    }

    public List<LimsConsignmentInfo> selectListByDelegateOrgId(Integer delegateOrgId) {
        return limsConsignmentInfoDao.selectListByDelegateOrgId(delegateOrgId);
    }

    public List<LimsConsignmentInfo> selectLimsConsignmentInfoList(LimsConsignmentInfo limsConsignmentInfo) {
        return limsConsignmentInfoDao.selectLimsConsignmentInfoList(limsConsignmentInfo);
    }

}
