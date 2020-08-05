package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.LimsCaseInfo;
import com.bazl.hslims.manager.model.po.LimsSampleGene;

import java.util.List;

/**
 * Created by Administrator on 2017/1/12.
 */
public class CaseCompareResultGroup {

    private int groupId;

    private List<LimsSampleGene> matchedSamples;

    private List<LimsSampleGene> mixMatchedSamples;

    private LimsCaseInfo caseInfo;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public List<LimsSampleGene> getMatchedSamples() {
        return matchedSamples;
    }

    public void setMatchedSamples(List<LimsSampleGene> matchedSamples) {
        this.matchedSamples = matchedSamples;
    }

    public List<LimsSampleGene> getMixMatchedSamples() {
        return mixMatchedSamples;
    }

    public void setMixMatchedSamples(List<LimsSampleGene> mixMatchedSamples) {
        this.mixMatchedSamples = mixMatchedSamples;
    }

    public LimsCaseInfo getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(LimsCaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }
}
