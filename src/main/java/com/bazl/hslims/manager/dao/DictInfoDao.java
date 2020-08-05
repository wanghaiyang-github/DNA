package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.DictInfo;

/**
 * Created by Administrator on 2017/9/30.
 */
public interface DictInfoDao extends BaseDao<DictInfo,String>{
    public int deleteByDictTypeCode(String dictTypeCode);
}
