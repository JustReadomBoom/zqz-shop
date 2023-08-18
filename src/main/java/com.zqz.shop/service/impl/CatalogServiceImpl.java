package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zqz.shop.entity.Category;
import com.zqz.shop.service.CatalogService;
import com.zqz.shop.service.business.CategoryBusService;
import com.zqz.shop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CatalogServiceImpl
 * @Date: Created in 16:51 2023-8-18
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CategoryBusService categoryBusService;


    @Override
    public Object doIndex(Integer id) {
        // 所有一级分类目录
        List<Category> l1CategoryList = categoryBusService.queryL1();
        Category currentCategory;
        if (ObjectUtil.isNotEmpty(id)) {
            currentCategory = categoryBusService.queryById(id);
        } else {
            currentCategory = l1CategoryList.get(0);
        }
        // 当前一级分类目录对应的二级分类目录
        List<Category> currentSubCategory = null;
        if (ObjectUtil.isNotEmpty(currentCategory)) {
            currentSubCategory = categoryBusService.queryByParentId(currentCategory.getId());
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put("categoryList", l1CategoryList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doCurrent(Integer id) {
        //当前分类
        Category currentCategory = categoryBusService.queryById(id);
        if(ObjectUtil.isEmpty(currentCategory)){
            return ResponseUtil.ok();
        }
        Map<String, Object> data = new HashMap<>(2);
        List<Category> currentSubCategory = categoryBusService.queryByParentId(currentCategory.getId());
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        return ResponseUtil.ok(data);
    }
}
