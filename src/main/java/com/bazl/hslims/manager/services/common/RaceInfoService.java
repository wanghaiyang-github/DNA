package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.RaceInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
public interface RaceInfoService {

    public List<RaceInfo> selectRaceInfoList(RaceInfo raceInfo);

    public int insert(RaceInfo raceInfo);

    public int update(RaceInfo raceInfo);

    public int delete(Integer raceId);

    public List<RaceInfo> selectRaceInfoRepeatQueryList(RaceInfo raceInfo);

    public List<RaceInfo> selectAllRaceInfoList();

}
