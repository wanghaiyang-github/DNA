package com.bazl.hslims.web.datamodel;

/**
 * Created by Administrator on 2017/1/17.
 */
public class PcrSystemParams {

    public PcrSystemParams(){

    }


    private String pcrSytemCode;

    private String pcrSystemName;


    private String sampleType;

    private String primerUl;

    private String bufferUl;

    private String templateUl;

    private String ddwUl;

    public String getPcrSytemCode() {
        return pcrSytemCode;
    }

    public void setPcrSytemCode(String pcrSytemCode) {
        this.pcrSytemCode = pcrSytemCode;
    }

    public String getPcrSystemName() {
        return pcrSystemName;
    }

    public void setPcrSystemName(String pcrSystemName) {
        this.pcrSystemName = pcrSystemName;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getPrimerUl() {
        return primerUl;
    }

    public void setPrimerUl(String primerUl) {
        this.primerUl = primerUl;
    }

    public String getBufferUl() {
        return bufferUl;
    }

    public void setBufferUl(String bufferUl) {
        this.bufferUl = bufferUl;
    }

    public String getTemplateUl() {
        return templateUl;
    }

    public void setTemplateUl(String templateUl) {
        this.templateUl = templateUl;
    }

    public String getDdwUl() {
        return ddwUl;
    }

    public void setDdwUl(String ddwUl) {
        this.ddwUl = ddwUl;
    }
}
