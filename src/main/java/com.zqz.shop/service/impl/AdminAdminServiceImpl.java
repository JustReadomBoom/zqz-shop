package com.zqz.shop.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.paginate.Page;
import com.zqz.shop.bean.admin.req.AdminUpdateReq;
import com.zqz.shop.bean.admin.resp.PageQueryResp;
import com.zqz.shop.entity.Admin;
import com.zqz.shop.enums.AdminResponseCode;
import com.zqz.shop.service.AdminAdminService;
import com.zqz.shop.service.business.AdminBusService;
import com.zqz.shop.utils.ResponseUtil;
import com.zqz.shop.utils.bcrypt.BCryptPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public Object doQueryList(Integer adminUserId, Integer page, Integer limit, String username) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Page<Admin> adminPage = adminBusService.queryPage(page, limit, username);
        PageQueryResp<Admin> queryResp = new PageQueryResp<>();
        queryResp.setTotal(adminPage.getTotalRow());
        queryResp.setItems(adminPage.getRecords());
        return ResponseUtil.ok(queryResp);
    }

    @Override
    public Object doDeleteInfo(Integer adminUserId, Admin admin) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        Integer id = admin.getId();
        if (ObjectUtil.isEmpty(id)) {
            return ResponseUtil.badArgument();
        }
        int delete = adminBusService.logicalDeleteById(id);
        if (delete <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }

    @Override
    public Object doUpdateInfo(Integer adminUserId, AdminUpdateReq admin) {
        if (ObjectUtil.isEmpty(adminUserId)) {
            return ResponseUtil.unlogin();
        }
        String username = admin.getUsername();
        if (StrUtil.isBlank(username)) {
            return ResponseUtil.badArgument();
        }
        String password = admin.getPassword();
        if (StrUtil.isBlank(password) || password.length() < 6) {
            return ResponseUtil.fail(AdminResponseCode.ADMIN_INVALID_PASSWORD);
        }

        Admin newAdmin = new Admin();
        BeanUtil.copyProperties(admin, newAdmin);
        newAdmin.setRoleIds(JSONUtil.toJsonStr(admin.getRoleIds()));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        newAdmin.setPassword(encodedPassword);

        int update = adminBusService.updateById(newAdmin);
        if (update <= 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
}
