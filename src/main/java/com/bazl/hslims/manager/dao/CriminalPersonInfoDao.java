package com.bazl.hslims.manager.dao;


import com.bazl.hslims.manager.model.po.CriminalPersonInfo;
import com.bazl.hslims.manager.model.vo.CriminalPersonInfoVo;

import java.util.List;

/**
 * Created by Administrator on 2018-06-08.
 */
public interface CriminalPersonInfoDao extends BaseDao<CriminalPersonInfo, Integer> {

    public List<CriminalPersonInfoVo> selectVoPaginationList(CriminalPersonInfoVo query);

    public List<CriminalPersonInfoVo> selectGenePaginationList(CriminalPersonInfoVo query);

    public int selectVoCount(CriminalPersonInfoVo query);

    public List<CriminalPersonInfo> selectByPersonNo(String personNo);

    List<CriminalPersonInfoVo> selectGenePersonList(CriminalPersonInfoVo criminalPersonInfoVo);

    int selectGenePersonCount(CriminalPersonInfoVo criminalPersonInfoVo);

}
