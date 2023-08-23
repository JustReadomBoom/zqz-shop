package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 取消订单入参
 * @ClassName: CancelOrderReq
 * @Date: Created in 14:52 2023-8-23
 */
@Data
public class CancelOrderReq implements Serializable {
    private static final long serialVersionUID = 3641695534759060346L;

    private Integer orderId;
}
