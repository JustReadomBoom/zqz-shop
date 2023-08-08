package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("groupon_rules")
public class GrouponRules {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private Long goodsId;

    private String goodsName;

    private String picUrl;

    private BigDecimal discount;

    private Integer discountMember;

    private Date addTime;

    private Date updateTime;

    private Date expireTime;

    private Boolean deleted;
}
