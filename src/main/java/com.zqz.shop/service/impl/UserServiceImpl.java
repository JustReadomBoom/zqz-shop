package com.zqz.shop.service.impl;

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
        Map<String, Object> data = new HashMap<>(1);
        Map<String, Object> orderInfoMap = orderBusService.getOrderInfoByUserId(userId);
        data.put("order", orderInfoMap);
        return data;
    }
}
