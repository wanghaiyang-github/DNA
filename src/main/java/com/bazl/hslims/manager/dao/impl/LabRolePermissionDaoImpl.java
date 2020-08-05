package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LabRolePermissionDao;
import com.bazl.hslims.manager.model.po.LabRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
@Repository
public class LabRolePermissionDaoImpl extends BaseDaoImpl<LabRolePermission, Integer> implements LabRolePermissionDao {

    @Override
    public String namespace() {
        return LabRolePermission.class.getName();
    }

    public List<LabRolePermission> selectListByUserId(Integer userId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByUserId", userId);
    }

    public int deleteByRoleId(Integer roleId) {
        return this.getSqlSessionTemplate().delete(namespace() + ".deleteByRoleId", roleId);
    }

}
