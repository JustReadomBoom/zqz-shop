package com.zqz.shop.service.business;

import com.zqz.shop.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserBusService
 * @Date: Created in 16:34 2023-8-15
 */
@Service
public class UserBusService {
    @Resource
    private UserMapper userMapper;



}
