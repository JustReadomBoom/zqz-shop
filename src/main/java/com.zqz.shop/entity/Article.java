package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("article")
public class Article {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private String type;

    private String title;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String content;

}
