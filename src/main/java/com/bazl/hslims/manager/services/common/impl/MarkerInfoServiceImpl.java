package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.MarkerInfoDao;
import com.bazl.hslims.manager.model.po.MarkerInfo;
import com.bazl.hslims.manager.services.common.MarkerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
@Service
public class MarkerInfoServiceImpl implements MarkerInfoService {

    @Autowired
    MarkerInfoDao markerInfoDao;

    public List<MarkerInfo> selectListByPanelName(String panelName) {
        return markerInfoDao.selectListByPanelName(panelName);
    }

    public List<MarkerInfo> selectMarkerInfoByName(String markerName) {
        return markerInfoDao.selectMarkerInfoByName(markerName);
    }

    public List<MarkerInfo> selectMarkerInfoList(MarkerInfo markerInfo){
        return markerInfoDao.selectMarkerInfoList(markerInfo);
    }

    public int selectRepeatQuery(MarkerInfo markerInfo){
        return markerInfoDao.selectRepeatQuery(markerInfo);
    }

    public int addMarkerInfo(MarkerInfo markerInfo) {
        int result = 0;
        result = markerInfoDao.insert(markerInfo);
        return result;
    }

    public int deleteMarkerInfo(MarkerInfo markerInfo) {
        int result = 0;
        result = markerInfoDao.deleteById(markerInfo.getId());
        return result;
    }

    public int updateMarkerInfo(MarkerInfo markerInfo) {
        int result = 0;
        result = markerInfoDao.update(markerInfo);
        return result;
    }

    public List<MarkerInfo> selectAllMarkerInfoList() {
        return markerInfoDao.selectAllMarkerInfoList();
    }

}
