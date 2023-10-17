package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("storage")
public class Storage {

    private Integer id;

    private String key;

    private String name;

    private String type;

    private Integer size;

    private String url;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}
