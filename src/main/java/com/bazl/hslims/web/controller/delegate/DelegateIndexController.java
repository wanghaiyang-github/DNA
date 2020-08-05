package com.bazl.hslims.web.controller.delegate;

import com.bazl.hslims.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2017/1/2.
 */
@Controller
@RequestMapping("/wt")
public class DelegateIndexController extends BaseController {


    @RequestMapping(value="/index.html")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView view = new ModelAndView();

        view.setViewName("redirect:/wt/reg/1.html");
        return view;
    }

}
