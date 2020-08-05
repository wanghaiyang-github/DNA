package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsPersonInfoDao;
import com.bazl.hslims.manager.model.po.LimsPersonInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Repository
public class LimsPersonInfoDaoImpl extends BaseDaoImpl<LimsPersonInfo, Integer>  implements LimsPersonInfoDao {


    @Override
    public String namespace() {
        return LimsPersonInfo.class.getName();
    }

    @Override
    public List<LimsPersonInfo> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByCaseId", caseId);
    }

    @Override
    public List<LimsPersonInfo> selectListByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByConsignmentId", consignmentId);
    }

    public int deleteByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteByCaseId", caseId);
    }

    @Override
    public int deleteByConsignmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".deleteByConsignmentId", consignmentId);
    }
}
