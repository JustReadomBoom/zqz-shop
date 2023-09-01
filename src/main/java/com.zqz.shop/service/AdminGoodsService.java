package com.zqz.shop.service;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminGoodsService
 * @Date: Created in 14:36 2023-8-30
 */
public interface AdminGoodsService {
    Object doQueryList(Integer userId, Integer page, Integer limit, String goodsSn, String name);

    Object doCatAndBrand(Integer userId);

    boolean isBrandManager(Integer userId);

    List<Integer> getBrandIds(Integer userId);
}
