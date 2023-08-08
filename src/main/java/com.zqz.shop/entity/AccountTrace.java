package com.zqz.shop.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Table("account_trace")
public class AccountTrace {
    private Integer id;

    private String traceSn;

    private Integer userId;

    private Integer type;

    private BigDecimal amount;

    private BigDecimal totalAmount;

    private Date addTime;

    private String mobile;

    private String smsCode;

    private Byte status;

    private String traceMsg;

    private Date updateTime;

}
