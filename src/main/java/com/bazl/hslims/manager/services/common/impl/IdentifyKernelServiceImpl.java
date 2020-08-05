package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.IdentifyKernelDao;
import com.bazl.hslims.manager.model.po.IdentifyKernel;
import com.bazl.hslims.manager.services.common.IdentifyKernelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
@Service
public class IdentifyKernelServiceImpl implements IdentifyKernelService {

    @Autowired
    IdentifyKernelDao identifyKernelDao;

    public List<IdentifyKernel> selectAll () {
        return identifyKernelDao.selectAll();
    }

    public int insert (IdentifyKernel identifyKernel) {
        return identifyKernelDao.insert(identifyKernel);
    }

    public int update (IdentifyKernel identifyKernel) {
        return identifyKernelDao.update(identifyKernel);
    }

    public int delete (Integer id) {
        return identifyKernelDao.deleteById(id);
    }

    public IdentifyKernel selectListByName (String identifuKernelName) {
        return identifyKernelDao.selectListByName(identifuKernelName);
    }
}
