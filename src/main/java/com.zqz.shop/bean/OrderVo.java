package com.zqz.shop.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zqz.shop.entity.OrderGoods;
import com.zqz.shop.utils.OrderHandleOption;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderVo
 * @Date: Created in 14:19 2023-10-2
 */
@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 8958492051051511665L;

    private Integer id;

    private String orderSn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addTime;

    private String consignee;

    private String mobile;

    private String address;

    private BigDecimal goodsPrice;

    private BigDecimal freightPrice;

    private BigDecimal actualPrice;

    private String orderStatusText;

    private OrderHandleOption handleOption;

    private String expCode;

    private String expNo;

    private List<OrderGoods> goodsList;

    private BigDecimal discountPrice;
}
