package com.zqz.shop.service.impl;

import com.zqz.shop.bean.resp.HomeQueryIndexResp;
import com.zqz.shop.entity.Ad;
import com.zqz.shop.entity.Category;
import com.zqz.shop.entity.Topic;
import com.zqz.shop.exception.ShopException;
import com.zqz.shop.service.HomeService;
import com.zqz.shop.service.business.AdBusService;
import com.zqz.shop.service.business.CategoryBusService;
import com.zqz.shop.service.business.TopicBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: HomeServiceImpl
 * @Date: Created in 15:57 2023-8-4
 */
@Service
@Slf4j
public class HomeServiceImpl implements HomeService {
    @Autowired
    private AdBusService adBusService;
    @Autowired
    private CategoryBusService categoryBusService;
    @Autowired
    private TopicBusService topicBusService;


    @Override
    public Object doQueryIndex(Integer userId) {
        try {
            List<Ad> ads = adBusService.queryAdList();
            List<Category> categories = categoryBusService.queryListPage(1, 9);
            List<Topic> topics = topicBusService.queryListPage(1, 9);
            HomeQueryIndexResp indexResp = new HomeQueryIndexResp();
            indexResp.setBanner(ads);
            indexResp.setChannel(categories);
            indexResp.setTopicList(topics);
            return ResponseUtil.ok(indexResp);
        } catch (Exception e) {
            throw new ShopException(String.format("查询主页信息异常:%s", e.getMessage()));
        }
    }


}
