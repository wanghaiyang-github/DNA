package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.manager.model.po.MarkerInfo;
import com.bazl.hslims.manager.model.po.Panel;
import com.bazl.hslims.manager.model.po.PanelInfo;
import com.bazl.hslims.manager.services.common.MarkerInfoService;
import com.bazl.hslims.manager.services.common.PanelInfoService;
import com.bazl.hslims.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21.
 */
@Controller
@RequestMapping("/center/7")
public class PanelManagementController extends BaseController {

    @Autowired
    MarkerInfoService markerInfoService;
    @Autowired
    PanelInfoService panelInfoService;

    @RequestMapping("/06.html")
    public ModelAndView into(HttpServletRequest request, PanelInfo panelInfo) {

        panelInfo = getPanelInfo(panelInfo);

        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("panelInfoList",panelInfoList);
        modelAndView.addObject("panelInfo",panelInfo);
        modelAndView.setViewName("center/system/panelQuery");

        return modelAndView;
    }

    @RequestMapping("/viewMarkerList.html")
    public ModelAndView viewMarkerList(HttpServletRequest request,Panel panel,String view){
        panel = getPanel(panel);

        PanelInfo panelInfo = new PanelInfo();
        panelInfo = getPanelInfo(panelInfo);
        panelInfo.setId(panel.getPanelInfoId());
        panelInfo.setPanelName(panel.getPanelName());
        List<PanelInfo> panelInfoList = panelInfoService.selectQueryList(panelInfo);

        panelInfo.setPanelName(panelInfoList.get(0).getPanelName());
        panelInfo.setPanelVersion(panelInfoList.get(0).getPanelVersion());
        panelInfo.setPanelProducer(panelInfoList.get(0).getPanelProducer());
        panelInfo.setCreateDatetime(panelInfoList.get(0).getCreateDatetime());

        MarkerInfo markerInfo = new MarkerInfo();
        List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoList(markerInfo);

        List<Panel> panelList = panelInfoService.selectPanelList(panel);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("view",view);
        modelAndView.addObject("panelInfo",panelInfo);
        modelAndView.addObject("markerInfoList",markerInfoList);
        modelAndView.addObject("panelList",panelList);
        modelAndView.setViewName("center/system/viewMarkerList");
        return modelAndView;
    }

    @RequestMapping(value="/selectPanelInfoRepeatQuery.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public int selectPanelInfoRepeatQuery(HttpServletRequest request, @RequestBody PanelInfo panelInfo){
        panelInfo = getPanelInfo(panelInfo);

        int result = 0;
        try {
            result = panelInfoService.selectPanelInfoRepeatQuery(panelInfo);
        }catch (Exception e){
            logger.error("selectPanelInfoRepeatQuery error!",e);
        }

        return result;
    }

    @RequestMapping(value="/selectPanelRepeatQuery.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public int selectPanelRepeatQuery(HttpServletRequest request, @RequestBody Panel panel){
        panel = getPanel(panel);

        int result = 0;
        try {
            List<Panel> panelList = panelInfoService.selectPanelList(panel);
            result = panelList.size();
        }catch (Exception e){
            logger.error("selectPanelRepeatQuery error!",e);
        }

        return result;
    }

    @RequestMapping(value="/deletePanelInfo.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deletePanelInfo(HttpServletRequest request,@RequestBody PanelInfo panelInfo){
        panelInfo = getPanelInfo(panelInfo);

        Map<String,Object> result = new HashMap<>();
        try{
            panelInfoService.deletePanelInfo(panelInfo);
            result.put("success",true);
        }catch (Exception e){
            logger.error("deletePanelInfo error",e);
            result.put("error",false);
        }

        return result;
    }

    @RequestMapping(value="/deletePanel.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deletePanel(HttpServletRequest request,@RequestBody Panel panel){
        panel = getPanel(panel);

        Map<String,Object> result = new HashMap<>();
        try{
            panelInfoService.deletePanel(panel);
            result.put("success",true);
        }catch (Exception e){
            logger.error("deletePanel error",e);
            result.put("error",false);
        }

        return result;
    }

   @RequestMapping(value="/addPanelInfo.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addPanelInfo(HttpServletRequest request,@RequestBody PanelInfo panelInfo){
        panelInfo = getPanelInfo(panelInfo);;

        Map<String,Object> result = new HashMap<>();
        try{
            panelInfoService.addPanelInfo(panelInfo);
            result.put("success",true);
        }catch (Exception e){
            logger.error("addPanelInfo error",e);
            result.put("error",false);
        }
        return result;
    }

    @RequestMapping(value="/addPanel.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addMarkerInfo(HttpServletRequest request,@RequestBody Panel panel){
        panel = getPanel(panel);

        String markerName = panel.getMarkerName();
        List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoByName(markerName);
        panel.setMarkerInfoId(markerInfoList.get(0).getId());

        Map<String,Object> result = new HashMap<>();
        try{
            panelInfoService.addPanel(panel);
            result.put("success",true);
        }catch (Exception e){
            logger.error("addPanel error",e);
            result.put("error",false);
        }
        return result;
    }

    public PanelInfo getPanelInfo(PanelInfo panelInfo){

        if (panelInfo.getId() == null){
            panelInfo.setId(null);
        }else {
            panelInfo.setId(panelInfo.getId());
        }

        if(panelInfo.getPanelName() !=null && !"".equals(panelInfo.getPanelName())){
            panelInfo.setPanelName(panelInfo.getPanelName());
        }else {
            panelInfo.setPanelName(null);
        }

        if(StringUtils.isBlank(panelInfo.getCreatePerson())){
            panelInfo.setCreatePerson(null);
        }else {
            panelInfo.setCreatePerson(panelInfo.getCreatePerson());
        }

        if(StringUtils.isBlank(panelInfo.getPanelProducer())){
            panelInfo.setPanelProducer(null);
        }else {
            panelInfo.setPanelProducer(panelInfo.getPanelProducer());
        }

        if(StringUtils.isBlank(panelInfo.getPanelVersion())){
            panelInfo.setPanelVersion(null);
        }else {
            panelInfo.setPanelVersion(panelInfo.getPanelVersion());
        }

        return panelInfo;
    }

    public Panel getPanel(Panel panel){

        if (panel.getId() == null){
            panel.setId(null);
        }else {
            panel.setId(panel.getId());
        }

        if(panel.getPanelInfoId() == null){
            panel.setPanelInfoId(null);
        }else {
            panel.setPanelInfoId(panel.getPanelInfoId());
        }

        if(panel.getMarkerInfoId() == null){
            panel.setMarkerInfoId(null);
        }else {
            panel.setMarkerInfoId(panel.getMarkerInfoId());
        }

        if(StringUtils.isBlank(panel.getPanelName())){
            panel.setPanelName(null);
        }else {
            panel.setPanelName(panel.getPanelName());
        }

        if(StringUtils.isBlank(panel.getMarkerName())){
            panel.setMarkerName(null);
        }else {
            panel.setMarkerName(panel.getMarkerName());
        }

        if(panel.getCreateDatetime() == null){
            panel.setCreateDatetime(null);
        }else {
            panel.setCreateDatetime(panel.getCreateDatetime());
        }

        return panel;
    }

}
