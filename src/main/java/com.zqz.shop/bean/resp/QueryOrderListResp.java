package com.zqz.shop.bean.resp;

import com.zqz.shop.bean.OrderVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: QueryOrderListResp
 * @Date: Created in 14:27 2023-10-2
 */
@Data
public class QueryOrderListResp implements Serializable {
    private static final long serialVersionUID = 53716035405399867L;

    private long count;

    private List<OrderVo> data;

    private long totalPages;
}
