package com.biubiu.user.controller;

import com.biubiu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:36
 */
@RestController
@RequestMapping("/rest")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Object users() {
        return userService.findAllUsers();
    }

}
