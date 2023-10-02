package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserOrderInfo
 * @Date: Created in 15:13 2023-10-2
 */
@Data
public class UserOrderInfo implements Serializable {
    private static final long serialVersionUID = 2419425630599460204L;

    private int unpaid;

    private int unship;

    private int unrecv;

    private int uncomment;
}
