package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.IdentifyKernel;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
public interface IdentifyKernelService {

    public List<IdentifyKernel> selectAll();

    public int insert (IdentifyKernel identifyKernel);

    public int update (IdentifyKernel identifyKernel);

    public int delete (Integer id);

    public IdentifyKernel selectListByName(String identifyKernelName);

}
