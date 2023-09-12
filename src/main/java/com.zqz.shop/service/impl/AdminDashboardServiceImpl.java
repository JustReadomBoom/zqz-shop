package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.zqz.shop.bean.admin.*;
import com.zqz.shop.bean.admin.resp.DashboardCharResp;
import com.zqz.shop.bean.admin.resp.DashboardInfoResp;
import com.zqz.shop.service.AdminDashboardService;
import com.zqz.shop.service.business.GoodsBusService;
import com.zqz.shop.service.business.GoodsProductBusService;
import com.zqz.shop.service.business.OrderBusService;
import com.zqz.shop.service.business.UserBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminDashboardServiceImpl
 * @Date: Created in 20:01 2023-8-29
 */
@Service
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {
    @Autowired
    private UserBusService userBusService;
    @Autowired
    private OrderBusService orderBusService;
    @Autowired
    private GoodsBusService goodsBusService;
    @Autowired
    private GoodsProductBusService goodsProductBusService;


    @Override
    public Object doChar(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        DashboardCharResp charResp = new DashboardCharResp();

        //近期用户，订单增长量查询
        UserOrderCntVo cntVo = new UserOrderCntVo();
        List<DayStatis> userCntList = userBusService.queryRecentCount(30);

        List<DayStatis> orderCntList = orderBusService.queryRecentCount(30);

        String[] dayData = unionDayData(userCntList, orderCntList);
        cntVo.setDayData(dayData);
        cntVo.setUserCnt(fetchArrCnt(dayData, userCntList));
        cntVo.setOrderCnt(fetchArrCnt(dayData, orderCntList));

        //订单请款统计，订单笔数与订单金额
        OrderAmtsVo orderAmts = fetchOrderAmtsVo(orderCntList);

        //大类销售统计情况
        List<CategorySellAmts> categorySellStatis = orderBusService.categorySell();

        CategorySellVo categorySell = fetchCategorySell(categorySellStatis);

        charResp.setUserOrderCnt(cntVo);
        charResp.setOrderAmts(orderAmts);
        charResp.setCategorySell(categorySell);
        return ResponseUtil.ok(charResp);
    }

    @Override
    public Object doInfo(Integer userId) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        DashboardInfoResp infoResp = new DashboardInfoResp();

        Integer userCount = userBusService.queryCount();
        Integer goodsCount = goodsBusService.queryCount();
        Integer productCount = goodsProductBusService.queryCount();
        Integer orderCount = orderBusService.queryCount();

        infoResp.setUserTotal(userCount);
        infoResp.setGoodsTotal(goodsCount);
        infoResp.setProductTotal(productCount);
        infoResp.setOrderTotal(orderCount);
        return ResponseUtil.ok(infoResp);
    }


    /**
     * 获取日期数据并排序
     *
     * @param userCntList
     * @param orderCntList
     * @return
     */
    private String[] unionDayData(List<DayStatis> userCntList, List<DayStatis> orderCntList) {
        Set<String> days = new HashSet<>();
        for (DayStatis userCnt : userCntList) {
            days.add(userCnt.getDayStr());
        }
        for (DayStatis orderCnt : orderCntList) {
            days.add(orderCnt.getDayStr());
        }
        List<String> list = new ArrayList<>(days);
        Collections.sort(list);
        return list.toArray(new String[0]);
    }


    /**
     * 从统计集合中获取数量 不存在则设置 0
     *
     * @param dayData
     * @param dayStatisCnts
     * @return
     */
    private int[] fetchArrCnt(String[] dayData, List<DayStatis> dayStatisCnts) {
        int[] arrCnts = new int[dayData.length];
        for (int i = 0; i < dayData.length; i++) {
            int dayCnt = 0;
            String dayStr = dayData[i];
            for (DayStatis ds : dayStatisCnts) {
                if (dayStr.equals(ds.getDayStr())) {
                    dayCnt = ds.getCnts();
                    break;
                }
            }
            arrCnts[i] = dayCnt;
        }
        return arrCnts;
    }

    /**
     * 获取订单统计数据
     *
     * @param orderCntList
     * @return
     */
    private OrderAmtsVo fetchOrderAmtsVo(List<DayStatis> orderCntList) {
        OrderAmtsVo orderAmts = new OrderAmtsVo();
        int size = 0;
        if (orderCntList != null && orderCntList.size() > 0) {
            size = orderCntList.size();
        }
        String[] dayData = new String[size];
        int[] orderCntData = new int[size];
        BigDecimal[] orderAmtData = new BigDecimal[size];
        for (int i = 0; i < size; i++) {
            dayData[i] = orderCntList.get(i).getDayStr();
            orderCntData[i] = orderCntList.get(i).getCnts();
            orderAmtData[i] = orderCntList.get(i).getAmts();
        }
        orderAmts.setDayData(dayData);
        orderAmts.setOrderAmtData(orderAmtData);
        orderAmts.setOrderCntData(orderCntData);
        return orderAmts;
    }

    /**
     * 获取大类的销售统计数据
     *
     * @param categorySellData
     * @return
     */
    private CategorySellVo fetchCategorySell(List<CategorySellAmts> categorySellData) {
        CategorySellVo categorySell = new CategorySellVo();
        int size = 0;
        if (categorySellData != null && categorySellData.size() > 0) {
            size = categorySellData.size();
        }
        String[] categoryNames = new String[size];
        for (int i = 0; i < size; i++) {
            categoryNames[i] = categorySellData.get(i).getName();
        }
        categorySell.setCategoryNames(categoryNames);
        categorySell.setCategorySellData(categorySellData);
        return categorySell;
    }
}
