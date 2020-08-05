package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LabPermissionDao;
import com.bazl.hslims.manager.model.po.LabPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */
@Repository
public class LabPermissionDaoImpl extends BaseDaoImpl<LabPermission,Integer> implements LabPermissionDao {


    @Override
    public String namespace() {
        return LabPermission.class.getName();
    }

    public List<LabPermission> selectAll() {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAll");
    }

    public List<LabPermission> selectListByRoleId(Integer roleId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByRoleId", roleId);
    }

}
