package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.ReagentSuppliesInfoDao;
import com.bazl.hslims.manager.model.po.ReagentSuppliesInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Repository
public class ReagentSuppliesInfoDaoImpl extends BaseDaoImpl<ReagentSuppliesInfo, Integer> implements ReagentSuppliesInfoDao {
    @Override
    public String namespace() {
        return ReagentSuppliesInfo.class.getName();
    }

    public int selectCount(ReagentSuppliesInfo reagentSuppliesInfo){
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectCount",reagentSuppliesInfo);
    }

    public List<ReagentSuppliesInfo> selectPaginationList(ReagentSuppliesInfo reagentSuppliesInfo){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectPaginationList", reagentSuppliesInfo);
    }
}
