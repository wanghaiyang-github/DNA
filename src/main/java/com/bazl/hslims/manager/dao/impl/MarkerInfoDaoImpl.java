package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.MarkerInfoDao;
import com.bazl.hslims.manager.model.po.MarkerInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
@Repository
public class MarkerInfoDaoImpl extends BaseDaoImpl<MarkerInfo, Integer> implements MarkerInfoDao {

    @Override
    public String namespace() {
        return MarkerInfo.class.getName();
    }

    public List<MarkerInfo> selectListByPanelName(String panelName) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByPanelName", panelName);
    }

    public List<MarkerInfo> selectMarkerInfoByName(String markerName) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectMarkerInfoByName", markerName);
    }

    public List<MarkerInfo> selectMarkerInfoList(MarkerInfo markerInfo){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectMarkerInfoList",markerInfo);
    }

    public int selectRepeatQuery(MarkerInfo markerInfo){
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectRepeatQuery",markerInfo);
    }

    public List<MarkerInfo> selectAllMarkerInfoList() {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectAllMarkerInfoList");
    }

}
