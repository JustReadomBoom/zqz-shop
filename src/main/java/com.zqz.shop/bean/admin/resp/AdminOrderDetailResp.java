package com.zqz.shop.bean.admin.resp;

import com.zqz.shop.bean.admin.UserVo;
import com.zqz.shop.entity.Order;
import com.zqz.shop.entity.OrderGoods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AdminOrderDetailResp
 * @Date: Created in 14:13 2023-10-2
 */
@Data
public class AdminOrderDetailResp implements Serializable {

    private static final long serialVersionUID = -5900863055118302465L;

    private Order order;

    private List<OrderGoods> orderGoods;

    private UserVo user;
}
