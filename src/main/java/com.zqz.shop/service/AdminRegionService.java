package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminRegionService
 * @Date: Created in 9:02 2023-9-1
 */
public interface AdminRegionService {
    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String name, String code);
}
