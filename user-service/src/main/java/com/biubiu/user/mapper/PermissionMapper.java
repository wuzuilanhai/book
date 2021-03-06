package com.biubiu.user.mapper;

import com.biubiu.user.po.Permission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Haibiao.Zhang on 2019-03-27 12:59
 */
@Repository
public interface PermissionMapper extends Mapper<Permission> {

    List<Permission> findByRids(List<String> rids);

}
