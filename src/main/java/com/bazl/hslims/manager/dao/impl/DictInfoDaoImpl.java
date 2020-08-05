package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.DictInfoDao;
import com.bazl.hslims.manager.model.po.DictInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/9/30.
 */
@Repository
public class DictInfoDaoImpl extends BaseDaoImpl<DictInfo,String> implements DictInfoDao {
    @Override
    public String namespace() {
        return DictInfo.class.getName();
    }

    public int deleteByDictTypeCode(String dictTypeCode){
        return this.getSqlSessionTemplate().delete(this.namespace() + ".deleteByDictTypeCode",dictTypeCode);
    }
}
