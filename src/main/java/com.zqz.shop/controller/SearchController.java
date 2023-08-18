package com.zqz.shop.controller;

import com.zqz.shop.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: SearchController
 * @Date: Created in 17:25 2023-8-18
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;


    @GetMapping("/index")
    public Object index(Integer userId){
        return searchService.doIndex(userId);
    }
}
