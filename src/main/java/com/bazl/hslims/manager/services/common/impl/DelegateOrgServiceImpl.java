package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.DelegateOrgDao;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.services.common.DelegateOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Service
public class DelegateOrgServiceImpl implements DelegateOrgService {

    @Autowired
    DelegateOrgDao delegateOrgDao;

    public List<DelegateOrg> selectAll() {
        return delegateOrgDao.selectAll();
    }

    public int update(DelegateOrg delegateOrg) {
        return delegateOrgDao.update(delegateOrg);
    }

    public int insert(DelegateOrg delegateOrg) {
        return delegateOrgDao.insert(delegateOrg);
    }

    public DelegateOrg selectByOrgCode(String orgCode) {
        return delegateOrgDao.selectByOrgCode(orgCode);
    }

    public DelegateOrg selectById(Integer id) {
        return delegateOrgDao.selectById(id);
    }

    public int delete(Integer id){
        return delegateOrgDao.deleteById(id);
    }

    public DelegateOrg selectByOrgName(String orgName) {
        return delegateOrgDao.selectByOrgName(orgName);
    }

    public DelegateOrg selectByDelegateOrgCode(String orgCode) {
        return delegateOrgDao.selectByDelegateOrgCode(orgCode);
    }

}
