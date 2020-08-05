package com.bazl.hslims.manager.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019-04-30.
 */
public class XkFeedbackCaseInfo implements Serializable {

    private int caseId;
    private String caseStatus;
    private int consignmentId;
    private String consignmentNo;
    private String caseXkNo;
    private String delegateOrg;
    private String delegateOrgName;
    private Date delegateDatetime;

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public int getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(int consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getCaseXkNo() {
        return caseXkNo;
    }

    public void setCaseXkNo(String caseXkNo) {
        this.caseXkNo = caseXkNo;
    }

    public String getDelegateOrg() {
        return delegateOrg;
    }

    public void setDelegateOrg(String delegateOrg) {
        this.delegateOrg = delegateOrg;
    }

    public String getDelegateOrgName() {
        return delegateOrgName;
    }

    public void setDelegateOrgName(String delegateOrgName) {
        this.delegateOrgName = delegateOrgName;
    }

    public Date getDelegateDatetime() {
        return delegateDatetime;
    }

    public void setDelegateDatetime(Date delegateDatetime) {
        this.delegateDatetime = delegateDatetime;
    }
}
