package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import com.mybatisflex.core.util.UpdateEntity;
import com.zqz.shop.bean.admin.CategorySellAmts;
import com.zqz.shop.bean.admin.DayStatis;
import com.zqz.shop.entity.Order;
import com.zqz.shop.mapper.OrderMapper;
import com.zqz.shop.utils.OrderUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zqz.shop.entity.table.OrderGoodsTableDef.ORDER_GOODS;
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

    public String generateOrderSn(Integer userId) {
        String now = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String orderSn = now + RandomUtil.randomNumbers(6);
        while (countByOrderSn(userId, orderSn) != 0) {
            orderSn = RandomUtil.randomNumbers(6);
        }
        return orderSn;
    }

    public int countByOrderSn(Integer userId, String orderSn) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ORDER.USER_ID.eq(userId))
                .and(ORDER.ORDER_SN.eq(orderSn))
                .and(ORDER.DELETED.eq(false));
        return (int) orderMapper.selectCountByQuery(wrapper);
    }

    public int add(Order order) {
        return orderMapper.insertSelective(order);
    }

    public Order queryById(Integer orderId) {
        return orderMapper.selectOneById(orderId);
    }

    public int updateOrder(Order order) {
        order.setUpdateTime(new Date());
        return orderMapper.update(order);
    }

    public int deleteById(Integer orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setUpdateTime(new Date());
        order.setDeleted(true);
        return orderMapper.update(order);
    }

    public List<DayStatis> queryRecentCount(Integer days) {
        return orderMapper.queryRecentCount(days);
    }

    public List<CategorySellAmts> categorySell() {
        return orderMapper.categorySell();
    }

    public Integer queryCount() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .and(ORDER.DELETED.eq(false));
        return (int) orderMapper.selectCountByQuery(wrapper);

    }

    public List<Map> statOrder() {
        return orderMapper.statOrder();
    }


    public Page<Order> queryPage(Integer userId, String orderSn, List<Short> orderStatusArray, Integer page, Integer limit) {
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select().and("1 = 1");
        if (ObjectUtil.isNotEmpty(userId)) {
            select = select.and(ORDER.USER_ID.eq(userId));
        }
        if (StrUtil.isNotBlank(orderSn)) {
            select = select.and(ORDER.ORDER_SN.eq(orderSn));
        }
        if (CollectionUtil.isNotEmpty(orderStatusArray)) {
            select = select.and(ORDER.ORDER_STATUS.in(orderStatusArray));
        }
        select.and(ORDER.DELETED.eq(false))
                .orderBy(ORDER.ADD_TIME.desc());
        return orderMapper.paginateWithRelations(page, limit, select);
    }


    public Page<Order> queryBrandPage(List<Integer> brandIds, Integer userId, String orderSn, List<Short> orderStatusArray, Integer page, Integer limit) {
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select(ORDER.ALL_COLUMNS)
                .from(ORDER)
                .leftJoin(ORDER_GOODS).on(ORDER.ID.eq(ORDER_GOODS.GOODS_ID))
                .and(ORDER.DELETED.eq(false));
        if (ObjectUtil.isNotEmpty(userId)) {
            select = select.and(ORDER.USER_ID.eq(userId));
        }
        if (StrUtil.isNotBlank(orderSn)) {
            select = select.and(ORDER.ORDER_SN.eq(orderSn));
        }
        if (CollectionUtil.isNotEmpty(orderStatusArray)) {
            select = select.and(ORDER.ORDER_STATUS.in(orderStatusArray));
        }
        if(CollectionUtil.isNotEmpty(brandIds)){
            select = select.and(ORDER_GOODS.BRAND_ID.in(brandIds));
        }
        select.orderBy(ORDER.ADD_TIME.desc());
        return orderMapper.paginateWithRelations(page, limit, select);


    }
}
