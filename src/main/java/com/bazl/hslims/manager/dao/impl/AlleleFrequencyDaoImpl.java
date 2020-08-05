package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.AlleleFrequencyDao;
import com.bazl.hslims.manager.model.po.AlleleFrequency;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */
@Repository
public class AlleleFrequencyDaoImpl extends BaseDaoImpl<AlleleFrequency, Integer> implements AlleleFrequencyDao {

    @Override
    public String namespace() { return AlleleFrequency.class.getName(); }

    public List<AlleleFrequency> selectAlleleFrequencyList(AlleleFrequency alleleFrequency) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAlleleFrequencyList", alleleFrequency);
    }

    public int selectAlleleFrequencyCount(AlleleFrequency alleleFrequency){
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectAlleleFrequencyCount",alleleFrequency);
    }

    public List<AlleleFrequency> selectAlleleFrequencyRepeatList(AlleleFrequency alleleFrequency) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAlleleFrequencyRepeatList", alleleFrequency);
    }

}
