package com.zqz.shop.controller;

import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Ad;
import com.zqz.shop.mapper.AdMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.zqz.shop.entity.table.AdTableDef.AD;


/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: TestController
 * @Date: Created in 10:43 2023-8-3
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private AdMapper adMapper;


    @GetMapping("/1")
    public Object test1() {
        QueryWrapper queryWrapper = QueryWrapper.create();
        queryWrapper.select().where(AD.ENABLED.eq(1));
        List<Ad> ads = adMapper.selectListByQuery(queryWrapper);
        log.info("all ad = {}", JSONUtil.toJsonStr(ads));
        return JSONUtil.toJsonStr(ads);
    }


}
