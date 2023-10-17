package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("coupon_user")
public class CouponUser {

    private Integer id;

    private Integer userId;

    private Integer couponId;

    private Short status;

    private Date usedTime;

    private Date startTime;

    private Date endTime;

    private String orderSn;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;



}
