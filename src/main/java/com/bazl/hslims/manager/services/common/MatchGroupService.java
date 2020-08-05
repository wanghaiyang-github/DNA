package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.MatchGroup;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/1/6
 */
public interface MatchGroupService {

    public List<MatchGroup> selectList(MatchGroup matchGroup, PageInfo pageInfo);
    public int selectListCount(MatchGroup matchGroup);

    public int insert(MatchGroup matchGroup);

    public int deleteById(Integer matchGroupId);

}
