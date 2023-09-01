package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.service.AdminAdminService;
import com.zqz.shop.service.business.AdminBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminAdminServiceImpl
 * @Date: Created in 14:21 2023-8-31
 */
@Service
@Slf4j
public class AdminAdminServiceImpl implements AdminAdminService {
    @Autowired
    private AdminBusService adminBusService;

    @Override
    public Object doQueryList(Integer userId, Integer page, Integer limit, String username) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Page<Admin> adminPage = adminBusService.queryPage(page, limit, username);
        Map<String, Object> data = new HashMap<>(2);
        data.put("total", adminPage.getTotalRow());
        data.put("items", adminPage.getRecords());
        return ResponseUtil.ok(data);
    }
}
