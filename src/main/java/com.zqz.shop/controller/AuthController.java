package com.zqz.shop.controller;

import com.zqz.shop.annotation.LoginUser;
import com.zqz.shop.bean.BindPhoneReq;
import com.zqz.shop.bean.WxLoginInfo;
import com.zqz.shop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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


    /**
     * 微信登录
     *
     * @param wxLoginInfo
     * @param request
     * @return
     */
    @PostMapping("/login_by_weixin")
    public Object wxLogin(@RequestBody @Valid WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        return authService.doWxLogin(wxLoginInfo, request);
    }

    /**
     * 绑定手机号
     *
     * @param userId
     * @param req
     * @return
     */
    @PostMapping("/bindPhone")
    public Object bindPhone(@LoginUser Integer userId, @RequestBody BindPhoneReq req) {
        return authService.doBindPhone(userId, req);
    }

}
