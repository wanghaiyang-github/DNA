package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.EquipmentInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentInfo;
import com.bazl.hslims.manager.services.common.EquipmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */
@Service
public class EquipmentInfoServiceImpl implements EquipmentInfoService {

    @Autowired
    EquipmentInfoDao equipmentInfoDao;

    public EquipmentInfo selectById(Integer id) {
        return equipmentInfoDao.selectById(id);
    }

    public int deleteById(Integer id) {
        return equipmentInfoDao.deleteById(id);
    }

    public int insertEquipmentInfo(EquipmentInfo equipmentInfo) {
        return equipmentInfoDao.insert(equipmentInfo);
    }

    public int updateEquipmentInfo(EquipmentInfo equipmentInfo) {
        return equipmentInfoDao.update(equipmentInfo);
    }

    public List<EquipmentInfo> selectAllList() {
        return equipmentInfoDao.selectAllList();
    }

    public List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo) {
        return equipmentInfoDao.selectEquipmentInfoList(equipmentInfo);
    }

    public List<EquipmentInfo> selectEquipmentNo(String equipmentNo) {
        return equipmentInfoDao.selectEquipmentNo(equipmentNo);
    }

    public int updateEquipmentStatus(Integer id) {
        return equipmentInfoDao.updateEquipmentStatus(id);
    }

}
