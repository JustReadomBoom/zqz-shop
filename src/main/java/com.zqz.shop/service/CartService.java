package com.zqz.shop.service;

import com.zqz.shop.bean.req.AddCartProductReq;
import com.zqz.shop.bean.req.UpdateCartReq;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartService
 * @Date: Created in 14:53 2023-8-18
 */
public interface CartService {
    Object doQueryGoodCount(Integer userId);

    Object doAddProduct(Integer userId, AddCartProductReq req);

    Object doIndex(Integer userId);

    Object doChecked(Integer userId, String body);

    Object doUpdate(Integer userId, UpdateCartReq req);

    Object doDelete(Integer userId, String body);

    Object doCheckout(Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId);


}
