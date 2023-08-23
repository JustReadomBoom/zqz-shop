package com.zqz.shop.controller;

import com.zqz.shop.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: RegionController
 * @Date: Created in 11:50 2023-8-23
 */
@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;


    @GetMapping("/list")
    public Object queryList(@RequestParam("pid") Integer pid) {
        return regionService.doQueryList(pid);
    }
}
