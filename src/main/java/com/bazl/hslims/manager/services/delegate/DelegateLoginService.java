package com.bazl.hslims.manager.services.delegate;

import com.bazl.hslims.manager.model.po.DelegateOrg;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */
public interface DelegateLoginService {

    List<DelegateOrg> selectAllDelegateOrgList();

    DelegateOrg selectByOrgCode(String orgCode);

}
