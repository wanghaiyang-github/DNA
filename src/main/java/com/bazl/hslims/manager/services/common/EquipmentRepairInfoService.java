package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.EquipmentRepairInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public interface EquipmentRepairInfoService {

    public List<EquipmentRepairInfo> selectAllList();

    public int insert(EquipmentRepairInfo equipmentRepairInfo);

    public List<EquipmentRepairInfo> selectEquipmentRepairInfoList(EquipmentRepairInfo equipmentRepairInfo);
}
