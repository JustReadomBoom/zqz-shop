package com.zqz.shop.service;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminOrderService
 * @Date: Created in 17:20 2023-8-31
 */
public interface AdminOrderService {
    Object doQueryList(Integer userId, Integer adminUserId, Integer page, Integer limit, List<Short> orderStatusArray, String orderSn);

    Object doListShipChannel(Integer adminUserId);

    Object doQueryDetail(Integer adminUserId, Integer id);
}
