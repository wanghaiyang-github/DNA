package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.QcPersonGene;

import java.util.List;

/**
 * Created by Administrator on 2017/1/25.
 */
public interface QcPersonGeneService {

    public QcPersonGene selectById(Integer id);

    public int deleteById(Integer id);

    public int insert(QcPersonGene qcPersonGene);

    public int update(QcPersonGene qcPersonGene);

    public List<QcPersonGene> selectList(QcPersonGene qcPersonGene);

}
