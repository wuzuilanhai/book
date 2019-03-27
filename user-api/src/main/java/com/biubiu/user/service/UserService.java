package com.biubiu.user.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:29
 */
@FeignClient(value = "user-service", path = "/rest", fallback = UserServiceHystrix.class)
public interface UserService {

    @GetMapping("/users")
    Object findAllUsers();

}
