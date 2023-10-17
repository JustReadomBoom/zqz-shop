package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("order_goods")
public class OrderGoods {

    private Integer id;

    private Integer orderId;

    private Integer brandId;

    private Integer goodsId;

    private String goodsName;

    private String goodsSn;

    private Integer productId;

    private Short number;

    private BigDecimal price;

    private String specifications;

    private String picUrl;

    private Integer comment;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}
