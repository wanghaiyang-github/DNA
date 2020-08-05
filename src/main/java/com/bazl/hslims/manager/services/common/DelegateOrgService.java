package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.DelegateOrg;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface DelegateOrgService {

    public List<DelegateOrg> selectAll();

    public int update(DelegateOrg delegateOrg);

    public int insert(DelegateOrg delegateOrg);

    public DelegateOrg selectByOrgCode(String orgCode);

    public DelegateOrg selectById(Integer id);

    public int delete(Integer id);

    public DelegateOrg selectByOrgName(String orgName);

    public DelegateOrg selectByDelegateOrgCode(String orgCode);

}
