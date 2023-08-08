package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("user_account")
public class UserAccount {
    private Integer id;

    private Integer userId;

    private BigDecimal remainAmount;

    private BigDecimal totalAmount;

    private Date createTime;

    private Date modifyTime;

    private Integer settlementRate;

    private Byte status;

    private String shareUrl;


}
