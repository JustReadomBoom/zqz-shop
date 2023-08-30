package com.zqz.shop.bean.admin;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: DayStatis
 * @Date: Created in 20:07 2023-8-29
 */
@Data
public class DayStatis implements Serializable {
    private static final long serialVersionUID = 2657137646538105015L;

    private String dayStr;

    private int cnts;

    private BigDecimal amts;
}
