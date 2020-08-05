package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.EquipmentRepairInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public interface EquipmentRepairInfoDao extends BaseDao<EquipmentRepairInfo, Integer> {

    public List<EquipmentRepairInfo> selectAllList();

    public List<EquipmentRepairInfo> selectEquipmentRepairInfoList(EquipmentRepairInfo equipmentRepairInfo);

}
