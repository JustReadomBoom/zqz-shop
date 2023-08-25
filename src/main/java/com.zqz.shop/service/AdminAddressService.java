package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAddressService
 * @Date: Created in 14:36 2023-8-25
 */
public interface AdminAddressService {
    Object doList(Integer userId, Integer page, Integer limit, Integer queryUserId, String name);
}
