package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.CriminalPersonInfo;
import com.bazl.hslims.manager.model.vo.CriminalPersonInfoVo;

import java.util.List;

/**
 * Created by Administrator on 2018-08-17.
 */
public interface CriminalPersonInfoService {

    public List<CriminalPersonInfoVo> selectVoPaginationList(CriminalPersonInfoVo query, PageInfo pageInfo);

    public List<CriminalPersonInfoVo> selectGenePaginationList(CriminalPersonInfoVo query, PageInfo pageInfo);

    public int selectVoCount(CriminalPersonInfoVo query);

    public CriminalPersonInfo selectById(Integer id);

    public List<CriminalPersonInfo> selectByPersonNo(String personNo);

    public int update(CriminalPersonInfo criminalPersonInfo);

    public int delete(Integer id);

    public int insert(CriminalPersonInfo criminalPersonInfo);


    List<CriminalPersonInfoVo> selectGenePersonList(CriminalPersonInfoVo criminalPersonInfoVo,PageInfo pageInfo);

    int selectGenePersonCount(CriminalPersonInfoVo criminalPersonInfoVo);

}
