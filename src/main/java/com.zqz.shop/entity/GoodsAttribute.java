package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("goods_attribute")
public class GoodsAttribute {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private Integer goodsId;

    private String attribute;

    private String value;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}
