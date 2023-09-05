package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: RoleMapper
 * @Date: Created in 14:48 2023-8-29
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Delete("update role set deleted = 1, update_time = now() where id = #{id}")
    int logicalDeleteById(@Param("id") Integer id);
}
