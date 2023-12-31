package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.OrderGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderGoodsMapper
 * @Date: Created in 17:29 2023-8-15
 */
public interface OrderGoodsMapper extends BaseMapper<OrderGoods> {

    @Select("select " +
            "substr(add_time, 1, 10) as day," +
            "count(distinct order_id) as orders," +
            "sum(number) as products," +
            "sum(number*price) as amount " +
            "from order_goods " +
            "group by substr(add_time, 1, 10)")
    List<Map> statGoods();

    @Update("update order_goods set deleted = 1, update_time = now() where order_id = #{orderId}")
    int logicalDeleteByOrderId(@Param("orderId") Integer orderId);
}
