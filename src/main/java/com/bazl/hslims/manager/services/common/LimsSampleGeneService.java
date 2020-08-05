package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.model.vo.LimsSampleGeneVO;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
public interface LimsSampleGeneService {

    public int insert(LimsSampleGene limsSampleGene);

    public int update(LimsSampleGene limsSampleGene);

    public List<LimsSampleGene> selectAuditList();

    public List<LimsSampleGene> selectListBySampleId(Integer sampleId);


    LimsSampleGene selectAuditStatusBySampleId(Integer sampleId);

    public List<LimsSampleGene> selectListByGeneId(Integer id);

    public List<LimsSampleGene> selectListGeneId(Integer id);

    public List<LimsSampleGene> selectListByCaseId(Integer caseId);

    public List<LimsSampleGene> selectMixListByCaseId(Integer caseId);

    public List<LimsSampleGene> selectAuditedListByCaseId(Integer caseId);
    public LimsSampleGene selectAuditedBySampleId(Integer sampleId);

    public List<LimsSampleGene> selectListByCriminalPersonId(Integer sampleId);

    public int selectVOCount(LimsSampleGeneVO query);

    public List<LimsSampleGeneVO> selectVOPaginationList(LimsSampleGeneVO query, PageInfo pageInfo);

    public List<LimsSampleGene> selectGeneInfoList(LimsSampleGene sampleGene);

    public int updateBySampleId(Integer sampleId);

    public int updateById(LimsSampleGene limsSampleGene);

    public List<LimsSampleGene> selectListByCriminalId(Integer sampleId);

}
