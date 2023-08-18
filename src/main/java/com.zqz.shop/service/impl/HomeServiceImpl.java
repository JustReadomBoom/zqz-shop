package com.zqz.shop.service.impl;

import com.zqz.shop.entity.Ad;
import com.zqz.shop.entity.Category;
import com.zqz.shop.entity.Topic;
import com.zqz.shop.service.HomeService;
import com.zqz.shop.service.business.AdBusService;
import com.zqz.shop.service.business.CategoryBusService;
import com.zqz.shop.service.business.TopicBusService;
import com.zqz.shop.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            Map<String, Object> data = new HashMap<>(3);
            List<Ad> ads = adBusService.queryAdList();
            List<Category> categories = categoryBusService.queryListPage(1, 9);
            List<Topic> topics = topicBusService.queryListPage(1, 9);
            data.put("banner", ads);
            data.put("channel", categories);
            data.put("topicList", topics);
            return ResponseUtil.ok(data);
        } catch (Exception e) {
            log.error("doQueryIndex error:{}", e.getMessage());
            return "fail";
        }
    }


}
