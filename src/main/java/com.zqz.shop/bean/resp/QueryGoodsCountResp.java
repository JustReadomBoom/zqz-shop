package com.zqz.shop.bean.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: QueryGoodsCountResp
 * @Date: Created in 9:56 2023-9-29
 */
@Data
public class QueryGoodsCountResp implements Serializable {
    private static final long serialVersionUID = -3008667142098217113L;

    private Object goodsCount;
}
