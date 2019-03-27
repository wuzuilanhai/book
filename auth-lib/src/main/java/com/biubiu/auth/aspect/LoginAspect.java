package com.biubiu.auth.aspect;

import com.biubiu.auth.annotation.Login;
import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.exception.AuthException;
import com.biubiu.auth.util.HttpUtil;
import com.biubiu.auth.util.JwtUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登陆校验
 * Created by Haibiao.Zhang on 2019-03-27 17:31
 */
@Aspect
@Component
public class LoginAspect implements Ordered {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1000;
    }

    @Around("@annotation(login)")
    public Object proceed(ProceedingJoinPoint joinPoint, Login login) throws Throwable {
        HttpServletRequest request = HttpUtil.getRequest();
        Object jwtObj = request.getAttribute(JwtConstants.AUTH_HEADER);
        if (jwtObj == null) throw new AuthException("header[Authorization] require not null");
        //jwt校验
        if (!jwtUtil.verifyJwt((String) jwtObj)) throw new AuthException("expired jwt");
        return joinPoint.proceed();
    }

}
