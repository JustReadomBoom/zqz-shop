package com.zqz.shop.service.business;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.common.Constant;
import com.zqz.shop.entity.Comment;
import com.zqz.shop.mapper.CommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zqz.shop.entity.table.CommentTableDef.COMMENT;
import static com.zqz.shop.entity.table.GoodsTableDef.GOODS;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CommentBusService
 * @Date: Created in 11:24 2023-9-1
 */
@Service
public class CommentBusService {
    @Resource
    private CommentMapper commentMapper;

    public Page<Comment> queryPage(String userId, String valueId, Integer page, Integer limit) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select().and(Constant.WHERE_ONE_TO_ONE)
                .and(COMMENT.TYPE.ne((byte) 2))
                .and(COMMENT.DELETED.eq(false));
        if (StrUtil.isNotBlank(userId)) {
            wrapper.and(COMMENT.USER_ID.eq(Integer.valueOf(userId)));
        }
        if (StrUtil.isNotBlank(valueId)) {
            wrapper.and(COMMENT.VALUE_ID.eq(Integer.valueOf(valueId))).and(COMMENT.TYPE.eq((byte) 0));
        }
        wrapper.orderBy(COMMENT.ADD_TIME.desc());
        return commentMapper.paginateWithRelations(page, limit, wrapper);
    }

    public Page<Comment> queryBrandPage(List<Integer> brandIds, String userId, String valueId, Integer page, Integer limit) {
        QueryWrapper wrapper = QueryWrapper.create();
        QueryWrapper select = wrapper.select(COMMENT.ALL_COLUMNS)
                .from(COMMENT)
                .leftJoin(GOODS).on(COMMENT.VALUE_ID.eq(GOODS.ID))
                .and(COMMENT.DELETED.eq(false))
                .and(COMMENT.TYPE.eq((byte) 0));
        if (CollectionUtil.isNotEmpty(brandIds)) {
            select.and(GOODS.BRAND_ID.in(brandIds));
        }
        if (StrUtil.isNotBlank(userId)) {
            select.and(COMMENT.USER_ID.eq(userId));
        }
        if (StrUtil.isNotBlank(valueId)) {
            select.and(COMMENT.VALUE_ID.eq(valueId));
        }
        select.orderBy(COMMENT.ADD_TIME.desc());
        return commentMapper.paginateWithRelations(page, limit, select);
    }


    public int logicalDeleteById(Integer id) {
        return commentMapper.logicalDeleteById(id);
    }
}
