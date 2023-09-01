package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.OrderGoods;
import com.zqz.shop.mapper.OrderGoodsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.zqz.shop.entity.table.OrderGoodsTableDef.ORDER_GOODS;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderGoodsBusService
 * @Date: Created in 17:31 2023-8-15
 */
@Service
public class OrderGoodsBusService {
    @Resource
    private OrderGoodsMapper orderGoodsMapper;


    public List<OrderGoods> queryByOrderId(Integer orderId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(ORDER_GOODS.DELETED.eq(false))
                .and(ORDER_GOODS.ORDER_ID.eq(orderId));
        return orderGoodsMapper.selectListByQuery(wrapper);
    }

    public int add(OrderGoods orderGoods) {
        return orderGoodsMapper.insertSelective(orderGoods);
    }

    public List<Map> statGoods() {
        return orderGoodsMapper.statGoods();
    }
}
