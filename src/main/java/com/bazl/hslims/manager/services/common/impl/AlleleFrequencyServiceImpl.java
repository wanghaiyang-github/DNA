package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.AlleleFrequencyDao;
import com.bazl.hslims.manager.model.po.AlleleFrequency;
import com.bazl.hslims.manager.services.common.AlleleFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */
@Service
public class AlleleFrequencyServiceImpl implements AlleleFrequencyService {

    @Autowired
    AlleleFrequencyDao alleleFrequencyDao;

    public List<AlleleFrequency> selectAlleleFrequencyList(AlleleFrequency alleleFrequency, PageInfo pageInfo){
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        alleleFrequency.setOffset((pageNo - 1) * pageSize);
        alleleFrequency.setRows(pageSize);
        return alleleFrequencyDao.selectAlleleFrequencyList(alleleFrequency);
    }

    public int selectAlleleFrequencyCount(AlleleFrequency alleleFrequency){
        return alleleFrequencyDao.selectAlleleFrequencyCount(alleleFrequency);
    }

    public int insert(AlleleFrequency alleleFrequency){
        int result = 0;
        result = alleleFrequencyDao.insert(alleleFrequency);
        return result;
    }

    public int update(AlleleFrequency alleleFrequency){
        int result = 0;
        result = alleleFrequencyDao.update(alleleFrequency);
        return result;
    }

    public int delete(Integer id){
        int result = 0;
        result = alleleFrequencyDao.deleteById(id);
        return result;
    }

    public List<AlleleFrequency> selectAlleleFrequencyRepeatList(AlleleFrequency alleleFrequency){
        return alleleFrequencyDao.selectAlleleFrequencyRepeatList(alleleFrequency);
    }

}
