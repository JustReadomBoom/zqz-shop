package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminGoodsController
 * @Date: Created in 14:35 2023-8-30
 */
@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {
    @Autowired
    private AdminGoodsService adminGoodsService;


    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer userId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String goodsSn,
                            String name) {
        return adminGoodsService.doQueryList(userId, page, limit, goodsSn, name);
    }


    @GetMapping("/catAndBrand")
    public Object catAndBrand(@AdminLoginUser Integer userId) {
        return adminGoodsService.doCatAndBrand(userId);
    }
}
