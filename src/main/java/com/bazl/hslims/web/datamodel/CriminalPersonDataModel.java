package com.bazl.hslims.web.datamodel;

import com.bazl.hslims.manager.model.po.CriminalPersonInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangliu on 2018/8/17.
 */
public class CriminalPersonDataModel implements Serializable {

    private CriminalPersonInfo criminalPerson;

    private List<CriminalPersonInfo> criminalPersonInfoList;

    private int sampleGeneId;

    public CriminalPersonInfo getCriminalPerson() {
        return criminalPerson;
    }

    public void setCriminalPerson(CriminalPersonInfo criminalPerson) {
        this.criminalPerson = criminalPerson;
    }

    public List<CriminalPersonInfo> getCriminalPersonInfoList() {
        return criminalPersonInfoList;
    }

    public void setCriminalPersonInfoList(List<CriminalPersonInfo> criminalPersonInfoList) {
        this.criminalPersonInfoList = criminalPersonInfoList;
    }

}
