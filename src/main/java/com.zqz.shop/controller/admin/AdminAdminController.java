package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAdminController
 * @Date: Created in 14:20 2023-8-31
 */
@RestController
@RequestMapping("/admin/admin")
public class AdminAdminController {
    @Autowired
    private AdminAdminService adminAdminService;

    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer userId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String username) {
        return adminAdminService.doQueryList(userId, page, limit, username);
    }
}
