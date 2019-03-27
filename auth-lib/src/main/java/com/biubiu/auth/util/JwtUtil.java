package com.biubiu.auth.util;

import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.exception.AuthException;
import com.biubiu.auth.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.Resource;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Haibiao.Zhang on 2019-03-27 18:24
 */
public class JwtUtil {

    @Resource
    private JwtProperties jwtProperties;

    /**
     * 生成JWT
     *
     * @param userId      用户ID
     * @param username    用户名称
     * @param roles       用户角色
     * @param permissions 用户权限
     * @return jwt字符串
     */
    public String createJwt(String userId, String username, String roles, String permissions) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtProperties.getSecret());
        Key signInKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                //jti (JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat (Issued At)：签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(userId)
                .claim(JwtConstants.USER_ID, userId)
                .claim(JwtConstants.USER_NAME, username)
                .claim(JwtConstants.USER_ROLES, roles)
                .claim(JwtConstants.USER_PERMISSIONS, permissions)
                .signWith(signatureAlgorithm, signInKey);
        long expireMillis = jwtProperties.getExpires();
        if (expireMillis >= 0) {
            long expMillis = nowMillis + expireMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param jwt jwt字符串
     * @return 用户相关信息
     */
    public Claims parseJwt(String jwt) {
        if (!verifyJwt(jwt)) throw new AuthException("expired jwt");
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtProperties.getSecret()))
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 校验jwt
     *
     * @param jwt jwt字符串
     * @return true:未过期 false:已过期
     */
    public boolean verifyJwt(String jwt) {
        Date now = new Date();
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtProperties.getSecret()))
                .parseClaimsJws(jwt)
                .getBody();
        Date expiration = claims.getExpiration();
        boolean result = now.after(expiration);
        if (result) refreshJwt(now, expiration, claims);
        return result;
    }

    /**
     * 刷新jwt
     *
     * @param now        当前时间
     * @param expiration 过期时间
     * @param claims     claims
     */
    private void refreshJwt(Date now, Date expiration, Claims claims) {
        //是否在过期后的有效期间内,在则续租新的jwt,否则不续租
        long survival = jwtProperties.getSurvival();
        Date calcDate = new Date(expiration.getTime() + survival);
        if (calcDate.before(now)) {
            //续租
            String userId = (String) claims.get(JwtConstants.USER_ID);
            String username = (String) claims.get(JwtConstants.USER_NAME);
            String roles = (String) claims.get(JwtConstants.USER_ROLES);
            String permissions = (String) claims.get(JwtConstants.USER_PERMISSIONS);
            String newJwt = createJwt(userId, username, roles, permissions);
            HttpUtil.getResponse().setHeader(JwtConstants.AUTH_HEADER, newJwt);
        }
    }

}
