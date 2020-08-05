package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsCaseInformation;

import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface LimsCaseInformationDao extends BaseDao<LimsCaseInformation, Integer> {

    public LimsCaseInformation selectByEntrustmentId(Integer entrustmentId);

    public LimsCaseInformation selectByCaseNo(String caseNo);

    public int refuseCase(LimsCaseInformation limsCaseInformation);

    public int selectCount(LimsCaseInformation limsCaseInformation);

    public List<LimsCaseInformation> selectPaginationList(LimsCaseInformation query);

}
