package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.MatchResult;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/1/6
 */
public interface MatchResultService {

    public List<MatchResult> selectListByGroupId(MatchResult matchResult);

    public List<MatchResult> selectList();

    public MatchResult selectByMatchId(Integer matchId);

    public List<MatchResult> selectByGroupId(MatchResult matchResult);

    public List<MatchResult> selectListBySampleId(Integer sampleId);

    public int insert(MatchResult matchResult);

    public List<MatchResult> selectListBySampleIdAndMatchMode(Integer sampleId, String matchMode);

    public int deleteById(Integer matchResultId);

}
