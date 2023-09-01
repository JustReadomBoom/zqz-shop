package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.bean.admin.CategorySellAmts;
import com.zqz.shop.bean.admin.DayStatis;
import com.zqz.shop.entity.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderMapper
 * @Date: Created in 16:36 2023-8-15
 */
public interface OrderMapper extends BaseMapper<Order> {


    @Select("select date_format(t.add_time, '%Y-%m-%d') as dayStr, count(1) as cnts, sum(actual_price) as amts " +
            " from `order` t where t.add_time > date_add(now(), interval - #{days} day) " +
            " and t.deleted = 0 and t.order_status not in(101, 102, 103) " +
            " group by date_format(t.add_time, '%Y-%m-%d')")
    List<DayStatis> queryRecentCount(@Param("days") Integer days);

    @Select("select pc.name as name, sum(og.price) as value from order_goods og " +
            " join `order` o on o.id = og.order_id " +
            " join goods g on g.id = og.goods_id " +
            " join category c on g.category_id = c.id " +
            " join category pc on pc.id = c.pid " +
            " where og.deleted = 0 and o.order_status not in(101, 102, 103) " +
            " group by pc.name ")
    List<CategorySellAmts> categorySell();


    @Select(" select " +
            " substr(add_time, 1, 10) as day," +
            " count(id) as orders," +
            " count(distinct user_id) as customers," +
            " sum(actual_price) as amount," +
            " round(sum(actual_price)/count(distinct user_id), 2) as pcr" +
            " from `order` " +
            " group by substr(add_time, 1, 10)")
    List<Map> statOrder();

}
