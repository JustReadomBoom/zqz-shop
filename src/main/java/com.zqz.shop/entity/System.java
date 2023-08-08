package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("system")
public class System {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private String keyName;

    private String keyValue;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;

}
