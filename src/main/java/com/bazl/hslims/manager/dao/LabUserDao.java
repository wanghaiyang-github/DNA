package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LabUser;

/**
 * Created by Administrator on 2016/12/30.
 */
public interface LabUserDao extends BaseDao<LabUser, Integer> {

    LabUser selectByLoginName(String loginName);

}
