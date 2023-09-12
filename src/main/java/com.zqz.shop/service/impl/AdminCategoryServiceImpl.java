package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.admin.resp.PageQueryResp;
import com.zqz.shop.bean.admin.resp.ValueLabelResp;
import com.zqz.shop.entity.Category;
import com.zqz.shop.enums.CategoryLevelEnum;
import com.zqz.shop.service.AdminCategoryService;
import com.zqz.shop.service.business.CategoryBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        PageQueryResp<Category> queryResp = new PageQueryResp<>();
        Page<Category> categoryPage = categoryBusService.queryPage(page, limit, id, name);
        queryResp.setTotal(categoryPage.getTotalRow());
        queryResp.setItems(categoryPage.getRecords());
        return ResponseUtil.ok(queryResp);
    }

    @Override
    public Object doCategoryL1(Integer adminUserId) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        List<Category> categoryList = categoryBusService.queryL1();
        List<ValueLabelResp> data = new ArrayList<>(categoryList.size());
        for (Category category : categoryList) {
            ValueLabelResp labelResp = new ValueLabelResp();
            labelResp.setValue(category.getId());
            labelResp.setLabel(category.getName());
            data.add(labelResp);
        }
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doCreateInfo(Integer adminUserId, Category category) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        String name = category.getName();
        if (StrUtil.isBlank(name)) {
            return ResponseUtil.badArgument();
        }

        String level = category.getLevel();
        if (StrUtil.isBlank(level)) {
            return ResponseUtil.badArgument();
        }
        if (ObjectUtil.isEmpty(CategoryLevelEnum.match(level))) {
            return ResponseUtil.badArgument();
        }

        Integer pid = category.getPid();
        if (CategoryLevelEnum.L2.getLevel().equals(level) && (pid == null)) {
            return ResponseUtil.badArgument();
        }

        int add = categoryBusService.add(category);
        if (add <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doDeleteInfo(Integer adminUserId, Category category) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Integer id = category.getId();
        if (ObjectUtil.isEmpty(id)) {
            return ResponseUtil.badArgument();
        }
        int delete = categoryBusService.logicalDeleteById(id);
        if (delete <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doUpdateInfo(Integer adminUserId, Category category) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        if (ObjectUtil.isNotEmpty(validate(category))) {
            return ResponseUtil.badArgument();
        }
        int update = categoryBusService.updateById(category);
        if (update <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    private Object validate(Category category) {
        String name = category.getName();
        if (StrUtil.isBlank(name)) {
            return ResponseUtil.badArgument();
        }

        String level = category.getLevel();
        if (StrUtil.isBlank(level)) {
            return ResponseUtil.badArgument();
        }
        if (ObjectUtil.isEmpty(CategoryLevelEnum.match(level))) {
            return ResponseUtil.badArgumentValue();
        }

        Integer pid = category.getPid();
        if (CategoryLevelEnum.L2.getLevel().equals(level) && ObjectUtil.isEmpty(pid)) {
            return ResponseUtil.badArgument();
        }
        return null;
    }
}
