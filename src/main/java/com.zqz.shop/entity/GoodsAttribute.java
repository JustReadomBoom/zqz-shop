package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("goods_attribute")
public class GoodsAttribute {

    private Integer id;

    private Integer goodsId;

    private String attribute;

    private String value;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}
