package com.zqz.shop.controller;

import com.zqz.shop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsController
 * @Date: Created in 15:32 2023-8-16
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


    @GetMapping("/count")
    public Object queryCount() {
        return goodsService.doQueryCount();
    }


    @GetMapping("/category")
    public Object queryByCategoryId(@RequestParam("id") Integer id) {
        return goodsService.doQueryByCategoryId(id);
    }


    @GetMapping("/list")
    public Object queryListPage(@RequestParam("categoryId") Integer categoryId,
                                String keyword,
                                Boolean isNew,
                                Boolean isHot,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer size) {
        return goodsService.doQueryListPage(categoryId, keyword, isNew, isHot, page, size);
    }


    @GetMapping("/detail")
    public Object queryDetail(@RequestParam("id") Integer id) {
        return goodsService.doQueryDetail(id);
    }

}
