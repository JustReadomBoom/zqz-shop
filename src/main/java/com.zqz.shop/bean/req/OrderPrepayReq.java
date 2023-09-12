package com.zqz.shop.bean.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 订单预支付入参
 * @ClassName: OrderPrepayReq
 * @Date: Created in 16:02 2023-8-23
 */
@Data
public class OrderPrepayReq implements Serializable {
    private static final long serialVersionUID = 7340674400663117790L;

    private Integer orderId;
}
