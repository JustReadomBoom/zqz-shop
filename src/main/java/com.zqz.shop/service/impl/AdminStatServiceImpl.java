package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zqz.shop.bean.admin.StatVo;
import com.zqz.shop.service.AdminStatService;
import com.zqz.shop.service.business.OrderBusService;
import com.zqz.shop.service.business.OrderGoodsBusService;
import com.zqz.shop.service.business.UserBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminStatServiceImpl
 * @Date: Created in 13:49 2023-8-30
 */
@Service
@Slf4j
public class AdminStatServiceImpl implements AdminStatService {
    @Autowired
    private UserBusService userBusService;
    @Autowired
    private OrderBusService orderBusService;
    @Autowired
    private OrderGoodsBusService orderGoodsBusService;

    @Override
    public Object doStatUser(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Map> statUser = userBusService.statUser();
        String[] columns = new String[]{"day", "users"};
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(statUser);
        return ResponseUtil.ok(statVo);
    }

    @Override
    public Object doStatOrder(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Map> statOrder = orderBusService.statOrder();
        String[] columns = new String[] { "day", "orders", "customers", "amount", "pcr" };
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(statOrder);
        return ResponseUtil.ok(statVo);
    }

    @Override
    public Object doStatGoods(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Map> statGoods = orderGoodsBusService.statGoods();
        String[] columns = new String[] { "day", "orders", "products", "amount" };
        StatVo statVo = new StatVo();
        statVo.setColumns(columns);
        statVo.setRows(statGoods);
        return ResponseUtil.ok(statVo);
    }
}
