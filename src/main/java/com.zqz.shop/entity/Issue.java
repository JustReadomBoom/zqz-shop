package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("issue")
public class Issue {
    public static final Boolean NOT_DELETED = false;

    public static final Boolean IS_DELETED = true;

    private Integer id;

    private String question;

    private String answer;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}
