package com.bazl.hslims.manager.dao.impl;

import com.bazl.hslims.manager.dao.PanelInfoDao;
import com.bazl.hslims.manager.model.po.Panel;
import com.bazl.hslims.manager.model.po.PanelInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
@Repository
public class PanelInfoDaoImpl extends BaseDaoImpl implements PanelInfoDao {

    @Override
    public String namespace() {
        return PanelInfo.class.getName();
    }

    public List<PanelInfo> selectPanelInfoList(PanelInfo panelInfo){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectPanelInfoList", panelInfo);
    }

    public List<PanelInfo> selectQueryList(PanelInfo panelInfo){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectQueryList", panelInfo);
    }

    public int selectPanelInfoRepeatQuery(PanelInfo panelInfo){
        return this.getSqlSessionTemplate().selectOne(namespace() + ".selectPanelInfoRepeatQuery",panelInfo);
    }

    public List<Panel> selectPanelList(Panel panel){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectPanelList", panel);
    }

    public int addPanel(Panel panel){
        return this.getSqlSessionTemplate().insert(namespace() + ".addPanel", panel);
    }

    public int deletePanel(Panel panel){
        return this.getSqlSessionTemplate().delete(namespace() + ".deletePanel", panel);
    }

    public List<Panel> selectGenePanelList(Panel panel){
        return this.getSqlSessionTemplate().selectList(namespace() + ".selectGenePanelList",panel);
    }

}
