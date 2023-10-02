package com.zqz.shop.bean.resp;

import com.zqz.shop.bean.OrderVo;
import com.zqz.shop.entity.OrderGoods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: QueryOrderDetailResp
 * @Date: Created in 14:37 2023-10-2
 */
@Data
public class QueryOrderDetailResp implements Serializable {
    private static final long serialVersionUID = -6785605060634150457L;

    private OrderVo orderInfo;

    private List<OrderGoods> orderGoods;
}
