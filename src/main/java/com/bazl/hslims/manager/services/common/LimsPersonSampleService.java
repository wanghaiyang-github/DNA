package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsPersonSample;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;

import java.util.List;
import java.util.Map;

/**
 * Created by wangliu on 2018/5/13.
 */
public interface LimsPersonSampleService {

    public int insert(LimsPersonSample limsPersonSample);

    public boolean deleteById(Integer personId);

    public Map identifyPersonReg(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public Map updatePersonSample(String operateType, ConsignmentDataModel consignmentDataModel) throws Exception;

    public List<LimsPersonSample> selectListByEntrustmentId(Integer entrustmentId);

    public List<LimsPersonSample> selectListByCaseInformationId(Integer caseInformationId);

    public List<LimsPersonSample> selectListBySampleLaboratoryNo(String sampleLaboratoryNo);

}
