package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.LimsSampleGene;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public class CheckGeneDataModel implements Serializable {

    private List<LimsSampleGene> sampleGeneList;

    public List<LimsSampleGene> getSampleGeneList() {
        return sampleGeneList;
    }

    public void setSampleGeneList(List<LimsSampleGene> sampleGeneList) {
        this.sampleGeneList = sampleGeneList;
    }
}
