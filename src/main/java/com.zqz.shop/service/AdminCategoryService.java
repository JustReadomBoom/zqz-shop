package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminCategoryService
 * @Date: Created in 9:25 2023-9-1
 */
public interface AdminCategoryService {
    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String id, String name);

    Object doCategoryL1(Integer adminUserId);

}
