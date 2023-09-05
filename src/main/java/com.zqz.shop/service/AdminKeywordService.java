package com.zqz.shop.service;

import com.zqz.shop.entity.SysKeyword;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminKeywordService
 * @Date: Created in 11:05 2023-9-1
 */
public interface AdminKeywordService {
    Object doQueryList(Integer adminUserId, Integer page, Integer limit, String keyword, String url);

    Object doDelete(Integer adminUserId, SysKeyword keyword);

    Object doUpdateInfo(Integer adminUserId, SysKeyword keyword);

    Object doCreateInfo(Integer adminUserId, SysKeyword keyword);
}
