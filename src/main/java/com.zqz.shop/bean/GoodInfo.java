package com.zqz.shop.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodInfo
 * @Date: Created in 16:18 2023-8-18
 */
@Data
public class GoodInfo {

    private Integer id;

    private String goodsSn;

    private String name;

    private Integer categoryId;

    private Integer brandId;

    private List<String> gallery;

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
