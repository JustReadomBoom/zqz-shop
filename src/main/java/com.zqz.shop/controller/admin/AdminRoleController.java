package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.entity.Role;
import com.zqz.shop.service.AdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/create")
    public Object createInfo(@AdminLoginUser Integer adminUserId,
                             @RequestBody Role role) {
        return adminRoleService.doCreateInfo(adminUserId, role);
    }

    @PostMapping("/delete")
    public Object delete(@AdminLoginUser Integer adminUserId,
                         @RequestBody Role role) {
        return adminRoleService.doDelete(adminUserId, role);
    }

    @PostMapping("/update")
    public Object updateInfo(@AdminLoginUser Integer adminUserId,
                         @RequestBody Role role) {
        return adminRoleService.doUpdateInfo(adminUserId, role);
    }
}
