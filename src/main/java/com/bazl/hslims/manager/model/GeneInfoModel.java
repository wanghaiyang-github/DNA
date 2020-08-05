package com.bazl.hslims.manager.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/16.
 */
public class GeneInfoModel implements Serializable {

    private String locusName;

    private String geneVal1;

    private String geneVal2;

    private String geneVal3;

    private String geneVal4;

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName;
    }

    public String getGeneVal1() {
        return geneVal1;
    }

    public void setGeneVal1(String geneVal1) {
        this.geneVal1 = geneVal1;
    }

    public String getGeneVal2() {
        return geneVal2;
    }

    public void setGeneVal2(String geneVal2) {
        this.geneVal2 = geneVal2;
    }

    public String getGeneVal3() {
        return geneVal3;
    }

    public void setGeneVal3(String geneVal3) {
        this.geneVal3 = geneVal3;
    }

    public String getGeneVal4() {
        return geneVal4;
    }

    public void setGeneVal4(String geneVal4) {
        this.geneVal4 = geneVal4;
    }
}
