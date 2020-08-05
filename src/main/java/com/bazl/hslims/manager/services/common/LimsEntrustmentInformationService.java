package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsEntrustmentInformation;

/**
 * Created by wangliu on 2018/5/13.
 */
public interface LimsEntrustmentInformationService {

    public int insert(LimsEntrustmentInformation limsEntrustmentInformation);

    public boolean deleteById(Integer caseInformationId);

    public LimsEntrustmentInformation selectById(Integer id);

    public LimsEntrustmentInformation selectByCaseInformationId(Integer caseInformationId);
}
