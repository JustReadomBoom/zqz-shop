package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zqz.shop.entity.SearchHistory;
import com.zqz.shop.entity.SysKeyword;
import com.zqz.shop.service.SearchService;
import com.zqz.shop.service.business.SearchBusService;
import com.zqz.shop.service.business.SysKeywordBusService;
import com.zqz.shop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SearchServiceImpl
 * @Date: Created in 17:28 2023-8-18
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchBusService searchBusService;
    @Autowired
    private SysKeywordBusService keywordBusService;

    @Override
    public Object doIndex(Integer userId) {
        //默认关键词
        SysKeyword defaultKeyword = keywordBusService.queryDefault();
        //热门关键词
        List<SysKeyword> hotKeywordList = keywordBusService.queryHots();

        List<SearchHistory> historyList;
        if (ObjectUtil.isNotEmpty(userId)) {
        // 取出用户历史关键字
            historyList = searchBusService.queryByUserId(userId);
        } else {
            historyList = new ArrayList<>(0);
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put("defaultKeyword", defaultKeyword);
        data.put("historyKeywordList", historyList);
        data.put("hotKeywordList", hotKeywordList);
        return ResponseUtil.ok(data);
    }
}
