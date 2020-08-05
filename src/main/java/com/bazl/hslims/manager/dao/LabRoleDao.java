package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LabRole;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */
public interface LabRoleDao extends BaseDao<LabRole, Integer> {

    public List<LabRole> selectAll();

    public List<LabRole> selectByLabUserId(Integer labUserId);

}
