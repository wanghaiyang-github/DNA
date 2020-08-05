package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsCaseInformationDao;
import com.bazl.hslims.manager.model.po.LimsCaseInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author wangliu
 * @author Administrator
 * @date 2017/1/4
 */
@Repository
public class LimsCaseInformationDaoImpl extends BaseDaoImpl<LimsCaseInformation, Integer> implements LimsCaseInformationDao {


    @Override
    public String namespace() {
        return LimsCaseInformation.class.getName();
    }

    @Override
    public LimsCaseInformation selectByEntrustmentId(Integer consignmentId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectByEntrustmentId",consignmentId);
    }

    @Override
    public LimsCaseInformation selectByCaseNo(String caseNo) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectByCaseNo", caseNo);
    }

    @Override
    public int refuseCase(LimsCaseInformation limsCaseInformation){
        return this.getSqlSessionTemplate().update(this.namespace() + ".refuseCase", limsCaseInformation);
    }

    @Override
    public int selectCount(LimsCaseInformation limsCaseInformation) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectCount", limsCaseInformation);
    }

    @Override
    public List<LimsCaseInformation> selectPaginationList(LimsCaseInformation query) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectPaginationList",query);
    }

}
