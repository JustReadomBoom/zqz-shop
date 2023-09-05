package com.zqz.shop.service;

import com.zqz.shop.entity.Role;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminRoleService
 * @Date: Created in 14:40 2023-8-31
 */
public interface AdminRoleService {
    Object doOptions(Integer adminUserId);

    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String roleName);

    Object doCreateInfo(Integer adminUserId, Role role);

    Object doDelete(Integer adminUserId, Role role);

    Object doUpdateInfo(Integer adminUserId, Role role);
}
