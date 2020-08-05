package com.bazl.hslims.web.datamodel;


import com.bazl.hslims.manager.model.po.LimsSampleInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */
public class SampleInfoDataModel {

    private List<LimsSampleInfo> sampleInfoList;

    public List<LimsSampleInfo> getSampleInfoList() {
        return sampleInfoList;
    }

    public void setSampleInfoList(List<LimsSampleInfo> sampleInfoList) {
        this.sampleInfoList = sampleInfoList;
    }
}
