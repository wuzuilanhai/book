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
import com.biubiu.user.request.UserLoginRequest;
import com.biubiu.user.request.UserRegisterRequest;
import com.biubiu.user.service.UserService;
import com.biubiu.util.ModelMapperUtil;
import com.biubiu.util.RedisUtil;
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

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 用户注册
     */
    @PostMapping("/user")
    public Response user(@RequestBody @Valid UserRegisterRequest request) {
        UserRegisterDto userRegisterDto = ModelMapperUtil.convert(request, UserRegisterDto.class);
        return userService.user(userRegisterDto);
    }

    /**
     * 用户登陆
     */
    @PostMapping("/user/login")
    public Response login(@RequestBody @Valid UserLoginRequest request) {
        UserLoginDto userLoginDto = ModelMapperUtil.convert(request, UserLoginDto.class);
        Response<UserLoginRespDto> response = userService.login(userLoginDto);
        UserLoginRespDto respDto = response.getPayload();
        if (respDto != null) {
            String userId = respDto.getUserId();
            String jwt = jwtUtil.createJwt(respDto.getUserId(), respDto.getUsername(), respDto.getRoles(), respDto.getPermissions());
            HttpUtil.getResponse().setHeader(JwtConstants.AUTH_HEADER, jwt);
            redisUtil.set(userId, jwt);
        }
        return response;
    }

    /**
     * 用户注销
     */
    @PostMapping("/user/logout")
    @Login
    public Response logout() {
        String userId = (String) HttpUtil.getRequest().getAttribute(JwtConstants.USER_ID);
        redisUtil.del(userId);
        return Response.succeed();
    }

    @GetMapping("/users")
    @Login
    @Role(roles = {"role0"})
    @Permission(permissions = {"permission0"})
    public Response users() {
        return userService.findAllUsers();
    }

}
