package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAddressController
 * @Date: Created in 13:48 2023-8-24
 */
@RestController
@RequestMapping("/admin/address")
public class AdminAddressController {
    @Autowired
    private AdminAddressService adminAddressService;


    @GetMapping("/list")
    public Object list(@AdminLoginUser Integer userId, Integer queryUserId, String name, @RequestParam("page") Integer page,
                       @RequestParam("limit") Integer limit) {
        return adminAddressService.doList(userId, page, limit, queryUserId, name);
    }
}
