package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.LimsCaseInformation;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public interface LimsCaseInformationService {


    public LimsCaseInformation selectByEntrustmentId(Integer entrustmentId);

    public LimsCaseInformation selectByCaseNo(String caseNo);

    public int refuseCase(LimsCaseInformation limsCaseInformation);

    public Map identifyCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map updateCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map identifyMissingCaseReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public LimsCaseInformation selectById(Integer caseId);

    public List<LimsCaseInformation> selectPaginationList(LimsCaseInformation query, PageInfo pageInfo);

    public int selectCount(LimsCaseInformation limsCaseInformation);

    public boolean deleteById(Integer caseId);

}
