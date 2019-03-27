package com.biubiu.user.service;

import org.springframework.stereotype.Component;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:31
 */
@Component
public class UserServiceHystrix implements UserService {

    @Override
    public Object findAllUsers() {
        return "user service not available when execute method [findAllUsers]";
    }

}
