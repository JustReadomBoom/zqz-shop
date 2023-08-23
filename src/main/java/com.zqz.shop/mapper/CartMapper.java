package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartMapper
 * @Date: Created in 14:54 2023-8-18
 */
public interface CartMapper extends BaseMapper<Cart> {


    @Update("<script>"
            + "update cart set checked=#{checked}, update_time=SYSDATE() "
            + "where product_id in "
            + "<foreach item='id' index='index' collection='productIds' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + " and user_id = #{userId} "
            + " and deleted = #{deleted}"
            + "</script>")
    int updateByProductIds(@Param("checked") boolean checked,
                           @Param("productIds") List<Integer> productIds,
                           @Param("userId") Integer userId,
                           @Param("deleted") boolean deleted);

    @Delete("<script>"
            + "delete from cart "
            + "where product_id in "
            + "<foreach item='id' index='index' collection='productIds' open='(' separator=',' close=')'>"
            + "#{id}"
            + "</foreach>"
            + " and user_id = #{userId} "
            + "</script>")
    int deleteByProductIds(@Param("userId") Integer userId,
                           @Param("productIds") List<Integer> productIds);


    @Update("<script>"
            + "update cart "
            + "set deleted = true "
            + "where user_id = #{userId} "
            + "and checked = true"
            + "</script>")
    int deleteByUserId(@Param("userId") Integer userId);

}
