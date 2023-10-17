package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("user")
public class User {

    private Integer id;

    private String username;

    private String password;

    private Byte gender;

    private Date birthday;

    private Date lastLoginTime;

    private String lastLoginIp;

    private Byte userLevel;

    private String nickname;

    private String mobile;

    private String avatar;

    private String weixinOpenid;

    private Byte status;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private Integer shareUserId;


}
