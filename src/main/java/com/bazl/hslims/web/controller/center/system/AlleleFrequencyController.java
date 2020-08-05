package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.AlleleFrequency;
import com.bazl.hslims.manager.model.po.MarkerInfo;
import com.bazl.hslims.manager.model.po.RaceInfo;
import com.bazl.hslims.manager.services.common.AlleleFrequencyService;
import com.bazl.hslims.manager.services.common.MarkerInfoService;
import com.bazl.hslims.manager.services.common.RaceInfoService;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/22.
 */
@Controller
@RequestMapping("/center/7")
public class AlleleFrequencyController extends BaseController {

    @Autowired
    MarkerInfoService markerInfoService;
    @Autowired
    AlleleFrequencyService alleleFrequencyService;
    @Autowired
    RaceInfoService raceInfoService;

    @RequestMapping("08.html")
    public ModelAndView intoAllele(HttpServletRequest request, AlleleFrequency alleleFrequency, PageInfo pageInfo) {
        alleleFrequency = getAlleleFrequency(alleleFrequency);

        MarkerInfo markerInfo = new MarkerInfo();
        List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoList(markerInfo);
        RaceInfo raceInfo = new RaceInfo();
        List<RaceInfo> raceInfoList = raceInfoService.selectRaceInfoList(raceInfo);

        List<AlleleFrequency> alleleFrequencyList = alleleFrequencyService.selectAlleleFrequencyList(alleleFrequency, pageInfo);
        int totalCnt = alleleFrequencyService.selectAlleleFrequencyCount(alleleFrequency);

        for (AlleleFrequency af:alleleFrequencyList){
            float frequency = af.getAlleleFrequency();
            DecimalFormat fnum  = new DecimalFormat("##0.0000");
            af.setFrequency(fnum.format(frequency));
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("alleleFrequencyList",alleleFrequencyList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("markerInfoList",markerInfoList);
        modelAndView.addObject("raceInfoList",raceInfoList);
        modelAndView.addObject("alleleFrequency",alleleFrequency);
        modelAndView.setViewName("center/system/alleleFrequency");
        return modelAndView;
    }

    @RequestMapping(value = "/selectAlleleRepeatQuery.html")
    @ResponseBody
    public int selectAlleleRepeatQuery(HttpServletRequest request,String markerName, String alleleName,String raceName){
        int result = 0;
        AlleleFrequency alleleFrequency = new AlleleFrequency();
        alleleFrequency.setMarkerName(markerName);
        alleleFrequency.setAlleleName(alleleName);
        alleleFrequency.setRaceName(raceName);
        try {
            List<AlleleFrequency> alleleFrequencyList = alleleFrequencyService.selectAlleleFrequencyRepeatList(alleleFrequency);
            result = alleleFrequencyList.size();
        }catch (Exception e){
            logger.error("selectAlleleRepeatQuery error!",e);
        }
        return result;
    }

    @RequestMapping(value="/insertAlleleFrequency.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> insertAlleleFrequency(HttpServletRequest request, @RequestBody AlleleFrequency alleleFrequency){
        alleleFrequency.setCreatePerson(LimsSecurityUtils.getLoginName());
        alleleFrequency.setAlleleFrequency(alleleFrequency.getAlleleFrequency());
        alleleFrequency = getAlleleFrequency(alleleFrequency);
        RaceInfo raceInfo = new RaceInfo();
        raceInfo.setRaceName(alleleFrequency.getRaceName());
        List<RaceInfo> raceInfoList = raceInfoService.selectRaceInfoList(raceInfo);
        if (ListUtils.isNotNullAndEmptyList(raceInfoList))
            alleleFrequency.setRaceId(raceInfoList.get(0).getId());

        Map<String,Object> result = new HashMap<>();
        try {
            alleleFrequencyService.insert(alleleFrequency);
            result.put("success",true);
        }catch (Exception e){
            logger.error("insertAlleleFrequency error!",e);
            result.put("error",false);
        }
        return result;
    }

    @RequestMapping(value = "/updateAlleleFrequency.html",method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateAlleleFrequency(HttpServletRequest request, @RequestBody AlleleFrequency alleleFrequency){

        Map<String,Object> result = new HashMap<>();
        try {
            alleleFrequencyService.update(alleleFrequency);
            result.put("success",true);
        }catch (Exception e){
            logger.error("insertAlleleFrequency error!",e);
            result.put("error",false);
        }

        return result;
    }

    @RequestMapping(value = "/deleteAlleleFrequency.html")
    @ResponseBody
    public Map<String,Object> deleteAlleleFrequency(HttpServletRequest request, Integer id){

        Map<String,Object> result = new HashMap<>();
        try {
            alleleFrequencyService.delete(id);
            result.put("success",true);
        }catch (Exception e){
            logger.error("insertAlleleFrequency error!",e);
            result.put("error",false);
        }

        return result;
    }

    public AlleleFrequency getAlleleFrequency(AlleleFrequency alleleFrequency) {

        if (StringUtils.isBlank(alleleFrequency.getRaceName())) {
            alleleFrequency.setRaceName(null);
        }else {
            alleleFrequency.setRaceName(alleleFrequency.getRaceName());
        }

        if (StringUtils.isBlank(alleleFrequency.getMarkerName())) {
            alleleFrequency.setMarkerName(null);
        }else {
            alleleFrequency.setMarkerName(alleleFrequency.getMarkerName().trim());
        }

        if (StringUtils.isBlank(alleleFrequency.getAlleleName())) {
            alleleFrequency.setAlleleName(null);
        }else {
            alleleFrequency.setAlleleName(alleleFrequency.getAlleleName().trim());
        }

        if (StringUtils.isBlank(alleleFrequency.getFrequency())) {
            alleleFrequency.setAlleleFrequency(0);
        }else {
            alleleFrequency.setAlleleFrequency(Float.parseFloat(alleleFrequency.getFrequency().trim()));
        }

        if (StringUtils.isBlank(alleleFrequency.getCreatePerson())) {
            alleleFrequency.setCreatePerson(null);
        }else {
            alleleFrequency.setCreatePerson(alleleFrequency.getCreatePerson().trim());
        }

        return alleleFrequency;
    }

}
