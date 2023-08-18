package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderService
 * @Date: Created in 16:58 2023-8-15
 */
public interface OrderService {
    Object doQueryList(Integer userId, Integer showType, Integer page, Integer size);
}
