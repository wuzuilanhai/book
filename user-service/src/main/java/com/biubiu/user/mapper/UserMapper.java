package com.biubiu.user.mapper;

import com.biubiu.user.po.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by Haibiao.Zhang on 2019-03-27 12:59
 */
@Repository
public interface UserMapper extends Mapper<User> {

    User selectUser(@Param("username") String username);

    int uniqueName(@Param("username") String username);

}
