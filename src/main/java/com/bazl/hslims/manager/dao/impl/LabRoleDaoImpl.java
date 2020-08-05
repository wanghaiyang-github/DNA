package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LabRoleDao;
import com.bazl.hslims.manager.model.po.LabRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/18.
 */
@Repository
public class LabRoleDaoImpl extends BaseDaoImpl<LabRole,Integer> implements LabRoleDao {


    @Override
    public String namespace() {
        return LabRole.class.getName();
    }

    public List<LabRole> selectAll(){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAll");
    }

    public List<LabRole> selectByLabUserId(Integer labUserId){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectByLabUserId", labUserId);
    }

}
