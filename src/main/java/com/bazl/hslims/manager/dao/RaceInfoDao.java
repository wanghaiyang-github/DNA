package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.RaceInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface RaceInfoDao extends BaseDao<RaceInfo, Integer> {

    public List<RaceInfo> selectRaceInfoList(RaceInfo raceInfo);

    public List<RaceInfo> selectRaceInfoRepeatQueryList(RaceInfo raceInfo);

    public List<RaceInfo> selectAllRaceInfoList();

}
