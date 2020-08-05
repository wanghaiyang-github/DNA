package com.bazl.hslims.web.controller.center.equipment;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.EquipmentInfo;
import com.bazl.hslims.manager.model.po.EquipmentNameInfo;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.EquipmentInfoService;
import com.bazl.hslims.manager.services.common.EquipmentNameInfoService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/24.
 */
@Controller
@RequestMapping("/center/equipment")
public class EquipmentRegisterController {

    @Autowired
    DictService dictService;
    @Autowired
    EquipmentInfoService equipmentInfoService;
    @Autowired
    EquipmentNameInfoService equipmentNameInfoService;

    @RequestMapping("equipmentInfoList.html")
    public ModelAndView equipmentInfoList(HttpServletRequest request) {

        List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("equipmentInfoList", equipmentInfoList);
        modelAndView.setViewName("center/equipment/equipmentInfoList");
        return modelAndView;
    }

    @RequestMapping("equipmentRegister.html")
    public ModelAndView equipmentRegister(HttpServletRequest request, EquipmentInfo equipmentInfo, String operateType) {
        ModelAndView modelAndView = init();

        equipmentInfo = getEquipmentInfo(equipmentInfo);

        if (StringUtils.isBlank(operateType)) {
            modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        }else {
            equipmentInfo = equipmentInfoService.selectById(equipmentInfo.getId());
            modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        }

        modelAndView.addObject("equipmentInfo", equipmentInfo);
        modelAndView.setViewName("center/equipment/equipmentRegister");

        return modelAndView;
    }

    @RequestMapping("selectIsRepeat.html")
    @ResponseBody
    public int selectIsRepeat (HttpServletRequest request, String equipmentNo) {
        int result = 0;

        try {
            List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectEquipmentNo(equipmentNo);
            result = equipmentInfoList.size();
        }catch (Exception e) {
            result = -1;
        }

        return result;
    }

    @RequestMapping(value="/saveEquipmentInfo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveEquipmentInfo(HttpServletRequest request, @RequestBody EquipmentInfo equipmentInfo, String operateType) {

        equipmentInfo = getEquipmentInfo(equipmentInfo);

        Map<String, Object> result = new HashMap<>();
        try {
            if (operateType.equals(Constants.OPERATE_TYPE_ADD)) {
                equipmentInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                equipmentInfoService.insertEquipmentInfo(equipmentInfo);
            }else {
                equipmentInfo.setUpdatePerson(LimsSecurityUtils.getLoginName());
                equipmentInfoService.updateEquipmentInfo(equipmentInfo);
            }
            result.put("success", true);
        }catch(Exception ex){
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("delEquipmentInfo.html")
    @ResponseBody
    public Map<String, Object> delEquipmentInfo (HttpServletRequest request, Integer id) {
        Map<String, Object> result = new HashMap<>();

        try {
            equipmentInfoService.deleteById(id);
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("equipmentUselessList.html")
    public ModelAndView equipmentUselessList(HttpServletRequest request, EquipmentInfo equipmentInfo) {
        ModelAndView modelAndView = init();

        equipmentInfo = getEquipmentInfo(equipmentInfo);

        List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectEquipmentInfoList(equipmentInfo);

        modelAndView.addObject("equipmentInfo", equipmentInfo);
        modelAndView.addObject("equipmentInfoList", equipmentInfoList);
        modelAndView.setViewName("center/equipment/equipmentUselessList");

        return modelAndView;
    }

    @RequestMapping("equipmentUseless.html")
    @ResponseBody
    public Map<String, Object> equipmentUseless(HttpServletRequest request, Integer id) {
        Map<String, Object> result = new HashMap<>();

        try {
            equipmentInfoService.updateEquipmentStatus(id);
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();

        List<EquipmentNameInfo> equipmentNameInfoList = equipmentNameInfoService.selectAllList();
        List<DictItem> equipmentStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EQUIPMENT_STATUS);

        modelAndView.addObject("equipmentNameInfoList", equipmentNameInfoList);
        modelAndView.addObject("equipmentStatusList", equipmentStatusList);

        return modelAndView;
    }

    private EquipmentInfo getEquipmentInfo(EquipmentInfo equipmentInfo) {
        if (StringUtils.isBlank(equipmentInfo.getEquipmentNo())) {
            equipmentInfo.setEquipmentNo(null);
        }else {
            equipmentInfo.setEquipmentNo(equipmentInfo.getEquipmentNo().trim());
        }

        if (StringUtils.isBlank(equipmentInfo.getEquipmentSpecification())) {
            equipmentInfo.setEquipmentSpecification(null);
        }else {
            equipmentInfo.setEquipmentSpecification(equipmentInfo.getEquipmentSpecification());
        }

        if (StringUtils.isBlank(equipmentInfo.getEquipmentName())) {
            equipmentInfo.setEquipmentName(null);
        }else {
            equipmentInfo.setEquipmentName(equipmentInfo.getEquipmentName());
        }

        if (StringUtils.isBlank(equipmentInfo.getEquipmentStatus())) {
            equipmentInfo.setEquipmentStatus(null);
        }else {
            equipmentInfo.setEquipmentStatus(equipmentInfo.getEquipmentStatus());
        }

        if (equipmentInfo.getEquipmentNum() == null || equipmentInfo.getEquipmentNum() == 0) {
            equipmentInfo.setEquipmentNum(0);
        }else {
            equipmentInfo.setEquipmentNum(equipmentInfo.getEquipmentNum());
        }

        if (equipmentInfo.getUseBlueWarn() == null || equipmentInfo.getUseBlueWarn() == 0) {
            equipmentInfo.setUseBlueWarn(0);
        }else {
            equipmentInfo.setUseBlueWarn(equipmentInfo.getUseBlueWarn());
        }

        if (equipmentInfo.getUseRedWarn() == null || equipmentInfo.getUseRedWarn() == 0) {
            equipmentInfo.setUseRedWarn(0);
        }else {
            equipmentInfo.setUseRedWarn(equipmentInfo.getUseRedWarn());
        }

        if (equipmentInfo.getRepairBlueWarn() == null || equipmentInfo.getRepairBlueWarn() == 0) {
            equipmentInfo.setRepairBlueWarn(0);
        }else {
            equipmentInfo.setRepairBlueWarn(equipmentInfo.getRepairBlueWarn());
        }

        if (equipmentInfo.getRepairRedWarn() == null || equipmentInfo.getRepairRedWarn() == 0) {
            equipmentInfo.setRepairRedWarn(0);
        }else {
            equipmentInfo.setRepairRedWarn(equipmentInfo.getRepairRedWarn());
        }

        return equipmentInfo;
    }
}
