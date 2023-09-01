package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.Region;
import com.zqz.shop.service.AdminRegionService;
import com.zqz.shop.service.business.RegionBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminRegionServiceImpl
 * @Date: Created in 9:02 2023-9-1
 */
@Service
@Slf4j
public class AdminRegionServiceImpl implements AdminRegionService {
    @Autowired
    private RegionBusService regionBusService;


    @Override
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String name, String code) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Page<Region> regionPage = regionBusService.queryPage(page, limit, name, code);
        Map<String, Object> data = new HashMap<>(2);
        data.put("total", regionPage.getTotalRow());
        data.put("items", regionPage.getRecords());
        return ResponseUtil.ok(data);
    }
}
