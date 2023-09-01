package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminStatController
 * @Date: Created in 13:47 2023-8-30
 */
@RestController
@RequestMapping("/admin/stat")
public class AdminStatController {
    @Autowired
    private AdminStatService adminStatService;


    @GetMapping("/user")
    public Object statUser(@AdminLoginUser Integer userId) {
        return adminStatService.doStatUser(userId);
    }


    @GetMapping("/order")
    public Object statOrder(@AdminLoginUser Integer userId) {
        return adminStatService.doStatOrder(userId);
    }

    @GetMapping("/goods")
    public Object statGoods(@AdminLoginUser Integer userId) {
        return adminStatService.doStatGoods(userId);
    }
}
