package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.EquipmentInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */
@Repository
public class EquipmentInfoDaoImpl extends BaseDaoImpl<EquipmentInfo, Integer> implements EquipmentInfoDao {

    public String namespace() { return EquipmentInfo.class.getName(); }

    public List<EquipmentInfo> selectAllList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAllList");
    }

    public List<EquipmentInfo> selectEquipmentInfoList(EquipmentInfo equipmentInfo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectEquipmentInfoList", equipmentInfo);
    }

    public List<EquipmentInfo> selectEquipmentNo(String equipmentNo) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectEquipmentNo", equipmentNo);
    }

    public int updateEquipmentStatus(Integer id) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".updateEquipmentStatus", id);
    }

}
