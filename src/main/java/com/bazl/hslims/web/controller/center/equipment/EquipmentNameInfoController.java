package com.bazl.hslims.web.controller.center.equipment;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.EquipmentNameInfo;
import com.bazl.hslims.manager.model.po.EquipmentTypeInfo;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.EquipmentNameInfoService;
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
public class EquipmentNameInfoController {

    @Autowired
    DictService dictService;
    @Autowired
    EquipmentTypeInfoService equipmentTypeInfoService;
    @Autowired
    EquipmentNameInfoService equipmentNameInfoService;

    @RequestMapping("equipmentNameList.html")
    public ModelAndView equipmentTypeList(HttpServletRequest request, EquipmentNameInfo equipmentNameInfo) {
        ModelAndView modelAndView = new ModelAndView();

        equipmentNameInfo = getEquipmentNameInfo(equipmentNameInfo);

        List<EquipmentTypeInfo> equipmentTypeInfoList = equipmentTypeInfoService.selectAllList();

        List<EquipmentNameInfo> equipmentNameInfoList = equipmentNameInfoService.selectEquipmentNameInfoList(equipmentNameInfo);

        modelAndView.addObject("equipmentNameInfo", equipmentNameInfo);
        modelAndView.addObject("equipmentTypeInfoList", equipmentTypeInfoList);
        modelAndView.addObject("equipmentNameInfoList", equipmentNameInfoList);
        modelAndView.setViewName("center/equipment/equipmentNameInfoList");

        return modelAndView;
    }

    @RequestMapping(value = "/saveEquipmentNameInfo.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveEquipmentInfo(HttpServletRequest request, @RequestBody EquipmentNameInfo equipmentNameInfo, String operateType) {
        Map<String, Object> result = new HashMap<>();

        equipmentNameInfo = getEquipmentNameInfo(equipmentNameInfo);

        try {
            if (operateType.equals(Constants.OPERATE_TYPE_ADD)) {
                equipmentNameInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                equipmentNameInfoService.insertEquipmentNameInfo(equipmentNameInfo);
            }else {
                equipmentNameInfo.setUpdatePerson(LimsSecurityUtils.getLoginName());
                equipmentNameInfoService.updateEquipmentNameInfo(equipmentNameInfo);
            }
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("delEquipmentNameInfo.html")
    @ResponseBody
    public Map<String, Object> delEquipmentNameInfo(HttpServletRequest request, Integer id) {
        Map<String, Object> result = new HashMap<>();

        try {
            equipmentNameInfoService.deleteById(id);
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    public EquipmentNameInfo getEquipmentNameInfo(EquipmentNameInfo equipmentNameInfo) {

        if (StringUtils.isBlank(equipmentNameInfo.getEquipmentNo())) {
            equipmentNameInfo.setEquipmentNo(null);
        }else {
            equipmentNameInfo.setEquipmentNo(equipmentNameInfo.getEquipmentNo().trim());
        }

        if (StringUtils.isBlank(equipmentNameInfo.getEquipmentName())) {
            equipmentNameInfo.setEquipmentName(null);
        }else {
            equipmentNameInfo.setEquipmentName(equipmentNameInfo.getEquipmentName().trim());
        }

        return equipmentNameInfo;
    }

}
