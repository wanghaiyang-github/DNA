package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.MatchResultDao;
import com.bazl.hslims.manager.model.po.MatchResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date 2017/1/6
 */
@Repository
public class MatchResultDaoImpl extends BaseDaoImpl<MatchResult, Integer> implements MatchResultDao {

    @Override
    public String namespace() {
        return MatchResult.class.getName();
    }


    @Override
    public List<MatchResult> selectListByGroupId(MatchResult matchResult) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByGroupId",matchResult);
    }

    @Override
    public List<MatchResult> selectList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectList");
    }

    @Override
    public List<MatchResult> selectByGroupId(MatchResult matchResult) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectByGroupId",matchResult);
    }

    @Override
    public MatchResult selectByMatchId(Integer matchId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectByMatchId",matchId);
    }

    @Override
    public List<MatchResult> selectListBySampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListBySampleId",sampleId);
    }

    @Override
    public List<MatchResult> selectListBySampleIdAndMatchMode(Map param) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListBySampleIdAndMatchMode", param);
    }


}
