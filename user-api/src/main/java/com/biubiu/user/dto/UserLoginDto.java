package com.biubiu.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Haibiao.Zhang on 2019-03-28 09:53
 */
@Data
public class UserLoginDto implements Serializable {

    private static final long serialVersionUID = 2775290808133778054L;

    private String username;

    private String password;

}
