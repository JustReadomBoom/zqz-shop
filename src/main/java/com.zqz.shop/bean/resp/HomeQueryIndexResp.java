package com.zqz.shop.bean.resp;

import com.zqz.shop.entity.Ad;
import com.zqz.shop.entity.Category;
import com.zqz.shop.entity.Topic;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: HomeQueryIndexResp
 * @Date: Created in 14:16 2023-10-2
 */
@Data
public class HomeQueryIndexResp implements Serializable {
    private static final long serialVersionUID = -6260115188397095122L;

    private List<Ad> banner;

    private List<Category> channel;

    private List<Topic> topicList;
}
