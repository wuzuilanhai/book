package com.biubiu.auth.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Haibiao.Zhang on 2019-03-30 14:16
 */
@Data
@Builder
public class AuthUser {

    private String userId;

    private String username;

    private String roles;

    private String permission;

}
