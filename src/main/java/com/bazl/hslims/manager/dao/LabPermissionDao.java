package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LabPermission;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */
public interface LabPermissionDao extends BaseDao<LabPermission, Integer> {


    public List<LabPermission> selectAll();

    public List<LabPermission> selectListByRoleId(Integer roleId);

}
