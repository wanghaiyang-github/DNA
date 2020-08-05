package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.vo.AbstractBaseVO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */
public interface BaseDao<T, PK extends Serializable> {

    /**
     * 每个继承该接口的dao，必须实现该接口，该接口主要针对mybatis的namespace
     * @return
     */
    String namespace();

    public T selectById(PK id) ;

    public List<T> selectList(T t);

    public int selectCount(AbstractBaseVO<T> abstractBaseVO);
    public List<T> selectPaginationList(AbstractBaseVO<T> abstractBaseVO);

    public int insert(T t) ;

    public int update(T t) ;

    public int deleteById(PK id) ;

}
