package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.LimsExtractRecord;
import com.bazl.hslims.manager.model.po.LimsExtractRecordSample;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public class ExtractRecordDataModel  implements Serializable {

    private LimsExtractRecord limsExtractRecord;

    private List<LimsExtractRecordSample> limsExtractRecordSampleList;

    public LimsExtractRecord getLimsExtractRecord() {
        return limsExtractRecord;
    }

    public void setLimsExtractRecord(LimsExtractRecord limsExtractRecord) {
        this.limsExtractRecord = limsExtractRecord;
    }

    public List<LimsExtractRecordSample> getLimsExtractRecordSampleList() {
        return limsExtractRecordSampleList;
    }

    public void setLimsExtractRecordSampleList(List<LimsExtractRecordSample> limsExtractRecordSampleList) {
        this.limsExtractRecordSampleList = limsExtractRecordSampleList;
    }
}
