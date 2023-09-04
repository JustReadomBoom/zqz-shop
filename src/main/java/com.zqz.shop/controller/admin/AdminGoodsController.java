package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.bean.admin.GoodsCreateReq;
import com.zqz.shop.bean.admin.GoodsUpdateReq;
import com.zqz.shop.entity.Goods;
import com.zqz.shop.service.AdminGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/delete")
    public Object delete(@AdminLoginUser Integer adminUserId,
                         @RequestBody Goods goods) {
        return adminGoodsService.doDelete(adminUserId, goods);
    }


    @GetMapping("/detail")
    public Object detail(@AdminLoginUser Integer adminUserId, @RequestParam("id") Integer id) {
        return adminGoodsService.doDetail(adminUserId, id);
    }


    @PostMapping("/update")
    public Object updateInfo(@AdminLoginUser Integer adminUserId,
                             @RequestBody GoodsUpdateReq req) {
        return adminGoodsService.doUpdateInfo(adminUserId, req);
    }

    @PostMapping("/create")
    public Object createInfo(@AdminLoginUser Integer adminUserId, @RequestBody GoodsCreateReq req) {
        return adminGoodsService.doCreateInfo(adminUserId, req);
    }
}
