package com.zqz.shop.service;

import com.zqz.shop.bean.admin.req.AdminUpdateReq;
import com.zqz.shop.entity.Admin;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAdminService
 * @Date: Created in 14:21 2023-8-31
 */
public interface AdminAdminService {
    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String username);

    Object doDeleteInfo(Integer adminUserId, Admin admin);

    Object doUpdateInfo(Integer adminUserId, AdminUpdateReq admin);
}
