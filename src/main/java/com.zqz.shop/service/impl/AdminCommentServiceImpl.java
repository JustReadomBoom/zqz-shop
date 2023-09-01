package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.Comment;
import com.zqz.shop.service.AdminCommentService;
import com.zqz.shop.service.AdminGoodsService;
import com.zqz.shop.service.business.CommentBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminCommentServiceImpl
 * @Date: Created in 11:22 2023-9-1
 */
@Service
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {
    @Autowired
    private CommentBusService commentBusService;
    @Autowired
    private AdminGoodsService adminGoodsService;


    @Override
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String userId, String valueId) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Map<String, Object> data = new HashMap<>(2);
        List<Integer> brandIds = null;
        if (adminGoodsService.isBrandManager(adminUserId)) {
            brandIds = adminGoodsService.getBrandIds(adminUserId);
            if (CollectionUtil.isEmpty(brandIds)) {
                data.put("total", 0L);
                data.put("items", null);
                return ResponseUtil.ok(data);
            }
        }
        Page<Comment> commentPage;
        List<Comment> commentList;
        long total;
        if (CollectionUtil.isEmpty(brandIds)) {
            commentPage = commentBusService.queryPage(userId, valueId, page, limit);
            commentList = commentPage.getRecords();
            total = commentPage.getTotalRow();
        } else {
            commentPage = commentBusService.queryBrandPage(brandIds, userId, valueId, page, limit);
            commentList = commentPage.getRecords();
            total = commentPage.getTotalRow();
        }
        data.put("total", total);
        data.put("items", commentList);
        return ResponseUtil.ok(data);
    }
}
