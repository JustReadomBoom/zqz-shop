package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.bean.admin.DayStatis;
import com.zqz.shop.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserMapper
 * @Date: Created in 10:21 2023-8-11
 */
public interface UserMapper extends BaseMapper<User> {


    @Select("select date_format(t.add_time,\"%Y-%m-%d\") as dayStr, count(1) as cnts " +
            " from user t where t.add_time > date_add(now(), interval - #{days} day) " +
            " and t.deleted = 0 " +
            " group by date_format(t.add_time,\"%Y-%m-%d\")")
    List<DayStatis> queryRecentCount(@Param("days") Integer days);


    @Select("select " +
            "substr(add_time, 1, 10) as day," +
            "count(distinct id) as users " +
            "from user " +
            "group by substr(add_time, 1, 10)")
    List<Map> statUser();

}
