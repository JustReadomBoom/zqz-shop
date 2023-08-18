package com.zqz.shop.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: UserToken
 * @Date: Created in 11:05 2023-8-11
 */
@Data
public class UserToken implements Serializable {
    private static final long serialVersionUID = 4586969455444950905L;

    private Integer userId;

    private String token;

    private String sessionKey;

    private Date expireTime;

    private Date updateTime;

}
