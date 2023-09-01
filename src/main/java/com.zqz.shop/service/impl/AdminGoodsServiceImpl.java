package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.admin.CatVo;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.entity.Brand;
import com.zqz.shop.entity.Category;
import com.zqz.shop.entity.Goods;
import com.zqz.shop.service.AdminGoodsService;
import com.zqz.shop.service.business.*;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminGoodsServiceImpl
 * @Date: Created in 14:37 2023-8-30
 */
@Service
@Slf4j
public class AdminGoodsServiceImpl implements AdminGoodsService {
    private static final String BRAND_ROLE_NAME = "品牌制造商";
    private static final String SUPER_ROLE_NAME = "超级管理员";

    @Autowired
    private AdminBusService adminBusService;
    @Autowired
    private RoleBusService roleBusService;
    @Autowired
    private BrandBusService brandBusService;
    @Autowired
    private GoodsBusService goodsBusService;
    @Autowired
    private CategoryBusService categoryBusService;

    @Override
    public Object doQueryList(Integer userId, Integer page, Integer limit, String goodsSn, String name) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Integer> brandIds = null;
        if (isBrandManager(userId)) {
            brandIds = getBrandIds(userId);
            if (CollectionUtil.isEmpty(brandIds)) {
                Map<String, Object> data = new HashMap<>(2);
                data.put("total", 0L);
                data.put("items", null);
                return ResponseUtil.ok(data);
            }
        }
        return queryList(goodsSn, name, page, limit, brandIds);
    }

    @Override
    public Object doCatAndBrand(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Category> l1CategoryList = categoryBusService.queryL1();
        if (CollectionUtil.isEmpty(l1CategoryList)) {
            return ResponseUtil.dataEmpty();
        }
        List<CatVo> categoryList = new ArrayList<>(l1CategoryList.size());
        for (Category l1 : l1CategoryList) {
            CatVo l1CatVo = new CatVo();
            l1CatVo.setValue(l1.getId());
            l1CatVo.setLabel(l1.getName());

            List<Category> l2CatList = categoryBusService.queryByParentId(l1.getId());
            List<CatVo> children = new ArrayList<>(l2CatList.size());
            for (Category l2 : l2CatList) {
                CatVo l2CatVo = new CatVo();
                l2CatVo.setValue(l2.getId());
                l2CatVo.setLabel(l2.getName());
                children.add(l2CatVo);
            }
            l1CatVo.setChildren(children);
            categoryList.add(l1CatVo);
        }
        //品牌商获取需要控制数据权限，如果是店铺管理员下拉的品牌商只能选择当前用户可管理的品牌商
        List<Map<String, Object>> brandList = new ArrayList<>();
        List<Brand> list;
        if (isBrandManager(userId)) {
            list = brandBusService.getAdminBrands(userId);
        } else {
            list = brandBusService.queryAll();
            brandList = new ArrayList<>(list.size());
        }
        for (Brand brand : list) {
            Map<String, Object> b = new HashMap<>(2);
            b.put("value", brand.getId());
            b.put("label", brand.getName());
            brandList.add(b);
        }
        Map<String, Object> data = new HashMap<>(2);
        data.put("categoryList", categoryList);
        data.put("brandList", brandList);
        return ResponseUtil.ok(data);
    }

    private Object queryList(String goodsSn, String name, Integer page, Integer limit, List<Integer> brandIds) {
        Page<Goods> goodsPage = goodsBusService.queryPage(goodsSn, name, page, limit, brandIds);
        Map<String, Object> data = new HashMap<>(2);
        data.put("total", goodsPage.getTotalRow());
        data.put("items", goodsPage.getRecords());
        return ResponseUtil.ok(data);
    }


    /**
     * 是否属于运营商管理员，超级管理员除外
     *
     * @return
     */
    @Override
    public boolean isBrandManager(Integer userId) {
        Admin admin = adminBusService.queryByUserId(userId);
        if (ObjectUtil.isNotEmpty(admin)) {
            String roleIdStr = admin.getRoleIds();
            List<Integer> roleIds = JSONUtil.parseArray(roleIdStr).toList(Integer.class);
            Set<String> roles = roleBusService.queryByIds(roleIds);
            //仅仅只是品牌管理员且不属于超级管理员
            if (roles.contains(BRAND_ROLE_NAME) && !roles.contains(SUPER_ROLE_NAME)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取当前用户的管理的品牌商铺
     *
     * @return
     */
    @Override
    public List<Integer> getBrandIds(Integer userId) {
        List<Integer> brandIds = null;
        List<Brand> brands = brandBusService.getAdminBrands(userId);
        if (brands != null && brands.size() > 0) {
            brandIds = new ArrayList<>();
            for (Brand brand : brands) {
                brandIds.add(brand.getId());
            }
        }
        return brandIds;
    }


}
