package com.zqz.shop.controller;

import com.zqz.shop.annotation.LoginUser;
import com.zqz.shop.bean.AddCartProductReq;
import com.zqz.shop.bean.UpdateCartReq;
import com.zqz.shop.entity.Cart;
import com.zqz.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Object queryGoodCount(@LoginUser Integer userId) {
        return cartService.doQueryGoodCount(userId);
    }


    @PostMapping("/add")
    public Object addProduct(@LoginUser Integer userId, @RequestBody @Valid AddCartProductReq req) {
        return cartService.doAddProduct(userId, req);
    }


    @GetMapping("/index")
    public Object index(@LoginUser Integer userId) {
        return cartService.doIndex(userId);
    }


    @PostMapping("/checked")
    public Object checked(@LoginUser Integer userId, @RequestBody String body) {
        return cartService.doChecked(userId, body);
    }


    @PostMapping("/update")
    public Object update(@LoginUser Integer userId, @RequestBody @Valid UpdateCartReq req) {
        return cartService.doUpdate(userId, req);
    }

    @PostMapping("/delete")
    public Object delete(@LoginUser Integer userId, @RequestBody String body) {
        return cartService.doDelete(userId, body);
    }


    @GetMapping("/checkout")
    public Object checkout(@LoginUser Integer userId, Integer cartId,
                           Integer addressId, Integer couponId,
                           Integer grouponRulesId){
        return cartService.doCheckout(userId, cartId, addressId, couponId, grouponRulesId);
    }

}
