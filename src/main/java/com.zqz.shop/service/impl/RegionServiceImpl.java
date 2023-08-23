package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.zqz.shop.entity.Region;
import com.zqz.shop.service.RegionService;
import com.zqz.shop.service.business.RegionBusService;
import com.zqz.shop.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: RegionServiceImpl
 * @Date: Created in 11:54 2023-8-23
 */
@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    private RegionBusService regionBusService;

    @Override
    public Object doQueryList(Integer pid) {
        List<Region> regionList = regionBusService.queryByPid(pid);
        if (CollectionUtil.isEmpty(regionList)) {
            return ResponseUtil.ok();
        }
        return ResponseUtil.ok(regionList);
    }
}
