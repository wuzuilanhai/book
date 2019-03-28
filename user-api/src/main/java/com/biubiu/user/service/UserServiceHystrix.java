package com.biubiu.user.service;

import com.biubiu.user.dto.UserLoginDto;
import com.biubiu.user.dto.UserLoginRespDto;
import com.biubiu.user.dto.UserRegisterDto;
import com.biubiu.user.dto.UserRegisterRespDto;
import org.springframework.stereotype.Component;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:31
 */
@Component
public class UserServiceHystrix implements UserService {

    @Override
    public UserRegisterRespDto user(UserRegisterDto userRegisterDto) {
        return null;
    }

    @Override
    public UserLoginRespDto login(UserLoginDto request) {
        return null;
    }

    @Override
    public Object findAllUsers() {
        return "user service not available when execute method [findAllUsers]";
    }

}
