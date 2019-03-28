package com.biubiu.auth.aspect;

import com.biubiu.auth.annotation.Role;
import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.exception.AuthenticationException;
import com.biubiu.auth.exception.AuthorizationException;
import com.biubiu.auth.util.HttpUtil;
import com.biubiu.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * 角色校验
 * Created by Haibiao.Zhang on 2019-03-27 17:31
 */
@Aspect
@Component
public class RoleAspect implements Ordered {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1001;
    }

    @Around("@annotation(role)")
    public Object proceed(ProceedingJoinPoint joinPoint, Role role) throws Throwable {
        HttpServletRequest request = HttpUtil.getRequest();
        Object jwtObj = request.getHeader(JwtConstants.AUTH_HEADER);
        if (jwtObj == null) throw new AuthenticationException("header[Authorization] require not null");
        Claims claims = jwtUtil.parseJwt((String) jwtObj);
        List<String> userRoles = Arrays.asList(claims.get(JwtConstants.USER_ROLES, String.class).split(JwtConstants.SPLIT));
        List<String> requiredRoles = Arrays.asList(role.roles());
        if (!userRoles.containsAll(requiredRoles)) throw new AuthorizationException("authorized[role] failed");
        return joinPoint.proceed();
    }

}
