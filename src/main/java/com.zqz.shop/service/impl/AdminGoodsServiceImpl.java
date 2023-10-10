package com.zqz.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.GoodInfo;
import com.zqz.shop.bean.GoodsProductVo;
import com.zqz.shop.bean.admin.*;
import com.zqz.shop.bean.admin.req.GoodsUpdateReq;
import com.zqz.shop.bean.admin.resp.AdminGoodsDetailsResp;
import com.zqz.shop.bean.admin.resp.CatAndBrandResp;
import com.zqz.shop.bean.admin.resp.PageQueryResp;
import com.zqz.shop.bean.admin.resp.ValueLabelResp;
import com.zqz.shop.entity.*;
import com.zqz.shop.enums.AdminResponseCode;
import com.zqz.shop.exception.ShopException;
import com.zqz.shop.service.AdminGoodsService;
import com.zqz.shop.service.business.*;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Autowired
    private GoodsSpecificationBusService goodsSpecificationBusService;
    @Autowired
    private GoodsAttributeBusService goodsAttributeBusService;
    @Autowired
    private GoodsProductBusService goodsProductBusService;
    @Autowired
    private OrderGoodsBusService orderGoodsBusService;
    @Autowired
    private CartBusService cartBusService;

    @Override
    public Object doQueryList(Integer userId, Integer page, Integer limit, String goodsSn, String name) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Integer> brandIds = null;
        if (isBrandManager(userId)) {
            brandIds = getBrandIds(userId);
            if (CollectionUtil.isEmpty(brandIds)) {
                PageQueryResp<Goods> queryResp = new PageQueryResp<>();
                queryResp.setTotal(0L);
                queryResp.setItems(null);
                return ResponseUtil.ok(queryResp);
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
        List<ValueLabelResp> brandList = new ArrayList<>();
        List<Brand> list;
        if (isBrandManager(userId)) {
            list = brandBusService.getAdminBrands(userId);
        } else {
            list = brandBusService.queryAll();
            brandList = new ArrayList<>(list.size());
        }
        for (Brand brand : list) {
            ValueLabelResp labelResp = new ValueLabelResp();
            labelResp.setLabel(brand.getName());
            labelResp.setValue(brand.getId());
            brandList.add(labelResp);
        }
        CatAndBrandResp andBrandResp = new CatAndBrandResp();
        andBrandResp.setCategoryList(categoryList);
        andBrandResp.setBrandList(brandList);
        return ResponseUtil.ok(andBrandResp);
    }

    private Object queryList(String goodsSn, String name, Integer page, Integer limit, List<Integer> brandIds) {
        Page<Goods> goodsPage = goodsBusService.queryPage(goodsSn, name, page, limit, brandIds);
        PageQueryResp<Goods> queryResp = new PageQueryResp<>();
        queryResp.setTotal(goodsPage.getTotalRow());
        queryResp.setItems(goodsPage.getRecords());
        return ResponseUtil.ok(queryResp);
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

    @Override
    public Object doDelete(Integer adminUserId, Goods goods) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Integer id = goods.getId();
        goodsBusService.deleteById(id);
        goodsSpecificationBusService.deleteByGoodsId(id);
        goodsAttributeBusService.deleteByGoodsId(id);
        goodsProductBusService.deleteByGoodsId(id);
        return ResponseUtil.ok();
    }

    @Override
    public Object doDetail(Integer adminUserId, Integer id) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Goods goods = goodsBusService.queryById(id);

        List<GoodsProductVo> goodsProductVos = goodsProductBusService.queryListByGoodsId(id);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationBusService.queryByGoodsId(id);
        List<GoodsAttribute> goodsAttributes = goodsAttributeBusService.queryListByGoodsId(id);

        if (ObjectUtil.isEmpty(goods)) {
            return ResponseUtil.dataEmpty();
        }
        GoodInfo goodsVo = new GoodInfo();
        BeanUtil.copyProperties(goods, goodsVo);
        goodsVo.setGallery(JSONUtil.parseArray(goods.getGallery()).toList(String.class));

        Integer categoryId = goods.getCategoryId();
        Category category = categoryBusService.queryById(categoryId);
        Integer[] categoryIds = new Integer[]{};

        if (ObjectUtil.isNotEmpty(category)) {
            Integer parentCategoryId = category.getPid();
            categoryIds = new Integer[]{parentCategoryId, categoryId};
        }

        AdminGoodsDetailsResp goodsDetailsResp = new AdminGoodsDetailsResp();
        goodsDetailsResp.setGoods(goodsVo);
        goodsDetailsResp.setSpecifications(goodsSpecifications);
        goodsDetailsResp.setProducts(goodsProductVos);
        goodsDetailsResp.setAttributes(goodsAttributes);
        goodsDetailsResp.setCategoryIds(categoryIds);

        return ResponseUtil.ok(goodsDetailsResp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object doUpdateInfo(Integer adminUserId, GoodsUpdateReq req) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }

        Object validateMsg = updateValidate(req);
        if (ObjectUtil.isNotEmpty(validateMsg)) {
            return validateMsg;
        }

        Goods goods = req.getGoods();
        List<GoodsProductUpdateVo> products = req.getProducts();
        List<GoodsAttributeUpdateVo> attributes = req.getAttributes();
        List<GoodsSpecificationUpdateVo> specifications = req.getSpecifications();

        Integer goodsId = goods.getId();

        boolean orderGoodsExist = orderGoodsBusService.checkExist(goodsId);
        boolean cartExist = cartBusService.checkExist(goodsId);
        if (orderGoodsExist || cartExist) {
            return ResponseUtil.fail(AdminResponseCode.GOODS_UPDATE_NOT_ALLOWED);
        }

        int up = goodsBusService.updateById(goods);
        if (up <= 0) {
            throw new ShopException("商品更新失败!");
        }

        goodsSpecificationBusService.deleteByGoodsId(goodsId);
        goodsAttributeBusService.deleteByGoodsId(goodsId);
        goodsProductBusService.deleteByGoodsId(goodsId);

        for (GoodsSpecificationUpdateVo specification : specifications) {
            GoodsSpecification newSpec = new GoodsSpecification();
            BeanUtil.copyProperties(specification, newSpec);
            newSpec.setGoodsId(goodsId);
            newSpec.setUpdateTime(new Date());
            goodsSpecificationBusService.add(newSpec);
        }

        for (GoodsProductUpdateVo product : products) {
            GoodsProduct newPro = new GoodsProduct();
            BeanUtil.copyProperties(product, newPro);
            newPro.setSpecifications(JSONUtil.toJsonStr(product.getSpecifications()));
            newPro.setGoodsId(goodsId);
            newPro.setUpdateTime(new Date());
            goodsProductBusService.add(newPro);

        }

        for (GoodsAttributeUpdateVo attribute : attributes) {
            GoodsAttribute newAtt = new GoodsAttribute();
            BeanUtil.copyProperties(attribute, newAtt);
            newAtt.setGoodsId(goodsId);
            newAtt.setUpdateTime(new Date());
            goodsAttributeBusService.add(newAtt);
        }
        return ResponseUtil.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object doCreateInfo(Integer adminUserId, GoodsCreateReq req) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }

        Object validateMsg = createValidate(req);
        if (ObjectUtil.isNotEmpty(validateMsg)) {
            return validateMsg;
        }

        CreateGoodsVo goods = req.getGoods();
        List<GoodsProductUpdateVo> products = req.getProducts();
        List<GoodsAttributeUpdateVo> attributes = req.getAttributes();
        List<GoodsSpecificationUpdateVo> specifications = req.getSpecifications();

        String name = goods.getName();

        boolean goodsExist = goodsBusService.checkExistByName(name);
        if (goodsExist) {
            return ResponseUtil.fail(AdminResponseCode.GOODS_NAME_EXIST);
        }

        Goods newGoods = new Goods();
        BeanUtil.copyProperties(goods, newGoods);
        newGoods.setGallery(JSONUtil.toJsonStr(goods.getGallery()));
        int add = goodsBusService.add(newGoods);
        if (add <= 0) {
            throw new ShopException("商品上架失败!");
        }

        Integer goodsId = newGoods.getId();

        for (GoodsSpecificationUpdateVo specification : specifications) {
            GoodsSpecification newSpec = new GoodsSpecification();
            BeanUtil.copyProperties(specification, newSpec);
            newSpec.setGoodsId(goodsId);
            goodsSpecificationBusService.add(newSpec);
        }

        for (GoodsProductUpdateVo product : products) {
            GoodsProduct newPro = new GoodsProduct();
            BeanUtil.copyProperties(product, newPro);
            newPro.setSpecifications(JSONUtil.toJsonStr(product.getSpecifications()));
            newPro.setGoodsId(goodsId);
            goodsProductBusService.add(newPro);
        }

        for (GoodsAttributeUpdateVo attribute : attributes) {
            GoodsAttribute newAtt = new GoodsAttribute();
            BeanUtil.copyProperties(attribute, newAtt);
            newAtt.setGoodsId(goodsId);
            goodsAttributeBusService.add(newAtt);
        }
        return ResponseUtil.ok();
    }

    private Object updateValidate(GoodsUpdateReq req) {
        Goods goods = req.getGoods();
        String name = goods.getName();
        if (StrUtil.isBlank(name)) {
            return ResponseUtil.badArgument();
        }
        String goodsSn = goods.getGoodsSn();
        if (StrUtil.isBlank(goodsSn)) {
            return ResponseUtil.badArgument();
        }
        Integer brandId = goods.getBrandId();
        if (ObjectUtil.isNotEmpty(brandId) && brandId != 0) {
            if (ObjectUtil.isEmpty(brandBusService.queryById(brandId))) {
                return ResponseUtil.badArgumentValue();
            }
        }
        // 分类可以不设置，如果设置则需要验证分类存在
        Integer categoryId = goods.getCategoryId();
        if (ObjectUtil.isNotEmpty(categoryId) && categoryId != 0) {
            if (ObjectUtil.isEmpty(categoryBusService.queryById(categoryId))) {
                return ResponseUtil.badArgumentValue();
            }
        }
        return validate(req.getAttributes(), req.getSpecifications(), req.getProducts());
    }

    private Object createValidate(GoodsCreateReq req) {
        CreateGoodsVo goods = req.getGoods();
        String name = goods.getName();
        if (StrUtil.isBlank(name)) {
            return ResponseUtil.badArgument();
        }
        String goodsSn = goods.getGoodsSn();
        if (StrUtil.isBlank(goodsSn)) {
            return ResponseUtil.badArgument();
        }
        Integer brandId = goods.getBrandId();
        if (ObjectUtil.isNotEmpty(brandId) && brandId != 0) {
            if (ObjectUtil.isEmpty(brandBusService.queryById(brandId))) {
                return ResponseUtil.badArgumentValue();
            }
        }
        // 分类可以不设置，如果设置则需要验证分类存在
        Integer categoryId = goods.getCategoryId();
        if (ObjectUtil.isNotEmpty(categoryId) && categoryId != 0) {
            if (ObjectUtil.isEmpty(categoryBusService.queryById(categoryId))) {
                return ResponseUtil.badArgumentValue();
            }
        }
        return validate(req.getAttributes(), req.getSpecifications(), req.getProducts());
    }


    private Object validate(List<GoodsAttributeUpdateVo> attributes,
                            List<GoodsSpecificationUpdateVo> specifications, List<GoodsProductUpdateVo> products) {
        for (GoodsAttributeUpdateVo attribute : attributes) {
            String attr = attribute.getAttribute();
            if (StrUtil.isBlank(attr)) {
                return ResponseUtil.badArgument();
            }
            String value = attribute.getValue();
            if (StrUtil.isBlank(value)) {
                return ResponseUtil.badArgument();
            }
        }

        for (GoodsSpecificationUpdateVo specification : specifications) {
            String spec = specification.getSpecification();
            if (StrUtil.isBlank(spec)) {
                return ResponseUtil.badArgument();
            }
            String value = specification.getValue();
            if (StrUtil.isBlank(value)) {
                return ResponseUtil.badArgument();
            }
        }

        for (GoodsProductUpdateVo product : products) {
            Integer number = product.getNumber();
            if (ObjectUtil.isEmpty(number) || number < 0) {
                return ResponseUtil.badArgument();
            }

            BigDecimal price = product.getPrice();
            if (ObjectUtil.isEmpty(price)) {
                return ResponseUtil.badArgument();
            }

            List<String> productSpecifications = product.getSpecifications();
            if (CollectionUtil.isEmpty(productSpecifications)) {
                return ResponseUtil.badArgument();
            }

        }
        return null;
    }


}
