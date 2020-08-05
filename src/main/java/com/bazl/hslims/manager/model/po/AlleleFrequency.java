package com.bazl.hslims.manager.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/22.
 */
public class AlleleFrequency implements Serializable {

    private Integer id;

    private Integer raceId;

    private String raceName;

    private String markerName;

    private String alleleName;

    private float alleleFrequency;

    private String frequency;

    private String createPerson;

    private Date createDatetime;

    private int offset;

    private int rows;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }

    public String getAlleleName() {
        return alleleName;
    }

    public void setAlleleName(String alleleName) {
        this.alleleName = alleleName;
    }

    public float getAlleleFrequency() {
        return alleleFrequency;
    }

    public void setAlleleFrequency(float alleleFrequency) {
        this.alleleFrequency = alleleFrequency;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
