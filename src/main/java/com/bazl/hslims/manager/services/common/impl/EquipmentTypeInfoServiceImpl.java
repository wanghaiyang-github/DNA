package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.EquipmentTypeInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentTypeInfo;
import com.bazl.hslims.manager.services.common.EquipmentTypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
@Service
public class EquipmentTypeInfoServiceImpl implements EquipmentTypeInfoService {

    @Autowired
    EquipmentTypeInfoDao equipmentTypeInfoDao;

    public List<EquipmentTypeInfo> selectAllList() {
        return equipmentTypeInfoDao.selectAllList();
    }

    public EquipmentTypeInfo selectById(Integer id) {
        return equipmentTypeInfoDao.selectById(id);
    }

    public int insertEquipmentTypeInfo(EquipmentTypeInfo equipmentTypeInfo) {
        return equipmentTypeInfoDao.insert(equipmentTypeInfo);
    }

    public int updateEquipmentTypeInfo(EquipmentTypeInfo equipmentTypeInfo) {
        return equipmentTypeInfoDao.update(equipmentTypeInfo);
    }

    public int deleteById(Integer id) {
        return equipmentTypeInfoDao.deleteById(id);
    }

}
