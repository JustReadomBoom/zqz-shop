package com.zqz.shop.bean.resp;

import com.zqz.shop.entity.Category;
import com.zqz.shop.entity.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsQueryListPageResp
 * @Date: Created in 10:00 2023-9-15
 */
@Data
public class GoodsQueryListPageResp implements Serializable {
    private static final long serialVersionUID = -8955260342657644504L;

    private List<Goods> goodsList;

    private long count;

    private List<Category> filterCategoryList;

    private long totalPages;
}
