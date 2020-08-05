package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.EquipmentRepairInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentRepairInfo;
import com.bazl.hslims.manager.services.common.EquipmentRepairInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
@Service
public class EquipmentRepairInfoServiceImpl implements EquipmentRepairInfoService {

    @Autowired
    EquipmentRepairInfoDao equipmentRepairInfoDao;

    public List<EquipmentRepairInfo> selectAllList() {
        return equipmentRepairInfoDao.selectAllList();
    }

    public int insert(EquipmentRepairInfo equipmentRepairInfo) {
        return equipmentRepairInfoDao.insert(equipmentRepairInfo);
    }

    public List<EquipmentRepairInfo> selectEquipmentRepairInfoList(EquipmentRepairInfo equipmentRepairInfo) {
        return equipmentRepairInfoDao.selectEquipmentRepairInfoList(equipmentRepairInfo);
    }
}
