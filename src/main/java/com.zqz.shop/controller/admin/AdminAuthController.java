package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.bean.admin.AdminLoginReq;
import com.zqz.shop.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAuthController
 * @Date: Created in 9:58 2023-8-25
 */
@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {
    @Autowired
    private AdminAuthService adminAuthService;


    @PostMapping("/login")
    public Object login(@RequestBody @Valid AdminLoginReq req) {
        return adminAuthService.doLogin(req);
    }

    @GetMapping("/captchaImage")
    public Object getImgCode(HttpServletResponse response) {
        return adminAuthService.doGetImgCode();
    }

    @GetMapping("/info")
    public Object info(@AdminLoginUser Integer userId) {
        return adminAuthService.doInfo(userId);
    }
}
