package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.dao.LimsIdentifyBookDao;
import com.bazl.hslims.manager.model.po.LimsIdentifyBook;
import com.bazl.hslims.manager.services.common.LimsIdentifyBookService;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
@Service
public class LimsIdentifyBookServiceImpl implements LimsIdentifyBookService {

    @Autowired
    LimsIdentifyBookDao limsIdentifyBookDao;

    @Transactional
    public int generatReport(Integer caseId) {
        LimsIdentifyBook book = limsIdentifyBookDao.selectByCaseId(caseId);

        book.setStatus(Constants.IDENTIFY_BOOK_NEED_SIGN);
        book.setStatusName(Constants.IDENTIFY_BOOK_NEED_SIGN_NAME);
        book.setUpdatePersonName(LimsSecurityUtils.getLoginName());

        return limsIdentifyBookDao.update(book);
    }

    public LimsIdentifyBook selectByCaseId(Integer caseId) {
        return limsIdentifyBookDao.selectByCaseId(caseId);
    }

    @Transactional
    public int updateStatus(Integer caseId) {
        LimsIdentifyBook book = limsIdentifyBookDao.selectByCaseId(caseId);

        book.setStatus(Constants.IDENTIFY_BOOK_SIGNED);
        book.setStatusName(Constants.IDENTIFY_BOOK_SIGNED_NAME);
        book.setUpdatePersonName(LimsSecurityUtils.getLoginName());
        book.setSignedPerson(LimsSecurityUtils.getLoginName());
        book.setSignedDatetime(new Date());
        return limsIdentifyBookDao.update(book);
    }

    @Transactional
    public int updateReceiveStatus(Integer caseId) {
        LimsIdentifyBook book = limsIdentifyBookDao.selectByCaseId(caseId);

        book.setStatus(Constants.IDENTIFY_BOOK_FETCHED);
        book.setStatusName(Constants.IDENTIFY_BOOK_FETCHED_NAME);
        book.setUpdatePersonName(LimsSecurityUtils.getLoginName());
        book.setFetchedBy(LimsSecurityUtils.getLoginName());
        book.setFetchedDatetime(new Date());
        book.setSendNoticePerson(LimsSecurityUtils.getLoginName());
        book.setSendNoticeDatetime(new Date());

        return limsIdentifyBookDao.update(book);
    }

    public List<LimsIdentifyBook> selectIdentifyBookList(LimsIdentifyBook limsIdentifyBook) {
        return limsIdentifyBookDao.selectIdentifyBookList(limsIdentifyBook);
    }

    public int updateIdentifyBookPath(LimsIdentifyBook limsIdentifyBook) {

        limsIdentifyBook.setUpdatePersonName(LimsSecurityUtils.getLoginName());
        return limsIdentifyBookDao.update(limsIdentifyBook);
    }

    public int selectCount() {
        return limsIdentifyBookDao.selectCount();
    }

}
