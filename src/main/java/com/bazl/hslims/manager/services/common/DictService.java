package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.DictInfo;
import com.bazl.hslims.manager.model.po.DictItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface DictService {
    /**
     * 获取指定类型的字典列表
     * @return
     */
    public int addDictInfo(DictInfo dictInfo);
    public int updateDictInfo(DictInfo dictInfo);

    public int addDictItem(DictItem dictItem);
    public int updateDictItem(DictItem dictItem);

    public List<DictInfo> selecList(DictInfo dictInfo);
    public int deleteByDictTypeCode(String dictTypeCode);
    public int deleteByDictItemId(String dictItemId);

    public int selectRepeatDictItemQuery(DictItem dictItem);
    public List<DictInfo> selectByDictInfo(DictInfo dictInfo);
    public DictInfo selectDictInfoById(String dictInfoId);
    public List<DictItem> selectDictItemListByType(String dictType);
    public List<DictItem> selectByParentDictTypeCode(String dictTypeCode);
}
