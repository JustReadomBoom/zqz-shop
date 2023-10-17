package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("article")
public class Article {

    private Integer id;

    private String type;

    private String title;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

    private String content;

}
