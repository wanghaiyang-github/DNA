package com.bazl.hslims.manager.model.po.dna;

import com.bazl.hslims.manager.model.vo.AbstractBaseVO;
import java.io.Serializable;
import java.util.Date;

public class ExternalInfo
        extends AbstractBaseVO<ExternalInfo>
        implements Serializable
{
    private String externalSysNo;
    private String caseNo;
    private String caseName;
    private Date createDateTime;
    private String createUser;
    private String sampleNo;
    private Date createDatetimeStart;
    private Date createDatetimeEnd;

    public String getExternalSysNo()
    {
        return this.externalSysNo;
    }

    public void setExternalSysNo(String externalSysNo)
    {
        this.externalSysNo = externalSysNo;
    }

    public String getCaseNo()
    {
        return this.caseNo;
    }

    public void setCaseNo(String caseNo)
    {
        this.caseNo = caseNo;
    }

    public String getCaseName()
    {
        return this.caseName;
    }

    public void setCaseName(String caseName)
    {
        this.caseName = caseName;
    }

    public Date getCreateDateTime()
    {
        return this.createDateTime;
    }

    public void setCreateDateTime(Date createDateTime)
    {
        this.createDateTime = createDateTime;
    }

    public String getCreateUser()
    {
        return this.createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getSampleNo()
    {
        return this.sampleNo;
    }

    public void setSampleNo(String sampleNo)
    {
        this.sampleNo = sampleNo;
    }

    public Date getCreateDatetimeStart()
    {
        return this.createDatetimeStart;
    }

    public void setCreateDatetimeStart(Date createDatetimeStart)
    {
        this.createDatetimeStart = createDatetimeStart;
    }

    public Date getCreateDatetimeEnd()
    {
        return this.createDatetimeEnd;
    }

    public void setCreateDatetimeEnd(Date createDatetimeEnd)
    {
        this.createDatetimeEnd = createDatetimeEnd;
    }
}
