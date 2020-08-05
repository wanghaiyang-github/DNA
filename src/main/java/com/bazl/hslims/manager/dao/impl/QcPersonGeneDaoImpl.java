package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.QcPersonGeneDao;
import com.bazl.hslims.manager.model.po.QcPersonGene;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/1/25.
 */
@Repository
public class QcPersonGeneDaoImpl extends BaseDaoImpl<QcPersonGene, Integer> implements QcPersonGeneDao {

    @Override
    public String namespace() {
        return QcPersonGene.class.getName();
    }

}
