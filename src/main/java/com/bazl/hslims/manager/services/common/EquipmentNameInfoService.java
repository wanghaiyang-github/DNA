package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.EquipmentNameInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public interface EquipmentNameInfoService {

    public List<EquipmentNameInfo> selectAllList();

    public EquipmentNameInfo selectById(Integer id);

    public int insertEquipmentNameInfo(EquipmentNameInfo equipmentNameInfo);

    public int updateEquipmentNameInfo(EquipmentNameInfo equipmentNameInfo);

    public int deleteById(Integer id);

    public List<EquipmentNameInfo> selectEquipmentNameInfoList(EquipmentNameInfo equipmentNameInfo);

    public List<EquipmentNameInfo> selectEquipmentNameInfoListByTypeNo(String equipmentTypeNo);


}
