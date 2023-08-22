package com.zqz.shop.service;

import com.zqz.shop.entity.Cart;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartService
 * @Date: Created in 14:53 2023-8-18
 */
public interface CartService {
    Object doQueryGoodCount(Integer userId);

    Object doAddProduct(Integer userId, Cart cart);

    Object doIndex(Integer userId);

    Object doChecked(Integer userId, String body);

    Object doUpdate(Integer userId, Cart cart);
}
