package com.zqz.shop.service.business;

import com.zqz.shop.entity.System;
import com.zqz.shop.mapper.SystemConfigMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SystemConfigBusService
 * @Date: Created in 15:58 2023-8-21
 */
@Service
public class SystemConfigBusService {

    @Resource
    private SystemConfigMapper configMapper;

    public List<System> queryAll() {
        return configMapper.selectAll();
    }
}
