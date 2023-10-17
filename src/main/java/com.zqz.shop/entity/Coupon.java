package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("coupon")
public class Coupon {

    private Integer id;

    private String name;

    private String desc;

    private String tag;

    private Integer total;

    private BigDecimal discount;

    private BigDecimal min;

    private Short limit;

    private Short type;

    private Short status;

    private Short goodsType;

    private Integer[] goodsValue;

    private String code;

    private Short timeType;

    private Short days;

    private Date startTime;

    private Date endTime;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}
