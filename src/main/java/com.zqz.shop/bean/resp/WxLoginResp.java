package com.zqz.shop.bean.resp;

import com.zqz.shop.bean.UserInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: WxLoginResp
 * @Date: Created in 9:33 2023-9-12
 */
@Data
public class WxLoginResp implements Serializable {
    private static final long serialVersionUID = -7251488313568894593L;

    private String token;

    private String tokenExpire;

    private UserInfo userInfo;
}
