package com.biubiu.aspect;

import com.biubiu.auth.annotation.Login;
import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.exception.AuthenticationException;
import com.biubiu.auth.util.HttpUtil;
import com.biubiu.util.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Haibiao.Zhang on 2019-03-28 20:50
 */
@Aspect
@Component
public class GatewayLoginAspect implements Ordered {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2000;
    }

    @Around("@annotation(login)")
    public Object proceedAround(ProceedingJoinPoint joinPoint, Login login) throws Throwable {
        HttpServletRequest request = HttpUtil.getRequest();
        String userId = (String) request.getAttribute(JwtConstants.USER_ID);
        String jwt = request.getHeader(JwtConstants.AUTH_HEADER);
        Object redisJwt = redisUtil.get(userId);
        if (redisJwt != null) {
            String redisJwtStr = (String) redisJwt;
            if (!redisJwtStr.equals(jwt)) throw new AuthenticationException("expired jwt[gateway]");
        }
        return joinPoint.proceed();
    }

    @After("@annotation(login)")
    public void proceedAfter(Login login) throws Throwable {
        HttpServletRequest request = HttpUtil.getRequest();
        HttpServletResponse response = HttpUtil.getResponse();
        //刷新了jwt则更新redis缓存
        String refreshJwt = response.getHeader(JwtConstants.AUTH_HEADER);
        if (!StringUtils.isEmpty(refreshJwt)) {
            redisUtil.set((String) request.getAttribute(JwtConstants.USER_ID), refreshJwt);
        }
    }

}
