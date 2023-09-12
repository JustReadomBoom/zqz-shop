package com.zqz.shop.service;

import com.zqz.shop.bean.req.CancelOrderReq;
import com.zqz.shop.bean.req.OrderDeleteReq;
import com.zqz.shop.bean.req.OrderPrepayReq;
import com.zqz.shop.bean.req.OrderSubmitReq;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderService
 * @Date: Created in 16:58 2023-8-15
 */
public interface OrderService {
    Object doQueryList(Integer userId, Integer showType, Integer page, Integer size);

    Object doSubmit(Integer userId, OrderSubmitReq req);

    Object doCancel(Integer userId, CancelOrderReq req);

    Object doPrepay(Integer userId, OrderPrepayReq req, HttpServletRequest request);

    Object doDelete(Integer userId, OrderDeleteReq req);

    Object doDetail(Integer userId, Integer orderId);
}
