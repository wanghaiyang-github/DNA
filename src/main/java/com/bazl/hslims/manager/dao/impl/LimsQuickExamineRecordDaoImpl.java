package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsQuickExamineRecordDao;
import com.bazl.hslims.manager.model.po.LimsQuickExamineRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
@Repository
public class LimsQuickExamineRecordDaoImpl extends BaseDaoImpl<LimsQuickExamineRecord,Integer> implements LimsQuickExamineRecordDao {

    public String namespace() { return LimsQuickExamineRecord.class.getName(); }

    public int selectCountByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectCountByCaseId", caseId);
    }

    public List<LimsQuickExamineRecord> selectListByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectListByCaseId", caseId);
    }

}
