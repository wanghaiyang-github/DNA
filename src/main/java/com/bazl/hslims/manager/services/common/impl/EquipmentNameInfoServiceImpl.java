package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.EquipmentNameInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentNameInfo;
import com.bazl.hslims.manager.services.common.EquipmentNameInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
@Service
public class EquipmentNameInfoServiceImpl implements EquipmentNameInfoService {

    @Autowired
    EquipmentNameInfoDao equipmentNameInfoDao;

    @Override
    public List<EquipmentNameInfo> selectAllList() {
        return equipmentNameInfoDao.selectAllList();
    }

    @Override
    public EquipmentNameInfo selectById(Integer id) {
        return equipmentNameInfoDao.selectById(id);
    }

    @Override
    public int insertEquipmentNameInfo(EquipmentNameInfo equipmentNameInfo) {
        return equipmentNameInfoDao.insert(equipmentNameInfo);
    }

    @Override
    public int updateEquipmentNameInfo(EquipmentNameInfo equipmentNameInfo) {
        return equipmentNameInfoDao.update(equipmentNameInfo);
    }

    @Override
    public int deleteById(Integer id) {
        return equipmentNameInfoDao.deleteById(id);
    }

    @Override
    public List<EquipmentNameInfo> selectEquipmentNameInfoList(EquipmentNameInfo equipmentNameInfo) {
        return equipmentNameInfoDao.selectEquipmentNameInfoList(equipmentNameInfo);
    }

    @Override
    public List<EquipmentNameInfo> selectEquipmentNameInfoListByTypeNo(String equipmentTypeNo) {
        return equipmentNameInfoDao.selectEquipmentNameInfoListByTypeNo(equipmentTypeNo);
    }


}
