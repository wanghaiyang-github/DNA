package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LabUserRoleDao;
import com.bazl.hslims.manager.model.po.LabUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
@Repository
public class LabUserRoleDaoImpl extends BaseDaoImpl<LabUserRole,Integer> implements LabUserRoleDao {

    @Override
    public String namespace() {
        return LabUserRole.class.getName();
    }

    public List<LabUserRole> selectListByUserId(Integer userId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByUserId", userId);
    }

    public int deleteByUserId(Integer userId) {
        return this.getSqlSessionTemplate().update(namespace() + ".deleteByUserId", userId);
    }

}
