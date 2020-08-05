package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.EquipmentNameInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentNameInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
@Repository
public class EquipmentNameInfoDaoImpl extends BaseDaoImpl<EquipmentNameInfo, Integer> implements EquipmentNameInfoDao {

    @Override
    public String namespace() { return EquipmentNameInfo.class.getName(); }

    @Override
    public List<EquipmentNameInfo> selectAllList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAllList");
    }

    @Override
    public List<EquipmentNameInfo> selectEquipmentNameInfoList(EquipmentNameInfo equipmentNameInfo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectEquipmentNameInfoList", equipmentNameInfo);
    }

    @Override
    public List<EquipmentNameInfo> selectEquipmentNameInfoListByTypeNo(String equipmentTypeNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectEquipmentNameInfoListByTypeNo", equipmentTypeNo);
    }

}
