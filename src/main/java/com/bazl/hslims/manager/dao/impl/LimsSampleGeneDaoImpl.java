package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsSampleGeneDao;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.model.vo.LimsSampleGeneVO;
import com.bazl.hslims.utils.ListUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
@Repository
public class LimsSampleGeneDaoImpl extends BaseDaoImpl<LimsSampleGene, Integer> implements LimsSampleGeneDao {

    @Override
    public String namespace() {
        return LimsSampleGene.class.getName();
    }

    @Override
    public List<LimsSampleGene> selectAuditList() {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAuditList");
    }

    @Override
    public List<LimsSampleGene> selectListBySampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListBySampleId", sampleId);
    }

    @Override
    public LimsSampleGene selectAuditStatusBySampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace()+".selectAuditStatusBySampleId",sampleId);
    }

    @Override
    public List<LimsSampleGene> selectListByGeneId(Integer id) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByGeneId", id);
    }

    @Override
    public List<LimsSampleGene> selectListGeneId(Integer id) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListGeneId", id);
    }

    @Override
    public List<LimsSampleGene> selectListByCaseId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCaseId", sampleId);
    }

    @Override
    public List<LimsSampleGene> selectMixListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectMixListByCaseId", caseId);
    }

    @Override
    public List<LimsSampleGene> selectAuditedListByCaseId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAuditedListByCaseId", sampleId);
    }

    @Override
    public LimsSampleGene selectAuditedBySampleId(Integer sampleId) {
        List<LimsSampleGene> list = this.getSqlSessionTemplate().selectList(this.namespace() + ".selectAuditedBySampleId", sampleId);
        return ListUtils.isNullOrEmptyList(list) ? null : list.get(0);
    }

    @Override
    public List<LimsSampleGene> selectListByCriminalPersonId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCriminalPersonId",sampleId);
    }

    @Override
    public int selectVOCount(LimsSampleGeneVO limsSampleGeneVO) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectVOCount", limsSampleGeneVO);
    }

    @Override
    public List<LimsSampleGeneVO> selectVOPaginationList(LimsSampleGeneVO limsSampleGeneVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectVOPaginationList", limsSampleGeneVO);
    }

    @Override
    public List<LimsSampleGene> selectGeneInfoList(LimsSampleGene sampleGene){
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectGeneInfoList",sampleGene);
    }

    @Override
    public int updateBySampleId(Integer sampleId) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".updateBySampleId", sampleId);
    }

    @Override
    public int updateById(LimsSampleGene sampleGene) {
        return this.getSqlSessionTemplate().update(this.namespace() + ".updateById", sampleGene);
    }

    @Override
    public List<LimsSampleGene> selectListByCriminalId(Integer sampleId) {
        return this.getSqlSessionTemplate().selectList(this.namespace()+".selectListByCriminalId",sampleId);
    }
}
