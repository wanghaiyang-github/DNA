package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.EquipmentRepairInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentRepairInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
@Repository
public class EquipmentRepairInfoDaoImpl extends BaseDaoImpl<EquipmentRepairInfo, Integer> implements EquipmentRepairInfoDao {

    public String namespace() { return EquipmentRepairInfo.class.getName(); }

    public List<EquipmentRepairInfo> selectAllList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAllList");
    }

    public List<EquipmentRepairInfo> selectEquipmentRepairInfoList(EquipmentRepairInfo equipmentRepairInfo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectEquipmentRepairInfoList", equipmentRepairInfo);
    }

}
