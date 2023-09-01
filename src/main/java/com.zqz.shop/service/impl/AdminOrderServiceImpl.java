package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.admin.UserVo;
import com.zqz.shop.entity.Order;
import com.zqz.shop.entity.OrderGoods;
import com.zqz.shop.service.AdminGoodsService;
import com.zqz.shop.service.AdminOrderService;
import com.zqz.shop.service.business.OrderBusService;
import com.zqz.shop.service.business.OrderGoodsBusService;
import com.zqz.shop.service.business.UserBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminOrderServiceImpl
 * @Date: Created in 17:20 2023-8-31
 */
@Service
@Slf4j
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    private AdminGoodsService adminGoodsService;
    @Autowired
    private OrderBusService orderBusService;
    @Autowired
    private OrderGoodsBusService orderGoodsBusService;
    @Autowired
    private UserBusService userBusService;

    @Override
    public Object doQueryList(Integer userId, Integer adminUserId, Integer page, Integer limit, List<Short> orderStatusArray, String orderSn) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        List<Integer> brandIds = null;
        if (adminGoodsService.isBrandManager(adminUserId)) {
            brandIds = adminGoodsService.getBrandIds(userId);
            if (CollectionUtil.isEmpty(brandIds)) {
                Map<String, Object> data = new HashMap<>(2);
                data.put("total", 0L);
                data.put("items", null);
                return ResponseUtil.ok(data);
            }
        }
        List<Order> orderList;
        long total;
        if (CollectionUtil.isEmpty(brandIds)) {
            Page<Order> orderPage = orderBusService.queryPage(userId, orderSn, orderStatusArray, page, limit);
            orderList = orderPage.getRecords();
            total = orderPage.getTotalRow();
        } else {
            Page<Order> brandPage = orderBusService.queryBrandPage(brandIds, userId, orderSn, orderStatusArray, page, limit);
            orderList = brandPage.getRecords();
            total = brandPage.getTotalRow();
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put("total", total);
        data.put("items", orderList);
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doListShipChannel(Integer adminUserId) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        //todo 物流信息
        return ResponseUtil.ok();
    }

    @Override
    public Object doQueryDetail(Integer adminUserId, Integer id) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Order order = orderBusService.queryById(id);
        if (ObjectUtil.isEmpty(order)) {
            return ResponseUtil.dataEmpty();
        }
        List<OrderGoods> orderGoods = orderGoodsBusService.queryByOrderId(id);
        if (CollectionUtil.isEmpty(orderGoods)) {
            return ResponseUtil.dataEmpty();
        }
        UserVo userVo = userBusService.queryUserVoById(order.getUserId());
        Map<String, Object> data = new HashMap<>(3);
        data.put("order", order);
        data.put("orderGoods", orderGoods);
        data.put("user", userVo);
        return ResponseUtil.ok(data);
    }
}
