package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.MatchResultDao;
import com.bazl.hslims.manager.model.po.MatchResult;
import com.bazl.hslims.manager.services.common.MatchResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2017/1/6
 */
@Service
public class MatchResultServiceImpl implements MatchResultService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MatchResultDao matchResultDao;

    @Override
    public List<MatchResult> selectListByGroupId(MatchResult matchResult) {
        return matchResultDao.selectListByGroupId(matchResult);
    }

    @Override
    public List<MatchResult> selectList() {
        return matchResultDao.selectList();
    }

    @Override
    public MatchResult selectByMatchId(Integer matchId) {
        return matchResultDao.selectByMatchId(matchId);
    }

    @Override
    public List<MatchResult> selectByGroupId(MatchResult matchResult) {
        return matchResultDao.selectByGroupId(matchResult);
    }

    @Override
    public List<MatchResult> selectListBySampleId(Integer sampleId) {
        return matchResultDao.selectListBySampleId(sampleId);
    }

    @Override
    public int insert(MatchResult matchResult) {
        return matchResultDao.insert(matchResult);
    }

    @Override
    public List<MatchResult> selectListBySampleIdAndMatchMode(Integer sampleId, String matchMode) {
        Map<String, Object> param = new HashMap<>();
        param.put("sampleId", sampleId);
        param.put("matchMode", matchMode);
        return matchResultDao.selectListBySampleIdAndMatchMode(param);
    }

    @Override
    public int deleteById(Integer matchResultId) {
        return matchResultDao.deleteById(matchResultId);
    }


}
