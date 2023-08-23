package com.zqz.shop.controller;

import com.zqz.shop.annotation.LoginUser;
import com.zqz.shop.entity.UserAddress;
import com.zqz.shop.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AddressController
 * @Date: Created in 10:58 2023-8-23
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/list")
    public Object queryList(@LoginUser Integer userId) {
        return userAddressService.doQueryList(userId);
    }

    @PostMapping("/save")
    public Object addAddress(@LoginUser Integer userId, @RequestBody UserAddress address) {
        return userAddressService.doAddAddress(userId, address);
    }
}
