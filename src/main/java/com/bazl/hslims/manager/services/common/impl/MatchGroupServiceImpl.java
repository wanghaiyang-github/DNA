package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.MatchGroupDao;
import com.bazl.hslims.manager.model.po.MatchGroup;
import com.bazl.hslims.manager.services.common.MatchGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/1/6
 */
@Service
public class MatchGroupServiceImpl implements MatchGroupService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MatchGroupDao matchGroupDao;

    @Override
    public List<MatchGroup> selectList(MatchGroup matchGroup, PageInfo pageInfo) {

        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        matchGroup.setOffset((pageNo - 1) * pageSize);
        matchGroup.setRows(pageSize);

        return matchGroupDao.selectList(matchGroup);
    }

    @Override
    public int selectListCount(MatchGroup matchGroup) {
        return matchGroupDao.selectListCount(matchGroup);
    }

    @Override
    public int insert(MatchGroup matchGroup) {
        return matchGroupDao.insert(matchGroup);
    }

    @Override
    public int deleteById(Integer matchGroupId) {
        return matchGroupDao.deleteById(matchGroupId);
    }

}
