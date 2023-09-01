package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminUserService
 * @Date: Created in 14:24 2023-8-30
 */
public interface AdminUserService {
    Object doQueryList(Integer userId, Integer page, Integer limit, String username, String mobile);
}
