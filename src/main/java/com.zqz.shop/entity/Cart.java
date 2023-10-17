package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("cart")
public class Cart {

    private Integer id;

    private Integer userId;

    private Integer brandId;

    private Integer goodsId;

    private String goodsSn;

    private String goodsName;

    private Integer productId;

    private BigDecimal price;

    private Short number;

    private String specifications;

    private Boolean checked;

    private String picUrl;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}
