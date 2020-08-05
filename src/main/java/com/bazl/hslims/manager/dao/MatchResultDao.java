package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.MatchResult;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 * @date 2017/1/6
 */
public interface MatchResultDao extends BaseDao<MatchResult, Integer> {

    public List<MatchResult> selectListByGroupId(MatchResult matchResult);

    public List<MatchResult> selectList();

    public List<MatchResult> selectByGroupId(MatchResult matchResult);

    public MatchResult selectByMatchId(Integer matchId);

    public List<MatchResult> selectListBySampleId(Integer sampleId);

    public List<MatchResult> selectListBySampleIdAndMatchMode(Map param);

}
