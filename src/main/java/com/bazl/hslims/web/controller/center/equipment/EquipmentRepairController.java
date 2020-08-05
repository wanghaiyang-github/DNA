package com.bazl.hslims.web.controller.center.equipment;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.EquipmentInfo;
import com.bazl.hslims.manager.model.po.EquipmentNameInfo;
import com.bazl.hslims.manager.model.po.EquipmentRepairInfo;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.EquipmentInfoService;
import com.bazl.hslims.manager.services.common.EquipmentNameInfoService;
import com.bazl.hslims.manager.services.common.EquipmentRepairInfoService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/25.
 */
@Controller
@RequestMapping("/center/equipment")
public class EquipmentRepairController {

    @Autowired
    DictService dictService;
    @Autowired
    EquipmentInfoService equipmentInfoService;
    @Autowired
    EquipmentRepairInfoService equipmentRepairInfoService;
    @Autowired
    EquipmentNameInfoService equipmentNameInfoService;

    @RequestMapping("equipmentRepairList.html")
    public ModelAndView equipmentInfoList(HttpServletRequest request) {

        List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("equipmentInfoList", equipmentInfoList);
        modelAndView.setViewName("center/equipment/equipmentRepairList");
        return modelAndView;
    }

    @RequestMapping("equipmentRepair.html")
    public ModelAndView equipmentRepair(HttpServletRequest request, EquipmentRepairInfo equipmentRepairInfo) {
        ModelAndView modelAndView = new ModelAndView();

        equipmentRepairInfo = getEquipmentRepairInfo(equipmentRepairInfo);

        EquipmentInfo equipmentInfo = equipmentInfoService.selectById(equipmentRepairInfo.getEquipmentInfoId());

        List<EquipmentNameInfo> equipmentNameInfoList = equipmentNameInfoService.selectAllList();

        List<EquipmentRepairInfo> equipmentRepairInfoList = equipmentRepairInfoService.selectEquipmentRepairInfoList(equipmentRepairInfo);

        modelAndView.addObject("equipmentInfo", equipmentInfo);
        modelAndView.addObject("equipmentRepairInfo", equipmentRepairInfo);
        modelAndView.addObject("equipmentNameInfoList", equipmentNameInfoList);
        modelAndView.addObject("equipmentRepairInfoList", equipmentRepairInfoList);
        modelAndView.setViewName("center/equipment/equipmentRepair");
        return modelAndView;
    }

    @RequestMapping(value = "/saveEquipmentRepairInfo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveEquipmentInfo(HttpServletRequest request, @RequestBody EquipmentRepairInfo equipmentRepairInfo) {
        Map<String, Object> result = new HashMap<>();

        equipmentRepairInfo = getEquipmentRepairInfo(equipmentRepairInfo);

        try {
            equipmentRepairInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
            equipmentRepairInfoService.insert(equipmentRepairInfo);
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    public EquipmentRepairInfo getEquipmentRepairInfo(EquipmentRepairInfo equipmentRepairInfo) {

        if (StringUtils.isBlank(equipmentRepairInfo.getRepairTime())) {
            equipmentRepairInfo.setEquipmentRepairTime(new Date());
        }else {
            equipmentRepairInfo.setEquipmentRepairTime(DateUtils.stringToDate(equipmentRepairInfo.getRepairTime(), "yyyy-MM-dd hh:mm"));
        }

        if (StringUtils.isBlank(equipmentRepairInfo.getFailureCause())) {
            equipmentRepairInfo.setFailureCause(null);
        }else {
            equipmentRepairInfo.setFailureCause(equipmentRepairInfo.getFailureCause());
        }

        if (StringUtils.isBlank(equipmentRepairInfo.getEquipmentRepairPerson())) {
            equipmentRepairInfo.setEquipmentRepairPerson(null);
        }else {
            equipmentRepairInfo.setEquipmentRepairPerson(equipmentRepairInfo.getEquipmentRepairPerson().trim());
        }

        return equipmentRepairInfo;
    }
}
