package com.zqz.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.admin.BrandVo;
import com.zqz.shop.bean.admin.CatVo;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.entity.Brand;
import com.zqz.shop.entity.Category;
import com.zqz.shop.service.AdminBrandService;
import com.zqz.shop.service.business.AdminBusService;
import com.zqz.shop.service.business.BrandBusService;
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
 * @ClassName: AdminBrandServiceImpl
 * @Date: Created in 9:51 2023-9-1
 */
@Service
@Slf4j
public class AdminBrandServiceImpl implements AdminBrandService {
    @Autowired
    private BrandBusService brandBusService;
    @Autowired
    private CategoryBusService categoryBusService;
    @Autowired
    private AdminBusService adminBusService;


    @Override
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String id, String name) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Page<Brand> brandPage = brandBusService.queryPage(page, limit, id, name);
        Map<String, Object> data = new HashMap<>(2);

        long total = brandPage.getTotalRow();
        List<Brand> brandList = brandPage.getRecords();

        List<BrandVo> brandVoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(brandList)) {
            for (Brand brand : brandList) {
                BrandVo brandVo = new BrandVo();
                BeanUtil.copyProperties(brand, brandVo);
                Integer categoryId = brand.getDefaultCategoryId();
                Category category = categoryBusService.queryById(categoryId);
                Integer[] categoryIds;
                if (ObjectUtil.isNotEmpty(category)) {
                    Integer parentCategoryId = category.getPid();
                    categoryIds = new Integer[]{parentCategoryId, categoryId};
                    brandVo.setCategoryIds(categoryIds);
                }
                brandVoList.add(brandVo);
            }
            data.put("total", total);
            data.put("items", brandVoList);
        } else {
            data.put("total", 0);
            data.put("items", brandVoList);
        }
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doQueryCategoryAndAdmin(Integer adminUserId) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        List<Category> l1Categorys = categoryBusService.queryL1();

        List<CatVo> categoryList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(l1Categorys)) {
            for (Category l1 : l1Categorys) {
                CatVo l1CatVo = new CatVo();
                l1CatVo.setValue(l1.getId());
                l1CatVo.setLabel(l1.getName());

                List<Category> l2CategoryList = categoryBusService.queryByParentId(l1.getPid());
                List<CatVo> children = new ArrayList<>();
                if (CollectionUtil.isNotEmpty(l2CategoryList)) {
                    for (Category l2 : l2CategoryList) {
                        CatVo l2CatVo = new CatVo();
                        l2CatVo.setValue(l2.getId());
                        l2CatVo.setLabel(l2.getName());
                        children.add(l2CatVo);
                    }
                    l1CatVo.setChildren(children);
                    categoryList.add(l1CatVo);
                }
            }
        }
        //系统用户
        List<Admin> admins = adminBusService.queryAllAdmin();
        List<Map<String, Object>> adminList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(admins)) {
            for (Admin admin : admins) {
                Map<String, Object> b = new HashMap<>(2);
                b.put("value", admin.getId());
                b.put("label", admin.getUsername());
                adminList.add(b);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        data.put("adminList", adminList);
        return ResponseUtil.ok(data);
    }
}
