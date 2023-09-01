package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAdminService
 * @Date: Created in 14:21 2023-8-31
 */
public interface AdminAdminService {
    Object doQueryList(Integer userId, Integer page, Integer limit, String username);
}
