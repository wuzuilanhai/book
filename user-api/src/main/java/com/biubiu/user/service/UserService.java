package com.biubiu.user.service;

import com.biubiu.user.dto.UserLoginDto;
import com.biubiu.user.dto.UserLoginRespDto;
import com.biubiu.user.dto.UserRegisterDto;
import com.biubiu.user.dto.UserRegisterRespDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:29
 */
@FeignClient(value = "user-service", path = "/rest", fallback = UserServiceHystrix.class)
public interface UserService {

    @PostMapping("/user")
    UserRegisterRespDto user(@RequestBody UserRegisterDto userRegisterDto);

    @PostMapping("/user/login")
    UserLoginRespDto login(@RequestBody UserLoginDto request);

    @GetMapping("/users")
    Object findAllUsers();

}
