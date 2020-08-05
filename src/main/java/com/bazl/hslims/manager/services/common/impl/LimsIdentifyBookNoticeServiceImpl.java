package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.LimsIdentifyBookNoticeDao;
import com.bazl.hslims.manager.model.po.LimsIdentifyBookNotice;
import com.bazl.hslims.manager.services.common.LimsIdentifyBookNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/6/1.
 */
@Service
public class LimsIdentifyBookNoticeServiceImpl implements LimsIdentifyBookNoticeService {

    @Autowired
    LimsIdentifyBookNoticeDao identifyBookNoticeDao;

    public int insert(LimsIdentifyBookNotice identifyBookNotice){
        return identifyBookNoticeDao.insert(identifyBookNotice);
    }

    public int deleteByCaseId(Integer caseId){
        return identifyBookNoticeDao.deleteByCaseId(caseId);
    };

}
