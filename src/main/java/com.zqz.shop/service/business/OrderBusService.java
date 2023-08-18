package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Order;
import com.zqz.shop.mapper.OrderMapper;
import com.zqz.shop.utils.OrderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zqz.shop.entity.table.OrderTableDef.ORDER;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderBusService
 * @Date: Created in 16:36 2023-8-15
 */
@Service
public class OrderBusService {
    @Resource
    private OrderMapper orderMapper;

    public Map<String, Object> getOrderInfoByUserId(Integer userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(ORDER.USER_ID.eq(userId))
                .and(ORDER.DELETED.eq(false));
        List<Order> orders = orderMapper.selectListByQuery(wrapper);
        if (CollectionUtil.isNotEmpty(orders)) {
            int unpaid = 0;
            int unship = 0;
            int unrecv = 0;
            int uncomment = 0;
            for (Order order : orders) {
                if (OrderUtil.isCreateStatus(order)) {
                    unpaid++;
                } else if (OrderUtil.isPayStatus(order)) {
                    unship++;
                } else if (OrderUtil.isShipStatus(order)) {
                    unrecv++;
                } else if (OrderUtil.isConfirmStatus(order) || OrderUtil.isAutoConfirmStatus(order)) {
                    uncomment += order.getComments();
                } else {
                    // do nothing
                }
            }

            Map<String, Object> orderInfo = new HashMap<>(4);
            orderInfo.put("unpaid", unpaid);
            orderInfo.put("unship", unship);
            orderInfo.put("unrecv", unrecv);
            orderInfo.put("uncomment", uncomment);
            return orderInfo;
        }
        return null;
    }

    public Page<Order> queryByOrderStatus(Integer userId, List<Short> orderStatus, Integer page, Integer size) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(ORDER.USER_ID.eq(userId))
                .and(ORDER.DELETED.eq(false));

        if (CollectionUtil.isNotEmpty(orderStatus)) {
            wrapper.and(ORDER.ORDER_STATUS.in(orderStatus));
        }
        wrapper.orderBy(ORDER.ADD_TIME.desc());
        return orderMapper.paginateWithRelations(page, size, wrapper);
    }
}
