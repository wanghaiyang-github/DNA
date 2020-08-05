package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.MarkerInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public interface MarkerInfoService {

    public List<MarkerInfo> selectListByPanelName(String panelName);

    public List<MarkerInfo> selectMarkerInfoByName(String markerName);

    public List<MarkerInfo> selectMarkerInfoList(MarkerInfo markerInfo);

    public int selectRepeatQuery(MarkerInfo markerInfo);

    public int addMarkerInfo(MarkerInfo markerInfo);

    public int deleteMarkerInfo(MarkerInfo markerInfo);

    public int updateMarkerInfo(MarkerInfo markerInfo);

    public List<MarkerInfo> selectAllMarkerInfoList();

}
