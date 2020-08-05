package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.MatchGroup;

/**
 *
 * @author Administrator
 * @date 2017/1/6
 */
public interface MatchGroupDao extends BaseDao<MatchGroup, Integer> {

    public int selectListCount(MatchGroup matchGroup);
}
