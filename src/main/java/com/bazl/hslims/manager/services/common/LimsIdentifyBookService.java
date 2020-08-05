package com.bazl.hslims.manager.services.common;

import com.bazl.hslims.manager.model.po.LimsIdentifyBook;

import java.util.List;

/**
 * Created by Administrator on 2017/1/13.
 */
public interface LimsIdentifyBookService {

    public int generatReport(Integer caseId);

    public LimsIdentifyBook selectByCaseId(Integer caseId);

    public int updateStatus(Integer caseId);

    public int updateReceiveStatus(Integer caseId);

    public List<LimsIdentifyBook> selectIdentifyBookList(LimsIdentifyBook limsIdentifyBook);

    public int updateIdentifyBookPath(LimsIdentifyBook limsIdentifyBook);

    public int selectCount();

}
