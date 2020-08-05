package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.EquipmentNameInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public interface EquipmentNameInfoDao extends BaseDao<EquipmentNameInfo, Integer> {

    public List<EquipmentNameInfo> selectAllList();

    public List<EquipmentNameInfo> selectEquipmentNameInfoList(EquipmentNameInfo equipmentNameInfo);

    public List<EquipmentNameInfo> selectEquipmentNameInfoListByTypeNo(String equipmentTypeNo);

}
