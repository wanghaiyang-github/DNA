package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsEntrustmentInformationDao;
import com.bazl.hslims.manager.model.po.LimsEntrustmentInformation;
import org.springframework.stereotype.Repository;

/**
 * Created by wangliu on 2018/5/13.
 */
@Repository
public class LimsEntrustmentInformationDaoImpl extends BaseDaoImpl<LimsEntrustmentInformation, Integer> implements LimsEntrustmentInformationDao {

    @Override
    public String namespace() {
        return LimsEntrustmentInformation.class.getName();
    }


    @Override
    public LimsEntrustmentInformation selectByCaseInformationId(Integer caseInformationId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectByCaseInformationId",caseInformationId);
    }
}
