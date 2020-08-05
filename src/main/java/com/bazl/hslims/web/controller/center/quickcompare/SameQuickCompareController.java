package com.bazl.hslims.web.controller.center.quickcompare;

import com.bazl.hslims.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/1/6.
 */
@Controller
@RequestMapping("/center/5")
public class SameQuickCompareController extends BaseController {

    @RequestMapping("/01.html")
    public ModelAndView into(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("center/quickcompare/sameQuickCompare");

        return modelAndView;
    }
}
