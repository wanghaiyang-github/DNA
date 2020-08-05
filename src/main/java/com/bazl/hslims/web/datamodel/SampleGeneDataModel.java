package com.bazl.hslims.web.datamodel;


import com.bazl.hslims.manager.model.GeneInfoModel;

import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */
public class SampleGeneDataModel {

    private String barcode;

    private String geneType;

    private List<GeneInfoModel> geneModelList1;

    private List<GeneInfoModel> geneModelList2;

    private List<GeneInfoModel> geneModelList3;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    public List<GeneInfoModel> getGeneModelList1() {
        return geneModelList1;
    }

    public void setGeneModelList1(List<GeneInfoModel> geneModelList1) {
        this.geneModelList1 = geneModelList1;
    }

    public List<GeneInfoModel> getGeneModelList2() {
        return geneModelList2;
    }

    public void setGeneModelList2(List<GeneInfoModel> geneModelList2) {
        this.geneModelList2 = geneModelList2;
    }

    public List<GeneInfoModel> getGeneModelList3() {
        return geneModelList3;
    }

    public void setGeneModelList3(List<GeneInfoModel> geneModelList3) {
        this.geneModelList3 = geneModelList3;
    }
}
