package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.zqz.shop.bean.req.AddCartProductReq;
import com.zqz.shop.bean.BrandCartGoods;
import com.zqz.shop.bean.req.UpdateCartReq;
import com.zqz.shop.bean.resp.CartCheckoutResp;
import com.zqz.shop.bean.resp.CartIndexResp;
import com.zqz.shop.bean.resp.CartTotalVo;
import com.zqz.shop.entity.*;
import com.zqz.shop.enums.ResponseCode;
import com.zqz.shop.service.CartService;
import com.zqz.shop.service.business.*;
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
    @Autowired
    private UserAddressBusService addressBusService;

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
    public Object doAddProduct(Integer userId, AddCartProductReq req) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Integer productId = req.getProductId();
        int num = req.getNumber().intValue();
        Integer goodsId = req.getGoodsId();

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
            Cart cart = new Cart();
            cart.setProductId(productId);
            cart.setNumber(req.getNumber());
            cart.setGoodsId(goodsId);
            cart.setGoodsSn(goods.getGoodsSn());
            cart.setBrandId(goods.getBrandId());
            cart.setGoodsName((goods.getName()));
            cart.setPicUrl(goods.getPicUrl());
            cart.setPrice(product.getPrice());
            cart.setSpecifications(product.getSpecifications());
            cart.setUserId(userId);
            cart.setAddTime(new Date());
            cart.setChecked(true);
            cart.setDeleted(false);
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
        CartIndexResp indexResp = new CartIndexResp();
        CartTotalVo totalVo = new CartTotalVo();
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
            totalVo.setGoodsCount(goodsCount);
            totalVo.setGoodsAmount(goodsAmount);
            totalVo.setCheckedGoodsCount(checkedGoodsCount);
            totalVo.setCheckedGoodsAmount(checkedGoodsAmount);
            indexResp.setCartTotal(totalVo);
        }
        // 如果需要拆订单，则需要按店铺显示购物车商品
        if (SystemConfig.isMultiOrderModel()) {
            indexResp.setIsMultiOrderModel(1);
            List<BrandCartGoods> brandCartGoodsList = new ArrayList<>();
            for (Cart cart : cartList) {
                Integer brandId = cart.getBrandId();
                boolean hasExist = false;
                for (int i = 0; i < brandCartGoodsList.size(); i++) {
                    if (brandCartGoodsList.get(i).getBrandId().intValue() == brandId.intValue()) {
                        brandCartGoodsList.get(i).getCartList().add(cart);
                        hasExist = true;
                        break;
                    }
                }
                if (!hasExist) {
                    // 还尚未加入，则需要查询品牌入驻商铺
                    Brand dtsBrand = brandBusService.queryById(brandId);
                    BrandCartGoods bandCartGoods = BrandCartGoods.init(dtsBrand);
                    List<Cart> dtsCartList = new ArrayList<>();
                    dtsCartList.add(cart);
                    bandCartGoods.setCartList(dtsCartList);
                    brandCartGoodsList.add(bandCartGoods);
                }
            }
            indexResp.setBrandCartgoods(brandCartGoodsList);
        } else {
            indexResp.setIsMultiOrderModel(1);
            indexResp.setCartList(cartList);
        }
        return ResponseUtil.ok(indexResp);
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
    public Object doUpdate(Integer userId, UpdateCartReq req) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Integer id = req.getId();
        Integer productId = req.getProductId();
        Short number = req.getNumber();
        Integer goodsId = req.getGoodsId();

        //判断是否存在该订单,如果不存在，直接返回错误
        Cart existCart = cartBusService.queryById(id);
        if (ObjectUtil.isEmpty(existCart)) {
            log.error("更新购物车失败，该订单不存在!");
            return ResponseUtil.badArgumentValue();
        }
        //判断goodsId和productId是否与当前cart里的值一致
        if (!existCart.getGoodsId().equals(goodsId)) {
            log.error("更新购物车失败，goodsId:{}与当前购物车里的{}值不一致!", goodsId, existCart.getGoodsId());
            return ResponseUtil.badArgumentValue();
        }
        if (!existCart.getProductId().equals(productId)) {
            log.error("更新购物车失败，productId:{}与当前购物车里的{}值不一致!", productId, existCart.getProductId());
            return ResponseUtil.badArgumentValue();
        }
        //判断商品是否可以购买
        Goods goods = goodsBusService.queryById(goodsId);
        if (ObjectUtil.isEmpty(goods) || !goods.getIsOnSale()) {
            log.warn("更新购物车失败，商品已下架!");
            return ResponseUtil.fail(ResponseCode.GOODS_UNSHELVE);
        }
        //取得规格的信息,判断规格库存
        GoodsProduct product = productBusService.queryById(productId);
        if (ObjectUtil.isEmpty(product) || product.getNumber() < number.intValue()) {
            log.warn("更新购物车失败，库存不足!");
            return ResponseUtil.fail(ResponseCode.GOODS_NO_STOCK);
        }
        existCart.setNumber(number);
        if (cartBusService.updateById(existCart) <= 0) {
            log.error("更新购物车信息失败!");
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doDelete(Integer userId, String body) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        List<Integer> productIds = Optional.ofNullable(body).map(JSONUtil::parseObj).map(p2 -> p2.getJSONArray("productIds")).map(p3 -> p3.toList(Integer.class)).orElse(null);
        if (CollectionUtil.isEmpty(productIds)) {
            return ResponseUtil.badArgument();
        }
        int delete = cartBusService.deleteByProductIds(userId, productIds);
        if (delete <= 0) {
            log.error("删除购物车信息失败!");
            return ResponseUtil.serious();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doCheckout(Integer userId, Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        CartCheckoutResp checkoutResp = new CartCheckoutResp();
        UserAddress checkedAddress;
        if (ObjectUtil.isEmpty(addressId) || addressId.equals(0)) {
            checkedAddress = addressBusService.queryDefault(userId);
            //如果仍然没有地址，则是没有收获地址
            if (ObjectUtil.isEmpty(checkedAddress)) {
                checkedAddress = new UserAddress();
                checkedAddress.setId(0);
                addressId = 0;
            } else {
                addressId = checkedAddress.getId();
            }

        } else {
            checkedAddress = addressBusService.queryById(addressId);
            if (ObjectUtil.isEmpty(checkedAddress)) {
                return ResponseUtil.badArgumentValue();
            }
        }

        //商品价格
        List<Cart> checkedGoodsList;
        if (ObjectUtil.isEmpty(cartId) || cartId.equals(0)) {
            //如果未从购物车发起的下单，则获取用户选好的商品
            checkedGoodsList = cartBusService.queryByUserIdAndChecked(userId);
        } else {
            Cart cart = cartBusService.queryById(cartId);
            if (ObjectUtil.isEmpty(cart)) {
                log.error("下单失败，cartId:{}对应购物车信息为空!", cartId);
                return ResponseUtil.badArgumentValue();
            }
            checkedGoodsList = new ArrayList<>(1);
            checkedGoodsList.add(cart);
        }

        //商品总价 （包含团购减免，即减免团购后的商品总价，多店铺需将所有商品相加）
        BigDecimal goodsTotalPrice = BigDecimal.ZERO;
        //总配送费 （单店铺模式一个，多店铺模式多个配送费的总和）
        BigDecimal totalFreightPrice = BigDecimal.ZERO;

        if (SystemConfig.isMultiOrderModel()) {
            // a.按入驻店铺归类checkout商品
            List<BrandCartGoods> brandCartGoodsList = new ArrayList<>();
            for (Cart cart : checkedGoodsList) {
                Integer brandId = cart.getBrandId();
                boolean hasExsit = false;
                for (BrandCartGoods brandCartGoods : brandCartGoodsList) {
                    if (brandCartGoods.getBrandId().intValue() == brandId.intValue()) {
                        brandCartGoods.getCartList().add(cart);
                        hasExsit = true;
                        break;
                    }
                }
                if (!hasExsit) {
                    // 还尚未加入，则需要查询品牌入驻商铺
                    Brand dtsBrand = brandBusService.queryById(brandId);
                    BrandCartGoods bandCartGoods = BrandCartGoods.init(dtsBrand);
                    List<Cart> dtsCartList = new ArrayList<>();
                    dtsCartList.add(cart);
                    bandCartGoods.setCartList(dtsCartList);
                    brandCartGoodsList.add(bandCartGoods);
                }
            }

            // b.核算每个店铺的各项价格指标
            List<BrandCartGoods> checkBrandGoodsList = new ArrayList<>();
            for (BrandCartGoods bcg : brandCartGoodsList) {
                List<Cart> bandCarts = bcg.getCartList();
                BigDecimal bandGoodsTotalPrice = BigDecimal.ZERO;
                BigDecimal bandFreightPrice = BigDecimal.ZERO;

                for (Cart cart : bandCarts) {
                    // 循环店铺各自的购物商品,只有当团购规格商品ID符合才进行团购优惠
                    bandGoodsTotalPrice = bandGoodsTotalPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));
                }

                // 每个店铺都单独计算运费，满xxx则免运费，否则按配置的邮寄费x元计算；
                if (bandGoodsTotalPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                    bandFreightPrice = SystemConfig.getFreight();
                }

                goodsTotalPrice = goodsTotalPrice.add(bandGoodsTotalPrice);
                totalFreightPrice = totalFreightPrice.add(bandFreightPrice);

                bcg.setBandGoodsTotalPrice(bandGoodsTotalPrice);
                bcg.setBandFreightPrice(bandFreightPrice);

                checkBrandGoodsList.add(bcg);
            }
            checkoutResp.setIsMultiOrderModel(1);
            checkoutResp.setGoodsTotalPrice(goodsTotalPrice);
            checkoutResp.setFreightPrice(totalFreightPrice);
            checkoutResp.setBrandCartgoods(checkBrandGoodsList);
        } else {
            //不拆订单，则统一呈现
            for (Cart cart : checkedGoodsList) {
                goodsTotalPrice = goodsTotalPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //根据订单商品总价计算运费，满66则免运费，否则6元；
            if (goodsTotalPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                totalFreightPrice = SystemConfig.getFreight();
            }
            checkoutResp.setIsMultiOrderModel(0);
            checkoutResp.setGoodsTotalPrice(goodsTotalPrice);
            checkoutResp.setFreightPrice(totalFreightPrice);
            checkoutResp.setCheckedGoodsList(checkedGoodsList);
        }

        int availableCouponLength = 0;
        BigDecimal couponPrice = BigDecimal.ZERO;

        // 用户积分减免
        BigDecimal integralPrice = BigDecimal.ZERO;

        BigDecimal orderTotalPrice = goodsTotalPrice.add(totalFreightPrice).subtract(couponPrice);
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        // 返回界面的通用数据
        checkoutResp.setAddressId(addressId);
        checkoutResp.setCheckedAddress(checkedAddress);
        checkoutResp.setCouponId(couponId);
        checkoutResp.setAvailableCouponLength(availableCouponLength);
        checkoutResp.setGrouponRulesId(grouponRulesId);
        // 单店铺，多店铺 一个总订单都只能用一张券
        checkoutResp.setCouponPrice(couponPrice);
        // 订单总价：goodsTotalPrice + totalFreightPrice - couponPrice
        checkoutResp.setOrderTotalPrice(orderTotalPrice);
        // 订单实际付款金额：orderTotalPrice - integralPrice
        checkoutResp.setActualPrice(actualPrice);
        return ResponseUtil.ok(checkoutResp);
    }


}
