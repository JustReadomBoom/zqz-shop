package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.SysKeyword;
import com.zqz.shop.service.AdminKeywordService;
import com.zqz.shop.service.business.SysKeywordBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminKeywordServiceImpl
 * @Date: Created in 11:05 2023-9-1
 */
@Service
@Slf4j
public class AdminKeywordServiceImpl implements AdminKeywordService {
    @Autowired
    private SysKeywordBusService keywordBusService;

    @Override
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String keyword, String url) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Page<SysKeyword> keywordPage = keywordBusService.queryPage(page, limit, keyword, url);
        Map<String, Object> data = new HashMap<>(2);
        data.put("total", keywordPage.getTotalRow());
        data.put("items", keywordPage.getRecords());
        return ResponseUtil.ok(data);
    }
}
