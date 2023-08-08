package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("user_formid")
public class UserFormid {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private String formid;

    private Boolean isprepay;

    private Integer useamount;

    private Date expireTime;

    private String openid;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
