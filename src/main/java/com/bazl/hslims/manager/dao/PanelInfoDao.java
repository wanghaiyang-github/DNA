package com.bazl.hslims.manager.dao;

import com.bazl.hslims.manager.model.po.Panel;
import com.bazl.hslims.manager.model.po.PanelInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public interface PanelInfoDao extends BaseDao  {

    public List<PanelInfo> selectPanelInfoList(PanelInfo panelInfo);

    public List<PanelInfo> selectQueryList(PanelInfo panelInfo);

    public List<Panel> selectGenePanelList(Panel panel);

    public int selectPanelInfoRepeatQuery(PanelInfo panelInfo);

    public List<Panel> selectPanelList(Panel panel);

    public int addPanel(Panel panel);

    public int deletePanel(Panel panel);

}
