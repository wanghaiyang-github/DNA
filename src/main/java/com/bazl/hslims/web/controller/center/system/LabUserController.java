package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.LabRole;
import com.bazl.hslims.manager.model.po.LabUser;
import com.bazl.hslims.manager.services.common.LabRoleService;
import com.bazl.hslims.manager.services.common.LabUserService;
import com.bazl.hslims.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/11.
 */
@RequestMapping("/center/7")
@Controller
public class LabUserController extends BaseController {

    @Autowired
    LabUserService labUserService;
    @Autowired
    LabRoleService labRoleService;

    @RequestMapping("/01.html")
    public ModelAndView into(HttpServletRequest request) {

        List<LabUser> labUserList = labUserService.selectAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("center/system/listLabUser");
        modelAndView.addObject("labUserList", labUserList);

        return modelAndView;
    }


    @RequestMapping("/editLabUser.html")
    public ModelAndView edit(HttpServletRequest request, Integer labUserId, String operateType){
        List<LabRole> labRoleList = labRoleService.selectAll();

        LabUser labUser = new LabUser();
        if(Constants.OPERATE_TYPE_EDIT.equals(operateType)) {
            labUser = labUserService.selectById(labUserId);
            List<LabRole> userRoleList= labRoleService.selectByLabUserId(labUserId);
            for(LabRole lr : labRoleList){
                for(LabRole ur : userRoleList){
                    if(lr.getId().equals(ur.getId())){
                        lr.setCheckedForUser(Constants.FLAG_TRUE);
                        break;
                    }
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", operateType);
        modelAndView.addObject("labUser", labUser);
        modelAndView.addObject("labRoleList", labRoleList);
        modelAndView.setViewName("center/system/editLabUser");

        return modelAndView;
    }

    @RequestMapping("/delLabUser.html")
    public ModelAndView del(HttpServletRequest request, Integer labUserId){
        labUserService.deleteById(labUserId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/center/7/01.html");
        return modelAndView;
    }

    //
    //
    //
    @RequestMapping("/saveLabUser.html")
    @ResponseBody
    public Map<String, Object> save(HttpServletRequest request, @RequestBody LabUser labUser, String operateType){

        if(Constants.OPERATE_TYPE_ADD.equals(operateType)){
            labUserService.add(labUser);
        }else if(Constants.OPERATE_TYPE_EDIT.equals(operateType)){
            labUserService.update(labUser);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }

}
