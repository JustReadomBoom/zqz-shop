package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminKeywordController
 * @Date: Created in 11:03 2023-9-1
 */
@RestController
@RequestMapping("/admin/keyword")
public class AdminKeywordController {
    @Autowired
    private AdminKeywordService adminKeywordService;

    @GetMapping("/list")
    public Object queryList(@AdminLoginUser Integer adminUserId,
                            @RequestParam(defaultValue = "1") Integer page,
                            @RequestParam(defaultValue = "10") Integer limit,
                            String keyword,
                            String url) {
        return adminKeywordService.doQueryList(adminUserId, page, limit, keyword, url);
    }
}
