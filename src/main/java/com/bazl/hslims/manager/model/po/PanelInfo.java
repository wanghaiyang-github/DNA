package com.bazl.hslims.manager.model.po;

import java.util.Date;

public class PanelInfo {
    private Integer id;

    private String panelName;

    private String panelVersion;

    private String panelProducer;

    private String createPerson;

    private Date createDatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getPanelVersion() {
        return panelVersion;
    }

    public void setPanelVersion(String panelVersion) {
        this.panelVersion = panelVersion;
    }

    public String getPanelProducer() {
        return panelProducer;
    }

    public void setPanelProducer(String panelProducer) {
        this.panelProducer = panelProducer;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }
}