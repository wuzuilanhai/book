package com.biubiu.auth.aspect;

import com.biubiu.auth.annotation.Permission;
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
 * 权限校验
 * Created by Haibiao.Zhang on 2019-03-27 17:31
 */
@Aspect
@Component
public class PermissionAspect implements Ordered {

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1002;
    }

    @Around("@annotation(permission)")
    public Object proceed(ProceedingJoinPoint joinPoint, Permission permission) throws Throwable {
        HttpServletRequest request = HttpUtil.getRequest();
        Object jwtObj = request.getHeader(JwtConstants.AUTH_HEADER);
        if (jwtObj == null) throw new AuthenticationException("header[Authorization] require not null");
        Claims claims = jwtUtil.parseJwt((String) jwtObj);
        List<String> userPermissions = Arrays.asList(claims.get(JwtConstants.USER_PERMISSIONS, String.class).split(JwtConstants.SPLIT));
        List<String> requiredPermissions = Arrays.asList(permission.permissions());
        if (!userPermissions.containsAll(requiredPermissions))
            throw new AuthorizationException("authorized[permission] failed");
        return joinPoint.proceed();
    }

}
