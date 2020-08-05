package com.bazl.hslims.manager.services.common;

/**
 * Created by Administrator on 2017/1/7.
 */
public interface SeqNoGenerateService {

    public String getNextCaseNoVal(String code);

    public String getNextConsignmentNoVal(String code);

    public String getNextAcceptCaseNoVal(String code);

    public String getNextPersonNoVal(String code);

    public String getNextSampleNoVal(String code);

    public String getNextExtractTaskNoVal(String code);

    public String getNextPcrTaskNoVal(String code);

    public String getNextSyTaskNoVal(String code);

    public int deleteCode(String code);

}
