package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CategorySellVo
 * @Date: Created in 20:20 2023-8-29
 */
@Data
public class CategorySellVo implements Serializable {

    private static final long serialVersionUID = 7394233292494895976L;

    /**
     * 一级大类目录名称
     */
    private String[] categoryNames;

    /**
     * 大类销售金额集合
     */
    private List<CategorySellAmts> categorySellData;
}
