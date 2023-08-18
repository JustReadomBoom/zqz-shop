package com.zqz.shop.service.business;

import com.mybatisflex.core.query.QueryWrapper;
import com.zqz.shop.entity.Topic;
import com.zqz.shop.mapper.TopicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.zqz.shop.entity.table.TopicTableDef.TOPIC;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: TopicBusService
 * @Date: Created in 17:14 2023-8-17
 */
@Service
public class TopicBusService {

    @Resource
    private TopicMapper topicMapper;


    public List<Topic> queryListPage(Integer pageNumber, Integer pageSize) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select()
                .where(TOPIC.DELETED.eq(false))
                .orderBy(TOPIC.ADD_TIME.desc());
        return topicMapper.paginateWithRelations(pageNumber, pageSize, wrapper).getRecords();
    }
}
