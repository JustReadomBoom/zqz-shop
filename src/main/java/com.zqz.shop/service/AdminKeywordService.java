package com.zqz.shop.service;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminKeywordService
 * @Date: Created in 11:05 2023-9-1
 */
public interface AdminKeywordService {
    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String keyword, String url);
}
