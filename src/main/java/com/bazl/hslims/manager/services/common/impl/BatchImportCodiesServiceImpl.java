package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.BatchImportCodiesDao;
import com.bazl.hslims.manager.model.po.BatchImportCodies;
import com.bazl.hslims.manager.services.common.BatchImportCodiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/20.
 */
@Service
public class BatchImportCodiesServiceImpl implements BatchImportCodiesService {

    @Autowired
    BatchImportCodiesDao batchImportCodiesDao;

    public int insert(BatchImportCodies batchImportCodies){
        int result = 0;
        result = batchImportCodiesDao.insert(batchImportCodies);
        return result;
    }
}
