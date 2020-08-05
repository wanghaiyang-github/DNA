package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.web.helper.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wangliu on 2019/5/6.
 */
public class SceneInfoVO implements Serializable {

    String investigationNo;
    String caseNo;
    String receptionNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    Date investDateFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    Date investDateTo;
    String invesPlace;
    Date occuDateFrom;
    Date occuDateTo;
    String caseType;
    String caseTypeCn;
    String investigators;

    public String getInvestigationNo() {
        return investigationNo;
    }

    public void setInvestigationNo(String investigationNo) {
        this.investigationNo = investigationNo;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getReceptionNo() {
        return receptionNo;
    }

    public void setReceptionNo(String receptionNo) {
        this.receptionNo = receptionNo;
    }

    public Date getInvestDateFrom() {
        return investDateFrom;
    }

    public void setInvestDateFrom(Date investDateFrom) {
        this.investDateFrom = investDateFrom;
    }

    public Date getInvestDateTo() {
        return investDateTo;
    }

    public void setInvestDateTo(Date investDateTo) {
        this.investDateTo = investDateTo;
    }

    public Date getOccuDateFrom() {
        return occuDateFrom;
    }

    public void setOccuDateFrom(Date occuDateFrom) {
        this.occuDateFrom = occuDateFrom;
    }

    public Date getOccuDateTo() {
        return occuDateTo;
    }

    public void setOccuDateTo(Date occuDateTo) {
        this.occuDateTo = occuDateTo;
    }

    public String getInvesPlace() {
        return invesPlace;
    }

    public void setInvesPlace(String invesPlace) {
        this.invesPlace = invesPlace;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseTypeCn() {
        return caseTypeCn;
    }

    public void setCaseTypeCn(String caseTypeCn) {
        this.caseTypeCn = caseTypeCn;
    }

    public String getInvestigators() {
        return investigators;
    }

    public void setInvestigators(String investigators) {
        this.investigators = investigators;
    }
}
