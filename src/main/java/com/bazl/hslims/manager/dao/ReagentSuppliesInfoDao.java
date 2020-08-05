package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.ReagentSuppliesInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
public interface ReagentSuppliesInfoDao extends BaseDao<ReagentSuppliesInfo, Integer>{
    public int selectCount(ReagentSuppliesInfo reagentSuppliesInfo);

    public List<ReagentSuppliesInfo> selectPaginationList(ReagentSuppliesInfo reagentSuppliesInfo);
}
