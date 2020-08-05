package com.bazl.hslims.manager.model.vo;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-07-16.
 */
public class CriminalPersonDataStats implements Serializable {

    private int orgId;
    private String orgName;

    private int collectedCount;
    private int pendingCount;
    private int delegatedCount;
    private int acceptedCount;
    private int refusedCount;

    private String songjianLv;

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public int getCollectedCount() {
        return collectedCount;
    }

    public void setCollectedCount(int collectedCount) {
        this.collectedCount = collectedCount;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public int getDelegatedCount() {
        return delegatedCount;
    }

    public void setDelegatedCount(int delegatedCount) {
        this.delegatedCount = delegatedCount;
    }

    public int getAcceptedCount() {
        return acceptedCount;
    }

    public void setAcceptedCount(int acceptedCount) {
        this.acceptedCount = acceptedCount;
    }

    public int getRefusedCount() {
        return refusedCount;
    }

    public void setRefusedCount(int refusedCount) {
        this.refusedCount = refusedCount;
    }

    public String getSongjianLv() {
        return songjianLv;
    }

    public void setSongjianLv(String songjianLv) {
        this.songjianLv = songjianLv;
    }
}
