package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.GoodsProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsProductMapper
 * @Date: Created in 14:24 2023-8-18
 */
public interface GoodsProductMapper extends BaseMapper<GoodsProduct> {

    @Update("<script>"
            + "update goods "
            + "set browse = browse + #{number}, update_time = SYSDATE() "
            + "where id = #{goodsId} "
            + "</script>")
    int updateSales(@Param("goodsId") Integer goodsId, @Param("number") Short number);


    @Update("<script>"
            + "update goods_product "
            + "set number = number - #{number}, update_time = SYSDATE() "
            + "where id = #{id} "
            + "and number >= #{number}"
            + "</script>")
    int reduceStock(@Param("id") Integer id, @Param("number") Short number);

    @Update("<script>"
            + "update goods_product "
            + "set number = number + #{number}, update_time = SYSDATE() "
            + "where id = #{id} "
            + "</script>")
    int addStock(@Param("id") Integer id, @Param("number") Short number);
}
