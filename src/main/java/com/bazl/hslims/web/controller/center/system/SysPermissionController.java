package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.LabPermission;
import com.bazl.hslims.manager.model.po.LabRole;
import com.bazl.hslims.manager.model.po.LabRolePermission;
import com.bazl.hslims.manager.services.common.LabPermissionService;
import com.bazl.hslims.manager.services.common.LabRolePermissionService;
import com.bazl.hslims.manager.services.common.LabRoleService;
import com.bazl.hslims.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/18.
 */
@Controller
@RequestMapping("/center/7")
public class SysPermissionController extends BaseController {

    @Autowired
    LabRoleService labRoleService;
    @Autowired
    LabPermissionService labPermissionService;
    @Autowired
    LabRolePermissionService labRolePermissionService;

    @RequestMapping("04.html")
    public ModelAndView list(HttpServletRequest request) {
        List<LabRole> labRoleList = labRoleService.selectAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("labRoleList", labRoleList);
        modelAndView.setViewName("center/system/listPermission");
        return modelAndView;
    }

    @RequestMapping("/editPermission.html")
    public ModelAndView edit(HttpServletRequest request, Integer labRoleId, String operateType){
        List<LabPermission> labPermissionList = labPermissionService.selectAll();

        LabRole labRole = new LabRole();
        if(Constants.OPERATE_TYPE_EDIT.equals(operateType)) {
            labRole = labRoleService.selectById(labRoleId);
            List<LabPermission> rolePermissionList= labPermissionService.selectListByRoleId(labRoleId);
            for(LabPermission lr : labPermissionList){
                for(LabPermission ur : rolePermissionList){
                    if(lr.getId().equals(ur.getId())){
                        lr.setCheckedForRole(Constants.FLAG_TRUE);
                        break;
                    }
                }
            }
        }

        List<LabPermission> labParentPermissionList = new ArrayList<>();
        List<LabPermission> labChildPermissionList = new ArrayList<>();
        for (LabPermission lp : labPermissionList) {
            if (lp.getRootFlag().equals(Constants.FLAG_TRUE)) {
                labParentPermissionList.add(lp);
            }else {
                labChildPermissionList.add(lp);
            }
        }

        List<LabPermission> newLabPermissionList = new ArrayList<>();
        for (LabPermission lpp : labParentPermissionList) {
            newLabPermissionList.add(lpp);
            for (LabPermission lcp : labChildPermissionList) {
                if (lcp.getParentId() != null && lpp.getId().equals(lcp.getParentId()))
                    newLabPermissionList.add(lcp);
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", operateType);
        modelAndView.addObject("labRole", labRole);
        modelAndView.addObject("labPermissionList", newLabPermissionList);
        modelAndView.setViewName("center/system/editPermission");

        return modelAndView;
    }

    @RequestMapping("/saveLabRolePermission.html")
    @ResponseBody
    public Map<String, Object> save(HttpServletRequest request, @RequestBody LabRolePermission labRolePermission, String operateType){
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if(Constants.OPERATE_TYPE_ADD.equals(operateType)){
                labRolePermissionService.add(labRolePermission);
            }else if(Constants.OPERATE_TYPE_EDIT.equals(operateType)){
                labRolePermissionService.update(labRolePermission);
            }

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }


}
