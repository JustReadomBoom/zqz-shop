package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminUserController
 * @Date: Created in 14:21 2023-8-30
 */
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;


    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer userId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String username,
                            String mobile) {
        return adminUserService.doQueryList(userId, page, limit, username, mobile);
    }
}
