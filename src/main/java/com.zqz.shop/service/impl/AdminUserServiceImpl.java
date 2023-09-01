package com.zqz.shop.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.User;
import com.zqz.shop.service.AdminUserService;
import com.zqz.shop.service.business.UserBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminUserServiceImpl
 * @Date: Created in 14:24 2023-8-30
 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private UserBusService userBusService;

    @Override
    public Object doQueryList(Integer userId, Integer page, Integer limit, String username, String mobile) {
        if (ObjectUtil.isEmpty(userId)) {
            return ResponseUtil.unlogin();
        }
        Map<String, Object> data = new HashMap<>(2);
        Page<User> userPage = userBusService.queryPage(username, mobile, page, limit);
        data.put("total", userPage.getTotalRow());
        data.put("items", userPage.getRecords());
        return ResponseUtil.ok(data);
    }
}
