package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("topic")
public class Topic {

    private Integer id;

    private String title;

    private String subtitle;

    private BigDecimal price;

    private String readCount;

    private String picUrl;

    private Integer sortOrder;

    private String[] goods;

    private Date addTime;

    private Date updateTime;

    private String shareUrl;

    private Boolean deleted;

    private String content;

}
