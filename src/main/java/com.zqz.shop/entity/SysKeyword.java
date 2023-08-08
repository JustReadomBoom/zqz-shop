package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("sys_keyword")
public class SysKeyword {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private String keyword;

    private String url;

    private Boolean isHot;

    private Boolean isDefault;

    private Integer sortOrder;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}
