package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("user_address")
public class UserAddress {

    private Integer id;

    private String name;

    private Integer userId;

    private Integer provinceId;

    private Integer cityId;

    private Integer areaId;

    private String address;

    private String mobile;

    private Boolean isDefault;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
