package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("brand")
public class Brand {

    private Integer id;

    private String name;

    private String desc;

    private String picUrl;

    private Byte sortOrder;

    private BigDecimal floorPrice;

    private Date addTime;

    private Date updateTime;

    private String shareUrl;

    private Integer adminId;

    private Boolean deleted;

    private String commpany;

    private Boolean autoUpdateGood;

    private String shopUrl;

    private Integer defaultCategoryId;

    private Integer defaultPages;

    private Integer addPrecent;


}
