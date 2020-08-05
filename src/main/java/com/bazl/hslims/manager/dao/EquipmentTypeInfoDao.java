package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.EquipmentTypeInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
public interface EquipmentTypeInfoDao extends BaseDao<EquipmentTypeInfo, Integer> {

    public List<EquipmentTypeInfo> selectAllList();

}
