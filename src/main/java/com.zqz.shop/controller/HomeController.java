package com.zqz.shop.controller;

import com.zqz.shop.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description: 主页入口
 * @ClassName: HomeController
 * @Date: Created in 15:31 2023-8-4
 */
@RestController
@Slf4j
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/index")
    public Object index(@RequestParam Integer userId) {
        return homeService.doQueryIndex(userId);
    }



}
