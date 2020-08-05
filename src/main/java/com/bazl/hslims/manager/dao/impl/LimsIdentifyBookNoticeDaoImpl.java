package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsIdentifyBookNoticeDao;
import com.bazl.hslims.manager.model.po.LimsIdentifyBookNotice;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/6/1.
 */
@Repository
public class LimsIdentifyBookNoticeDaoImpl extends BaseDaoImpl<LimsIdentifyBookNotice, Integer> implements LimsIdentifyBookNoticeDao {

    public String namespace() { return LimsIdentifyBookNotice.class.getName(); }

    public int deleteByCaseId(Integer caseId) {
        return this.getSqlSessionTemplate().delete(this.namespace() + ".deleteByCaseId", caseId);
    }

}
