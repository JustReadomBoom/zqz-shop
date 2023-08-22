package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.zqz.shop.bean.BrandCartGoods;
import com.zqz.shop.entity.Brand;
import com.zqz.shop.entity.Cart;
import com.zqz.shop.entity.Goods;
import com.zqz.shop.entity.GoodsProduct;
import com.zqz.shop.enums.ResponseCode;
import com.zqz.shop.service.CartService;
import com.zqz.shop.service.business.BrandBusService;
import com.zqz.shop.service.business.CartBusService;
import com.zqz.shop.service.business.GoodsBusService;
import com.zqz.shop.service.business.GoodsProductBusService;
import com.zqz.shop.system.SystemConfig;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartServiceImpl
 * @Date: Created in 14:53 2023-8-18
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private CartBusService cartBusService;
    @Autowired
    private GoodsBusService goodsBusService;
    @Autowired
    private GoodsProductBusService productBusService;
    @Autowired
    private BrandBusService brandBusService;

    @Override
    public Object doQueryGoodCount(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Cart> cartList = cartBusService.queryByUserId(userId);
        if (CollectionUtil.isEmpty(cartList)) {
            return ResponseUtil.ok(0);
        }
        int goodsCount = 0;
        for (Cart cart : cartList) {
            goodsCount += cart.getNumber();
        }
        return ResponseUtil.ok(goodsCount);
    }

    @Override
    public Object doAddProduct(Integer userId, Cart cart) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Integer productId = cart.getProductId();
        int num = cart.getNumber().intValue();
        Integer goodsId = cart.getGoodsId();
        if (ObjectUtil.isAllEmpty(productId, num, goodsId)) {
            return ResponseUtil.badArgument();
        }
        //判断商品是否可以购买
        Goods goods = goodsBusService.queryById(goodsId);
        if (ObjectUtil.isEmpty(goods) || !goods.getIsOnSale()) {
            log.warn("加入购物车失败，商品已下架!");
            return ResponseUtil.fail(ResponseCode.GOODS_UNSHELVE);
        }
        GoodsProduct product = productBusService.queryById(productId);
        //判断购物车中是否存在此规格商品
        Cart existCart = cartBusService.queryExist(goodsId, productId, userId);
        if (ObjectUtil.isEmpty(existCart)) {
            //取得规格的信息,判断规格库存
            if (ObjectUtil.isEmpty(product) || num > product.getNumber()) {
                log.warn("加入购物车失败，商品库存不足!");
                return ResponseUtil.fail(ResponseCode.GOODS_NO_STOCK);
            }
            cart.setId(null);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setBrandId(goods.getBrandId());
            cart.setGoodsName((goods.getName()));
            cart.setPicUrl(goods.getPicUrl());
            cart.setPrice(product.getPrice());
            cart.setSpecifications(product.getSpecifications());
            cart.setUserId(userId);
            cart.setChecked(true);
            cartBusService.add(cart);
        } else {
            //取得规格的信息,判断规格库存
            int newNum = existCart.getNumber() + num;
            if (newNum > product.getNumber()) {
                log.warn("加入购物车失败，商品库存不足!");
                return ResponseUtil.fail(ResponseCode.GOODS_NO_STOCK);
            }
            existCart.setNumber((short) num);
            if (cartBusService.updateById(existCart) == 0) {
                log.error("加入购物车失败，更新购物车信息失败!");
                return ResponseUtil.updatedDataFailed();
            }
        }
        return doQueryGoodCount(userId);
    }

    @Override
    public Object doIndex(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Map<String, Object> result = new HashMap<>(1);
        Map<String, Object> cartTotal = new HashMap<>(4);
        List<Cart> cartList = cartBusService.queryByUserId(userId);
        if (CollectionUtil.isNotEmpty(cartList)) {
            Integer goodsCount = 0;
            Integer checkedGoodsCount = 0;
            BigDecimal goodsAmount = BigDecimal.ZERO;
            BigDecimal checkedGoodsAmount = BigDecimal.ZERO;
            for (Cart cart : cartList) {
                goodsCount += cart.getNumber();
                goodsAmount = goodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));
                if (cart.getChecked()) {
                    checkedGoodsCount += cart.getNumber();
                    checkedGoodsAmount = checkedGoodsAmount.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
            cartTotal.put("goodsCount", goodsCount);
            cartTotal.put("goodsAmount", goodsAmount);
            cartTotal.put("checkedGoodsCount", checkedGoodsCount);
            cartTotal.put("checkedGoodsAmount", checkedGoodsAmount);
            result.put("cartTotal", cartTotal);
        }
        // 如果需要拆订单，则需要按店铺显示购物车商品
        if (SystemConfig.isMultiOrderModel()) {
            result.put("isMultiOrderModel", 1);
            List<BrandCartGoods> brandCartgoodsList = new ArrayList<>();
            for (Cart cart : cartList) {
                Integer brandId = cart.getBrandId();
                boolean hasExsist = false;
                for (int i = 0; i < brandCartgoodsList.size(); i++) {
                    if (brandCartgoodsList.get(i).getBrandId().intValue() == brandId.intValue()) {
                        brandCartgoodsList.get(i).getCartList().add(cart);
                        hasExsist = true;
                        break;
                    }
                }
                if (!hasExsist) {
                    // 还尚未加入，则需要查询品牌入驻商铺
                    Brand dtsBrand = brandBusService.queryById(brandId);
                    BrandCartGoods bandCartGoods = BrandCartGoods.init(dtsBrand);
                    List<Cart> dtsCartList = new ArrayList<>();
                    dtsCartList.add(cart);
                    bandCartGoods.setCartList(dtsCartList);
                    brandCartgoodsList.add(bandCartGoods);
                }
            }
            result.put("brandCartgoods", brandCartgoodsList);
        } else {
            result.put("isMultiOrderModel", 0);
            result.put("cartList", cartList);
        }
        return ResponseUtil.ok(result);
    }

    @Override
    public Object doChecked(Integer userId, String body) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Integer> productIds = Optional.ofNullable(body).map(JSONUtil::parseObj).map(p2 -> p2.getJSONArray("productIds")).map(p3 -> p3.toList(Integer.class)).orElse(null);
        Integer isChecked = Optional.ofNullable(body).map(JSONUtil::parseObj).map(p4 -> p4.getInt("isChecked")).orElse(null);
        if (null == productIds || null == isChecked) {
            return ResponseUtil.badArgument();
        }
        Boolean chk = (isChecked == 1);
        cartBusService.updateByParam(userId, productIds, chk);
        return doIndex(userId);
    }

    @Override
    public Object doUpdate(Integer userId, Cart cart) {

        return null;
    }


}
