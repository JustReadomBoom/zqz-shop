package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminBrandService
 * @Date: Created in 9:51 2023-9-1
 */
public interface AdminBrandService {
    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String id, String name);

    Object doQueryCategoryAndAdmin(Integer adminUserId);


}
