package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.MatchGroupDao;
import com.bazl.hslims.manager.model.po.MatchGroup;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Administrator
 * @date 2017/1/6
 */
@Repository
public class MatchGroupDaoImpl extends BaseDaoImpl<MatchGroup, Integer> implements MatchGroupDao {

    @Override
    public String namespace() {
        return MatchGroup.class.getName();
    }

    @Override
    public int selectListCount(MatchGroup matchGroup) {
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectListCount", matchGroup);
    }

}
