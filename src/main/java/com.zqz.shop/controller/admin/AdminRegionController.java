package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminRegionController
 * @Date: Created in 21:30 2023-8-31
 */
@RestController
@RequestMapping("/admin/region")
public class AdminRegionController {
    @Autowired
    private AdminRegionService adminRegionService;


    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String name,
                            String code) {
        return adminRegionService.doQueryList(adminUserId, page, limit, name, code);
    }


}
