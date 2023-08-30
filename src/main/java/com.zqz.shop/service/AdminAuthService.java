package com.zqz.shop.service;

import com.zqz.shop.bean.admin.AdminLoginReq;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAuthService
 * @Date: Created in 10:08 2023-8-25
 */
public interface AdminAuthService {
    Object doLogin(AdminLoginReq req);

    Object doGetImgCode();

    Object doInfo(Integer userId);
}
