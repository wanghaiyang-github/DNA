package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.EquipmentTypeInfoDao;
import com.bazl.hslims.manager.model.po.EquipmentTypeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/10/25.
 */
@Repository
public class EquipmentTypeInfoDaoImpl extends BaseDaoImpl<EquipmentTypeInfo, Integer> implements EquipmentTypeInfoDao {

    public String namespace() { return EquipmentTypeInfo.class.getName(); }

    public List<EquipmentTypeInfo> selectAllList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAllList");
    }

}
