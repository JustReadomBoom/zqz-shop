package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("category")
public class Category {

    private Integer id;

    private String name;

    private String keywords;

    private String desc;

    private Integer pid;

    private String iconUrl;

    private String picUrl;

    private String level;

    private Byte sortOrder;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
