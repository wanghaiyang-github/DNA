package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.manager.model.po.MarkerInfo;
import com.bazl.hslims.manager.services.common.MarkerInfoService;
import com.bazl.hslims.utils.ListUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21.
 */
@Controller
@RequestMapping("/center/7")
public class GeneManagementController extends BaseController {

    @Autowired
    MarkerInfoService markerInfoService;

    @RequestMapping("/05.html")
    public ModelAndView into(HttpServletRequest request, MarkerInfo markerInfo) {
        markerInfo = getMarkerInfo(markerInfo);

        List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoList(markerInfo);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("markerInfoList",markerInfoList);
        modelAndView.addObject("markerInfo",markerInfo);
        modelAndView.setViewName("center/system/geneManagement");

        return modelAndView;
    }

    @RequestMapping(value="/selectRepeatQuery.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public int selectRepeatQuery(HttpServletRequest request, @RequestBody MarkerInfo markerInfo){
        int result = 0;

        try {
            result = markerInfoService.selectRepeatQuery(markerInfo);
        }catch (Exception e){
            logger.error("selectRepeatQuery error!",e);
        }

        return result;
    }

    @RequestMapping(value="/delete.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> deleteMarkerInfo(HttpServletRequest request,@RequestBody MarkerInfo markerInfo){
        markerInfo = getMarkerInfo(markerInfo);
        Map<String,Object> result = new HashMap<>();
        try{
            markerInfoService.deleteMarkerInfo(markerInfo);
            result.put("success",true);
        }catch (Exception e){
            logger.error("delete error",e);
            result.put("error",false);
        }
        return result;
    }

    @RequestMapping(value="/update.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateMarkerInfo(HttpServletRequest request,@RequestBody MarkerInfo markerInfo){
        markerInfo = getMarkerInfo(markerInfo);
        Map<String,Object> result = new HashMap<>();
        try{
            markerInfoService.updateMarkerInfo(markerInfo);
            result.put("success",true);
        }catch (Exception e){
            logger.error("update error",e);
            result.put("error",false);
        }
        return result;
    }


    @RequestMapping(value="/add.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addMarkerInfo(HttpServletRequest request,@RequestBody MarkerInfo markerInfo){
        markerInfo = getMarkerInfo(markerInfo);
        Map<String,Object> result = new HashMap<>();
        try{
            markerInfoService.addMarkerInfo(markerInfo);
            result.put("success",true);
        }catch (Exception e){
            logger.error("addMarkerInfo error",e);
            result.put("error",false);
        }
        return result;
    }

    public MarkerInfo getMarkerInfo(MarkerInfo markerInfo){

        if(markerInfo.getId() == null){
            markerInfo.setId(null);
        }else {
            markerInfo.setId(markerInfo.getId());
        }

        if(StringUtils.isBlank(markerInfo.getMarkerName())){
            markerInfo.setMarkerName(null);
        }else {
            markerInfo.setMarkerName(markerInfo.getMarkerName().trim());
        }

        if(StringUtils.isBlank(markerInfo.getMarkerAlias())){
            markerInfo.setMarkerAlias(null);
        }else {
            markerInfo.setMarkerAlias(markerInfo.getMarkerAlias());
        }

        if(markerInfo.getMarkerOrder() == null){
            markerInfo.setMarkerOrder(null);
        }else {
            markerInfo.setMarkerOrder(markerInfo.getMarkerOrder());
        }

        if(StringUtils.isBlank(markerInfo.getMarkerDesc())){
            markerInfo.setMarkerDesc(null);
        }else {
            markerInfo.setMarkerDesc(markerInfo.getMarkerDesc());
        }

        return markerInfo;
    }

}
