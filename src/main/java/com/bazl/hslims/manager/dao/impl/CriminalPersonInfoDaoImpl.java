package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.CriminalPersonInfoDao;
import com.bazl.hslims.manager.model.po.CriminalPersonInfo;
import com.bazl.hslims.manager.model.vo.CriminalPersonInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018-06-08.
 */
@Repository
public class CriminalPersonInfoDaoImpl extends BaseDaoImpl<CriminalPersonInfo, Integer> implements CriminalPersonInfoDao {

    @Override
    public String namespace(){
        return CriminalPersonInfo.class.getName();
    }


    @Override
    public List<CriminalPersonInfoVo> selectVoPaginationList(CriminalPersonInfoVo query) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectVoPaginationList",query);
    }

    @Override
    public List<CriminalPersonInfoVo> selectGenePaginationList(CriminalPersonInfoVo query) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectGenePaginationList",query);
    }

    @Override
    public int selectVoCount(CriminalPersonInfoVo query) {
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectVoCount", query);
    }

    @Override
    public List<CriminalPersonInfo> selectByPersonNo(String personNo) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectByPersonNo",personNo);
    }

    @Override
    public List<CriminalPersonInfoVo> selectGenePersonList(CriminalPersonInfoVo criminalPersonInfoVo) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectGenePersonList",criminalPersonInfoVo);
    }

    @Override
    public int selectGenePersonCount(CriminalPersonInfoVo criminalPersonInfoVo) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectGenePersonCount",criminalPersonInfoVo);
    }
}
