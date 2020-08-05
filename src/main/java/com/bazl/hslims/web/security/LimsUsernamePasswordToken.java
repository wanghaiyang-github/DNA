package com.bazl.hslims.web.security;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by Administrator on 2016/12/28.
 */
public class LimsUsernamePasswordToken extends UsernamePasswordToken {
    /**
     * 判断登录类型
     */
    private String loginType;

    public String getLoginType() {
        return this.loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted
     * during an authentication attempt, with a <tt>null</tt> {@link #getHost() host} and
     * a <tt>rememberMe</tt> default of <tt>false</tt>
     * <p/>
     * <p>This is a convience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param username the username submitted for authentication
     * @param password the password string submitted for authentication
     */
    public LimsUsernamePasswordToken(final String username, final String password) {
        super(username, password);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, as well as if the user
     * wishes their identity to be remembered across sessions.
     * <p/>
     * <p>This is a convience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param username   the username submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @since 0.9
     */
    public LimsUsernamePasswordToken(final String username, final String password, final boolean rememberMe) {
        super(username, password, rememberMe);
    }

    /**
     * Constructs a new UsernamePasswordToken encapsulating the username and password submitted, if the user
     * wishes their identity to be remembered across sessions, and the inetAddress from where the attempt is ocurring.
     * <p/>
     * <p>This is a convience constructor and maintains the password internally via a character
     * array, i.e. <tt>password.toCharArray();</tt>.  Note that storing a password as a String
     * in your code could have possible security implications as noted in the class JavaDoc.</p>
     *
     * @param username   the username submitted for authentication
     * @param password   the password string submitted for authentication
     * @param rememberMe if the user wishes their identity to be remembered across sessions
     * @param host       the host name or IP string from where the attempt is occuring
     * @since 1.0
     */
    public LimsUsernamePasswordToken(final String username, final String password,
                                 final boolean rememberMe, final String host) {
        super(username, password, rememberMe, host);
    }
}
