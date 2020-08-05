package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.LimsIdentifyBookDao;
import com.bazl.hslims.manager.model.po.LimsIdentifyBook;
import com.bazl.hslims.utils.ListUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/1/11.
 */
@Repository
public class LimsIdentifyBookDaoImpl extends BaseDaoImpl<LimsIdentifyBook, Integer> implements LimsIdentifyBookDao {

    @Override
    public String namespace() {
        return LimsIdentifyBook.class.getName();
    }

    public LimsIdentifyBook selectByCaseId(Integer caseId) {
        List<LimsIdentifyBook> list = this.getSqlSessionTemplate().selectList(this.namespace() + ".selectByCaseId", caseId);
        return ListUtils.isNotNullAndEmptyList(list) ? list.get(0) : null;
    }

    public List<LimsIdentifyBook> selectIdentifyBookList(LimsIdentifyBook limsIdentifyBook) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectIdentifyBookList", limsIdentifyBook);
    }

    public int selectCount() {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectCount");
    }

}
