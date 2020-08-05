package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public interface LimsSampleInfoDao extends BaseDao<LimsSampleInfo, Integer>  {

    public List<LimsSampleInfo> selectListBySampleNo(String sampleNo);
    public List<LimsSampleInfo> selectListByCaseNo(String caseNo);
    public List<LimsSampleInfo> selectListByCaseId(Integer caseId);
    public List<LimsSampleInfo> selectAcceptStatusListByCaseId(Integer caseId);
    public List<LimsSampleInfo> selectHasGeneListByCaseId(Integer caseId);
    public List<LimsSampleInfo> selectListByConsignmentId(Integer consignmentId);
    public List<LimsSampleInfo> selectAcceptStatusListByConsignmentId(Integer consignmentId);
    public List<LimsSampleInfo> selectAuditStatusListByCaseId(Integer caseId);


    public int selectVOCount(LimsSampleInfoVO limsSampleInfoVO);
    int selectVOSampleCount(LimsSampleInfoVO query);
    int selectVOPersonSampleCount(LimsSampleInfoVO query);

    public List<LimsSampleInfoVO> selectVOPaginationList(LimsSampleInfoVO limsSampleInfoVO);
    List<LimsSampleInfoVO>  selectVOPersonSampleList(LimsSampleInfoVO query);
    List<LimsSampleInfoVO>  selectVOSampleInfoList(LimsSampleInfoVO query);
    public boolean deleteByPersonId(Integer personId);

    public boolean updateById(LimsSampleInfo limsSampleInfo);

    public boolean updateSampleType(LimsSampleInfo sampleInfo);

    public List<LimsSampleInfo> selectListBySampleId(Integer sampleId);

    public int updatePhotoPath(LimsSampleInfo limsSampleInfo);

    public int deleteByCaseId(Integer caseId);

    public int deleteByConsignmentId(Integer consignmentId);

    public List<LimsSampleInfo> selectAcceptStatusSortListByCaseId(Integer caseId);

    public List<LimsSampleInfo> selectList(LimsSampleInfo evidenceLimsSampleInfo);

    public boolean updateSubmitFlag(LimsSampleInfo limsSampleInfo);

    public int updateByEvidenceNo(LimsSampleInfo limsSampleInfo);

}
