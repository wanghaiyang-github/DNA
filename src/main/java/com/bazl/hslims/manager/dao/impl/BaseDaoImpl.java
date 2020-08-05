package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.BaseDao;
import com.bazl.hslims.manager.model.vo.AbstractBaseVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/12/30.
 */
public class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T,PK> {

    @Autowired
    protected SqlSessionTemplate sqlSessionTemplate;

    public SqlSessionTemplate getSqlSessionTemplate() {
        if(this.sqlSessionTemplate==null){
            throw new RuntimeException("sqlSessionTemplate not to init!");
        }
        return sqlSessionTemplate;
    }

    @Override
    public String namespace() {
        throw new RuntimeException("children is not init namespace method.");
    }

    @Override
    public T selectById(PK id) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectByPrimaryKey", id);
    }

    @Override
    public List<T> selectList(T t) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectList", t);
    }

    public int selectCount(AbstractBaseVO<T> abstractBaseVO) {
        return this.getSqlSessionTemplate().selectOne(this.namespace() + ".selectCount", abstractBaseVO);
    }

    @Override
    public List<T> selectPaginationList(AbstractBaseVO<T> abstractBaseVO) {
        return this.getSqlSessionTemplate().selectList(this.namespace() + ".selectPaginationList", abstractBaseVO);
    }


    @Override
    public int insert(T t) {
        return this.getSqlSessionTemplate().insert(this.namespace()+".insert", t);
    }

    public int update(T t) {
        return this.getSqlSessionTemplate().update(this.namespace()+".updateByPrimaryKey", t);
    }

    public int deleteById(PK id) {
        return this.getSqlSessionTemplate().update(this.namespace()+".deleteByPrimaryKey", id);
    }

}
