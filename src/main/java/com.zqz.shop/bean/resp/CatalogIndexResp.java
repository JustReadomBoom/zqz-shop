package com.zqz.shop.bean.resp;

import com.zqz.shop.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CatalogIndexResp
 * @Date: Created in 16:13 2023-10-7
 */
@Data
public class CatalogIndexResp implements Serializable {

    private static final long serialVersionUID = -8777170192378667567L;

    private List<Category> categoryList;

    private Category currentCategory;

    private List<Category> currentSubCategory;
}
