package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.EquipmentInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */
public interface EquipmentInfoService {

    public EquipmentInfo selectById(Integer id);

    public int deleteById (Integer id);

    public int insertEquipmentInfo(EquipmentInfo equipmentInfo);

    public int updateEquipmentInfo(EquipmentInfo equipmentInfo);

    public List<EquipmentInfo> selectAllList();

    public List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo);

    public List<EquipmentInfo> selectEquipmentNo(String equipmentNo);

    public int updateEquipmentStatus(Integer id);

}
