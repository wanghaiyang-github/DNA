package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.manager.model.po.IdentifyKernel;
import com.bazl.hslims.manager.services.common.IdentifyKernelService;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
 * Created by Administrator on 2017/7/5.
 */
@Controller
@RequestMapping("/center/7")
public class IdentifyKernelController extends BaseController {

    @Autowired
    IdentifyKernelService identifyKernelService;

    @RequestMapping(value = "/identifyKernel.html")
    public ModelAndView identifyKernel (HttpServletRequest request) {

        List<IdentifyKernel> identifyKernelList = identifyKernelService.selectAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("identifyKernelList", identifyKernelList);
        modelAndView.setViewName("center/system/identifyKernel");
        return modelAndView;
    }

    @RequestMapping(value = "/selectRepeatIdentifyKernel.html")
    @ResponseBody
    public Map<String, Object> selectRepeatIdentifyKernel (HttpServletRequest request, String identifyKernelName) {
        Map<String, Object> result = new HashMap<>();

        IdentifyKernel identifyKernel = identifyKernelService.selectListByName(identifyKernelName.trim());

        if (identifyKernel == null) {
            result.put("success", false);
        }else {
            result.put("success", true);
        }

        return result;
    }

    @RequestMapping(value="/addOrEditIdentifyKernel.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addOrEditIdentifyKernel (HttpServletRequest request, @RequestBody IdentifyKernel identifyKernel) {

        identifyKernel = initIdentifyKerner(identifyKernel);

        Map<String, Object> result = new HashMap<>();

        try {

            if (identifyKernel.getId() == null || identifyKernel.getId() == 0) {
                identifyKernel.setCreatePerson(LimsSecurityUtils.getLoginLabUser().getUserName());
                identifyKernelService.insert(identifyKernel);
            }else {
                identifyKernel.setUpdatePerson(LimsSecurityUtils.getLoginLabUser().getUserName());
                identifyKernelService.update(identifyKernel);
            }

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping(value = "/delIdentifyKernel.html")
    @ResponseBody
    public Map<String, Object> delIdentifyKernel (HttpServletRequest request, Integer id) {
        Map<String, Object> result = new HashMap<>();

        try {
            identifyKernelService.delete(id);
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    public IdentifyKernel initIdentifyKerner (IdentifyKernel identifyKernel) {

        if (StringUtils.isBlank(identifyKernel.getIdentifyKernelName())) {
            identifyKernel.setIdentifyKernelName(null);
        }else {
            identifyKernel.setIdentifyKernelName(identifyKernel.getIdentifyKernelName());
        }

        return identifyKernel;
    }

}
