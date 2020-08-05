package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.DnaNoSeq;

/**
 * Created by Administrator on 2017/1/7.
 */
public interface SeqNoGenerateDao extends BaseDao<DnaNoSeq, String> {

    public int selectNextVal(String code);

    public int deleteCode(String code);


}
