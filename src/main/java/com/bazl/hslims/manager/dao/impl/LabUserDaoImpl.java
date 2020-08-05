package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LabUserDao;
import com.bazl.hslims.manager.model.po.LabUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */
@Repository
public class LabUserDaoImpl extends BaseDaoImpl<LabUser, Integer> implements LabUserDao {

    @Override
    public String namespace() {
        return LabUser.class.getName();
    }

    public LabUser selectByLoginName(String loginName) {
        List<LabUser> list = this.getSqlSessionTemplate().selectList(namespace() + ".selectByLoginName", loginName);
        return (list== null || list.size() == 0) ? null : list.get(0);
    }
}
