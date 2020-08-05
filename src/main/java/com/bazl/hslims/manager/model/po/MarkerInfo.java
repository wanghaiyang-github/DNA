package com.bazl.hslims.manager.model.po;

public class MarkerInfo {
    private Integer id;

    private Integer panelId;

    private String markerName;

    private String markerAlias;

    private Integer markerOrder;

    private String markerDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarkerAlias() {
        return markerAlias;
    }

    public void setMarkerAlias(String markerAlias) {
        this.markerAlias = markerAlias;
    }

    public String getMarkerDesc() {
        return markerDesc;
    }

    public void setMarkerDesc(String markerDesc) {
        this.markerDesc = markerDesc;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public Integer getMarkerOrder() {
        return markerOrder;
    }

    public void setMarkerOrder(Integer markerOrder) {
        this.markerOrder = markerOrder;
    }

    public Integer getPanelId() {
        return panelId;
    }

    public void setPanelId(Integer panelId) {
        this.panelId = panelId;
    }
}