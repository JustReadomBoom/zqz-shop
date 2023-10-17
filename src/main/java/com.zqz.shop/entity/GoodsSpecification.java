package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("goods_specification")
public class GoodsSpecification {

    private Integer id;

    private Integer goodsId;

    private String specification;

    private String value;

    private String picUrl;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;




}
