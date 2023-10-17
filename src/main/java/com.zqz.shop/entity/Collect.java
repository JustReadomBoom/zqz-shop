package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("collect")
public class Collect {

    private Integer id;

    private Integer userId;

    private Integer valueId;

    private Byte type;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
