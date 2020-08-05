package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface LimsSampleInfoService {

    public LimsSampleInfo selectById(Integer id);

    public List<LimsSampleInfo> selectListBySampleNo(String sampleNo);

    public List<LimsSampleInfo> selectListByCaseNo(String caseNo);

    public List<LimsSampleInfo> selectListByCaseId(Integer caseId);

    public List<LimsSampleInfo> selectAcceptStatusListByCaseId(Integer caseId);

    public List<LimsSampleInfo> selectAcceptedListByCaseId(Integer caseId);

    public List<LimsSampleInfo> selectHasGeneListByCaseId(Integer caseId);

    public List<LimsSampleInfo> selectListByConsignmentId(Integer consignmentId);

    public List<LimsSampleInfo> selectAcceptStatusListByConsignmentId(Integer consignmentId);
    public List<LimsSampleInfo> selectAuditStatusListByCaseId(Integer caseId);


    public int selectVOCount(LimsSampleInfoVO query);
    int selectVOSampleCount(LimsSampleInfoVO query);
   int selectVOPersonSampleCount(LimsSampleInfoVO query);
    public List<LimsSampleInfoVO> selectVOPaginationList(LimsSampleInfoVO query, PageInfo pageInfo);


    List<LimsSampleInfoVO>  selectVOSampleInfoList(LimsSampleInfoVO query, PageInfo pageInfo);
    List<LimsSampleInfoVO>  selectVOPersonSampleList(LimsSampleInfoVO query, PageInfo pageInfo);

    public int selectCountByType(String sampleType);

    public boolean deleteById(Integer sampleId);

    public boolean updateById(LimsSampleInfo limsSampleInfo);


    public List<LimsSampleInfo> selectListBySampleId(Integer sampleId);

    public int updatePhotoPath(LimsSampleInfo limsSampleInfo);

    public int deleteByCaseId(Integer caseId);

    public int insertSample(LimsSampleInfo limsSampleInfo);

    public int deleteByConsignmentId(Integer consignmentId);

    public List<LimsSampleInfo> selectAcceptStatusSortListByCaseId(Integer caseId);

    public List<LimsSampleInfo> selectList(LimsSampleInfo evidenceLimsSampleInfo);

    public boolean updateSubmitFlag(LimsSampleInfo limsSampleInfo);

    public int updateByEvidenceNo(LimsSampleInfo limsSampleInfo);

}
