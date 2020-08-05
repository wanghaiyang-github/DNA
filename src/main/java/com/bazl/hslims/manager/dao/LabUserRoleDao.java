package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LabUserRole;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public interface LabUserRoleDao extends BaseDao<LabUserRole, Integer> {

    public List<LabUserRole> selectListByUserId(Integer userId);

    public int deleteByUserId(Integer userId);
}
