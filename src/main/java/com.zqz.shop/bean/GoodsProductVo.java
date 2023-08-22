package com.zqz.shop.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: GoodsProductVo
 * @Date: Created in 14:12 2023-8-21
 */
@Data
public class GoodsProductVo {

    private Integer id;

    private Integer goodsId;

    private List<String> specifications;

    private BigDecimal price;

    private Integer number;

    private String url;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}
