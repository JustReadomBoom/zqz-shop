package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description: 微信登录信息
 * @ClassName: WxLoginInfo
 * @Date: Created in 11:30 2023-8-11
 */
@Data
public class WxLoginInfo implements Serializable {
    private static final long serialVersionUID = -7594026848277312139L;

    private String code;

    private UserInfo userInfo;

    private Integer shareUserId;

}
