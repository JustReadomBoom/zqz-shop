package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsService
 * @Date: Created in 15:40 2023-8-16
 */
public interface GoodsService {
    Object doQueryCount();

    Object doQueryByCategoryId(Integer id);

    Object doQueryListPage(Integer categoryId, String keyword, Boolean isNew, Boolean isHot, Integer page, Integer size);

    Object doQueryDetail(Integer id, Integer userId);
}
