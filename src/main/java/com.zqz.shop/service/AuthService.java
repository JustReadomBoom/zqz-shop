package com.zqz.shop.service;

import com.zqz.shop.bean.req.BindPhoneReq;
import com.zqz.shop.bean.WxLoginInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AuthService
 * @Date: Created in 10:10 2023-8-11
 */
public interface AuthService {

    Object doWxLogin(WxLoginInfo wxLoginInfo, HttpServletRequest request);

    Object doBindPhone(Integer userId, BindPhoneReq req);
}
