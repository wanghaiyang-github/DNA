package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsConsignment;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface LimsConsignmentDao extends BaseDao<LimsConsignment, Integer> {

    public List<LimsConsignment> selectListByCaseId(Integer caseId);

    public int refuseCase(LimsConsignment consignment);

    public int deleteByCaseId(Integer caseId);

    public int updateByConsignmentNo(LimsConsignment limsConsignment);

}
