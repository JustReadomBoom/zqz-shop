package com.zqz.shop.bean.resp;

import com.zqz.shop.bean.UserOrderInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserOrderIndexResp
 * @Date: Created in 15:16 2023-10-2
 */
@Data
public class UserOrderIndexResp implements Serializable {
    private static final long serialVersionUID = 6724100824611332041L;

    private UserOrderInfo order;
}
