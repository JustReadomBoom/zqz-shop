package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.SysKeyword;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SysKeywordMapper
 * @Date: Created in 17:31 2023-8-18
 */
public interface SysKeywordMapper extends BaseMapper<SysKeyword> {

    @Delete("update sys_keyword set deleted = 1, update_time = now() where id = #{id}")
    int logicalDeleteById(@Param("id") Integer id);
}
