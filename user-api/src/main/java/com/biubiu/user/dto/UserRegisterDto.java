package com.biubiu.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Haibiao.Zhang on 2019-03-28 12:14
 */
@Data
public class UserRegisterDto implements Serializable {

    private static final long serialVersionUID = -5015901527254372101L;

    private String username;

    private String password;

}
