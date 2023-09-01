package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.Category;
import com.zqz.shop.service.AdminCategoryService;
import com.zqz.shop.service.business.CategoryBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminCategoryServiceImpl
 * @Date: Created in 9:26 2023-9-1
 */
@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryBusService categoryBusService;

    @Override
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String id, String name) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Page<Category> categoryPage = categoryBusService.queryPage(page, limit, id, name);
        Map<String, Object> data = new HashMap<>();
        data.put("total", categoryPage.getTotalRow());
        data.put("items", categoryPage.getRecords());
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doCategoryL1(Integer adminUserId) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        List<Category> categoryList = categoryBusService.queryL1();
        List<Map<String, Object>> data = new ArrayList<>(categoryList.size());
        for (Category category : categoryList) {
            Map<String, Object> d = new HashMap<>(2);
            d.put("value", category.getId());
            d.put("label", category.getName());
            data.add(d);
        }
        return ResponseUtil.ok(data);
    }
}
