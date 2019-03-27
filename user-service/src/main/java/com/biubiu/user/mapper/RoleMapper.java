package com.biubiu.user.mapper;

import com.biubiu.user.po.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Haibiao.Zhang on 2019-03-27 12:59
 */
@Repository
public interface RoleMapper extends Mapper<Role> {

    List<Role> findByUid(@Param("userId") String userId);

}
