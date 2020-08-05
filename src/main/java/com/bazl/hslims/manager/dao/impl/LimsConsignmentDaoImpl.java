package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsConsignmentDao;
import com.bazl.hslims.manager.model.po.LimsConsignment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
@Repository
public class LimsConsignmentDaoImpl extends BaseDaoImpl<LimsConsignment, Integer>  implements LimsConsignmentDao {


    @Override
    public String namespace() {
        return LimsConsignment.class.getName();
    }


    public List<LimsConsignment> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectListByCaseId", caseId);
    }

    public int refuseCase(LimsConsignment consignment) {
        return this.getSqlSessionTemplate().update(namespace() + ".refuseCase", consignment);
    }

    public int deleteByCaseId(Integer caseId){
        return this.getSqlSessionTemplate().update(namespace() + ".deleteByCaseId",caseId);
    }

    @Override
    public int updateByConsignmentNo(LimsConsignment limsConsignment) {
        return this.getSqlSessionTemplate().update(namespace()+".updateByConsignmentNo",limsConsignment);
    }

}
