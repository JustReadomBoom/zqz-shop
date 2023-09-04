package com.zqz.shop.mapper;

import com.mybatisflex.core.BaseMapper;
import com.zqz.shop.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CommentMapper
 * @Date: Created in 11:24 2023-9-1
 */
public interface CommentMapper extends BaseMapper<Comment> {

    @Update("update comment set deleted = 1 where id = #{commentId}")
    int logicalDeleteById(@Param("commentId") Integer commentId);
}
