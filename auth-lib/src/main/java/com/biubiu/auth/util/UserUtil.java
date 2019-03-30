package com.biubiu.auth.util;

import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.entity.AuthUser;
import com.biubiu.auth.exception.AuthenticationException;

/**
 * Created by Haibiao.Zhang on 2019-03-30 14:12
 */
public class UserUtil {

    /**
     * 获取当前登陆用户ID
     *
     * @return 当前登陆用户ID
     */
    public static String currentUserId() {
        Object userId = HttpUtil.getRequest().getAttribute(JwtConstants.USER_ID);
        if (userId == null) throw new AuthenticationException("user login required");
        return (String) userId;
    }

    /**
     * 获取当前登陆用户信息
     *
     * @return 当前登陆用户信息
     */
    public static AuthUser currentUser() {
        Object userId = HttpUtil.getRequest().getAttribute(JwtConstants.USER_ID);
        Object username = HttpUtil.getRequest().getAttribute(JwtConstants.USER_NAME);
        Object roles = HttpUtil.getRequest().getAttribute(JwtConstants.USER_ROLES);
        Object permissions = HttpUtil.getRequest().getAttribute(JwtConstants.USER_PERMISSIONS);
        if (userId == null) throw new AuthenticationException("user login required");
        return AuthUser.builder()
                .userId((String) userId)
                .username((String) username)
                .roles((String) roles)
                .permission((String) permissions)
                .build();
    }

}
