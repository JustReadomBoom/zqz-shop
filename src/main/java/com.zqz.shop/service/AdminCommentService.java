package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminCommentService
 * @Date: Created in 11:22 2023-9-1
 */
public interface AdminCommentService {
    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String userId, String valueId);

}
