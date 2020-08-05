package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.IdentifyKernel;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
public interface IdentifyKernelDao extends BaseDao<IdentifyKernel, Integer> {

    public List<IdentifyKernel> selectAll ();

    public IdentifyKernel selectListByName (String identifyKernelName);

}
