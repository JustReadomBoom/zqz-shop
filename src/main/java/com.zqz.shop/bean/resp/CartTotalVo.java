package com.zqz.shop.bean.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: CartTotalVo
 * @Date: Created in 9:37 2023-9-15
 */
@Data
public class CartTotalVo implements Serializable {
    private static final long serialVersionUID = -5433401066345852785L;

    private Integer goodsCount;

    private BigDecimal goodsAmount;

    private Integer checkedGoodsCount;

    private BigDecimal checkedGoodsAmount;

}
