package com.bazl.hslims.web.controller.center.caseinfo;


import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 */
public class CaseInfoPagination implements Serializable {

    private LimsCaseInfoVO query;
    private PageInfo pageInfo;

    public LimsCaseInfoVO getQuery() {
        return query;
    }

    public void setQuery(LimsCaseInfoVO query) {
        this.query = query;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
