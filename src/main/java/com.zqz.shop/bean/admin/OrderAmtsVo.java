package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderAmtsVo
 * @Date: Created in 20:17 2023-8-29
 */
@Data
public class OrderAmtsVo implements Serializable {
    private static final long serialVersionUID = 7732263648613760985L;
    /**
     * 日期数据
     */
    private String[] dayData;

    /**
     * 日订单金额
     */
    private BigDecimal[] orderAmtData;

    /**
     * 日订单量
     */
    private int[] orderCntData;

}
