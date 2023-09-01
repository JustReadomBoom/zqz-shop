package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminOrderController
 * @Date: Created in 17:19 2023-8-31
 */
@RestController
@RequestMapping("/admin/order")
public class AdminOrderController {
    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/list")
    public Object queryList(Integer userId,
                            @AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            @RequestParam(required = false) List<Short> orderStatusArray,
                            String orderSn) {
        return adminOrderService.doQueryList(userId, adminUserId, page, limit, orderStatusArray, orderSn);
    }

    @GetMapping("/detail")
    public Object detail(@AdminLoginUser Integer adminUserId, @RequestParam Integer id) {
        return adminOrderService.doQueryDetail(adminUserId, id);
    }


    @GetMapping("/listShipChannel")
    public Object listShipChannel(@AdminLoginUser Integer adminUserId) {
        return adminOrderService.doListShipChannel(adminUserId);
    }
}
