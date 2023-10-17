package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.util.Date;

@Data
@Table("issue")
public class Issue {

    private Integer id;

    private String question;

    private String answer;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;


}
