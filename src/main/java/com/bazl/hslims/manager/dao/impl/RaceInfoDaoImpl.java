package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.RaceInfoDao;
import com.bazl.hslims.manager.model.po.RaceInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
@Repository
public class RaceInfoDaoImpl extends BaseDaoImpl<RaceInfo, Integer> implements RaceInfoDao {

    @Override
    public String namespace() { return RaceInfo.class.getName(); }

    public List<RaceInfo> selectRaceInfoList(RaceInfo raceInfo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectRaceInfoList", raceInfo);
    }

    public List<RaceInfo> selectRaceInfoRepeatQueryList(RaceInfo raceInfo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectRaceInfoRepeatQueryList", raceInfo);
    }

    public List<RaceInfo> selectAllRaceInfoList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAllRaceInfoList");
    }

}
