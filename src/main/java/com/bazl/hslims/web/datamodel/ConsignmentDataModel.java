package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public class ConsignmentDataModel implements Serializable {

    private String fromUrl;

    private String additionalFlag;

    private LimsConsignment consignment;

    private LimsCaseInformation caseInformation;

    private LimsCaseInfo caseInfo;

    private LimsEntrustmentInformation entrustmentInformation;

    private List<LimsPersonInfo> personInfoList;

    private List<LimsSampleInfo> sampleInfoList;

    private List<LimsPersonSample> personSampleList;

    private LimsPersonSample personSample;

    public LimsPersonSample getPersonSample() {
        return personSample;
    }

    public void setPersonSample(LimsPersonSample personSample) {
        this.personSample = personSample;
    }

    public LimsCaseInformation getCaseInformation() {
        return caseInformation;
    }

    public void setCaseInformation(LimsCaseInformation caseInformation) {
        this.caseInformation = caseInformation;
    }

    public LimsEntrustmentInformation getEntrustmentInformation() {
        return entrustmentInformation;
    }

    public void setEntrustmentInformation(LimsEntrustmentInformation entrustmentInformation) {
        this.entrustmentInformation = entrustmentInformation;
    }

    public List<LimsPersonSample> getPersonSampleList() {
        return personSampleList;
    }

    public void setPersonSampleList(List<LimsPersonSample> personSampleList) {
        this.personSampleList = personSampleList;
    }

    public String getFromUrl() {
        return fromUrl;
    }

    public void setFromUrl(String fromUrl) {
        this.fromUrl = fromUrl;
    }

    public String getAdditionalFlag() {
        return additionalFlag;
    }

    public void setAdditionalFlag(String additionalFlag) {
        this.additionalFlag = additionalFlag;
    }

    public LimsConsignment getConsignment() {
        return consignment;
    }

    public void setConsignment(LimsConsignment consignment) {
        this.consignment = consignment;
    }

    public LimsCaseInfo getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(LimsCaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public List<LimsPersonInfo> getPersonInfoList() {
        return personInfoList;
    }

    public void setPersonInfoList(List<LimsPersonInfo> personInfoList) {
        this.personInfoList = personInfoList;
    }

    public List<LimsSampleInfo> getSampleInfoList() {
        return sampleInfoList;
    }

    public void setSampleInfoList(List<LimsSampleInfo> sampleInfoList) {
        this.sampleInfoList = sampleInfoList;
    }
}
