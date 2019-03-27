package com.biubiu.auth;

import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.properties.JwtProperties;
import com.biubiu.auth.util.JwtUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Haibiao.Zhang on 2019-03-27 14:24
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = JwtConstants.JWT_PROPERTY_PREFIX, value = "refresh", havingValue = "true")
public class AuthAutoConfiguration {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}
