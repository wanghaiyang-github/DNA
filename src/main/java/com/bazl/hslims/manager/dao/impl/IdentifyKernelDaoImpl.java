package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.IdentifyKernelDao;
import com.bazl.hslims.manager.model.po.IdentifyKernel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/5.
 */
@Repository
public class IdentifyKernelDaoImpl extends BaseDaoImpl<IdentifyKernel, Integer> implements IdentifyKernelDao {

    public String namespace() {
        return IdentifyKernel.class.getName();
    }

    public List<IdentifyKernel> selectAll () {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAll");
    }

    @Override
    public IdentifyKernel selectListByName(String identifyKernelName) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectListByName", identifyKernelName);
    }
}
