package com.zqz.shop.bean.resp;

import com.zqz.shop.entity.Category;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CatalogCurrentResp
 * @Date: Created in 16:15 2023-10-7
 */
@Data
public class CatalogCurrentResp implements Serializable {
    private static final long serialVersionUID = -3325422597128330539L;

    private Category currentCategory;

    private List<Category> currentSubCategory;
}
