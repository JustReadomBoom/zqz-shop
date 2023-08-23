package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 订单提交入参
 * @ClassName: OrderSubmitReq
 * @Date: Created in 13:42 2023-8-23
 */
@Data
public class OrderSubmitReq implements Serializable {

    private static final long serialVersionUID = 4128243491748968191L;

    private Integer cartId;

    private Integer addressId;

    private Integer couponId;

    private String message;

    private Integer grouponRulesId;

    private Integer grouponLinkId;
}
