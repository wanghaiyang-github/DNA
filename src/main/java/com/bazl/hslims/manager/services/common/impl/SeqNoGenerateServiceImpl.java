package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.dao.SeqNoGenerateDao;
import com.bazl.hslims.manager.model.po.DnaNoSeq;
import com.bazl.hslims.manager.services.common.SeqNoGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/1/7.
 */
@Service
public class SeqNoGenerateServiceImpl implements SeqNoGenerateService {

    @Autowired
    SeqNoGenerateDao seqNoGenerateDao;

    private String getNextVal(String code) {
        int nextVal = seqNoGenerateDao.selectNextVal(code);
        if (nextVal == 0)
        {
            DnaNoSeq dns = new DnaNoSeq();
            dns.setCode(code);
            seqNoGenerateDao.insert(dns);
            nextVal = seqNoGenerateDao.selectNextVal(code);
        }
        return nextVal + "";
    }

    @Override
    public String getNextCaseNoVal(String code) {
        return code + "-" + getNextVal(code);
    }

    @Override
    public String getNextConsignmentNoVal(String code) {
        return code + FullCharString(getNextVal(code), Constants.CONSIGNMENT_NO_LENGTH);
    }

    @Override
    public String getNextAcceptCaseNoVal(String code) {
        return code + FullCharString(getNextVal(code), Constants.PERSON_NO_LENGTH);
    }

    @Override
    public String getNextPersonNoVal(String code) {
        return code + "-" + Constants.PERSON_NO_PREFIX + FullCharString(getNextVal(code), Constants.PERSON_NO_LENGTH);
    }

    @Override
    public String getNextSampleNoVal(String code) {
        return code + "-" + Constants.SAMPLE_NO_PREFIX + FullCharString(getNextVal(code), Constants.SAMPLE_NO_LENGTH);
    }


    @Override
    public String getNextExtractTaskNoVal(String code) {
        code = code + Constants.TQ_TASK_PREFIX + "-";
        return code + getNextVal(code);
    }


    @Override
    public String getNextPcrTaskNoVal(String code) {
        code = code + Constants.PCR_TASK_PREFIX + "-";
        return code + getNextVal(code);
    }


    @Override
    public String getNextSyTaskNoVal(String code) {
        code = code + Constants.SY_TASK_PREFIX + "-";
        return code + getNextVal(code);
    }


    private String FullCharString(String src, int len)
    {
        int srcLen = src.length();
        if(srcLen >= len)
        {
            return src;
        }

        int needlen = len - srcLen;
        for (int i = 0; i < needlen; i++ )
        {
            src = "0" + src;
        }

        return src;
    }

    @Override
    public int deleteCode (String code) {
        return seqNoGenerateDao.deleteCode(code);
    }
}
