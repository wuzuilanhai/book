package com.biubiu.auth.properties;

import com.biubiu.auth.constants.JwtConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Haibiao.Zhang on 2019-03-27 19:53
 */
@Data
@ConfigurationProperties(prefix = JwtConstants.JWT_PROPERTY_PREFIX)
public class JwtProperties {

    private String secret = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";

    private long expires = 2 * 60 * 60 * 1000;

    private long survival = 30 * 60 * 1000;

    private boolean refresh = true;

}
