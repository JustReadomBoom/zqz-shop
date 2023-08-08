package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("sys_permission")
public class SysPermission {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private Integer roleId;

    private String permission;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}