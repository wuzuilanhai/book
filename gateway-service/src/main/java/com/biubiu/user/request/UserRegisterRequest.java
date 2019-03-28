package com.biubiu.user.request;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Haibiao.Zhang on 2019-03-28 12:14
 */
@Data
public class UserRegisterRequest {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
