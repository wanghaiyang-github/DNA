package com.bazl.hslims.web.security.center;

import com.bazl.hslims.manager.model.po.LabUser;
import com.bazl.hslims.manager.services.center.CenterLoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Administrator on 2016/12/28.
 */
@Service
public class CenterMonitorRealm extends AuthorizingRealm {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CenterLoginService centerLoginService;

    public CenterMonitorRealm() {
        super();
    }

    /**
     * 获取授权信息,权限验证，
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LabUser user = (LabUser) principals.fromRealm(getName()).iterator().next();

        Set<String> roleNames = centerLoginService.selectRoleIdsByUserId(user.getId());//查找角色

        Set<String> permissions = centerLoginService.selectPermissionIdsByUserId(user.getId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 获取认证信息,登陆验证。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        LabUser user = null;
        try {
            user = centerLoginService.selectByLoginName(token.getUsername());
        }catch (Exception e){
            logger.error("获取鉴定账户失败！", e);
            throw e;
        }

        if (user == null) {
            throw new UnknownAccountException();// 没找到帐号
        }

        return new SimpleAuthenticationInfo(user, user.getLoginPassword(), getName());
    }
}
