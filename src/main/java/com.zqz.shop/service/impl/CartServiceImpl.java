package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zqz.shop.entity.Cart;
import com.zqz.shop.service.CartService;
import com.zqz.shop.service.business.CartBusService;
import com.zqz.shop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartServiceImpl
 * @Date: Created in 14:53 2023-8-18
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartBusService cartBusService;

    @Override
    public Object doQueryGoodCount(Integer userId) {
        List<Cart> cartList = cartBusService.queryByUserId(userId);
        if (CollectionUtil.isEmpty(cartList)) {
            return ResponseUtil.ok(0);
        }
        int goodsCount = 0;
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return ResponseUtil.ok(goodsCount);
    }
}
