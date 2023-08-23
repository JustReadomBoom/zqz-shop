package com.zqz.shop.service;


import com.zqz.shop.entity.UserAddress;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserAddressService
 * @Date: Created in 14:42 2023-8-22
 */
public interface UserAddressService {
    Object doQueryList(Integer userId);

    Object doAddAddress(Integer userId, UserAddress address);
}
