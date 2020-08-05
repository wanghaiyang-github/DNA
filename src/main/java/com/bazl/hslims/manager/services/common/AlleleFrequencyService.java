package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.AlleleFrequency;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */
public interface AlleleFrequencyService {

    public List<AlleleFrequency> selectAlleleFrequencyList(AlleleFrequency alleleFrequency, PageInfo pageInfo);

    public int selectAlleleFrequencyCount(AlleleFrequency alleleFrequency);

    public int insert(AlleleFrequency alleleFrequency);

    public int update(AlleleFrequency alleleFrequency);

    public int delete(Integer id);

    public List<AlleleFrequency> selectAlleleFrequencyRepeatList(AlleleFrequency alleleFrequency);

}
