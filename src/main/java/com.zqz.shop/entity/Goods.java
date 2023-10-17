package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import javax.servlet.annotation.HandlesTypes;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("goods")
public class Goods {

    private Integer id;

    private String goodsSn;

    private String name;

    private Integer categoryId;

    private Integer brandId;

    private String gallery;

    private String keywords;

    private String brief;

    private Boolean isOnSale;

    private Short sortOrder;

    private String picUrl;

    private String shareUrl;

    private Boolean isNew;

    private Boolean isHot;

    private String unit;

    private BigDecimal counterPrice;

    private BigDecimal retailPrice;

    private Date addTime;

    private Date updateTime;

    private Integer browse;

    private Integer sales;

    private Boolean deleted;

    private String commpany;

    private BigDecimal wholesalePrice;

    private String detail;

}
