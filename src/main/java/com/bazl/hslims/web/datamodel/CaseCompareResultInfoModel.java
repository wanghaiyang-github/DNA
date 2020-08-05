package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.CaseCompareResultInfo;
import com.bazl.hslims.manager.model.po.LimsCaseInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */
public class CaseCompareResultInfoModel implements Serializable {

    private LimsCaseInfo caseInfo;

    private List<CaseCompareResultInfo> caseCompareResultInfoList;

    public LimsCaseInfo getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(LimsCaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public List<CaseCompareResultInfo> getCaseCompareResultInfoList() {
        return caseCompareResultInfoList;
    }

    public void setCaseCompareResultInfoList(List<CaseCompareResultInfo> caseCompareResultInfoList) {
        this.caseCompareResultInfoList = caseCompareResultInfoList;
    }
}
