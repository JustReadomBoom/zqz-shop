package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserInfo
 * @Date: Created in 10:51 2023-8-11
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -5055425168325744718L;

    private Integer userId;

    private String nickName;

    private String avatarUrl;

    private String country;

    private String province;

    private String city;

    private String language;

    private Byte gender;

    private String phone;
    /**
     * 用户层级 0 普通用户，1 VIP用户，2 区域代理用户
     */
    private Byte userLevel;

    /**
     * 代理用户描述
     */
    private String userLevelDesc;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 注册日期
     */
    private String registerDate;
}
