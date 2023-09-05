package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminMapper
 * @Date: Created in 11:05 2023-8-25
 */
public interface AdminMapper extends BaseMapper<Admin> {

    @Delete("update `admin` set deleted = 1, update_time = now() where id = #{id}")
    int logicalDeleteById(@Param("id") Integer id);
}
