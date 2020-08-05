package com.bazl.hslims.web.security;

import com.bazl.hslims.common.Constants;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/28.
 */
public class LimsModularRealmAuthenticator extends ModularRealmAuthenticator {

    private Map<String, Object> definedRealms;

    /**
     * 多个realm实现
     */
    @Override
    protected AuthenticationInfo doMultiRealmAuthentication(
            Collection<Realm> realms, AuthenticationToken token) {
        return super.doMultiRealmAuthentication(realms, token);
    }

    /**
     * 调用单个realm执行操作
     */
    @Override
    protected AuthenticationInfo doSingleRealmAuthentication(
            Realm realm, AuthenticationToken token) {

        // 如果该realms不支持(不能验证)当前token
        if (!realm.supports(token)) {
            throw new ShiroException("token错误!");
        }
        AuthenticationInfo info = null;
        try {
            info = realm.getAuthenticationInfo(token);

            if (info == null) {
                throw new ShiroException("token不存在!");
            }
        } catch (Exception e) {
            throw new ShiroException("用户名或者密码错误!");
        }
        return info;
    }

    /**
     * 判断登录类型执行操作
     */
    @Override
    protected AuthenticationInfo doAuthenticate(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {
        this.assertRealmsConfigured();

        Realm realm = null;

        LimsUsernamePasswordToken token = (LimsUsernamePasswordToken) authenticationToken;
        if (token.getLoginType().equals(Constants.LOGIN_TYPE_DELEGATE)) {
            realm = (Realm) this.definedRealms.get("delegateMonitorRealm");
        }
        if (token.getLoginType().equals(Constants.LOGIN_TYPE_CENTER)) {
            realm = (Realm) this.definedRealms.get("centerMonitorRealm");
        }
        if (realm == null) {
            return null;
        }

        return this.doSingleRealmAuthentication(realm, authenticationToken);
    }

    @Override
    protected void assertRealmsConfigured() throws IllegalStateException {
        this.definedRealms = this.getDefinedRealms();
        if (CollectionUtils.isEmpty(this.definedRealms)) {
            throw new ShiroException("值传递错误!");
        }
    }

    public Map<String, Object> getDefinedRealms() {
        return this.definedRealms;
    }

    public void setDefinedRealms(Map<String, Object> definedRealms) {
        this.definedRealms = definedRealms;
    }

}
