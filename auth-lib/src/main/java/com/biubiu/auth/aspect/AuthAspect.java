package com.biubiu.auth.aspect;

import com.biubiu.auth.annotation.Auth;
import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.exception.AuthException;
import com.biubiu.auth.util.HttpUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 角色或权限校验
 * Created by Haibiao.Zhang on 2019-03-27 17:31
 */
@Aspect
@Component
public class AuthAspect implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1001;
    }

    @Around("@annotation(auth)")
    public Object proceed(ProceedingJoinPoint joinPoint, Auth auth) throws Throwable {
        HttpServletRequest request = HttpUtil.getRequest();
        Object jwtObj = request.getAttribute(JwtConstants.AUTH_HEADER);
        if (jwtObj == null) throw new AuthException("header[Authorization] require not null");

        return joinPoint.proceed();
    }

}
