package com.bazl.hslims.web.controller;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.model.po.LabUser;
import com.bazl.hslims.manager.services.common.DelegateOrgService;
import com.bazl.hslims.manager.services.common.LabUserService;
import com.bazl.hslims.utils.EncryptUtils;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.bazl.hslims.web.security.LimsUsernamePasswordToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2017/3/13.
 */
@Controller
public class UpdatePasswordController extends BaseController {

    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LabUserService labUserService;

    @RequestMapping("/wt/updatePassword.html")
    @ResponseBody
    public String updateDelegatePassword(HttpServletRequest request, HttpServletResponse response,
                                         Integer loginId, String loginType,
                                         String oldPassword, String newPassword) {
        Subject currentUser = SecurityUtils.getSubject();
        Object object = currentUser.getPrincipal();
        if(object instanceof DelegateOrg){
            DelegateOrg delegateOrg = (DelegateOrg) object;
            if(!delegateOrg.getLoginPassword().equals(EncryptUtils.encryptMD5(oldPassword))){
                return "1";
            }

            try {
                delegateOrg.setLoginPassword(EncryptUtils.encryptMD5(newPassword));
                delegateOrgService.update(delegateOrg);

                LimsUsernamePasswordToken token = new LimsUsernamePasswordToken(delegateOrg.getOrgCode(), delegateOrg.getLoginPassword(), true);
                token.setLoginType(Constants.LOGIN_TYPE_DELEGATE);

                currentUser.login(token);
            }catch(Exception ex){
                logger.error("委托用户修改密码错误！", ex);
                return "2";
            }
        }
        /*else{
            LabUser labUser = (LabUser) object;
            if(!labUser.getLoginPassword().equals(EncryptUtils.encryptMD5(oldPassword))){
                return "1";
            }
            try {
                labUser.setLoginPassword(EncryptUtils.encryptMD5(newPassword));
                labUserService.updatePassword(labUser);
            }catch(Exception ex){
                logger.error("受理用户修改密码错误！", ex);
                return "2";
            }
        }*/

        return "0";
    }


    @RequestMapping("/center/updatePassword.html")
    @ResponseBody
    public String updateCenterPassword(HttpServletRequest request, HttpServletResponse response,
                                       Integer loginId, String loginType,
                                       String oldPassword, String newPassword) {
        Subject currentUser = SecurityUtils.getSubject();
        Object object = currentUser.getPrincipal();
        if(object instanceof LabUser){
            LabUser labUser = (LabUser) object;
            if(!labUser.getLoginPassword().equals(EncryptUtils.encryptMD5(oldPassword))){
                return "1";
            }

            try {
                labUser.setLoginPassword(EncryptUtils.encryptMD5(newPassword));
                labUserService.updatePassword(labUser);

                LimsUsernamePasswordToken token = new LimsUsernamePasswordToken(labUser.getLoginName(), labUser.getLoginPassword(), true);
                token.setLoginType(Constants.LOGIN_TYPE_CENTER);

                currentUser.login(token);
            }catch(Exception ex){
                logger.error("鉴定用户修改密码错误！", ex);
                return "2";
            }
        }

        return "0";
    }

}
