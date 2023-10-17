package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("footprint")
public class Footprint {

    private Integer id;

    private Integer userId;

    private Integer goodsId;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}
