package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.UserAddress;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserAddressMapper
 * @Date: Created in 14:41 2023-8-22
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    @Update("<script>"
            + "update user_address set is_default = false, update_time = SYSDATE() "
            + "where user_id = #{userId} "
            + " and deleted = false "
            + " and is_default = true "
            + "</script>")
    int resetDefault(@Param("userId") Integer userId);
}
