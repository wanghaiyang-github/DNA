package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsConsignment;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface LimsConsignmentService {


    public LimsConsignment selectById(Integer id);

    public List<LimsConsignment> selectListByCaseId(Integer caseId);

    public int refuseCase(LimsConsignment consignment);

    public int deleteByCaseId(Integer caseId);

    public int deleteById(Integer id);

    public int updateByConsignmentNo(LimsConsignment limsConsignment);

}
