package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LabUser;

import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public interface LabUserService {

    public LabUser selectById(Integer labUserId);

    public int deleteById(Integer labUserId);

    public int add(LabUser labUser);

    public int update(LabUser labUser);
    public int updatePassword(LabUser labUser);

    public List<LabUser> selectAll();

}
