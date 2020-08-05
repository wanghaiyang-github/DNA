package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsConsignmentInfoDao;
import com.bazl.hslims.manager.model.po.LimsConsignmentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */
@Repository
public class LimsConsignmentInfoDaoImpl extends BaseDaoImpl<LimsConsignmentInfo, Integer> implements LimsConsignmentInfoDao {

    public String namespace() { return LimsConsignmentInfo.class.getName(); }

    public int deleteById(LimsConsignmentInfo limsConsignmentInfo) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteById", limsConsignmentInfo);
    }

    public List<LimsConsignmentInfo> selectListByDelegateOrgId(Integer delegateOrgId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByDelegateOrgId", delegateOrgId);
    }

    public List<LimsConsignmentInfo> selectLimsConsignmentInfoList(LimsConsignmentInfo limsConsignmentInfo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectLimsConsignmentInfoList", limsConsignmentInfo);
    }

}
