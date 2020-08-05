package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.ReagentSuppliesInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
public interface ReagentSuppliesInfoService {
    public int add(ReagentSuppliesInfo reagentSuppliesInfo);

    public int update(ReagentSuppliesInfo reagentSuppliesInfo);

    public ReagentSuppliesInfo selectById(Integer id);

    public int selectCount(ReagentSuppliesInfo reagentSuppliesInfo);

    public List<ReagentSuppliesInfo> selectPaginationList(ReagentSuppliesInfo reagentSuppliesInfo, PageInfo pageInfo);

    public List<ReagentSuppliesInfo> selectList(ReagentSuppliesInfo reagentSuppliesInfo);

    public int deleteById(Integer id) ;
}
