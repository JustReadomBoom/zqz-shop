package com.zqz.shop.bean.resp;

import com.zqz.shop.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsQueryByCategoryIdResp
 * @Date: Created in 9:57 2023-9-15
 */
@Data
public class GoodsQueryByCategoryIdResp implements Serializable {
    private static final long serialVersionUID = 2165573137646992302L;

    private Category currentCategory;

    private Category parentCategory;

    private List<Category> brotherCategory;


}
