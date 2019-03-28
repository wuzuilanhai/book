package com.biubiu.user.controller;

import com.biubiu.auth.annotation.Login;
import com.biubiu.auth.annotation.Permission;
import com.biubiu.auth.annotation.Role;
import com.biubiu.auth.constants.JwtConstants;
import com.biubiu.auth.util.HttpUtil;
import com.biubiu.auth.util.JwtUtil;
import com.biubiu.user.dto.UserLoginDto;
import com.biubiu.user.dto.UserLoginRespDto;
import com.biubiu.user.dto.UserRegisterDto;
import com.biubiu.user.dto.UserRegisterRespDto;
import com.biubiu.user.request.UserLoginRequest;
import com.biubiu.user.request.UserRegisterRequest;
import com.biubiu.user.service.UserService;
import com.biubiu.util.ModelMapperUtil;
import com.biubiu.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:36
 */
@RestController
@RequestMapping("/rest")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     */
    @PostMapping("/user")
    public Response user(@RequestBody @Valid UserRegisterRequest request) {
        UserRegisterDto userRegisterDto = ModelMapperUtil.convert(request, UserRegisterDto.class);
        UserRegisterRespDto userRegisterRespDto = userService.user(userRegisterDto);
        return Response.succeed(userRegisterDto);
    }

    /**
     * 用户登陆
     */
    @PostMapping("/user/login")
    public Response login(@RequestBody @Valid UserLoginRequest request) {
        UserLoginDto userLoginDto = ModelMapperUtil.convert(request, UserLoginDto.class);
        UserLoginRespDto respDto = null;
        try {
            respDto = userService.login(userLoginDto);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        if (respDto != null) {
            String jwt = jwtUtil.createJwt(respDto.getUserId(), respDto.getUsername(), respDto.getRoles(), respDto.getPermissions());
            HttpUtil.getResponse().setHeader(JwtConstants.AUTH_HEADER, jwt);
        }
        return Response.succeed(respDto);
    }

    @GetMapping("/users")
    @Login
    @Role(roles = {"role0"})
    @Permission(permissions = {"permission0"})
    public Response users() {
        return Response.succeed(userService.findAllUsers());
    }

}
