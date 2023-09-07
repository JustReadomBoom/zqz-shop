package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CategoryMapper
 * @Date: Created in 16:26 2023-8-4
 */
public interface CategoryMapper extends BaseMapper<Category> {

    @Update("update category set deleted = 1, update_time = now() where id = #{id}")
    int logicalDeleteById(@Param("id") Integer id);
}
