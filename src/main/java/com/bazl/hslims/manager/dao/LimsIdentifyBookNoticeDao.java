package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsIdentifyBookNotice;

/**
 * Created by Administrator on 2017/6/1.
 */
public interface LimsIdentifyBookNoticeDao extends BaseDao<LimsIdentifyBookNotice, Integer> {

    public int deleteByCaseId(Integer caseId);

}
