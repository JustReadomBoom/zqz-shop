package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("admin")
public class Admin {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private String username;

    private String password;

    private String lastLoginIp;

    private Date lastLoginTime;

    private String avatar;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String roleIds;

    private String desc;

    private String tel;


}
