package com.bazl.hslims.manager.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/14.
 */
public abstract class LoginModel implements Serializable {

    protected String loginType;      //  1:实验室用户； 2： 委托用户

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
