package com.zqz.shop.service;

import com.zqz.shop.bean.admin.GoodsCreateReq;
import com.zqz.shop.bean.admin.req.GoodsUpdateReq;
import com.zqz.shop.entity.Goods;

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

    Object doDelete(Integer adminUserId, Goods goods);

    Object doDetail(Integer adminUserId, Integer id);

    Object doUpdateInfo(Integer adminUserId, GoodsUpdateReq req);

    Object doCreateInfo(Integer adminUserId, GoodsCreateReq req);

}
