package com.zqz.shop.service.impl;

import com.zqz.shop.bean.UserOrderInfo;
import com.zqz.shop.bean.resp.UserOrderIndexResp;
import com.zqz.shop.service.UserService;
import com.zqz.shop.service.business.OrderBusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserServiceImpl
 * @Date: Created in 16:25 2023-8-15
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private OrderBusService orderBusService;


    @Override
    public Object doIndex(Integer userId) {
        UserOrderIndexResp indexResp = new UserOrderIndexResp();
        UserOrderInfo userOrderInfo = orderBusService.getOrderInfoByUserId(userId);
        indexResp.setOrder(userOrderInfo);
        return indexResp;
    }
}
