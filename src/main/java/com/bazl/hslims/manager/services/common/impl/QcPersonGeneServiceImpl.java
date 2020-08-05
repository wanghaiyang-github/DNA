package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.QcPersonGeneDao;
import com.bazl.hslims.manager.model.po.QcPersonGene;
import com.bazl.hslims.manager.services.common.QcPersonGeneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/25.
 */
@Service
public class QcPersonGeneServiceImpl implements QcPersonGeneService {

    @Autowired
    private QcPersonGeneDao qcPersonGeneDao;

    public int deleteById(Integer id) {
        return qcPersonGeneDao.deleteById(id);
    }

    public int insert(QcPersonGene qcPersonGene) {
        return qcPersonGeneDao.insert(qcPersonGene);
    }

    public int update(QcPersonGene qcPersonGene) {
        return qcPersonGeneDao.update(qcPersonGene);
    }

    public QcPersonGene selectById(Integer id) {
        return qcPersonGeneDao.selectById(id);
    }

    @Override
    public List<QcPersonGene> selectList(QcPersonGene qcPersonGene) {
        return qcPersonGeneDao.selectList(qcPersonGene);
    }

}
