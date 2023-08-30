package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CategorySellAmts
 * @Date: Created in 20:19 2023-8-29
 */
@Data
public class CategorySellAmts implements Serializable {
    private static final long serialVersionUID = -2266067072423528052L;

    private String name;

    private BigDecimal value;
}
