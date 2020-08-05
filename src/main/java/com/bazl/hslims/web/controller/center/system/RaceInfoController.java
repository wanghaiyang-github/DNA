package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.manager.model.po.LabUser;
import com.bazl.hslims.manager.model.po.RaceInfo;
import com.bazl.hslims.manager.services.common.LabUserService;
import com.bazl.hslims.manager.services.common.RaceInfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */
@Controller
@RequestMapping("/center/7")
public class RaceInfoController extends BaseController {

    @Autowired
    RaceInfoService raceInfoService;
    @Autowired
    LabUserService labUserService;
    @RequestMapping("07.html")
    public ModelAndView into(HttpServletRequest request, RaceInfo raceInfo) {

        raceInfo = getRaceInfo(raceInfo);

        List<RaceInfo> raceInfoList = raceInfoService.selectRaceInfoList(raceInfo);
        List<LabUser> labUserList = labUserService.selectAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("raceInfoList",raceInfoList);
        modelAndView.addObject("labUserList",labUserList);
        modelAndView.addObject("raceInfo",raceInfo);
        modelAndView.setViewName("center/system/raceInfo");
        return modelAndView;
    }

    @RequestMapping(value="/selectRaceInfoRepeatQuery.html")
    @ResponseBody
    public int selectRaceInfoRepeatQuery(HttpServletRequest request,String raceName) {
        int result = 0;

        RaceInfo raceInfo = new RaceInfo();
        raceInfo.setRaceName(raceName);

        try {
            List<RaceInfo> raceInfoList = raceInfoService.selectRaceInfoRepeatQueryList(raceInfo);
            result = raceInfoList.size();
        }catch (Exception e){
            logger.error("selectRaceInfoRepeatQuery error!",e);
        }
        return result;
    }

    @RequestMapping(value="/insertRaceInfo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertRaceInfo(HttpServletRequest request, @RequestBody RaceInfo raceInfo) {
        raceInfo = getRaceInfo(raceInfo);

        Map<String,Object> result = new HashMap<>();
        try {
            raceInfoService.insert(raceInfo);
            result.put("success",true);
        }catch (Exception e){
            logger.error("insertRaceInfo error!",e);
            result.put("error",false);
        }
        return result;
    }

    @RequestMapping(value = "/updateRaceInfo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateRaceInfo(HttpServletRequest request, @RequestBody RaceInfo raceInfo) {
        raceInfo = getRaceInfo(raceInfo);

        Map<String,Object> result = new HashMap<>();
        try {
            raceInfoService.update(raceInfo);
            result.put("success",true);
        }catch (Exception e){
            logger.error("updateRaceInfo error!",e);
            result.put("error",false);
        }
        return result;
    }

    @RequestMapping(value = "/deleteRaceInfo.html")
    @ResponseBody
    public Map<String,Object> deleteRaceInfo(HttpServletRequest request, Integer raceId) {

        Map<String,Object> result = new HashMap<>();
        try {
            raceInfoService.delete(raceId);
            result.put("success",true);
        }catch (Exception e){
            logger.error("deleteRaceInfo error!",e);
            result.put("error",false);
        }
        return result;
    }

    public RaceInfo getRaceInfo (RaceInfo raceInfo) {

        if (raceInfo.getId() == null) {
            raceInfo.setId(0);
        }else {
            raceInfo.setId(raceInfo.getId());
        }

        if (StringUtils.isBlank(raceInfo.getRaceName())) {
            raceInfo.setRaceName(null);
        }else {
            raceInfo.setRaceName(raceInfo.getRaceName().trim());
        }

        if (StringUtils.isBlank(raceInfo.getCreatePerson())) {
            raceInfo.setCreatePerson(null);
        }else {
            raceInfo.setCreatePerson(raceInfo.getCreatePerson().trim());
        }

        if (raceInfo.getCreateDatetime() == null) {
            raceInfo.setCreateDatetime(null);
        }else {
            raceInfo.setCreateDatetime(raceInfo.getCreateDatetime());
        }

        if (StringUtils.isBlank(raceInfo.getComments())) {
            raceInfo.setComments(null);
        }else {
            raceInfo.setComments(raceInfo.getComments().trim());
        }

        return raceInfo;
    }

}
