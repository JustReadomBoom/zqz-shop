package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminRoleService
 * @Date: Created in 14:40 2023-8-31
 */
public interface AdminRoleService {
    Object doOptions(Integer adminUserId);

    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String roleName);
}
