package com.zqz.shop.controller;

import com.zqz.shop.bean.WxLoginInfo;
import com.zqz.shop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AuthController
 * @Date: Created in 10:08 2023-8-11
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        return authService.doLogin(body, request);
    }

    @PostMapping("/login_by_weixin")
    public Object wxLogin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        return authService.doWxLogin(wxLoginInfo, request);
    }

}
