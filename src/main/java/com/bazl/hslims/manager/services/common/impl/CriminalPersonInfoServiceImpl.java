package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.dao.CriminalPersonInfoDao;
import com.bazl.hslims.manager.model.po.CriminalPersonInfo;
import com.bazl.hslims.manager.model.vo.CriminalPersonInfoVo;
import com.bazl.hslims.manager.services.common.CriminalPersonInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018-06-10.
 */
@Service
public class CriminalPersonInfoServiceImpl implements CriminalPersonInfoService {

    @Autowired
    CriminalPersonInfoDao criminalPersonInfoDao;

    @Override
    public List<CriminalPersonInfoVo> selectVoPaginationList(CriminalPersonInfoVo query, PageInfo pageInfo) {

        query = resetQueryParams(query);

        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return criminalPersonInfoDao.selectVoPaginationList(query);
    }

    @Override
    public List<CriminalPersonInfoVo> selectGenePaginationList(CriminalPersonInfoVo query, PageInfo pageInfo) {
        query = resetQueryParams(query);

        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        query.setOffset((pageNo - 1) * pageSize);
        query.setRows(pageSize);

        return criminalPersonInfoDao.selectGenePaginationList(query);
    }

    @Override
    public int selectVoCount(CriminalPersonInfoVo query) {
        query = resetQueryParams(query);
        return criminalPersonInfoDao.selectVoCount(query);
    }

    @Override
    public CriminalPersonInfo selectById(Integer id) {
        return criminalPersonInfoDao.selectById(id);
    }

    @Override
    public List<CriminalPersonInfo> selectByPersonNo(String personNo) {
        return criminalPersonInfoDao.selectByPersonNo(personNo);
    }

    @Override
    public int update(CriminalPersonInfo criminalPersonInfo) {
        return criminalPersonInfoDao.update(criminalPersonInfo);
    }

    @Override
    public int delete(Integer id) {
        return criminalPersonInfoDao.deleteById(id);
    }

    @Override
    public int insert(CriminalPersonInfo criminalPersonInfo) {
        return criminalPersonInfoDao.insert(criminalPersonInfo);
    }

    @Override
    public List<CriminalPersonInfoVo> selectGenePersonList(CriminalPersonInfoVo criminalPersonInfoVo,PageInfo pageInfo) {
        int pageNo = pageInfo.getPage();
        int pageSize = pageInfo.getEvePageRecordCnt();
        criminalPersonInfoVo.setOffset((pageNo - 1) * pageSize);
        criminalPersonInfoVo.setRows(pageSize);
        return criminalPersonInfoDao.selectGenePersonList(criminalPersonInfoVo);
    }

    @Override
    public int selectGenePersonCount(CriminalPersonInfoVo criminalPersonInfoVo) {
        return criminalPersonInfoDao.selectGenePersonCount(criminalPersonInfoVo);
    }

    private CriminalPersonInfoVo resetQueryParams(CriminalPersonInfoVo query){
        if(StringUtils.isBlank(query.getPersonNo())){
            query.setPersonNo(null);
        }else{
            query.setPersonNo(query.getPersonNo().trim());
        }

        if(StringUtils.isBlank(query.getEntity().getPersonName())){
            query.getEntity().setPersonName(null);
        }else{
            query.getEntity().setPersonName(query.getEntity().getPersonName().trim());
        }

        if(StringUtils.isBlank(query.getEntity().getIdcardNo())){
            query.getEntity().setIdcardNo(null);
        }else{
            query.getEntity().setIdcardNo(query.getEntity().getIdcardNo().trim());
        }

        if(query.getScanedDatetimeStart() == null ){
            query.setScanedDatetimeStart(null);
        }

        if(query.getScanedDatetime() == null ){
            query.setScanedDatetime(null);
        }

        return query;
    }

}
