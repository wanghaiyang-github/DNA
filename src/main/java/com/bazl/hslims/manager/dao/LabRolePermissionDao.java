package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LabRolePermission;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public interface LabRolePermissionDao extends BaseDao<LabRolePermission, Integer> {

    public List<LabRolePermission> selectListByUserId(Integer userId);

    public int deleteByRoleId(Integer roleId);
}
