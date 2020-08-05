package com.bazl.hslims.manager.services.delegate.impl;

import com.bazl.hslims.manager.dao.DelegateOrgDao;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.services.delegate.DelegateLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */
@Service
public class DelegateLoginServiceImpl implements DelegateLoginService {

    @Autowired
    DelegateOrgDao delegateOrgDao;

    public List<DelegateOrg> selectAllDelegateOrgList() {
        return delegateOrgDao.selectAll();
    }


    public DelegateOrg selectByOrgCode(String orgCode) {
        return delegateOrgDao.selectByOrgCode(orgCode);
    }
}
