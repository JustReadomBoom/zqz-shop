package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminCategoryController
 * @Date: Created in 9:23 2023-9-1
 */
@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Autowired
    private AdminCategoryService adminCategoryService;


    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String id,
                            String name) {
        return adminCategoryService.doQueryList(adminUserId, page, limit, id, name);
    }

    @GetMapping("/l1")
    public Object categoryL1(@AdminLoginUser Integer adminUserId){
        return adminCategoryService.doCategoryL1(adminUserId);
    }
}
