package com.zqz.shop.controller;

import com.zqz.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartController
 * @Date: Created in 14:46 2023-8-18
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;



    @GetMapping("/goodscount")
    public Object queryGoodCount(Integer userId) {
        return cartService.doQueryGoodCount(userId);
    }
}
