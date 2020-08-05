package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.DictItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface DictItemDao extends BaseDao<DictItem,String> {
    public int selectRepeatDictItemQuery(DictItem dictItem);
    public List<DictItem> selectListByDictType(String dictType);

    public int deleteByDictTypeCode(String dictTypeCode);
    public List<DictItem> selectByParentDictTypeCode(String dictTypeCode);
}
