package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/1/4.
 */
@Controller
@RequestMapping("/center/3")
public class CompareResultController extends BaseController {

    @RequestMapping("/matchedList.html")
    public ModelAndView codis(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("center/result/matchedList");
        return modelAndView;
    }
}
