package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.BatchImportCodiesDao;
import com.bazl.hslims.manager.model.po.BatchImportCodies;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/4/20.
 */
@Repository
public class BatchImportCodiesDaoImpl extends BaseDaoImpl<BatchImportCodies,Integer> implements BatchImportCodiesDao {

    @Override
    public String namespace() {
        return BatchImportCodies.class.getName();
    }

}
