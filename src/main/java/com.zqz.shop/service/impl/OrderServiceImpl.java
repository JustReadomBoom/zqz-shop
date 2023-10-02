package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.*;
import com.zqz.shop.bean.req.CancelOrderReq;
import com.zqz.shop.bean.req.OrderDeleteReq;
import com.zqz.shop.bean.req.OrderPrepayReq;
import com.zqz.shop.bean.req.OrderSubmitReq;
import com.zqz.shop.bean.resp.OrderSubmitResp;
import com.zqz.shop.bean.resp.QueryOrderDetailResp;
import com.zqz.shop.bean.resp.QueryOrderListResp;
import com.zqz.shop.common.Constant;
import com.zqz.shop.entity.*;
import com.zqz.shop.enums.ResponseCode;
import com.zqz.shop.exception.ShopException;
import com.zqz.shop.service.OrderService;
import com.zqz.shop.service.business.*;
import com.zqz.shop.system.SystemConfig;
import com.zqz.shop.utils.IpUtil;
import com.zqz.shop.utils.OrderHandleOption;
import com.zqz.shop.utils.OrderUtil;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderServiceImpl
 * @Date: Created in 16:58 2023-8-15
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderBusService orderBusService;
    @Autowired
    private OrderGoodsBusService orderGoodsBusService;
    @Autowired
    private UserAddressBusService addressBusService;
    @Autowired
    private CartBusService cartBusService;
    @Autowired
    private RegionBusService regionBusService;
    @Autowired
    private GoodsProductBusService goodsProductBusService;
    @Autowired
    private UserBusService userBusService;
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private UserFormidBusService userFormidBusService;

    @Override
    public Object doQueryList(Integer userId, Integer showType, Integer page, Integer size) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        QueryOrderListResp orderListResp = new QueryOrderListResp();
        List<Short> orderStatus = OrderUtil.orderStatus(showType);
        Page<Order> orderPage = orderBusService.queryByOrderStatus(userId, orderStatus, page, size);
        List<Order> orderRecords = orderPage.getRecords();
        if (CollectionUtil.isEmpty(orderRecords)) {
            return ResponseUtil.ok();
        }
        long totalRow = orderPage.getTotalRow();
        long totalPage = orderPage.getTotalPage();
        if (CollectionUtil.isNotEmpty(orderRecords)) {
            List<OrderVo> orderVoList = new ArrayList<>(orderRecords.size());
            for (Order order : orderRecords) {
                OrderVo vo = new OrderVo();
                vo.setId(order.getId());
                vo.setOrderSn(order.getOrderSn());
                vo.setAddTime(order.getAddTime());
                vo.setConsignee(order.getConsignee());
                vo.setMobile(order.getMobile());
                vo.setAddress(order.getAddress());
                vo.setGoodsPrice(order.getGoodsPrice());
                vo.setFreightPrice(order.getFreightPrice());
                vo.setActualPrice(order.getActualPrice());
                vo.setOrderStatusText(OrderUtil.orderStatusText(order));
                vo.setHandleOption(OrderUtil.build(order));
                vo.setExpCode(order.getShipChannel());
                vo.setExpNo(order.getShipSn());

                List<OrderGoods> orderGoodsList = orderGoodsBusService.queryByOrderId(order.getId());
                vo.setGoodsList(orderGoodsList);
                orderVoList.add(vo);
            }
            orderListResp.setCount(totalRow);
            orderListResp.setData(orderVoList);
            orderListResp.setTotalPages(totalPage);
        }
        return ResponseUtil.ok(orderListResp);
    }

    @Override
    @Transactional
    public Object doSubmit(Integer userId, OrderSubmitReq req) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        OrderSubmitResp submitResp = new OrderSubmitResp();
        Integer cartId = req.getCartId();
        Integer couponId = req.getCouponId();
        Integer addressId = req.getAddressId();
        String message = req.getMessage();

        if (cartId == null || addressId == null || couponId == null) {
            return ResponseUtil.badArgument();
        }

        UserAddress userAddress = addressBusService.queryById(addressId);
        if (ObjectUtil.isEmpty(userAddress)) {
            log.error("提交订单失败，地址不存在，addressId={}", addressId);
            return ResponseUtil.dataEmpty();
        }
        //商品价格
        List<Cart> checkedGoodsList;
        if (cartId.equals(0)) {
            checkedGoodsList = cartBusService.queryByUserIdAndChecked(userId);
        } else {
            Cart cart = cartBusService.queryById(cartId);
            checkedGoodsList = new ArrayList<>();
            checkedGoodsList.add(cart);
        }
        if (CollectionUtil.isEmpty(checkedGoodsList)) {
            log.error("提交订单失败，购物车商品信息错误");
            return ResponseUtil.dataEmpty();
        }
        // 商品总价 （包含团购减免，即减免团购后的商品总价，多店铺需将所有商品相加）
        BigDecimal goodsTotalPrice = BigDecimal.ZERO;
        // 总配送费 （单店铺模式一个，多店铺模式多个配送费的总和）
        BigDecimal totalFreightPrice = BigDecimal.ZERO;
        // 如果需要拆订单，则按店铺进行归类，在计算邮费
        if (SystemConfig.isMultiOrderModel()) {
            // a.按入驻店铺归类checkout商品
            List<BrandCartGoods> brandCartGoodsList = new ArrayList<>();
            for (Cart cart : checkedGoodsList) {
                Integer brandId = cart.getBrandId();
                boolean hasExist = false;
                for (BrandCartGoods brandCartGoods : brandCartGoodsList) {
                    if (brandCartGoods.getBrandId().intValue() == brandId.intValue()) {
                        brandCartGoods.getCartList().add(cart);
                        hasExist = true;
                        break;
                    }
                }
                if (!hasExist) {
                    //还尚未加入，则新增一类
                    BrandCartGoods bandCartGoods = new BrandCartGoods();
                    bandCartGoods.setBrandId(brandId);
                    List<Cart> dtsCartList = new ArrayList<>();
                    dtsCartList.add(cart);
                    bandCartGoods.setCartList(dtsCartList);
                    brandCartGoodsList.add(bandCartGoods);
                }
            }
            // b.核算每个店铺的商品总价，用于计算邮费
            for (BrandCartGoods bcg : brandCartGoodsList) {
                List<Cart> bandCarts = bcg.getCartList();
                BigDecimal bandGoodsTotalPrice = BigDecimal.ZERO;
                BigDecimal bandFreightPrice = BigDecimal.ZERO;
                for (Cart cart : bandCarts) {
                    //循环店铺各自的购物商品,只有当团购规格商品ID符合才进行团购优惠
                    bandGoodsTotalPrice = bandGoodsTotalPrice.add(cart.getPrice().multiply(new BigDecimal(cart.getNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));
                }

                // 每个店铺都单独计算运费，满66则免运费，否则6元；
                if (bandGoodsTotalPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                    bandFreightPrice = SystemConfig.getFreight();
                }
                goodsTotalPrice = goodsTotalPrice.add(bandGoodsTotalPrice);
                totalFreightPrice = totalFreightPrice.add(bandFreightPrice);
            }
        } else {
            // 单个店铺模式
            for (Cart checkGoods : checkedGoodsList) {
                // 只有当团购规格商品ID符合才进行团购优惠
                goodsTotalPrice = goodsTotalPrice.add(checkGoods.getPrice().multiply(new BigDecimal(checkGoods.getNumber())).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            // 根据订单商品总价计算运费，满足条件（例如66元）则免运费，否则需要支付运费（例如6元）；
            if (goodsTotalPrice.compareTo(SystemConfig.getFreightLimit()) < 0) {
                totalFreightPrice = SystemConfig.getFreight();
            }

        }
        // 获取可用的优惠券信息 使用优惠券减免的金额
        BigDecimal couponPrice = BigDecimal.ZERO;
        // 可以使用的其他钱，例如用户积分
        BigDecimal integralPrice = BigDecimal.ZERO;
        // 订单费用
        BigDecimal orderTotalPrice = goodsTotalPrice.add(totalFreightPrice).subtract(couponPrice);
        // 最终支付费用
        BigDecimal actualPrice = orderTotalPrice.subtract(integralPrice);

        Integer orderId;
        // 订单
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderSn(orderBusService.generateOrderSn(userId));
        order.setOrderStatus(OrderUtil.STATUS_CREATE);
        order.setConsignee(userAddress.getName());
        order.setMobile(userAddress.getMobile());
        order.setMessage(message);
        String detailedAddress = detailedAddress(userAddress);
        order.setAddress(detailedAddress);
        order.setGoodsPrice(goodsTotalPrice);
        order.setFreightPrice(totalFreightPrice);
        order.setCouponPrice(couponPrice);
        order.setIntegralPrice(integralPrice);
        order.setOrderPrice(orderTotalPrice);
        order.setActualPrice(actualPrice);
        // 团购价格
        order.setGrouponPrice(BigDecimal.ZERO);
        //代理的结算金额
        order.setSettlementMoney(BigDecimal.ZERO);
        order.setAddTime(new Date());
        orderBusService.add(order);

        orderId = order.getId();

        // 添加订单商品表项
        for (Cart cartGoods : checkedGoodsList) {
            // 订单商品
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(orderId);
            orderGoods.setGoodsId(cartGoods.getGoodsId());
            orderGoods.setGoodsSn(cartGoods.getGoodsSn());
            orderGoods.setProductId(cartGoods.getProductId());
            orderGoods.setGoodsName(cartGoods.getGoodsName());
            orderGoods.setPicUrl(cartGoods.getPicUrl());
            orderGoods.setPrice(cartGoods.getPrice());
            orderGoods.setNumber(cartGoods.getNumber());
            orderGoods.setSpecifications(cartGoods.getSpecifications());
            orderGoods.setAddTime(new Date());
            // 订单商品需加上入驻店铺标志
            orderGoods.setBrandId(cartGoods.getBrandId());
            orderGoodsBusService.add(orderGoods);
        }

        // 删除购物车里面的商品信息
        cartBusService.clearGoods(userId);

        // 商品货品数量减少
        for (Cart checkGoods : checkedGoodsList) {
            Integer productId = checkGoods.getProductId();
            GoodsProduct product = goodsProductBusService.queryById(productId);

            int remainNumber = product.getNumber() - checkGoods.getNumber();
            if (remainNumber < 0) {
                throw new ShopException("下单的商品货品数量大于库存量!");
            }
            if (goodsProductBusService.reduceStock(productId, checkGoods.getGoodsId(), checkGoods.getNumber()) == 0) {
                throw new ShopException("商品货品库存减少失败!");
            }
        }
        submitResp.setOrderId(orderId);
        return ResponseUtil.ok(submitResp);
    }

    @Override
    @Transactional
    public Object doCancel(Integer userId, CancelOrderReq req) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = req.getOrderId();
        Order order = orderBusService.queryById(orderId);
        if (ObjectUtil.isEmpty(order)) {
            log.error("取消订单失败，订单:{}不存在!", orderId);
            return ResponseUtil.dataEmpty();
        }
        if (!order.getUserId().equals(userId)) {
            log.error("取消订单失败，用户id不一致, 订单userId={}, userId={}", order.getUserId(), userId);
            return ResponseUtil.serious();
        }
        // 检测是否能够取消
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isCancel()) {
            log.error("取消订单失败，该订单不允许取消!");
            return ResponseUtil.fail(ResponseCode.ORDER_INVALID_OPERATION);
        }
        // 设置订单已取消状态
        order.setOrderStatus(OrderUtil.STATUS_CANCEL);
        order.setEndTime(new Date());
        if (orderBusService.updateOrder(order) == 0) {
            throw new ShopException("取消订单失败，更新订单信息异常!");
        }

        // 商品货品数量增加
        List<OrderGoods> orderGoodsList = orderGoodsBusService.queryByOrderId(orderId);
        for (OrderGoods orderGoods : orderGoodsList) {
            Integer productId = orderGoods.getProductId();
            Short number = orderGoods.getNumber();
            if (goodsProductBusService.addStock(productId, number) == 0) {
                throw new ShopException("商品货品库存增加失败!");
            }
        }
        return ResponseUtil.ok();
    }


    private String detailedAddress(UserAddress userAddress) {
        Integer provinceId = userAddress.getProvinceId();
        Integer cityId = userAddress.getCityId();
        Integer areaId = userAddress.getAreaId();
        String provinceName = regionBusService.queryById(provinceId).getName();
        String cityName = regionBusService.queryById(cityId).getName();
        String areaName = regionBusService.queryById(areaId).getName();
        String fullRegion = provinceName + " " + cityName + " " + areaName;
        return fullRegion + " " + userAddress.getAddress();
    }

    @Override
    public Object doPrepay(Integer userId, OrderPrepayReq req, HttpServletRequest request) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = req.getOrderId();
        Order order = orderBusService.queryById(orderId);
        if (ObjectUtil.isEmpty(order)) {
            log.error("预支付失败，订单:{}不存在!", orderId);
            return ResponseUtil.dataEmpty();
        }
        if (!order.getUserId().equals(userId)) {
            log.error("预支付失败，用户id不一致, 订单userId={}, userId={}", order.getUserId(), userId);
            return ResponseUtil.serious();
        }
        // 检测是否能够支付
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isPay()) {
            log.error("预支付失败，该订单不允许支付!");
            return ResponseUtil.fail(ResponseCode.ORDER_REPAY_OPERATION);
        }
        User user = userBusService.queryById(userId);
        if (ObjectUtil.isEmpty(user)) {
            log.error("预支付失败，用户:{}信息为空!", userId);
            return ResponseUtil.dataEmpty();
        }
        String openId = user.getWeixinOpenid();
        if (StrUtil.isBlank(openId)) {
            log.error("预支付失败, 用户:{} openId为空!", userId);
            return ResponseUtil.dataEmpty();
        }

        //TODO 微信支付逻辑
        WxPayMpOrderResult result;
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(order.getOrderSn());
            orderRequest.setOpenid(openId);
            orderRequest.setBody(Constant.DEFAULT_ORDER_FIX + order.getOrderSn());
            // 元转成分
            BigDecimal actualPrice = order.getActualPrice();
            int fee = actualPrice.multiply(new BigDecimal(100)).intValue();
            orderRequest.setTotalFee(fee);
            orderRequest.setSpbillCreateIp(IpUtil.getIpAddr(request));

            result = wxPayService.createOrder(orderRequest);

            // 缓存prepayID用于后续模版通知
            String prepayId = result.getPackageValue();
            prepayId = prepayId.replace("prepay_id=", "");
            UserFormid userFormid = new UserFormid();
            userFormid.setOpenid(user.getWeixinOpenid());
            userFormid.setFormid(prepayId);
            userFormid.setIsprepay(true);
            userFormid.setUseamount(3);
            userFormid.setAddTime(new Date());
            userFormid.setExpireTime(DateUtil.offsetDay(new Date(), +7).toJdkDate());
            userFormidBusService.add(userFormid);
        } catch (Exception e) {
            log.error("预支付失败：{}", e.getMessage());
            e.printStackTrace();
            return ResponseUtil.fail(ResponseCode.ORDER_PAY_FAIL);
        }
        return ResponseUtil.ok(result);
    }

    @Override
    public Object doDelete(Integer userId, OrderDeleteReq req) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Integer orderId = req.getOrderId();
        Order order = orderBusService.queryById(orderId);
        if (ObjectUtil.isEmpty(order)) {
            log.error("删除订单失败，订单:{}不存在!", orderId);
            return ResponseUtil.dataEmpty();
        }
        if (!order.getUserId().equals(userId)) {
            log.error("删除订单失败，用户id不一致, 订单userId={}, userId={}", order.getUserId(), userId);
            return ResponseUtil.serious();
        }
        // 校验是否能够删除
        OrderHandleOption handleOption = OrderUtil.build(order);
        if (!handleOption.isDelete()) {
            log.error("删除订单失败，该订单不允许删除!");
            return ResponseUtil.fail(ResponseCode.ORDER_DEL_OPERATION);
        }
        orderBusService.deleteById(orderId);

        //删除订单商品
        orderGoodsBusService.deleteByOrderId(orderId);
        return ResponseUtil.ok();
    }

    @Override
    public Object doDetail(Integer userId, Integer orderId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Order order = orderBusService.queryById(orderId);
        if (ObjectUtil.isEmpty(order)) {
            log.error("查看订单详情失败，订单:{}不存在!", orderId);
            return ResponseUtil.dataEmpty();
        }
        if (!order.getUserId().equals(userId)) {
            log.error("查看订单详情失败，用户id不一致, 订单userId={}, userId={}", order.getUserId(), userId);
            return ResponseUtil.serious();
        }
        OrderVo orderVo = new OrderVo();
        orderVo.setId(order.getId());
        orderVo.setOrderSn(order.getOrderSn());
        orderVo.setAddTime(order.getAddTime());
        orderVo.setConsignee(order.getConsignee());
        orderVo.setMobile(order.getMobile());
        orderVo.setAddress(order.getAddress());
        orderVo.setGoodsPrice(order.getGoodsPrice());
        orderVo.setFreightPrice(order.getFreightPrice());
        orderVo.setDiscountPrice(order.getIntegralPrice().add(order.getGrouponPrice()).add(order.getCouponPrice()));
        orderVo.setActualPrice(order.getActualPrice());
        orderVo.setOrderStatusText(OrderUtil.orderStatusText(order));
        orderVo.setHandleOption(OrderUtil.build(order));
        orderVo.setExpCode(order.getShipChannel());
        orderVo.setExpNo(order.getShipSn());
        List<OrderGoods> orderGoodsList = orderGoodsBusService.queryByOrderId(orderId);
        QueryOrderDetailResp detailResp = new QueryOrderDetailResp();
        detailResp.setOrderInfo(orderVo);
        detailResp.setOrderGoods(orderGoodsList);
        return ResponseUtil.ok(detailResp);
    }
}
