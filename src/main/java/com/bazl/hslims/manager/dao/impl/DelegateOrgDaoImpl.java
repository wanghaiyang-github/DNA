package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.DelegateOrgDao;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
@Repository
public class DelegateOrgDaoImpl extends BaseDaoImpl<DelegateOrg, Integer> implements DelegateOrgDao {


    @Override
    public String namespace() {
        return DelegateOrg.class.getName();
    }

    public List<DelegateOrg> selectAll() {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAll");
    }

    public DelegateOrg selectByOrgCode(String orgCode) {
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectByOrgCode", orgCode);
    }

    public DelegateOrg selectByOrgName(String orgName) {
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectByOrgName", orgName);
    }

    public DelegateOrg selectByDelegateOrgCode(String orgCode) {
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectByDelegateOrgCode", orgCode);

    }
}
