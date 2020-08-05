package com.bazl.hslims.manager.model.vo;

import com.bazl.hslims.manager.model.po.QcPolluteRecord;

/**
 * Created by Administrator on 2017/2/16.
 */
public class QcPolluteRecordVO extends AbstractBaseVO<QcPolluteRecord> {


    public QcPolluteRecordVO(){
        this.entity = new QcPolluteRecord();
    }


    public QcPolluteRecordVO(QcPolluteRecord entity){
        this.entity = entity;
    }

}
