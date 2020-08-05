package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsIdentifyBookNotice;

/**
 * Created by Administrator on 2017/6/1.
 */
public interface LimsIdentifyBookNoticeService {

    public int insert(LimsIdentifyBookNotice identifyBookNotice);

    public int deleteByCaseId(Integer caseId);

}
