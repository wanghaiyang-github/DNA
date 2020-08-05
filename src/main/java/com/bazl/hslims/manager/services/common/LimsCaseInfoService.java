package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.LimsCaseInfo;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface LimsCaseInfoService {

    public boolean deleteByConsignmentId(Integer consignmentId) throws Exception;

    public LimsCaseInfo selectById(Integer caseId);
    public LimsCaseInfo selectByConsignmentId(Integer consignmentId);

    public LimsCaseInfo selectListByConsignmentId(Integer consignmentId);

    public LimsCaseInfo selectListByCaseNo(String caseNo);

    public List<LimsCaseInfo> selectListByCaseXkNo(String caseXkNo);

    public List<LimsCaseInfo> selectNotConsignmentNoList(LimsCaseInfo limsCaseInfo);

    public int updateCaseXkAno(LimsCaseInfo limsCaseInfo);

    /**
     * 新增委托
     * @param consignmentDataModel
     * @return
     */
    public Integer identifyReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifyCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifyPersonReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifySampleReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifyPersonRegSupply(Integer newConsignmentId, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifySampleRegSupply(Integer newConsignmentId, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifyEditPersonRegSupply(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifyEditSampleRegSupply(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    /**
     * 补送委托
     * @param consignmentDataModel
     * @return
     */
    public Integer identifyRegSupply(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public int updateCaseStatus(Integer caseId);


    /**
     * 变更委托
     * @param consignmentDataModel
     * @return
     */
    public boolean Update(ConsignmentDataModel consignmentDataModel) throws Exception;

    /**
     * 受理委托
     * @param consignmentDataModel
     * @return
     */
    public boolean Accept(ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map acceptSavePerson(ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map acceptSaveSample(ConsignmentDataModel consignmentDataModel) throws Exception;

    public int selectCount(LimsCaseInfoVO query);
    public List<LimsCaseInfo> selectPaginationList(LimsCaseInfoVO query, PageInfo pageInfo);

    public int selectVOCount(LimsCaseInfoVO query);

    public List<LimsCaseInfoVO> selectVOPaginationList(LimsCaseInfoVO query, PageInfo pageInfo);

    public List<LimsCaseInfoVO> selectVOAllList(LimsCaseInfoVO query, PageInfo pageInfo);
    public int selectVOCnt(LimsCaseInfoVO query);

    public List<LimsCaseInfoVO> selectVOList(LimsCaseInfoVO query);

    public int selectCountByProperty(String caseProperty);

    public int refuseCase(LimsCaseInfo caseInfo);

    public boolean deleteById(Integer caseId);

    public List<LimsCaseInfoVO> selectVOGeneralQueryList(LimsCaseInfoVO query, PageInfo pageInfo);

    public int selectVOGeneralQueryCount(LimsCaseInfoVO query);

    public List<LimsCaseInfoVO> selectVOIdentifyBookList(LimsCaseInfoVO query, PageInfo pageInfo);

    public int selectVOIdentifyBookCount(LimsCaseInfoVO query);

}
