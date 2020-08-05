package com.bazl.hslims.web.security;

import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.model.po.LabUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Administrator on 2017/1/6.
 */
public class LimsSecurityUtils {

    public static String getLoginName() {
        Subject currentUser = SecurityUtils.getSubject();
        Object obj = currentUser.getPrincipal();
        if(obj instanceof LabUser) {
            LabUser employee = (LabUser) obj;
            return employee.getUserName();
        } else if (obj instanceof DelegateOrg) {
            DelegateOrg org = (DelegateOrg) obj;
            return org.getOrgName();
        }

        return null;
    }


    public static LabUser getLoginLabUser() {
        Subject currentUser = SecurityUtils.getSubject();

        try {
            LabUser employee = (LabUser) currentUser.getPrincipal();
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DelegateOrg getLoginDelegateOrg() {
        Subject currentUser = SecurityUtils.getSubject();

        try {
            DelegateOrg employee = (DelegateOrg) currentUser.getPrincipal();
            return employee;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
