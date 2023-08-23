package com.zqz.shop.controller;

import com.zqz.shop.annotation.LoginUser;
import com.zqz.shop.bean.CancelOrderReq;
import com.zqz.shop.bean.OrderDeleteReq;
import com.zqz.shop.bean.OrderPrepayReq;
import com.zqz.shop.bean.OrderSubmitReq;
import com.zqz.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZQZ
 * @Description: 订单操作入口
 * @ClassName: OrderController
 * @Date: Created in 16:56 2023-8-15
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 订单信息列表
     *
     * @param userId
     * @param showType
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public Object queryList(@LoginUser Integer userId, @RequestParam(defaultValue = "0") Integer showType,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer size) {
        return orderService.doQueryList(userId, showType, page, size);
    }


    /**
     * 订单提交
     *
     * @param userId
     * @param req
     * @return
     */
    @PostMapping("/submit")
    public Object submit(@LoginUser Integer userId, @RequestBody OrderSubmitReq req) {
        return orderService.doSubmit(userId, req);
    }


    /**
     * 取消订单
     *
     * @param userId
     * @param req
     * @return
     */
    @PostMapping("/cancel")
    public Object cancel(@LoginUser Integer userId, @RequestBody CancelOrderReq req) {
        return orderService.doCancel(userId, req);
    }


    /**
     * 预支付
     *
     * @param userId
     * @param req
     * @param request
     * @return
     */
    @PostMapping("/prepay")
    public Object prepay(@LoginUser Integer userId, @RequestBody OrderPrepayReq req, HttpServletRequest request) {
        return orderService.doPrepay(userId, req, request);
    }


    /**
     * 删除订单
     *
     * @param userId
     * @param req
     * @return
     */
    @PostMapping("/delete")
    public Object delete(@LoginUser Integer userId, @RequestBody OrderDeleteReq req) {
        return orderService.doDelete(userId, req);
    }


    /**
     * 详情
     * @param userId
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public Object detail(@LoginUser Integer userId, @RequestParam Integer orderId) {
        return orderService.doDetail(userId, orderId);
    }
}
