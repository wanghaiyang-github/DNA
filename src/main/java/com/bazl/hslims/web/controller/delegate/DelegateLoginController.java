package com.bazl.hslims.web.controller.delegate;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.services.delegate.DelegateLoginService;
import com.bazl.hslims.utils.EncryptUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsUsernamePasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/12/28.
 */
@Controller
@RequestMapping("/wt")
public class DelegateLoginController extends BaseController {

    @Autowired
    DelegateLoginService delegateLoginService;

    @RequestMapping(value = "/loginForm.html")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        List<DelegateOrg> delegateOrgList = delegateLoginService.selectAllDelegateOrgList();
        delegateOrgList = delegateOrgList.stream().filter(delegateOrg -> delegateOrg.getRootFlag().equals("1")).collect(Collectors.toList());
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/loginForm");
        return modelAndView;
    }

    @RequestMapping(value = "/login.html")
    public ModelAndView login(HttpServletRequest request, String loginname, String password, String rememberMe) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:/wt/loginForm.html");
        String message ="";

        if (loginname == null || loginname.isEmpty()) {
            message = "请选择登录单位！";
        } else {
            // Subject就是代表当前的用户
            Subject currentUser = SecurityUtils.getSubject();
            // 申请令牌
            LimsUsernamePasswordToken token = new LimsUsernamePasswordToken(loginname, EncryptUtils.encryptMD5(password), rememberMe != null);
            token.setLoginType(Constants.LOGIN_TYPE_DELEGATE);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException e) {
                logger.error(loginname + ":" + password + "登录失败！错误如下：" + e);
                message = "请选择登录单位！";
            } catch (AuthenticationException e) {
                logger.error(loginname + ":" + password + "登录失败！错误如下：" + e);
                if (e.getCause() != null && e.getCause() instanceof TransactionException)
                    message = "数据库连接失败！";
                else
                    message = "登录单位或密码错误！";
            } catch (Exception e) {
                logger.error(loginname + ":" + password + "登录失败！错误如下：" + e);
                message = "未知错误！";
            }

            // 验证是否通过
            if (currentUser.isAuthenticated()) {
                SecurityUtils.getSubject().getSession().setTimeout(14400000);        //设置4个小时

                modelAndView.setViewName("redirect:/wt/reg/1.html");
                return modelAndView;
            } else {
                message = "登录单位或密码错误！";
            }
        }

        modelAndView.addObject(MESSAGE, message);
        return modelAndView;
    }

    // 注销
    @RequestMapping(value = "/logout.html")
    public ModelAndView logout(HttpServletRequest request, String rebackUrl) {
        ModelAndView modelAndView = new ModelAndView();
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (AuthenticationException e) {
            logger.error("注销失败", e);
        }
        modelAndView.setViewName("redirect:/wt/loginForm.html");
        return modelAndView;
    }
}
