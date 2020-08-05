package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.AlleleFrequency;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */
public interface AlleleFrequencyDao extends BaseDao<AlleleFrequency, Integer> {

    public List<AlleleFrequency> selectAlleleFrequencyList(AlleleFrequency alleleFrequency);

    public int selectAlleleFrequencyCount(AlleleFrequency alleleFrequency);

    public List<AlleleFrequency> selectAlleleFrequencyRepeatList(AlleleFrequency alleleFrequency);

}
