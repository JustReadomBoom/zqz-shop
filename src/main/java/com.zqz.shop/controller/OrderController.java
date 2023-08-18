package com.zqz.shop.controller;

import com.zqz.shop.annotation.LoginUser;
import com.zqz.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description: 订单管理入口
 * @ClassName: OrderController
 * @Date: Created in 16:56 2023-8-15
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


    /**
     * 订单信息
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
}
