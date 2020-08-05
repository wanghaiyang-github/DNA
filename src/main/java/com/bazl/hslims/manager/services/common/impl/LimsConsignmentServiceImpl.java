package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.LimsConsignmentDao;
import com.bazl.hslims.manager.model.po.LimsConsignment;
import com.bazl.hslims.manager.services.common.LimsConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Service
public class LimsConsignmentServiceImpl implements LimsConsignmentService {

    @Autowired
    LimsConsignmentDao limsConsignmentDao;


    public LimsConsignment selectById(Integer id) {
        return limsConsignmentDao.selectById(id);
    }

    public List<LimsConsignment> selectListByCaseId(Integer caseId) {
        return limsConsignmentDao.selectListByCaseId(caseId);
    }

    public int refuseCase(LimsConsignment consignment) {
        return limsConsignmentDao.refuseCase(consignment);
    }

    public int deleteByCaseId(Integer caseId){
        return limsConsignmentDao.deleteByCaseId(caseId);
    }

    public int deleteById(Integer id) {
        return limsConsignmentDao.deleteById(id);
    }

    @Override
    public int updateByConsignmentNo(LimsConsignment limsConsignment) {
        return limsConsignmentDao.updateByConsignmentNo(limsConsignment);
    }

}
