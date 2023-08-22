package com.zqz.shop.service.business;

import com.zqz.shop.entity.Brand;
import com.zqz.shop.mapper.BrandMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: BrandBusService
 * @Date: Created in 16:08 2023-8-21
 */
@Service
public class BrandBusService {
    @Resource
    private BrandMapper brandMapper;

    public Brand queryById(Integer id) {
        return brandMapper.selectOneById(id);
    }
}
