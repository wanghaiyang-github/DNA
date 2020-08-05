package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.DictItemDao;
import com.bazl.hslims.manager.model.po.DictItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Repository
public class DictItemDaoImpl extends BaseDaoImpl<DictItem,String> implements DictItemDao {
    @Override
    public String namespace() {
        return DictItem.class.getName();
    }

    public int selectRepeatDictItemQuery(DictItem dictItem){
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectRepeatDictItemQuery",dictItem);
    }

    public List<DictItem> selectListByDictType(String dictType) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByDictType", dictType);
    }

    public List<DictItem> selectByParentDictTypeCode(String dictTypeCode){
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectByParentDictTypeCode", dictTypeCode);
    }

    public int deleteByDictTypeCode(String dictTypeCode){
        return this.getSqlSessionTemplate().delete(this.namespace() + ".deleteByDictTypeCode",dictTypeCode);
    }
}
