package com.biubiu.user.po;

import com.biubiu.util.KeyGenUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Haibiao.Zhang on 2019-03-27 12:59
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user_role")
public class UserRole {

    @Id
    @KeySql(genId = KeyGenUtil.class)
    private String id;

    private String userId;

    private String roleId;

    private String creator;

    private Date createTime;

    private String editor;

    private Date editTime;

    private Integer isDelete;

}
