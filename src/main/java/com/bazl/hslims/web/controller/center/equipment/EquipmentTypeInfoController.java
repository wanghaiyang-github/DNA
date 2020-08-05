package com.bazl.hslims.web.controller.center.equipment;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.EquipmentTypeInfo;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.EquipmentTypeInfoService;
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
 * Created by Administrator on 2017/10/25.
 */
@Controller
@RequestMapping("/center/equipment")
public class EquipmentTypeInfoController {

    @Autowired
    DictService dictService;
    @Autowired
    EquipmentTypeInfoService equipmentTypeInfoService;

    @RequestMapping("equipmentTypeList.html")
    public ModelAndView equipmentTypeList(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        List<EquipmentTypeInfo> equipmentTypeInfoList = equipmentTypeInfoService.selectAllList();

        modelAndView.addObject("equipmentTypeInfoList", equipmentTypeInfoList);
        modelAndView.setViewName("center/equipment/equipmentTypeInfoList");

        return modelAndView;
    }

    @RequestMapping("equipmentTypeInfo.html")
    public ModelAndView equipmentTypeInfo(HttpServletRequest request, EquipmentTypeInfo equipmentTypeInfo, String operateType) {
        ModelAndView modelAndView = init();

        equipmentTypeInfo = getEquipmentTypeInfo(equipmentTypeInfo);

        if (StringUtils.isBlank(operateType)) {
            modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        }else {
            equipmentTypeInfo = equipmentTypeInfoService.selectById(equipmentTypeInfo.getId());
            modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        }

        modelAndView.addObject("equipmentTypeInfo", equipmentTypeInfo);
        modelAndView.setViewName("center/equipment/equipmentTypeInfo");

        return modelAndView;
    }

    @RequestMapping(value="/saveEquipmentTypeInfo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveEquipmentInfo(HttpServletRequest request, @RequestBody EquipmentTypeInfo equipmentTypeInfo, String operateType) {

        equipmentTypeInfo = getEquipmentTypeInfo(equipmentTypeInfo);

        Map<String, Object> result = new HashMap<>();
        try {
            if (operateType.equals(Constants.OPERATE_TYPE_ADD)) {
                equipmentTypeInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                equipmentTypeInfoService.insertEquipmentTypeInfo(equipmentTypeInfo);
            }else {
                equipmentTypeInfo.setUpdatePerson(LimsSecurityUtils.getLoginName());
                equipmentTypeInfoService.updateEquipmentTypeInfo(equipmentTypeInfo);
            }
            result.put("success", true);
        }catch(Exception ex){
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("delEquipmentTypeInfo.html")
    @ResponseBody
    public Map<String, Object> delEquipmentTypeInfo(HttpServletRequest request, Integer id) {
        Map<String, Object> result = new HashMap<>();

        try {
            equipmentTypeInfoService.deleteById(id);
            result.put("success", true);
        }catch(Exception ex){
            result.put("success", false);
        }

        return result;
    }

    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();

        List<DictItem> experimentalStageList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXPERIMENTAL_STAGE);

        modelAndView.addObject("experimentalStageList", experimentalStageList);

        return modelAndView;
    }

    public EquipmentTypeInfo getEquipmentTypeInfo(EquipmentTypeInfo equipmentTypeInfo) {

        if (StringUtils.isBlank(equipmentTypeInfo.getEquipmentTypeNo())) {
            equipmentTypeInfo.setEquipmentTypeNo(null);
        }else {
            equipmentTypeInfo.setEquipmentTypeNo(equipmentTypeInfo.getEquipmentTypeNo().trim());
        }

        if (StringUtils.isBlank(equipmentTypeInfo.getEquipmentTypeName())) {
            equipmentTypeInfo.setEquipmentTypeName(null);
        }else {
            equipmentTypeInfo.setEquipmentTypeName(equipmentTypeInfo.getEquipmentTypeName().trim());
        }

        if (equipmentTypeInfo.getUseBlueWarn() == null || equipmentTypeInfo.getUseBlueWarn() == 0) {
            equipmentTypeInfo.setUseBlueWarn(0);
        }else {
            equipmentTypeInfo.setUseBlueWarn(equipmentTypeInfo.getUseBlueWarn());
        }

        if (equipmentTypeInfo.getUseRedWarn() == null || equipmentTypeInfo.getUseRedWarn() == 0) {
            equipmentTypeInfo.setUseRedWarn(0);
        }else {
            equipmentTypeInfo.setUseRedWarn(equipmentTypeInfo.getUseRedWarn());
        }

        if (equipmentTypeInfo.getRepairBlueWarn() == null || equipmentTypeInfo.getRepairBlueWarn() == 0) {
            equipmentTypeInfo.setRepairBlueWarn(0);
        }else {
            equipmentTypeInfo.setRepairBlueWarn(equipmentTypeInfo.getRepairBlueWarn());
        }

        if (equipmentTypeInfo.getRepairRedWarn() == null || equipmentTypeInfo.getRepairRedWarn() == 0) {
            equipmentTypeInfo.setRepairRedWarn(0);
        }else {
            equipmentTypeInfo.setRepairRedWarn(equipmentTypeInfo.getRepairRedWarn());
        }

        return equipmentTypeInfo;
    }

}
