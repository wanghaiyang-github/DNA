package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.LimsPcrRecord;
import com.bazl.hslims.manager.model.po.LimsPcrRecordSample;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
public class PcrRecordDataModel implements Serializable {

    private LimsPcrRecord limsPcrRecord;

    private List<LimsPcrRecordSample> limsPcrRecordSampleList;

    public LimsPcrRecord getLimsPcrRecord() {
        return limsPcrRecord;
    }

    public void setLimsPcrRecord(LimsPcrRecord limsPcrRecord) {
        this.limsPcrRecord = limsPcrRecord;
    }

    public List<LimsPcrRecordSample> getLimsPcrRecordSampleList() {
        return limsPcrRecordSampleList;
    }

    public void setLimsPcrRecordSampleList(List<LimsPcrRecordSample> limsPcrRecordSampleList) {
        this.limsPcrRecordSampleList = limsPcrRecordSampleList;
    }
}
