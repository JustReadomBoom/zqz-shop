package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("agency_share")
public class AgencyShare {
    private Integer id;

    private Integer userId;

    private String shareUrl;

    private Integer type;

    private Integer shareObjId;

    private Date addTime;

    private Date updateTime;

}
