package com.zqz.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.GoodInfo;
import com.zqz.shop.bean.GoodsProductVo;
import com.zqz.shop.bean.GoodsSpecificationVo;
import com.zqz.shop.entity.Category;
import com.zqz.shop.entity.Goods;
import com.zqz.shop.entity.GoodsAttribute;
import com.zqz.shop.entity.GoodsProduct;
import com.zqz.shop.service.GoodsService;
import com.zqz.shop.service.business.*;
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
 * @ClassName: GoodsServiceImpl
 * @Date: Created in 15:40 2023-8-16
 */
@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsBusService goodsBusService;
    @Autowired
    private CategoryBusService categoryBusService;
    @Autowired
    private GoodsAttributeBusService attributeBusService;
    @Autowired
    private GoodsSpecificationBusService specificationBusService;
    @Autowired
    private GoodsProductBusService productBusService;


    @Override
    public Object doQueryCount() {
        Map<String, Object> data = new HashMap<>(1);
        Object count = goodsBusService.doQueryCount();
        data.put("goodsCount", count);
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doQueryByCategoryId(Integer id) {
        Category category = categoryBusService.queryById(id);
        if (ObjectUtil.isEmpty(category)) {
            return null;
        }
        Category parent;
        List<Category> children;

        if (category.getPid() == 0) {
            parent = category;
            children = categoryBusService.queryByParentId(category.getId());
            category = children.size() > 0 ? children.get(0) : category;
        } else {
            parent = categoryBusService.queryById(category.getPid());
            children = categoryBusService.queryByParentId(category.getPid());
        }
        Map<String, Object> data = new HashMap<>(3);
        data.put("currentCategory", category);
        data.put("parentCategory", parent);
        data.put("brotherCategory", children);
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doQueryListPage(Integer categoryId, String keyword, Boolean isNew, Boolean isHot, Integer page, Integer size) {
        Page<Goods> goodsPage = goodsBusService.queryByParam(categoryId, keyword, isNew, isHot, page, size);
        List<Goods> goodsList = goodsPage.getRecords();
        if (CollectionUtil.isEmpty(goodsList)) {
            return ResponseUtil.ok();
        }
        List<Integer> catIds = goodsBusService.queryCategoryIds(keyword, isNew, isHot);
        List<Category> categoryList;
        if (CollectionUtil.isNotEmpty(catIds)) {
            categoryList = categoryBusService.queryL2ByIds(catIds);
        } else {
            categoryList = new ArrayList<>();
        }
        Map<String, Object> data = new HashMap<>(4);
        data.put("goodsList", goodsList);
        data.put("count", goodsPage.getTotalRow());
        data.put("filterCategoryList", categoryList);
        data.put("totalPages", goodsPage.getTotalPage());
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doQueryDetail(Integer id, Integer userId) {
        //商品信息
        Goods goodInfo = goodsBusService.queryById(id);
        GoodInfo newGoodInfo = new GoodInfo();
        if (ObjectUtil.isNotEmpty(goodInfo)) {
            BeanUtil.copyProperties(goodInfo, newGoodInfo);
            newGoodInfo.setGallery(JSONUtil.parseArray(goodInfo.getGallery()).toList(String.class));
        }
        //商品属性
        List<GoodsAttribute> attributeList = attributeBusService.queryListByGoodsId(id);
        //商品规格
        List<GoodsSpecificationVo> specificationVoList = specificationBusService.queryListVoByGoodsId(id);
        //商品规格对应的数量和价格
        List<GoodsProductVo> productList = productBusService.queryListByGoodsId(id);

        Map<String, Object> data = new HashMap<>(5);
        data.put("info", newGoodInfo);
        data.put("specificationList", specificationVoList);
        data.put("productList", productList);
        data.put("attribute", attributeList);
        // 商品分享图片地址
        data.put("shareImage", goodInfo.getShareUrl());
        return ResponseUtil.ok(data);
    }
}
