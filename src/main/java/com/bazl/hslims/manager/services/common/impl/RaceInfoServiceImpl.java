package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.RaceInfoDao;
import com.bazl.hslims.manager.model.po.RaceInfo;
import com.bazl.hslims.manager.services.common.RaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */
@Service
public class RaceInfoServiceImpl implements RaceInfoService {

    @Autowired
    RaceInfoDao raceInfoDao;

    public List<RaceInfo> selectRaceInfoList(RaceInfo raceInfo) {
        return raceInfoDao.selectRaceInfoList(raceInfo);
    }

    public int insert(RaceInfo raceInfo) {
        int result = 0;
        result = raceInfoDao.insert(raceInfo);
        return result;
    }

    public int update(RaceInfo raceInfo) {
        int result = 0;
        result = raceInfoDao.update(raceInfo);
        return result;
    }

    public int delete(Integer raceId) {
        int result = 0;
        result = raceInfoDao.deleteById(raceId);
        return result;
    }

    public List<RaceInfo> selectRaceInfoRepeatQueryList(RaceInfo raceInfo) {
        return raceInfoDao.selectRaceInfoRepeatQueryList(raceInfo);
    }

    public List<RaceInfo> selectAllRaceInfoList() {
        return raceInfoDao.selectAllRaceInfoList();
    }

}
