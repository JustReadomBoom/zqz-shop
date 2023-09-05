package com.zqz.shop.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.entity.Role;
import com.zqz.shop.enums.AdminResponseCode;
import com.zqz.shop.service.AdminRoleService;
import com.zqz.shop.service.business.RoleBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminRoleServiceImpl
 * @Date: Created in 14:40 2023-8-31
 */
@Service
@Slf4j
public class AdminRoleServiceImpl implements AdminRoleService {
    @Autowired
    private RoleBusService roleBusService;

    @Override
    public Object doOptions(Integer adminUserId) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        List<Role> roleList = roleBusService.queryAll();
        if (CollectionUtil.isEmpty(roleList)) {
            return ResponseUtil.dataEmpty();
        }
        List<Map<String, Object>> options = new ArrayList<>(roleList.size());
        for (Role role : roleList) {
            Map<String, Object> option = new HashMap<>(2);
            option.put("value", role.getId());
            option.put("label", role.getName());
            options.add(option);
        }
        return ResponseUtil.ok(options);
    }

    @Override
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String roleName) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Page<Role> rolePage = roleBusService.queryPage(page, limit, roleName);
        Map<String, Object> data = new HashMap<>(2);
        data.put("total", rolePage.getTotalRow());
        data.put("items", rolePage.getRecords());
        return ResponseUtil.ok(data);
    }

    @Override
    public Object doCreateInfo(Integer adminUserId, Role role) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        String name = role.getName();
        if (StrUtil.isBlank(name)) {
            return ResponseUtil.badArgument();
        }
        if (roleBusService.checkExist(name)) {
            return ResponseUtil.fail(AdminResponseCode.ROLE_NAME_EXIST);
        }
        int add = roleBusService.add(role);
        if (add <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doDelete(Integer adminUserId, Role role) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Integer id = role.getId();
        if (ObjectUtil.isEmpty(id)) {
            return ResponseUtil.badArgument();
        }
        int delete = roleBusService.logicalDeleteById(id);
        if (delete <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doUpdateInfo(Integer adminUserId, Role role) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Integer id = role.getId();
        if (ObjectUtil.isEmpty(id)) {
            return ResponseUtil.badArgument();
        }
        int update = roleBusService.updateById(role);
        if (update <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
}
