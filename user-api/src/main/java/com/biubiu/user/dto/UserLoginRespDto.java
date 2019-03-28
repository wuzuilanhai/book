package com.biubiu.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by Haibiao.Zhang on 2019-03-28 09:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRespDto implements Serializable{

    private static final long serialVersionUID = -1716690186218053968L;

    private String userId;

    private String username;

    private String roles;

    private String permissions;

}
