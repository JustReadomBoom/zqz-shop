package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminBrandController
 * @Date: Created in 9:47 2023-9-1
 */
@RestController
@RequestMapping("/admin/brand")
public class AdminBrandController {
    @Autowired
    private AdminBrandService adminBrandService;


    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String id,
                            String name) {
        return adminBrandService.doQueryList(adminUserId, page, limit, id, name);
    }


    @GetMapping("/catAndAdmin")
    public Object queryCategoryAndAdmin(@AdminLoginUser Integer adminUserId) {
        return adminBrandService.doQueryCategoryAndAdmin(adminUserId);
    }
}
