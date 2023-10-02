package com.zqz.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.GoodInfo;
import com.zqz.shop.bean.GoodsProductVo;
import com.zqz.shop.bean.GoodsSpecificationVo;
import com.zqz.shop.bean.resp.GoodsQueryByCategoryIdResp;
import com.zqz.shop.bean.resp.GoodsQueryDetailResp;
import com.zqz.shop.bean.resp.GoodsQueryListPageResp;
import com.zqz.shop.bean.resp.QueryGoodsCountResp;
import com.zqz.shop.entity.Category;
import com.zqz.shop.entity.Goods;
import com.zqz.shop.entity.GoodsAttribute;
import com.zqz.shop.service.GoodsService;
import com.zqz.shop.service.business.*;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
        QueryGoodsCountResp goodsCountResp = new QueryGoodsCountResp();
        Object count = goodsBusService.doQueryCount();
        goodsCountResp.setGoodsCount(count);
        return ResponseUtil.ok(goodsCountResp);
    }

    @Override
    public Object doQueryByCategoryId(Integer id) {
        Category category = categoryBusService.queryById(id);
        if (ObjectUtil.isEmpty(category)) {
            return null;
        }
        GoodsQueryByCategoryIdResp categoryIdResp = new GoodsQueryByCategoryIdResp();
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
        categoryIdResp.setCurrentCategory(category);
        categoryIdResp.setParentCategory(parent);
        categoryIdResp.setBrotherCategory(children);
        return ResponseUtil.ok(categoryIdResp);
    }

    @Override
    public Object doQueryListPage(Integer categoryId, String keyword, Boolean isNew, Boolean isHot, Integer page, Integer size) {
        Page<Goods> goodsPage = goodsBusService.queryByParam(categoryId, keyword, isNew, isHot, page, size);
        List<Goods> goodsList = goodsPage.getRecords();
        if (CollectionUtil.isEmpty(goodsList)) {
            return ResponseUtil.ok();
        }
        GoodsQueryListPageResp listPageResp = new GoodsQueryListPageResp();
        List<Integer> catIds = goodsBusService.queryCategoryIds(keyword, isNew, isHot);
        List<Category> categoryList;
        if (CollectionUtil.isNotEmpty(catIds)) {
            categoryList = categoryBusService.queryL2ByIds(catIds);
        } else {
            categoryList = new ArrayList<>();
        }
        listPageResp.setGoodsList(goodsList);
        listPageResp.setCount(goodsPage.getTotalRow());
        listPageResp.setFilterCategoryList(categoryList);
        listPageResp.setTotalPages(goodsPage.getTotalPage());
        return ResponseUtil.ok(listPageResp);
    }

    @Override
    public Object doQueryDetail(Integer id, Integer userId) {
        GoodsQueryDetailResp detailResp = new GoodsQueryDetailResp();
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
        detailResp.setInfo(newGoodInfo);
        detailResp.setSpecificationList(specificationVoList);
        detailResp.setProductList(productList);
        detailResp.setAttribute(attributeList);
        // 商品分享图片地址
        detailResp.setShareImage(goodInfo.getShareUrl());
        return ResponseUtil.ok(detailResp);
    }
}
