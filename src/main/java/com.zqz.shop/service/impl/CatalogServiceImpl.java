package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zqz.shop.bean.resp.CatalogCurrentResp;
import com.zqz.shop.bean.resp.CatalogIndexResp;
import com.zqz.shop.entity.Category;
import com.zqz.shop.service.CatalogService;
import com.zqz.shop.service.business.CategoryBusService;
import com.zqz.shop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        CatalogIndexResp indexResp = new CatalogIndexResp();
        indexResp.setCategoryList(l1CategoryList);
        indexResp.setCurrentCategory(currentCategory);
        indexResp.setCurrentSubCategory(currentSubCategory);
        return ResponseUtil.ok(indexResp);
    }

    @Override
    public Object doCurrent(Integer id) {
        //当前分类
        Category currentCategory = categoryBusService.queryById(id);
        if(ObjectUtil.isEmpty(currentCategory)){
            return ResponseUtil.ok();
        }
        CatalogCurrentResp currentResp = new CatalogCurrentResp();
        List<Category> currentSubCategory = categoryBusService.queryByParentId(currentCategory.getId());
        currentResp.setCurrentCategory(currentCategory);
        currentResp.setCurrentSubCategory(currentSubCategory);
        return ResponseUtil.ok(currentResp);
    }
}
