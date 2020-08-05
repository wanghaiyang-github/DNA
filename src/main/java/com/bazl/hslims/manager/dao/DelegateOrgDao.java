package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.DelegateOrg;

import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public interface DelegateOrgDao extends BaseDao<DelegateOrg, Integer> {

    public List<DelegateOrg> selectAll();

    public DelegateOrg selectByOrgCode(String orgCode);

    public DelegateOrg selectByOrgName(String orgName);

    public DelegateOrg selectByDelegateOrgCode(String orgCode);

}
