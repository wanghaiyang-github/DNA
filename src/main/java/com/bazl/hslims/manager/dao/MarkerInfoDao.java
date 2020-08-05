package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.MarkerInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public interface MarkerInfoDao extends BaseDao<MarkerInfo, Integer>  {

    public List<MarkerInfo> selectListByPanelName(String panelName);

    public List<MarkerInfo> selectMarkerInfoByName(String markerName);

    public List<MarkerInfo> selectMarkerInfoList(MarkerInfo markerInfo);

    public int selectRepeatQuery(MarkerInfo markerInfo);

    public List<MarkerInfo> selectAllMarkerInfoList();

}
