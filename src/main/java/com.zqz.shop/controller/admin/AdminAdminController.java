package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.bean.admin.req.AdminUpdateReq;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.service.AdminAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Object queryList(@AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String username) {
        return adminAdminService.doQueryList(adminUserId, page, limit, username);
    }


    @PostMapping("/delete")
    public Object deleteInfo(@AdminLoginUser Integer adminUserId,
                             @RequestBody Admin admin) {
        return adminAdminService.doDeleteInfo(adminUserId, admin);
    }


    @PostMapping("/update")
    public Object updateInfo(@AdminLoginUser Integer adminUserId,
                             @RequestBody AdminUpdateReq admin) {
        return adminAdminService.doUpdateInfo(adminUserId, admin);
    }
}
