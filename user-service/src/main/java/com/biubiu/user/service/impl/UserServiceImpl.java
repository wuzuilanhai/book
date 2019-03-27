package com.biubiu.user.service.impl;

import com.biubiu.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:53
 */
@RestController
@RequestMapping("/rest")
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/users")
    public Object users() {
        return userMapper.selectAll();
    }

}
