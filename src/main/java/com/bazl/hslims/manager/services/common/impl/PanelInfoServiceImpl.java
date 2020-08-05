package com.bazl.hslims.manager.services.common.impl;

import com.bazl.hslims.manager.dao.PanelInfoDao;
import com.bazl.hslims.manager.model.po.Panel;
import com.bazl.hslims.manager.model.po.PanelInfo;
import com.bazl.hslims.manager.services.common.PanelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
@Service
public class PanelInfoServiceImpl implements PanelInfoService {

    @Autowired
    PanelInfoDao panelInfoDao;

    public List<PanelInfo> selectPanelInfoList(PanelInfo panelInfo){
        return panelInfoDao.selectPanelInfoList(panelInfo);
    }

    public List<PanelInfo> selectQueryList(PanelInfo panelInfo){
        return panelInfoDao.selectQueryList(panelInfo);
    }

    public List<Panel> selectGenePanelList(Panel panel){
        return panelInfoDao.selectGenePanelList(panel);
    }

    public int selectPanelInfoRepeatQuery(PanelInfo panelInfo){
        return panelInfoDao.selectPanelInfoRepeatQuery(panelInfo);
    }

    public int addPanelInfo(PanelInfo panelInfo){
        int result = 0;
        result = panelInfoDao.insert(panelInfo);
        return result;
    }

    public int deletePanelInfo(PanelInfo panelInfo){
        int result = 0;
        result = panelInfoDao.deleteById(panelInfo.getId());
        return result;
    }

    public List<Panel> selectPanelList(Panel panel){
        return panelInfoDao.selectPanelList(panel);
    }

    public int addPanel(Panel panel){
        return panelInfoDao.addPanel(panel);
    }

    public int deletePanel(Panel panel) {
        return panelInfoDao.deletePanel(panel);
    }

}
