package com.zqz.shop.controller;

import com.zqz.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CatalogController
 * @Date: Created in 16:50 2023-8-18
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;


    @GetMapping("/index")
    public Object index(Integer id) {
        return catalogService.doIndex(id);
    }

    @GetMapping("/current")
    public Object current(@RequestParam("id") Integer id) {
        return catalogService.doCurrent(id);
    }
}
