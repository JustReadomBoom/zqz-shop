package com.zqz.shop.bean.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: OrderSubmitResp
 * @Date: Created in 14:31 2023-10-2
 */
@Data
public class OrderSubmitResp implements Serializable {

    private static final long serialVersionUID = 7610471791780671663L;

    private Integer orderId;
}
