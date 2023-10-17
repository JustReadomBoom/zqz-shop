package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("search_history")
public class SearchHistory {

    private Integer id;

    private Integer userId;

    private String keyword;

    private String from;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
