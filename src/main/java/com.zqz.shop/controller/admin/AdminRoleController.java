package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminRoleController
 * @Date: Created in 14:39 2023-8-31
 */
@RestController
@RequestMapping("/admin/role")
public class AdminRoleController {
    @Autowired
    private AdminRoleService adminRoleService;


    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String rolename) {
        return adminRoleService.doQueryList(adminUserId, page, limit, rolename);
    }


    @GetMapping("/options")
    public Object options(@AdminLoginUser Integer adminUserId) {
        return adminRoleService.doOptions(adminUserId);
    }
}
