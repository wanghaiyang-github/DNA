package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.EquipmentInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */
public interface EquipmentInfoDao extends BaseDao<EquipmentInfo, Integer> {

    public List<EquipmentInfo> selectAllList();

    public List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo);

    public List<EquipmentInfo> selectEquipmentNo(String equipmentNo);

    public int updateEquipmentStatus(Integer id);

}
