package com.zqz.shop.controller.admin;

import com.zqz.shop.annotation.AdminLoginUser;
import com.zqz.shop.service.AdminStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminStorageController
 * @Date: Created in 16:03 2023-9-4
 */
@RestController
@RequestMapping("/admin/storage")
public class AdminStorageController {
    @Autowired
    private AdminStorageService adminStorageService;


    @PostMapping("/create")
    public Object createStorage(@AdminLoginUser Integer adminUserId, @RequestParam("file") MultipartFile file){
        return adminStorageService.doCreateStorage(adminUserId, file);
    }
}
