package com.zqz.shop.controller;

import com.zqz.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserController
 * @Date: Created in 16:24 2023-8-15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/index")
    public Object index(Integer userId) {
        return userService.doIndex(userId);
    }
}
