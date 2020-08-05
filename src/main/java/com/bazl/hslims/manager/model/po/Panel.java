package com.bazl.hslims.manager.model.po;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/23.
 */
public class Panel {

    public Integer id;

    public Integer panelInfoId;

    public Integer markerInfoId;

    public String panelName;

    public String markerName;

    public Date createDatetime;

    private String geneInfo;

    private Integer geneInfoId;

    private String geneVal1;

    private String geneVal2;

    private String geneVal3;

    private String geneVal4;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPanelInfoId() {
        return panelInfoId;
    }

    public void setPanelInfoId(Integer panelInfoId) {
        this.panelInfoId = panelInfoId;
    }

    public Integer getMarkerInfoId() {
        return markerInfoId;
    }

    public void setMarkerInfoId(Integer markerInfoId) {
        this.markerInfoId = markerInfoId;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public Integer getGeneInfoId() {
        return geneInfoId;
    }

    public void setGeneInfoId(Integer geneInfoId) {
        this.geneInfoId = geneInfoId;
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

    public String getGeneVal3() { return geneVal3; }

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
