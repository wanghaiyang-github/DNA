package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsEntrustmentInformation;

/**
 * Created by wangliu on 2018/5/13.
 */
public interface LimsEntrustmentInformationDao extends BaseDao<LimsEntrustmentInformation, Integer> {

    public LimsEntrustmentInformation selectByCaseInformationId(Integer caseInformationId);

}
