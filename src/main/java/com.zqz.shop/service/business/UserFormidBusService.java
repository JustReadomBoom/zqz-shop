package com.zqz.shop.service.business;

import com.zqz.shop.entity.UserFormid;
import com.zqz.shop.mapper.UserFormidMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserFormidBusService
 * @Date: Created in 16:44 2023-8-23
 */
@Service
public class UserFormidBusService {
    @Resource
    private UserFormidMapper userFormidMapper;


    public int add(UserFormid userFormid) {
        return userFormidMapper.insertSelective(userFormid);
    }
}
