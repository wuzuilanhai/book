package com.biubiu.user.service;

import com.biubiu.user.dto.UserLoginDto;
import com.biubiu.user.dto.UserLoginRespDto;
import com.biubiu.user.dto.UserRegisterDto;
import com.biubiu.user.dto.UserRegisterRespDto;
import com.biubiu.web.Response;
import org.springframework.stereotype.Component;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:31
 */
@Component
public class UserServiceHystrix implements UserService {

    @Override
    public Response<UserRegisterRespDto> user(UserRegisterDto userRegisterDto) {
        return doErrorResponse(Response.INTERNAL_ERROR, "user service not available when execute method [findAllUsers]");
    }

    @Override
    public Response<UserLoginRespDto> login(UserLoginDto request) {
        return doErrorResponse(Response.INTERNAL_ERROR, "user service not available when execute method [findAllUsers]");
    }

    @Override
    public Response<Object> findAllUsers() {
        return doErrorResponse(Response.INTERNAL_ERROR, "user service not available when execute method [findAllUsers]");
    }

    private Response doErrorResponse(Long code, String message) {
        return Response.fail(code, message);
    }

}
