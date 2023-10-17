package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("role")
public class Role {

    private Integer id;

    private String name;

    private String desc;

    private Boolean enabled;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
