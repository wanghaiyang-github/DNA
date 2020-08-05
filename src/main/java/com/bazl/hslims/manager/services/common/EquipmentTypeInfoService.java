package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.EquipmentTypeInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public interface EquipmentTypeInfoService {

    public List<EquipmentTypeInfo> selectAllList();

    public EquipmentTypeInfo selectById(Integer id);

    public int insertEquipmentTypeInfo(EquipmentTypeInfo equipmentTypeInfo);

    public int updateEquipmentTypeInfo(EquipmentTypeInfo equipmentTypeInfo);

    public int deleteById(Integer id);

}
