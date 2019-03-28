package com.biubiu.user.service.impl;

import com.biubiu.user.dto.UserLoginDto;
import com.biubiu.user.dto.UserLoginRespDto;
import com.biubiu.user.dto.UserRegisterDto;
import com.biubiu.user.dto.UserRegisterRespDto;
import com.biubiu.user.exception.UserException;
import com.biubiu.user.mapper.PermissionMapper;
import com.biubiu.user.mapper.RoleMapper;
import com.biubiu.user.mapper.UserMapper;
import com.biubiu.user.po.Permission;
import com.biubiu.user.po.Role;
import com.biubiu.user.po.User;
import com.google.common.base.Joiner;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Haibiao.Zhang on 2019-03-27 13:53
 */
@RestController
@RequestMapping("/rest")
public class UserServiceImpl {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 用户注册
     */
    @PostMapping("/user")
    @Transactional
    public UserRegisterRespDto user(@RequestBody UserRegisterDto userRegisterDto) {
        String username = userRegisterDto.getUsername();
        if (userMapper.uniqueName(username) > 0) {
            throw new UserException("用户名已存在");
        }
        String password = userRegisterDto.getPassword();
        ByteSource salt = ByteSource.Util.bytes(username);
        String cryptPwd = new Sha256Hash(password.toCharArray(), salt, 2).toString();
        User newUser = User.builder()
                .username(username)
                .password(cryptPwd)
                .salt(salt.toBase64())
                .creator(username)
                .editor(username)
                .build();
        if (userMapper.insertSelective(newUser) == 0) throw new UserException("user register fail");
        return UserRegisterRespDto.builder()
                .userId(newUser.getId())
                .username(username)
                .build();
    }

    /**
     * 用户登陆
     */
    @PostMapping("/user/login")
    public UserLoginRespDto login(@RequestBody UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        User user = userMapper.selectUser(username);
        if (user == null) {
            throw new UserException("用户名不存在");
        }
        ByteSource salt = ByteSource.Util.bytes(username);
        String cryptPwd = new Sha256Hash(userLoginDto.getPassword().toCharArray(), salt, 2).toString();
        if (!cryptPwd.equals(user.getPassword())) {
            throw new UserException("用户名或密码错误");
        }
        String userId = user.getId();
        List<Role> roles = roleMapper.findByUid(userId);
        List<String> roleNames = roles.stream().map(Role::getName).collect(Collectors.toList());
        List<Permission> permissions = permissionMapper.findByRids(roles.stream().map(Role::getId).collect(Collectors.toList()));
        List<String> permissionNames = permissions.stream().map(Permission::getPermission).collect(Collectors.toList());
        return UserLoginRespDto.builder()
                .userId(user.getId())
                .username(username)
                .roles(Joiner.on(",").join(roleNames))
                .permissions(Joiner.on(",").join(permissionNames))
                .build();
    }

    /**
     * 用户注销
     */

    @GetMapping("/users")
    public Object users() {
        return userMapper.selectAll();
    }

}
