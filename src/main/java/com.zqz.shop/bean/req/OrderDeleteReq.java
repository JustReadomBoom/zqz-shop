package com.zqz.shop.bean.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 删除订单入参
 * @ClassName: OrderDeleteReq
 * @Date: Created in 16:57 2023-8-23
 */
@Data
public class OrderDeleteReq implements Serializable {
    private static final long serialVersionUID = -8726089453096536974L;

    private Integer orderId;
}
