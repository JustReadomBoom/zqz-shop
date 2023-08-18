package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.Order;
import com.zqz.shop.entity.OrderGoods;
import com.zqz.shop.service.OrderService;
import com.zqz.shop.service.business.OrderBusService;
import com.zqz.shop.service.business.OrderGoodsBusService;
import com.zqz.shop.utils.OrderUtil;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderServiceImpl
 * @Date: Created in 16:58 2023-8-15
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderBusService orderBusService;
    @Autowired
    private OrderGoodsBusService orderGoodsBusService;

    @Override
    public Object doQueryList(Integer userId, Integer showType, Integer page, Integer size) {
        Map<String, Object> result = new HashMap<>(3);
        List<Short> orderStatus = OrderUtil.orderStatus(showType);
        Page<Order> orderPage = orderBusService.queryByOrderStatus(userId, orderStatus, page, size);
        List<Order> orderRecords = orderPage.getRecords();
        long totalRow = orderPage.getTotalRow();
        long totalPage = orderPage.getTotalPage();
        if (CollectionUtil.isNotEmpty(orderRecords)) {
            List<Map<String, Object>> orderVoList = new ArrayList<>(orderRecords.size());
            for (Order order : orderRecords) {
                Map<String, Object> orderVo = new HashMap<>(14);
                orderVo.put("id", order.getId());
                orderVo.put("orderSn", order.getOrderSn());
                orderVo.put("addTime", order.getAddTime());
                orderVo.put("consignee", order.getConsignee());
                orderVo.put("mobile", order.getMobile());
                orderVo.put("address", order.getAddress());
                orderVo.put("goodsPrice", order.getGoodsPrice());
                orderVo.put("freightPrice", order.getFreightPrice());
                orderVo.put("actualPrice", order.getActualPrice());
                orderVo.put("orderStatusText", OrderUtil.orderStatusText(order));
                orderVo.put("handleOption", OrderUtil.build(order));
                orderVo.put("expCode", order.getShipChannel());
                orderVo.put("expNo", order.getShipSn());

                List<OrderGoods> orderGoodsList = orderGoodsBusService.queryByOrderId(order.getId());
                orderVo.put("goodsList", orderGoodsList);

                orderVoList.add(orderVo);
            }

            result.put("count", totalRow);
            result.put("data", orderVoList);
            result.put("totalPages", totalPage);
        }
        return ResponseUtil.ok(result);
    }
}
