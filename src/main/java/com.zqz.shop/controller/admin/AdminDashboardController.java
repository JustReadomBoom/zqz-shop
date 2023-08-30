package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminDashboardController
 * @Date: Created in 15:15 2023-8-29
 */
@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {
    @Autowired
    private AdminDashboardService adminDashboardService;



    @GetMapping("/chart")
    public Object chart(@AdminLoginUser Integer userId){
        return adminDashboardService.doChar(userId);
    }


    @GetMapping
    public Object info(@AdminLoginUser Integer userId){
        return adminDashboardService.doInfo(userId);
    }
}
