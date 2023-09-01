package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminStatService
 * @Date: Created in 13:49 2023-8-30
 */
public interface AdminStatService {
    Object doStatUser(Integer userId);

    Object doStatOrder(Integer userId);

    Object doStatGoods(Integer userId);
}
