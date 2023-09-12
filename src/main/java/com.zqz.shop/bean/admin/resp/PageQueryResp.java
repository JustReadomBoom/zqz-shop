package com.zqz.shop.bean.admin.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description: 分页查询返回
 * @ClassName: PageQueryResp
 * @Date: Created in 16:27 2023-9-11
 */
@Data
public class PageQueryResp<T> implements Serializable {
    private static final long serialVersionUID = 3486605694105546206L;

    private long total;

    private List<T> items;
}
