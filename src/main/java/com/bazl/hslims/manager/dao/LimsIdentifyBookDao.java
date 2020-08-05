package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.LimsIdentifyBook;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
public interface LimsIdentifyBookDao extends BaseDao<LimsIdentifyBook, Integer> {

    public LimsIdentifyBook selectByCaseId(Integer caseId);

    public List<LimsIdentifyBook> selectIdentifyBookList(LimsIdentifyBook limsIdentifyBook);

    public int selectCount();
}
