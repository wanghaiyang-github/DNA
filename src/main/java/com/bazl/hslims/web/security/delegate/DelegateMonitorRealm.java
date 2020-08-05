package com.bazl.hslims.web.security.delegate;

import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.services.delegate.DelegateLoginService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/28.
 */
@Service
public class DelegateMonitorRealm extends AuthorizingRealm {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DelegateLoginService delegateLoginService;

    public DelegateMonitorRealm() {
        super();
    }

    /**
     * 获取授权信息,权限验证，
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        return info;
    }

    /**
     * 获取认证信息,登陆验证。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        DelegateOrg delegateOrg = null;
        try {
            delegateOrg = delegateLoginService.selectByOrgCode(token.getUsername());
        }catch (Exception e){
            logger.error("获取委托账户失败！", e);
            throw e;
        }
        if (delegateOrg == null) {
            throw new UnknownAccountException();// 没找到帐号
        }

        return new SimpleAuthenticationInfo(delegateOrg, delegateOrg.getLoginPassword(), getName());
    }
}
